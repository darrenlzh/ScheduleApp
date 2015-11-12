package com.example.scheduleapp;

public class Task {
    public String _id;
    public String _title;
    public String _location;
    public String _desc;
    public String _date;
    public String _time;

    public Task(String id, String title, String location, String desc, String date, String time ) {
        _id = id;
        _title = title;
        _location = location;
        _desc = desc;
        _date = date;
        _time = time;
    }
}
