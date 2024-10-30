package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class BooksRepository {
    private final GoogleBooksApi googleBooksApi;

    public BooksRepository() {
        googleBooksApi = RetrofitClient.getClient().create(GoogleBooksApi.class);
    }

    public LiveData<List<Book>> searchBooks(String query, String apiKey) {
        MutableLiveData<List<Book>> data = new MutableLiveData<>();
        googleBooksApi.searchBooks(query, apiKey).enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getItems());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
