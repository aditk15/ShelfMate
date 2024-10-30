package com.example.project;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VolumeInfo {
    @SerializedName("title")
    private String title;

    @SerializedName("authors")
    private List<String> authors;

    public String getTitle() {
        return title;
    }

    public List<String> getAuthors() {
        return authors;
    }
}
