package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrackBookViewModel extends ViewModel {
    private final BooksRepository booksRepository;
    private final MutableLiveData<String> percentageResult = new MutableLiveData<>();

    // New fields to store total pages, pages read, book title, and author
    private int totalPages;
    private int pagesRead;
    private String bookTitle; // New field for book title
    private String bookAuthor; // New field for book author

    public TrackBookViewModel() {
        booksRepository = new BooksRepository();
    }

    public LiveData<String> getPercentageResult() {
        return percentageResult;
    }

    public int getTotalPages() {
        return totalPages; // Return total pages for display in activity
    }

    public int getPagesRead() {
        return pagesRead; // Return pages read for display in activity
    }

    public String getBookTitle() {
        return bookTitle; // Return the book title
    }

    public String getBookAuthor() {
        return bookAuthor; // Return the book author
    }

    public void calculatePercentage(String title, int pagesRead) {
        this.pagesRead = pagesRead; // Store the input value
        this.bookTitle = title; // Store the title

        booksRepository.searchBooks(title, "AIzaSyDZrKG46V_6b1bndzjQaavSDnM9vVJ8dfI").observeForever(books -> {
            if (books != null && !books.isEmpty()) {
                VolumeInfo volumeInfo = books.get(0).getVolumeInfo();
                this.bookAuthor = volumeInfo.getAuthors() != null ? String.join(", ", volumeInfo.getAuthors()) : "Unknown Author"; // Get authors
                totalPages = volumeInfo.getPageCount(); // Retrieve total pages

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