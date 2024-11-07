package com.example.project;

import android.content.SharedPreferences;
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

public class SearchResultsActivity extends AppCompatActivity implements BookAdapter.OnAddBookListener {
    private SearchViewModel searchViewModel;
    private BookAdapter bookAdapter;
    private DatabaseReference savedBooksRef;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        // Get user email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        userEmail = sharedPreferences.getString(LoginActivity.KEY_USER_EMAIL, null);

        // Initialize Firebase reference for savedBooks under user's email
        if (userEmail != null) {
            savedBooksRef = FirebaseDatabase.getInstance().getReference("savedBooks").child(userEmail.replace(".", ","));
        } else {
            Toast.makeText(this, "User email not found. Please log in.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no user email is available
            return;
        }

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
        bookAdapter = new BookAdapter(this); // Pass 'this' to implement the OnAddBookListener
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

    @Override
    public void onAddBook(Book book) {
        if (savedBooksRef != null) {
            savedBooksRef.push().setValue(book).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Book added to your list!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to add book.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Error: Firebase reference is null.", Toast.LENGTH_SHORT).show();
        }
    }
}
