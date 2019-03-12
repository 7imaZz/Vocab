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
import com.example.android.miwook.DataBase.WordDb;
import java.util.ArrayList;
import java.util.Locale;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class NounActivity extends AppCompatActivity {
    WordDb db;
    ListView listView;
    int id;
    String ed1, ed2;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        db = new WordDb(this);

        listView = findViewById(R.id.lv_numbers);


        showWords();

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
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Word word = (Word) adapterView.getItemAtPosition(pos);
                id = word.getId();
                ed1 = word.getMiwokTranslation();
                ed2 = word.getDefaultTranslation();

                AlertDialog.Builder builder = new AlertDialog.Builder(NounActivity.this);

                builder.setTitle("Decision")
                        .setMessage("What do you want to do with this word?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteItem(String.valueOf(id));
                                showWords();
                            }
                        }).setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(NounActivity.this);

                        builder.setTitle("Edit");

                        LinearLayout linearLayout = new LinearLayout(NounActivity.this);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);

                        final EditText input1 = new EditText(NounActivity.this);
                        input1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        input1.setText(ed1);
                        linearLayout.addView(input1);

                        final EditText input2 = new EditText(NounActivity.this);
                        input2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        input2.setText(ed2);
                        linearLayout.addView(input2);

                        builder.setView(linearLayout);

                        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.editItem(String.valueOf(id),input1.getText().toString(), input2.getText().toString());
                                showWords();
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

    public void showWords(){

        ArrayList <Word> words = db.getAllWords();

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_nouns);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        else if (id == R.id.add_word)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Add New Word");

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
                    db.insertNewWord(input1.getText().toString(), input2.getText().toString());
                    showWords();
                }
            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
        else if (id == R.id.del) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to delete all numbers?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.delAll();
                            showWords();
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
    protected void onStop() {
        super.onStop();
        textToSpeech.stop();
    }
}