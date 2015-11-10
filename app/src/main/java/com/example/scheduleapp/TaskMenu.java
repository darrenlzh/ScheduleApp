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
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

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
        String name, loc, desc, date, time;
        EditText text = (EditText)findViewById(R.id.name);
        if((name = text.getText().toString()).equals("")) {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage(name);
            dlgAlert.setTitle("Error Message...");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            return;
        }
        text = (EditText)findViewById(R.id.location);
        loc = text.getText().toString();
        text = (EditText)findViewById(R.id.description);
        desc = text.getText().toString();
        DatePicker dp = (DatePicker)findViewById(R.id.datePicker);
        TimePicker tp = (TimePicker)findViewById(R.id.timePicker);
        date = dp.getDayOfMonth() + " - " + dp.getMonth() + " - " + dp.getMonth();
        time = " ";
        if(MainActivity._db.insertData(name, loc, desc, date, time)) {
            Toast.makeText(TaskMenu.this, "Task Added", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(TaskMenu.this, "Task NOT Added", Toast.LENGTH_LONG).show();
        }
    }
}
