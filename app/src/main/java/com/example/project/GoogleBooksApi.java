package com.example.project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApi {
    @GET("volumes")
    Call<BooksResponse> searchBooks(@Query("q") String query, @Query("key") String apiKey);
}
