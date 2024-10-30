package com.example.project;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BooksResponse {
    @SerializedName("items")
    private List<Book> items;

    public List<Book> getItems() {
        return items;
    }
}
