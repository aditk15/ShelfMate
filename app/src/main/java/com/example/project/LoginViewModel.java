package com.example.project;

import android.app.Application;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginViewModel extends AndroidViewModel {

    private final DatabaseReference databaseReference;

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoginSuccessful = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    // Validate email
    public boolean validateEmail() {
        String email = name.getValue();
        if (email == null || email.isEmpty()) {
            Toast.makeText(getApplication(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplication(), "Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Validate password
    public boolean validatePassword() {
        String pass = password.getValue();
        if (pass == null || pass.isEmpty()) {
            Toast.makeText(getApplication(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (pass.length() < 6) {
            Toast.makeText(getApplication(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Perform login
    public void loginUser() {
        if (!validateEmail() || !validatePassword()) {
            return;
        }

        String email = name.getValue().replace(".", ","); // Replace dots with commas
        String pass = password.getValue();

        databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passwordFromDB = snapshot.child("password").getValue(String.class);
                    if (pass.equals(passwordFromDB)) {
                        isLoginSuccessful.setValue(true);
                    } else {
                        Toast.makeText(getApplication(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplication(), "User does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplication(), "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
