package com.example.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    public static final String DB_NAME = "text.db";
    public static final String TABLE_NAME = "messages";
    public static final String COLUMN_ONE = "id";
    public static final String COLUMN_TWO = "text";
    public static final String COLUMN_THREE = "isSent";
    public static final String COLUMN_FOUR = "timestamp";


    public DBManager(@Nullable Context context) {
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " ("+ COLUMN_ONE +" integer primary key autoincrement, "+ COLUMN_TWO +" text, "+ COLUMN_THREE +" boolean not null check ("+ COLUMN_THREE +" in (0,1)), "+ COLUMN_FOUR +" text) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String text, int isSent, String timestamp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TWO, text);
        contentValues.put(COLUMN_THREE, isSent);
        contentValues.put(COLUMN_FOUR, timestamp);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result == -1 ? false : true;

    }

    public Cursor getAllTextMessages(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }

    public Cursor getTextMessageByTimeStamp(String timestamp){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " where " + COLUMN_FOUR + " like '" + timestamp + "'", null);
    }

    public Integer deleteTextMessageByTimeStamp(String timestamp){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "timestamp = ?", new String[] {timestamp});
    }
}
