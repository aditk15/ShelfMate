package com.example.project;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchResultsActivity extends AppCompatActivity {

    private SearchViewModel searchViewModel;
    private BookAdapter bookAdapter;
    private DatabaseReference savedBooksRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Initialize Firebase reference
        savedBooksRef = FirebaseDatabase.getInstance().getReference("savedBooks");

        // Custom back arrow button setup
        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.booksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(book -> {
            // Add book to Firebase when the "+" button is clicked
            savedBooksRef.push().setValue(book)
                    .addOnSuccessListener(aVoid -> Toast.makeText(this, "Book added to saved list", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(this, "Failed to save book", Toast.LENGTH_SHORT).show());
        });

        recyclerView.setAdapter(bookAdapter);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        // Observing books from the ViewModel
        searchViewModel.getBooks().observe(this, books -> {
            if (books != null) {
                bookAdapter.setBooks(books);
            } else {
                Toast.makeText(SearchResultsActivity.this, "No books found", Toast.LENGTH_SHORT).show();
            }
        });

        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    // Trigger the search
                    searchViewModel.searchBooks(s.toString(), "AIzaSyDZrKG46V_6b1bndzjQaavSDnM9vVJ8dfI");
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}
