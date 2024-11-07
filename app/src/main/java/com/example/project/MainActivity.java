package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.action_search_books) {
                openSearchActivity();
                return true;
            } else if (item.getItemId() == R.id.action_track_books) {
                openTrackBookActivity();
                return true;
            } else if (item.getItemId() == R.id.action_saved_books) {
                openSavedBooksActivity();
                return true;
            }
            return false;
        });

        mainViewModel.getNavigateToRegister().observe(this, shouldNavigate -> {
            if (shouldNavigate != null && shouldNavigate) {
                openRegisterActivity();
                mainViewModel.resetNavigation();
            }
        });

        mainViewModel.getNavigateToLogin().observe(this, shouldNavigate -> {
            if (shouldNavigate != null && shouldNavigate) {
                openLoginActivity();
                mainViewModel.resetNavigation();
            }
        });

        ImageButton logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> logoutUser());
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(LoginActivity.KEY_USER_EMAIL); // Remove saved email
        editor.apply();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close MainActivity
    }

    private void openSearchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
        startActivity(intent);
    }

    private void openTrackBookActivity() {
        Intent intent = new Intent(MainActivity.this, TrackBookActivity.class);
        startActivity(intent);
    }

    private void openSavedBooksActivity() {
        Intent intent = new Intent(MainActivity.this, SavedBooksActivity.class);
        startActivity(intent);
    }

    private void openRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void openLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}