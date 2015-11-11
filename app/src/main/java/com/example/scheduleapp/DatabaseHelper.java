package com.example.scheduleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper sInstance;
    public static final String DATABASE_NAME = "Tasks.db";
    public static final int DATABASE_VERSION = 1;
    private static final String TEXT = " TEXT";
    private static final String CREATE_ENTRY =
            "CREATE TABLE " + Feeder.FeedEntry.TABLE_NAME + " (" +
                    Feeder.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    Feeder.FeedEntry.TASK_NAME + TEXT + "," +
                    Feeder.FeedEntry.TASK_LOCATION + TEXT + "," +
                    Feeder.FeedEntry.TASK_DESC + TEXT + "," +
                    Feeder.FeedEntry.TASK_DATE + TEXT + "," +
                    Feeder.FeedEntry.TASK_TIME + TEXT + ")";
    private static final String DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Feeder.FeedEntry.TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        SQLiteDatabase db = sInstance.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(Feeder.FeedEntry.TASK_NAME, name);
        content.put(Feeder.FeedEntry.TASK_LOCATION, location);
        content.put(Feeder.FeedEntry.TASK_DESC, desc);
        content.put(Feeder.FeedEntry.TASK_DATE, date);
        content.put(Feeder.FeedEntry.TASK_TIME, time);
        long result = db.insert(Feeder.FeedEntry.TABLE_NAME, null, content);
        if(result == -1) { return false; }
        return true;
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + Feeder.FeedEntry.TABLE_NAME, null);
        return result;
    }

}
