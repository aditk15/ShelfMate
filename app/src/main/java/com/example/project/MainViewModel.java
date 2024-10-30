package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Boolean> navigateToRegister = new MutableLiveData<>();
    private final MutableLiveData<Boolean> navigateToLogin = new MutableLiveData<>();
    private final MutableLiveData<List<Book>> books = new MutableLiveData<>();
    private final BooksRepository booksRepository;

    public MainViewModel() {
        booksRepository = new BooksRepository();
    }

    public LiveData<Boolean> getNavigateToRegister() {
        return navigateToRegister;
    }

    public LiveData<Boolean> getNavigateToLogin() {
        return navigateToLogin;
    }

    public LiveData<List<Book>> getBooks() {
        return books;
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

    public void searchBooks(String query, String apiKey) {
        booksRepository.searchBooks(query, apiKey).observeForever(books::setValue);
    }
}
