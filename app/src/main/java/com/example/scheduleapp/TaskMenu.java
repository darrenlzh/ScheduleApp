package com.example.scheduleapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TaskMenu extends AppCompatActivity {

    private boolean showDateAndTime = false;
    private  boolean timeIsVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_menu);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        setTitle(R.string.newReminderTitle);

    }

    public void buttonAddTask(View view) {
        Button button = (Button) view;
        String name, loc, desc, date, time;
        EditText text = (EditText)findViewById(R.id.name);
        if((name = text.getText().toString()).equals("")) {
            Toast.makeText(TaskMenu.this, R.string.noTitleError, Toast.LENGTH_LONG).show();
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
        if(showDateAndTime) {
            DatePicker dp = (DatePicker) findViewById(R.id.datePicker);
            TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
            date = String.valueOf(dp.getDayOfMonth()) + " - " + String.valueOf(dp.getMonth() + 1) + " - " + String.valueOf(dp.getYear());
            tp.clearFocus();
            if(!timeIsVisible) {
                String hour = String.valueOf(tp.getCurrentHour());
                String minute = String.valueOf(tp.getCurrentMinute());
                if (hour.length() == 1) {
                    hour = "0" + hour;
                }
                if (minute.length() == 1) {
                    minute = "0" + minute;
                }
                time = hour + ":" + minute;
            }
            else time="";
        } else {
            date =""; time = "";
        }
        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());
        if(db.insertData(name, loc, desc, date, time)) {
            Toast.makeText(TaskMenu.this, R.string.taskAdded, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(TaskMenu.this, R.string.taskNotAdded, Toast.LENGTH_LONG).show();
        }
        setResult(RESULT_OK, null);
        db.close();
        finish();
    }

    public void buttonDateAndTimeContainer(View view) {
        View date = findViewById(R.id.datePicker);
        View time = findViewById(R.id.timePicker);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkAllDay);
        View description = findViewById(R.id.description);
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) description.getLayoutParams();
        if(!showDateAndTime) {
            p.addRule(RelativeLayout.BELOW, R.id.checkAllDay);
            description.setLayoutParams(p);
            date.setVisibility(View.VISIBLE);
            if(!timeIsVisible) time.setVisibility(View.VISIBLE);
            checkBox.setVisibility(View.VISIBLE);
            showDateAndTime = true;
        } else {
            p.addRule(RelativeLayout.BELOW, R.id.dateAndTimeButton);
            description.setLayoutParams(p);
            date.setVisibility(View.GONE);
            time.setVisibility(View.INVISIBLE);
            checkBox.setVisibility(View.INVISIBLE);
            showDateAndTime = false;
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                View time = findViewById(R.id.timePicker);
                CheckBox checkBox = (CheckBox) findViewById(R.id.checkAllDay);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) checkBox.getLayoutParams();
                if (isChecked) {
                    lp.addRule(RelativeLayout.BELOW, R.id.datePicker);
                    checkBox.setLayoutParams(lp);
                    time.setVisibility(View.INVISIBLE);
                    timeIsVisible = true;
                } else {
                    lp.addRule(RelativeLayout.BELOW, R.id.timePicker);
                    time.setVisibility(View.VISIBLE);
                    checkBox.setLayoutParams(lp);
                    timeIsVisible = false;
                }

            }
        });
    }
}
