package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        register = findViewById(R.id.buttonreg);
        login = findViewById(R.id.buttonsignin);

        // Set click listener for the Register button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity(); // Open registration activity
            }
        });

        // Set click listener for the Login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLoginActivity(); // Open login activity
            }
        });
    }

    // Method to open the Register activity
    private void openRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class); // Ensure correct naming
        startActivity(intent);
    }

    // Method to open the Login activity
    private void openLoginActivity() {
        Intent intent = new Intent(MainActivity.this, login.class); // Ensure correct naming
        startActivity(intent);
    }
}
