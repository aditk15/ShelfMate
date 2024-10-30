package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.booksRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Adapter
        bookAdapter = new BookAdapter();
        recyclerView.setAdapter(bookAdapter);

        // Initialize ViewModel
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Observe navigation events for Register and Login buttons
        mainViewModel.getNavigateToRegister().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldNavigate) {
                if (shouldNavigate) {
                    openRegisterActivity();
                    mainViewModel.resetNavigation();
                }
            }
        });

        mainViewModel.getNavigateToLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldNavigate) {
                if (shouldNavigate) {
                    openLoginActivity();
                    mainViewModel.resetNavigation();
                }
            }
        });

        // Observe the book search results from Google Books API
        mainViewModel.getBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                if (books != null) {
                    bookAdapter.setBooks(books);
                } else {
                    Toast.makeText(MainActivity.this, "No books found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Initialize buttons and set up listeners
        Button register = findViewById(R.id.buttonreg);
        Button login = findViewById(R.id.buttonsignin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.onRegisterClicked();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.onLoginClicked();
            }
        });

        // Trigger a sample search in Google Books API
        mainViewModel.searchBooks("android", "AIzaSyDZrKG46V_6b1bndzjQaavSDnM9vVJ8dfI");
    }

    // Method to open the Register activity
    private void openRegisterActivity() {
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    // Method to open the Login activity
    private void openLoginActivity() {
        Intent intent = new Intent(MainActivity.this, login.class);
        startActivity(intent);
    }
}
