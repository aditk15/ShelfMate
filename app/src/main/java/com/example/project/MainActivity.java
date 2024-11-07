package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel for registration and login functionality
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Set up BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Use OnItemSelectedListener with if-else statements
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_search_books) {
                    openSearchActivity();
                    return true;
                } else if (item.getItemId() == R.id.action_track_books) {
                    openTrackBookActivity();
                    return true;
                }
                return false;
            }
        });

        // Observe navigation events for Register and Login buttons
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
    }

    private void openSearchActivity() {
        Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
        startActivity(intent);
    }

    private void openTrackBookActivity() {
        Intent intent = new Intent(MainActivity.this, TrackBookActivity.class);
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