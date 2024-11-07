package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books = new ArrayList<>();
    private OnAddBookListener onAddBookListener;

    public interface OnAddBookListener {
        void onAddBook(Book book);
    }

    public BookAdapter(OnAddBookListener listener) {
        this.onAddBookListener = listener;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.titleTextView.setText(book.getVolumeInfo().getTitle());

        List<String> authors = book.getVolumeInfo().getAuthors();
        holder.authorTextView.setText(authors != null ? String.join(", ", authors) : "Unknown Author");

        holder.addButton.setOnClickListener(v -> {
            if (onAddBookListener != null) {
                onAddBookListener.onAddBook(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;
        Button addButton;

        public BookViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.bookTitle);
            authorTextView = itemView.findViewById(R.id.bookAuthor);
            addButton = itemView.findViewById(R.id.addButton); // Make sure this ID matches your updated book_item.xml
        }
    }
}
