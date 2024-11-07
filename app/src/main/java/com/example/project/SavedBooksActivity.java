package com.example.project;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedBooksActivity extends AppCompatActivity {

    private SavedBooksViewModel savedBooksViewModel;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_books);

        RecyclerView recyclerView = findViewById(R.id.savedBooksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adapter without add button as this is just for viewing saved books
        bookAdapter = new BookAdapter(null);
        recyclerView.setAdapter(bookAdapter);

        savedBooksViewModel = new ViewModelProvider(this).get(SavedBooksViewModel.class);
        savedBooksViewModel.getSavedBooks().observe(this, books -> bookAdapter.setBooks(books));
    }
}
