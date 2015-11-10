package com.example.scheduleapp;

import android.provider.BaseColumns;
public final class Feeder {

    public Feeder() {}

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Task_List";
        public static final String TASK_NAME = "Task_Name";
        public static final String TASK_LOCATION = "Task_Location";
        public static final String TASK_DATE = "Task_Date";
        public static final String TASK_TIME = "Task_Time";
        public static final String TASK_DESC = "Task_Desc";

    }
}
