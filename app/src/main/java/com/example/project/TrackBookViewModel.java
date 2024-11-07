package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TrackBookViewModel extends ViewModel {
    private final BooksRepository booksRepository;
    private final MutableLiveData<String> percentageResult = new MutableLiveData<>();

    private int totalPages;
    private int pagesRead;
    private String bookTitle;
    private String bookAuthor;

    public TrackBookViewModel() {
        booksRepository = new BooksRepository();
    }

    public LiveData<String> getPercentageResult() {
        return percentageResult;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPagesRead() {
        return pagesRead;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void calculatePercentage(String title, int pagesRead) {
        this.pagesRead = pagesRead;
        this.bookTitle = title.trim();

        booksRepository.searchBooks(bookTitle, "AIzaSyDZrKG46V_6b1bndzjQaavSDnM9vVJ8dfI")
                .observeForever(books -> {
                    if (books != null && !books.isEmpty()) {
                        Book bestMatch = null;
                        for (Book book : books) {
                            VolumeInfo volumeInfo = book.getVolumeInfo();
                            if (volumeInfo != null && bookTitle.toLowerCase().equals(volumeInfo.getTitle().toLowerCase().trim())) {
                                bestMatch = book;
                                break;
                            }
                        }

                        if (bestMatch != null) {
                            VolumeInfo volumeInfo = bestMatch.getVolumeInfo();
                            this.bookTitle = volumeInfo.getTitle();
                            this.bookAuthor = volumeInfo.getAuthors() != null
                                    ? String.join(", ", volumeInfo.getAuthors())
                                    : "Unknown Author";
                            totalPages = volumeInfo.getPageCount();

                            if (totalPages > 0) {
                                double percentage = ((double) pagesRead / totalPages) * 100;

                                int pagesLeft = totalPages - pagesRead;
                                int pagesPerDay = 20;
                                int approxDays = (int) Math.ceil((double) pagesLeft / pagesPerDay);

                                percentageResult.setValue(
                                        "Book Title: " + bookTitle + "\n" +
                                                "Author(s): " + bookAuthor + "\n" +
                                                "You have read " + String.format("%.2f", percentage) + "% of the book.\n" +
                                                "Pages Left: " + pagesLeft + "\n" +
                                                "Approximate Days to Finish: " + approxDays + " days (assuming " + pagesPerDay + " pages/day)."
                                );
                            } else {
                                percentageResult.setValue(
                                        "Book Title: " + bookTitle + "\n" +
                                                "Author(s): " + bookAuthor + "\n" +
                                                "Page count not available."
                                );
                            }
                        } else {
                            percentageResult.setValue("Exact book not found.");
                        }
                    } else {
                        percentageResult.setValue("Book not found.");
                    }
                });
    }
}
