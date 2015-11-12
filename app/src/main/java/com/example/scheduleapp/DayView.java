package com.example.scheduleapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.PriorityQueue;

public class DayView extends Fragment {
    private ArrayList<Task> _array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.dayview, container, false);
        _array = new ArrayList<>();
        TextView dayOfMonth = (TextView) inflatedView.findViewById(R.id.dayOfMonth);
        TextView dayofWeek = (TextView) inflatedView.findViewById(R.id.dayOfWeek);
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        String day = dayFormat.format(calendar.getTime());
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        dayOfMonth.setText(mDay+"");
        dayofWeek.setText(day);
        LinearLayout linearLayout = (LinearLayout)inflatedView.findViewById(R.id.dayVertical);
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        Cursor cur = db.getAllData();
        if(cur.getCount() != 0) {
            while (cur.moveToNext()) {
                String foundDay;
                foundDay = cur.getString(4);
                if(foundDay.equals("")) { continue; }
                foundDay = foundDay.substring(0, foundDay.indexOf(' '));
                if (foundDay.equals(mDay + "")) {
                    _array.add(new Task(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5)));
                }
            }
        }
        for(Task task : _array) {
            String buff;
            System.out.println(task._time);
            if(task._time.equals("")) {
                System.out.println("HERE");
                buff = "All Day     ";
            }
            else {
                buff = task._time + "       ";
            }
            buff = buff + task._title;
            Button button = new Button(getContext());
            button.setId(_array.indexOf(task));
            button.setAllCaps(false);
            button.setText(buff);
            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            button.setPadding(25, button.getPaddingTop(), button.getPaddingRight(), button.getPaddingBottom());
            linearLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button)v;
                    Task task = _array.get(b.getId());
                    AlertDialog alert = new AlertDialog.Builder(getContext()).create();
                    alert.setTitle(task._title);
                    alert.setMessage(
                            "Time: " + task._time + "\n\n" +
                                    "Location: " + task._location + "\n\n" +
                                    "Notes: " + task._desc);
                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "BACK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alert.show();
                }
            });

        }
        db.close();

        return inflatedView;
    }

//    public void taskOnClick(Button button) {
//
//        AlertDialog alert = new AlertDialog.Builder(getContext()).create();
//        alert.setTitle();
//
//
//
//    }

}