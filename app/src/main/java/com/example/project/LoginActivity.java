package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Initialize views
        EditText name = findViewById(R.id.name);
        EditText password = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.b2);
        TextView signupButton = findViewById(R.id.signin_button);

        // Bind ViewModel data with EditText
        loginViewModel.name.observe(this, name::setText);
        loginViewModel.password.observe(this, password::setText);

        // Set login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.name.setValue(name.getText().toString());
                loginViewModel.password.setValue(password.getText().toString());
                loginViewModel.loginUser();
            }
        });

        // Observe login success to navigate to MainActivity
        loginViewModel.isLoginSuccessful.observe(this, success -> {
            if (success != null && success) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Redirect to sign-up activity on sign-up text click
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
