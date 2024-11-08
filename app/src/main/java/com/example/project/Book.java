package com.example.project;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

public class Book {
    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo;

    private String bookId;  // Unique identifier for the book

    public Book() {
        // Generate a unique bookId based on title and authors
        if (volumeInfo != null && volumeInfo.getTitle() != null) {
            this.bookId = generateBookId(volumeInfo.getTitle(), volumeInfo.getAuthors());
        }
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
        if (volumeInfo != null && volumeInfo.getTitle() != null) {
            this.bookId = generateBookId(volumeInfo.getTitle(), volumeInfo.getAuthors());
        }
    }

    // Getters and Setters
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    // Generate a unique ID for the book based on title and authors
    private String generateBookId(String title, List<String> authors) {
        String authorStr = authors != null ? String.join(", ", authors) : "Unknown Author";
        return String.valueOf(Objects.hash(title, authorStr)); // Use hash of title + authors as unique ID
    }
}
