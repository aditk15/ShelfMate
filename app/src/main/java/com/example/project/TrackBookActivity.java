package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class TrackBookActivity extends AppCompatActivity {
    private TrackBookViewModel trackBookViewModel;
    private EditText bookTitleEditText;
    private EditText pagesReadEditText;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_book);

        bookTitleEditText = findViewById(R.id.bookTitleEditText);
        pagesReadEditText = findViewById(R.id.pagesReadEditText);
        Button calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        trackBookViewModel = new ViewModelProvider(this).get(TrackBookViewModel.class);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = bookTitleEditText.getText().toString();
                int pagesRead = Integer.parseInt(pagesReadEditText.getText().toString());
                trackBookViewModel.calculatePercentage(title, pagesRead);
            }
        });

        trackBookViewModel.getPercentageResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String result) {
                resultTextView.setText(result);
            }
        });
    }
}