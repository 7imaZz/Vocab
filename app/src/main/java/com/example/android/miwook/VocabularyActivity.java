package com.example.android.miwook;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VocabularyActivity extends AppCompatActivity {

    TextView numbersTextView, familyTextView, colorsTextView, phrasesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        numbersTextView = findViewById(R.id.numbers);
        familyTextView = findViewById(R.id.family);
        colorsTextView = findViewById(R.id.colors);
        phrasesTextView = findViewById(R.id.phrases);

        numbersTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyActivity.this, NounActivity.class);
                startActivity(intent);
            }
        });

        familyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyActivity.this, VerbActivity.class);
                startActivity(intent);
            }
        });

        colorsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyActivity.this, AdjActivity.class);
                startActivity(intent);
            }
        });

        phrasesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VocabularyActivity.this, PhrasesActivity.class);
                startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(VocabularyActivity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
