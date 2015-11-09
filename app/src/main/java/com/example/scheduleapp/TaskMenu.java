package com.example.scheduleapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class TaskMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void buttonAddTask(View view) {
        Button button = (Button) view;
        String name, loc, desc;
        if((name = findViewById(R.id.name_line).toString()) .equals("")){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Name line must be filled.");
                dlgAlert.setTitle("Error Message...");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                return;
        }
        loc = findViewById(R.id.location_line).toString();
        desc = findViewById(R.id.description_line).toString();
        DatePicker date = (DatePicker)findViewById(R.id.datePicker2);
        TimePicker time = (TimePicker)findViewById(R.id.timePicker);


        MainActivity._db.insertData("", "", "", "", "");
    }
}
