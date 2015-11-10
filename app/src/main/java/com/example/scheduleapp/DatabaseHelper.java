package com.example.scheduleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tasks.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT = " TEXT";
    private static final String CREATE_ENTRY =
            "CREATE TABLE " + Feeder.FeedEntry.TABLE_NAME + " (" +
                    Feeder.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    Feeder.FeedEntry.TASK_NAME + TEXT +
                    Feeder.FeedEntry.TASK_LOCATION + TEXT +
                    Feeder.FeedEntry.TASK_DESC + TEXT +
                    Feeder.FeedEntry.TASK_DATE + TEXT +
                    Feeder.FeedEntry.TASK_TIME + TEXT;
    private static final String DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Feeder.FeedEntry.TABLE_NAME;
    private SQLiteDatabase _db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertData(String name, String location, String desc, String date, String time) {
        ContentValues content = new ContentValues();
        content.put(Feeder.FeedEntry.TASK_NAME, name);
        content.put(Feeder.FeedEntry.TASK_LOCATION, location);
        content.put(Feeder.FeedEntry.TASK_DESC, desc);
        content.put(Feeder.FeedEntry.TASK_DATE, date);
        content.put(Feeder.FeedEntry.TASK_TIME, time);
        long result = _db.insert(Feeder.FeedEntry.TABLE_NAME, null, content);
        if(result == -1) { return false; }
        return true;
    }

}
