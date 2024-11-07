package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class SignUpActivity extends AppCompatActivity {

    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initializing ViewModel
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        // Initializing views
        EditText s_name = findViewById(R.id.s_name);
        EditText s_email = findViewById(R.id.s_email);
        EditText s_password = findViewById(R.id.s_password);
        Button signUpButton = findViewById(R.id.b1);
        TextView loginButton = findViewById(R.id.login_button);

        // Bind ViewModel data with EditText
        signUpViewModel.name.observe(this, s_name::setText);
        signUpViewModel.email.observe(this, s_email::setText);
        signUpViewModel.password.observe(this, s_password::setText);

        // Set sign-up button click listener
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpViewModel.name.setValue(s_name.getText().toString());
                signUpViewModel.email.setValue(s_email.getText().toString());
                signUpViewModel.password.setValue(s_password.getText().toString());
                signUpViewModel.signUpUser();
            }
        });

        // Observe sign-up success
        signUpViewModel.isSignUpSuccessful.observe(this, success -> {
            if (success != null && success) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Redirect to login activity on login text click
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
