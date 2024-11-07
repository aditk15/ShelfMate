package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrackBookViewModel extends ViewModel {
    private final BooksRepository booksRepository;
    private final MutableLiveData<String> percentageResult = new MutableLiveData<>();

    public TrackBookViewModel() {
        booksRepository = new BooksRepository();
    }

    public LiveData<String> getPercentageResult() {
        return percentageResult;
    }

    public void calculatePercentage(String title, int pagesRead) {
        booksRepository.searchBooks(title, "AIzaSyDZrKG46V_6b1bndzjQaavSDnM9vVJ8dfI").observeForever(books -> {
            if (books != null && !books.isEmpty()) {
                int totalPages = books.get(0).getVolumeInfo().getPageCount(); // Assuming first result is used.
                if (totalPages > 0) {
                    double percentage = ((double) pagesRead / totalPages) * 100;
                    percentageResult.setValue("You have read " + String.format("%.2f", percentage) + "% of the book.");
                } else {
                    percentageResult.setValue("Page count not available.");
                }
            } else {
                percentageResult.setValue("Book not found.");
            }
        });
    }
}
