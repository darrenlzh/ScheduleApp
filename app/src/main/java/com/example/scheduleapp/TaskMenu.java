package com.example.scheduleapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TaskMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("New Reminder");

    }

    public void buttonAddTask(View view) {
        Button button = (Button) view;
        String name, loc, desc, date, time;
        EditText text = (EditText)findViewById(R.id.name);
        if((name = text.getText().toString()).equals("")) {
            Toast.makeText(TaskMenu.this, "Title can't be left empty", Toast.LENGTH_LONG).show();
            ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
            scrollView.smoothScrollTo(0,0);
            text.setFocusableInTouchMode(true);
            text.requestFocus();
            return;
        }
        text = (EditText)findViewById(R.id.location);
        loc = text.getText().toString();
        text = (EditText)findViewById(R.id.description);
        desc = text.getText().toString();
        DatePicker dp = (DatePicker)findViewById(R.id.datePicker);
        TimePicker tp = (TimePicker)findViewById(R.id.timePicker);
        date = String.valueOf(dp.getDayOfMonth()) + " - " + String.valueOf(dp.getMonth() + 1) + " - " + String.valueOf(dp.getYear());
        tp.clearFocus();
        time = String.valueOf(tp.getCurrentHour()) + ":" + String.valueOf(tp.getCurrentMinute());
        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        if(db.insertData(name, loc, desc, date, time)) {
            Toast.makeText(TaskMenu.this, "Task Added", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(TaskMenu.this, "Task NOT Added", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void buttonDateAndTimeContainer(View view) {
        View date = (View) findViewById(R.id.datePicker);
        View time = (View) findViewById(R.id.timePicker);
        View description = (View) findViewById(R.id.description);
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) description.getLayoutParams();
        p.addRule(RelativeLayout.BELOW, R.id.timePicker);
        description.setLayoutParams(p);
        date.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
    }
}
