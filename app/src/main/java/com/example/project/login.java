package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private EditText name, password;
    private Button b2;
    private TextView login, signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initializing views
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        b2 = findViewById(R.id.b2);
        login = findViewById(R.id.login);
        signup_button = findViewById(R.id.signin_button);

        // Set login button click listener
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateName() | !validatePassword()) {
                    return;  // Return if validation fails
                } else {
                    checkUser();
                }
            }
        });

        // Redirect to sign-up activity on sign-up text click
        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    // Validation for name
    public Boolean validateName() {
        String val = name.getText().toString();
        if (val.isEmpty()) {
            name.setError("Name cannot be empty");
            return false;
        } else {
            name.setError(null);
            return true;
        }
    }

    // Validation for password
    public Boolean validatePassword() {
        String val = password.getText().toString();
        if (val.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    // Function to check user credentials
    public void checkUser() {
        String userName = name.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.child(userName); // Directly using the name as a key

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passwordFromDB = snapshot.child("password").getValue(String.class);

                    if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                        password.setError(null);
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        password.setError("Invalid credentials");
                        password.requestFocus();
                    }
                } else {
                    name.setError("User does not exist");
                    name.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
