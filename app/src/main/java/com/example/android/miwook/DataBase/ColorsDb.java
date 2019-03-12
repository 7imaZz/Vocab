package com.example.android.miwook.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.miwook.Word;

import java.util.ArrayList;

public class ColorsDb extends SQLiteOpenHelper {
    private final static String DB_NAME = "colors.db";
    public ColorsDb(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table color(id integer primary key autoincrement, miwok text, translate text, imgId integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists color");
        onCreate(db);
    }

    public void insertColor(String miwok, String trans){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("miwok", miwok);
        contentValues.put("translate", trans);

        db.insert("color", null, contentValues);
    }

    public void insertColor(String miwok, String trans, int imgId){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("miwok", miwok);
        contentValues.put("translate", trans);
        contentValues.put("imgId", imgId);

        db.insert("color", null, contentValues);
    }

    public ArrayList<Word> getAllColors() {

        SQLiteDatabase db = this.getWritableDatabase();

        ArrayList<Word> words = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from color ", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {

            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String miwok = cursor.getString(cursor.getColumnIndex("miwok"));
            String trans = cursor.getString(cursor.getColumnIndex("translate"));
            int imgId = cursor.getInt(cursor.getColumnIndex("imgId"));

            words.add(new Word(miwok, trans, imgId, id));

            cursor.moveToNext();
        }
        return words;
    }

    public void deleteItem(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("color", "id = ?", new String[]{id});
    }

    public void editItem(String id, String word, String trans){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("miwok", word);
        contentValues.put("translate", trans);

        db.update("color", contentValues, "id = ?", new String[]{id});
    }

    public void deleteAllColors(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from color");
    }
}
