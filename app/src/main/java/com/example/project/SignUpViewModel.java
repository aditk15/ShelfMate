package com.example.project;

import android.app.Application;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpViewModel extends AndroidViewModel {

    private final DatabaseReference databaseReference;

    public MutableLiveData<String> name = new MutableLiveData<>();
    public MutableLiveData<String> email = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSignUpSuccessful = new MutableLiveData<>();

    public SignUpViewModel(@NonNull Application application) {
        super(application);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
    }

    // Validate name
    public boolean validateName() {
        String nameVal = name.getValue();
        if (nameVal == null || nameVal.isEmpty()) {
            Toast.makeText(getApplication(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Validate email
    public boolean validateEmail() {
        String emailVal = email.getValue();
        if (emailVal == null || emailVal.isEmpty()) {
            Toast.makeText(getApplication(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailVal).matches()) {
            Toast.makeText(getApplication(), "Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Validate password
    public boolean validatePassword() {
        String passVal = password.getValue();
        if (passVal == null || passVal.isEmpty()) {
            Toast.makeText(getApplication(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passVal.length() < 6) {
            Toast.makeText(getApplication(), "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Perform sign-up
    public void signUpUser() {
        if (!validateName() || !validateEmail() || !validatePassword()) {
            return;
        }

        String userName = name.getValue();
        String userEmail = email.getValue().replace(".", ","); // Replace dots with commas
        String userPassword = password.getValue();

        HelperClass user = new HelperClass(userName, email.getValue(), userPassword); // Store original email in HelperClass
        databaseReference.child(userEmail).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        isSignUpSuccessful.setValue(true);
                    } else {
                        Toast.makeText(getApplication(), "Sign-up failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
