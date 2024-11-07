package com.example.project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SavedBooksViewModel extends ViewModel {

    private final MutableLiveData<List<Book>> savedBooks = new MutableLiveData<>();
    private final DatabaseReference savedBooksRef = FirebaseDatabase.getInstance().getReference("savedBooks");

    public SavedBooksViewModel() {
        loadSavedBooks();
    }

    public LiveData<List<Book>> getSavedBooks() {
        return savedBooks;
    }

    private void loadSavedBooks() {
        savedBooksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Book> books = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Book book = dataSnapshot.getValue(Book.class);
                    if (book != null) {
                        books.add(book);
                    }
                }
                savedBooks.setValue(books);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                savedBooks.setValue(null);
            }
        });
    }
}
