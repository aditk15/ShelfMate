package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    public static final String PREFS_NAME = "UserPrefs";
    public static final String KEY_USER_EMAIL = "user_email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check if user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String userEmail = sharedPreferences.getString(KEY_USER_EMAIL, null);

        if (userEmail != null) {
            // User is already logged in, navigate to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close LoginActivity
        }

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Initialize views
        EditText name = findViewById(R.id.name);
        EditText password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.b2);
        TextView signupButton = findViewById(R.id.signin_button);

        // Set login button click listener
        loginButton.setOnClickListener(view -> {
            loginViewModel.name.setValue(name.getText().toString());
            loginViewModel.password.setValue(password.getText().toString());
            loginViewModel.loginUser();
        });

        // Observe login success to navigate to MainActivity
        loginViewModel.isLoginSuccessful.observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                // Save user email in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_USER_EMAIL, name.getText().toString().replace(".", ",")); // Replace dots with commas
                editor.apply();

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close LoginActivity
            }
        });

        // Redirect to sign-up activity on sign-up text click
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}