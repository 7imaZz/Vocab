package com.example.android.miwook.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.miwook.Word;

import java.util.ArrayList;

public class WordDb extends SQLiteOpenHelper{
    private final static String DB_NAME = "wordsql.db";
    public WordDb(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table wordtable (id integer primary key autoincrement, miwoktrans text, deftrans text, imgId integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists wordtable");
        onCreate(db);
    }


    public void insertNewWord(String miwokTrans, String defTrans, int imgId){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("miwoktrans", miwokTrans);
        contentValues.put("deftrans", defTrans);
        contentValues.put("imgId", imgId);

        db.insert("wordtable", null, contentValues);
    }

    public void insertNewWord(String miwokTrans, String defTrans){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("miwoktrans", miwokTrans);
        contentValues.put("deftrans", defTrans);

        db.insert("wordtable", null, contentValues);
    }

    public ArrayList<Word> getAllWords(){

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList <Word> words = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from wordtable", null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String miwokTrans = cursor.getString(cursor.getColumnIndex("miwoktrans"));
            String defTrans = cursor.getString(cursor.getColumnIndex("deftrans"));
            int imgId = cursor.getInt(cursor.getColumnIndex("imgId"));

            words.add(new Word(miwokTrans, defTrans, imgId, id));
            cursor.moveToNext();
        }
        return words;
    }

    public void delAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from wordtable");
    }

    public void deleteItem(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("wordtable", "id = ?", new String[]{id});
    }

    public void editItem(String id, String word, String trans){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("miwoktrans", word);
        contentValues.put("deftrans", trans);

        db.update("wordtable", contentValues, "id = ?", new String[]{id});
    }
}
