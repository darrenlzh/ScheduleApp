package com.example.scheduleapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Matthew on 11/9/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Tasks";
    public static final String TABLE_NAME = "Task_List";
    public static final String TASK_NAME = "Task_Name";
    public static final String TASK_LOCATION = "Task_Location";
    public static final String TASK_DATE = "Task_Date";
    public static final String TASK_TIME = "Task_Time";
    public static final String TASK_DESC = "Task_Desc";

    public DatabaseHelper(Context context) {//, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //super(context, name, factory, version);
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (Task_Name TEXT PRIMARY KEY, Task_Location TEXT, Task_Desc TEXT, Task_Date TEXT, Task_Time TEXT");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String location, String desc, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(TASK_NAME, name);
        content.put(TASK_LOCATION, location);
        content.put(TASK_DESC, desc);
        content.put(TASK_DATE, date);
        content.put(TASK_TIME, time);
        long result = db.insert(TABLE_NAME, null, content);
        if(result == -1) { return false; }
        return true;
    }
}
