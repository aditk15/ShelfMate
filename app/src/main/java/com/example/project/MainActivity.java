package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up button to open SearchResultsActivity
        Button openSearchButton = findViewById(R.id.openSearchButton);
        openSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                startActivity(intent);
            }
        });

        // Initialize ViewModel for registration and login functionality
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

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

        // Set up button for Register
        Button register = findViewById(R.id.buttonreg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.onRegisterClicked();
            }
        });

        // Set up button for Login
        Button login = findViewById(R.id.buttonsignin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.onLoginClicked();
            }
        });
    }

    // Method to open the Register activity
    private void openRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    // Method to open the Login activity
    private void openLoginActivity() {
        Intent intent = new Intent(MainActivity.this, login.class);
        startActivity(intent);
    }
}
