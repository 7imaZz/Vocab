package com.example.android.miwook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.android.miwook.DataBase.ColorsDb;

import java.util.ArrayList;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AdjActivity extends AppCompatActivity {

    ListView listView;
    ColorsDb db;
    int id;
    String ed1, ed2;
    TextToSpeech textToSpeech;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<Word> words = new ArrayList<>();

        db = new ColorsDb(this);

        listView = findViewById(R.id.lv_numbers);

        showAllColors();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = (Word) adapterView.getItemAtPosition(i);

                String speak = word.getMiwokTranslation();

                textToSpeech.stop();

                textToSpeech.speak(speak, TextToSpeech.QUEUE_ADD, null);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int pos, long l) {
                Word word = (Word) adapterView.getItemAtPosition(pos);
                id = word.getId();
                ed1 = word.getMiwokTranslation();
                ed2 = word.getDefaultTranslation();

                AlertDialog.Builder builder = new AlertDialog.Builder(AdjActivity.this);

                builder.setTitle("Edit")
                        .setMessage("What do you want to do with this word")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteItem(String.valueOf(id));
                                showAllColors();
                            }
                        }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AdjActivity.this);

                        builder.setTitle("Edit");

                        LinearLayout linearLayout = new LinearLayout(AdjActivity.this);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);

                        final EditText input1 = new EditText(AdjActivity.this);
                        input1.setText(ed1);
                        input1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        linearLayout.addView(input1);

                        final EditText input2 = new EditText(AdjActivity.this);
                        input2.setText(ed2);
                        input2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        linearLayout.addView(input2);

                        builder.setView(linearLayout);

                        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.editItem(String.valueOf(id),input1.getText().toString(), input2.getText().toString());
                                showAllColors();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                    }
                }).show();
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            finish();
        }

        else if(id == R.id.add_word){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Add New Color");

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            final EditText input1 = new EditText(this);
            input1.setHint("Enter English Word");
            input1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(input1);

            final EditText input2 = new EditText(this);
            input2.setHint("Enter Translation");
            input2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.addView(input2);

            builder.setView(linearLayout);

            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    db.insertColor(input1.getText().toString(), input2.getText().toString());
                    showAllColors();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        else if(id == R.id.del){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete all colors?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.deleteAllColors();
                            showAllColors();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop();
    }

    public void showAllColors(){

        ArrayList <Word> words = db.getAllColors();

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_adj);

        listView.setAdapter(adapter);
    }
}
