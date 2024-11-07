package com.example.project;

import android.content.SharedPreferences;
import android.os.Bundle;
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
        savedBooksRef = FirebaseDatabase.getInstance().getReference("savedBooks").child(userEmail.replace(".", ","));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookAdapter = new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);

        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getBooks().observe(this, books -> bookAdapter.setBooks(books));
    }

    @Override
    public void onAddBook(Book book) {
        savedBooksRef.push().setValue(book).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Book added to your list!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add book.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}