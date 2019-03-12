package com.example.android.miwook.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.miwook.Word;

import java.util.ArrayList;

public class PhraseDb extends SQLiteOpenHelper {
    private final static String DB_NAME = "phrase.db";
    public PhraseDb(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table phrase(id integer primary key autoincrement, miwok text, translate text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists phrase");
        onCreate(db);
    }


    public void insertPhrase(String miwok, String trans){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("miwok", miwok);
        contentValues.put("translate", trans);

        db.insert("phrase", null, contentValues);
    }

    public ArrayList<Word> getAllPhrases(){

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList <Word> words = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from phrase ", null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String miwok = cursor.getString(cursor.getColumnIndex("miwok"));
            String trans = cursor.getString(cursor.getColumnIndex("translate"));

            words.add(new Word(miwok, trans,-1 ,id));

            cursor.moveToNext();
        }

        return words;
    }

    public void deleteAllPhrases(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from phrase");
    }

    public void deleteItem(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("phrase", "id = ?", new String[]{id});
    }

    public void editItem(String id, String word, String trans){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("miwok", word);
        contentValues.put("translate", trans);

        db.update("phrase", contentValues, "id = ?", new String[]{id});
    }
}
