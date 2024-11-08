package com.example.project;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedBooksActivity extends AppCompatActivity {
    private SavedBooksViewModel savedBooksViewModel;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_books);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        String userEmail = sharedPreferences.getString(LoginActivity.KEY_USER_EMAIL, null);

        RecyclerView recyclerView = findViewById(R.id.savedBooksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create BookAdapter without "+" button since we're on the SavedBooksActivity page
        bookAdapter = new BookAdapter();
        recyclerView.setAdapter(bookAdapter);

        savedBooksViewModel = new ViewModelProvider(this).get(SavedBooksViewModel.class);
        savedBooksViewModel.init(userEmail); // Pass user email to ViewModel
        savedBooksViewModel.getSavedBooks().observe(this, books -> {
            if (books != null) {
                bookAdapter.setBooks(books);
            }
        });

        // Swipe-to-delete functionality
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Book book = bookAdapter.getBookAtPosition(position); // Get the book at the swiped position
                deleteBook(book);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void deleteBook(Book book) {
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFS_NAME, MODE_PRIVATE);
        String userEmail = sharedPreferences.getString(LoginActivity.KEY_USER_EMAIL, null);

        if (userEmail != null) {
            // Use the bookId to identify the book for deletion
            String bookId = book.getBookId(); // Get the unique book ID
            savedBooksViewModel.deleteSavedBook(userEmail, bookId);  // Delete the book using its ID
            Toast.makeText(this, "Book removed from saved list", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User email not found", Toast.LENGTH_SHORT).show();
        }
    }
}
