package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    EditText s_name, s_email, s_password;
    TextView signin, login_button;
    Button b1;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initializing views
        s_name = findViewById(R.id.s_name);
        s_email = findViewById(R.id.s_email);
        s_password = findViewById(R.id.s_password);
        signin = findViewById(R.id.signin);
        login_button = findViewById(R.id.login_button);
        b1 = findViewById(R.id.b1);

        // Signup button click listener
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String name = s_name.getText().toString();
                String email = s_email.getText().toString();
                String pass = s_password.getText().toString();

                if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                HelperClass helperClass = new HelperClass(name, email, pass);
                reference.child(name).setValue(helperClass);

                Toast.makeText(SignUpActivity.this, "You have Signed Up Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, login.class);
                startActivity(intent);
            }
        });

        // Login button click listener
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, login.class);
                startActivity(intent);
            }
        });
    }
}
