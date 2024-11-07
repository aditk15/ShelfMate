package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private Button button_login;
    private Button button_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize buttons
        button_login = findViewById(R.id.button_login);
        button_sign_up = findViewById(R.id.button_sign_up);

        // Set click listeners for the buttons
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Login Activity
                Intent loginIntent = new Intent(HomeActivity.this, login.class);
                startActivity(loginIntent);
            }
        });

        button_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Sign-Up Activity
                Intent signUpIntent = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}
