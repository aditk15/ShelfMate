package com.example.project;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Book {
    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }
}
