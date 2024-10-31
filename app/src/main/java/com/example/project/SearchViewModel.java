package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private final MutableLiveData<List<Book>> books = new MutableLiveData<>();
    private final BooksRepository booksRepository;

    public SearchViewModel() {
        booksRepository = new BooksRepository();
    }

    public LiveData<List<Book>> getBooks() {
        return books;
    }

    public void searchBooks(String query, String apiKey) {
        booksRepository.searchBooks(query, apiKey).observeForever(books::setValue);
    }
}
