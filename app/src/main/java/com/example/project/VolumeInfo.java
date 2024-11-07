package com.example.project;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class VolumeInfo {
    @SerializedName("title")
    private String title;

    @SerializedName("authors")
    private List<String> authors;

    @SerializedName("pageCount")
    private Integer pageCount;

    public String getTitle() {
        return title;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public List<String> getAuthors() {
        return authors;
    }
}
