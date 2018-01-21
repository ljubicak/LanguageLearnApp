package com.example.ljubica.deutschlernen;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ljubica on 16.12.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DeutschLernen.db";
    public static final String WORDS_TABLE_NAME = "words";
    public static final String WORDS_COLUMN_ID = "id";
    public static final String WORDS_COLUMN_DEFINITER_ARTIKEL = "definiter_artikel";
    public static final String WORDS_COLUMN_WORD = "word";
    public static final String WORDS_COLUMN_PLURAL = "plural";
    public static final String WORDS_COLUMN_TRANSLATION = "translation";
    public static final String WORDS_COLUMN_LESSON = "lesson";
    private HashMap hp;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table words " +
                        "(id integer primary key, definiter_artikel text,word text,plural text, translation text,lesson text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS words");
        onCreate(db);
    }

    public boolean insertWord (String definiter_artikel, String word, String plural, String translation,String lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("definiter_artikel", definiter_artikel);
        contentValues.put("word", word);
        contentValues.put("plural", plural);
        contentValues.put("translation", translation);
        contentValues.put("lesson", lesson);
        db.insert("words", null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from words where id="+id+"", null );
        return res;
    }
    public ArrayList<Word>  getDataByLesson(String lesson) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Word> array_list = new ArrayList<Word>();
        Cursor res =  db.rawQuery( "select * from words where lesson LIKE \""+lesson+"\"", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(new Word(
                    res.getString(res.getColumnIndex(WORDS_COLUMN_DEFINITER_ARTIKEL)),
                    res.getString(res.getColumnIndex(WORDS_COLUMN_WORD)),
                    res.getString(res.getColumnIndex(WORDS_COLUMN_PLURAL)),
                    res.getString(res.getColumnIndex(WORDS_COLUMN_TRANSLATION)),
                    res.getString(res.getColumnIndex(WORDS_COLUMN_LESSON))
                    ));
            res.moveToNext();
        }
        return array_list;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, WORDS_TABLE_NAME);
        return numRows;
    }
    public boolean updateWord (Integer id, String definiter_artikel, String word, String plural, String translation,String lesson) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("definiter_artikel", definiter_artikel);
        contentValues.put("word", word);
        contentValues.put("plural", plural);
        contentValues.put("translation", translation);
        contentValues.put("lesson", lesson);
        db.update("words", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteWord (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("words",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Word> getAllWords() {
        ArrayList<Word> array_list = new ArrayList<Word>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from words", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(new Word(
                    res.getString(res.getColumnIndex(WORDS_COLUMN_DEFINITER_ARTIKEL)),
                    res.getString(res.getColumnIndex(WORDS_COLUMN_WORD)),
                    res.getString(res.getColumnIndex(WORDS_COLUMN_PLURAL)),
                    res.getString(res.getColumnIndex(WORDS_COLUMN_TRANSLATION)),
                    res.getString(res.getColumnIndex(WORDS_COLUMN_LESSON))
            ));
            res.moveToNext();
        }
        return array_list;
    }
}
