package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>();

    public LiveData<Boolean> getNavigateToRegister() {
        return navigateToRegister;
    }

    public LiveData<Boolean> getNavigateToLogin() {
        return navigateToLogin;
    }

    public void onRegisterClicked() {
        navigateToRegister.setValue(true);
    }

    public void onLoginClicked() {
        navigateToLogin.setValue(true);
    }

    public void resetNavigation() {
        navigateToRegister.setValue(false);
        navigateToLogin.setValue(false);
    }
}
