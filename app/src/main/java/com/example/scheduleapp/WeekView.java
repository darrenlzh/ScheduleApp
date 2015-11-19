package com.example.scheduleapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class WeekView extends Fragment {
    private ArrayList<Task> _array;
    private static HashMap<String, Integer> _dayToInt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.weekview, container, false);

        _array = new ArrayList<>();
        if (_dayToInt == null) {
            setDayToInt();
        }

        TextView daysOfWeek = (TextView) inflatedView.findViewById(R.id.daysOfWeek);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");

        String dayWeek = dayFormat.format(MainActivity._calendar.getTime());

        int diff = _dayToInt.get(dayWeek);
        MainActivity._calendar.add(Calendar.DATE, diff);
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        LinearLayout linearLayout = (LinearLayout) inflatedView.findViewById(R.id.weekVertical);

        for (int i = 0; i < 7; i++) {
            dayWeek = dayFormat.format(MainActivity._calendar.getTime());
            String dayMonth = String.valueOf(MainActivity._calendar.get(Calendar.DAY_OF_MONTH));
            String monthYear = String.valueOf(MainActivity._calendar.get(Calendar.MONTH) + 1);
            if (i == 0) {
                daysOfWeek.setText(monthYear + "/" + dayMonth);
            }
            if (i == 6) {
                daysOfWeek.setText(daysOfWeek.getText() + " - " + monthYear + "/" + dayMonth);
            }

            TextView dayOfWeek = new TextView(getContext());
            dayOfWeek.setAllCaps(false);
            dayOfWeek.setText(dayWeek);
            dayOfWeek.setGravity(Gravity.CENTER);
            linearLayout.addView(dayOfWeek);

            Cursor cur = db.getAllData();
            if (cur.getCount() != 0) {
                while (cur.moveToNext()) {
                    String foundDate = cur.getString(4);
                    if (foundDate.equals("")) {
                        continue;
                    }

                    String foundDay = foundDate.substring(0, foundDate.indexOf(' '));
                    String foundMonth = foundDate.substring(foundDate.indexOf('-') + 2);
                    foundMonth = foundMonth.substring(0, foundMonth.indexOf(' '));
                    if (foundDay.equals(dayMonth) && foundMonth.equals(monthYear)) {
                        Task task = new Task(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5));
                        _array.add(task);
                        String buff;
                        if (task._time.equals("")) {
                            buff = "All Day     ";
                        } else {
                            buff = task._time + "       ";
                        }
                        buff = buff + cur.getString(1);
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
                                Button b = (Button) v;
                                Task task = _array.get(b.getId());
                                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                View view = inflater.inflate(R.layout.show_task_dialog, null);
                                TextView showTitle = (TextView) view.findViewById(R.id.showTaskDialogTitle);
                                showTitle.setText(task._title);
                                TextView showLocation = (TextView) view.findViewById(R.id.showTaskDialogLocation);
                                TextView showNotes = (TextView) view.findViewById(R.id.showTaskDialogNotes);
                                TextView showTime = (TextView) view.findViewById(R.id.showTaskDialogTime);
                                TextView showNotesLabel = (TextView) view.findViewById(R.id.notesLabel);
                                ScrollView showNotesView = (ScrollView) view.findViewById(R.id.showNotesView);
                                ImageView showLocationIcon = (ImageView) view.findViewById(R.id.locationIcon);
                                if (task._time.equals("")) showTime.setText(R.string.allDay);
                                else showTime.setText(task._time);
                                if (!task._location.equals(""))
                                    showLocation.setText(task._location);
                                else {
                                    showLocationIcon.setVisibility(View.GONE);
                                    showLocation.setVisibility(View.GONE);
                                }
                                if (!task._desc.equals("")) showNotes.setText(task._desc);
                                else {
                                    showNotesLabel.setVisibility(View.GONE);
                                    showNotesView.setVisibility(View.GONE);
                                }
                                alert.setNeutralButton("BACK",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alert.setView(view);
                                alert.show();
                            }
                        });
                    }
                }
            }
            MainActivity._calendar.add(Calendar.DATE, 1);
        }
        MainActivity._calendar.add(Calendar.DATE, (-7 + (-1 * diff)));
        db.close();

        return inflatedView;
    }

    private void setDayToInt() {
        _dayToInt = new HashMap<>();
        _dayToInt.put("Sunday", 0);
        _dayToInt.put("Monday", -1);
        _dayToInt.put("Tuesday", -2);
        _dayToInt.put("Wednesday", -3);
        _dayToInt.put("Thursday", -4);
        _dayToInt.put("Friday", -5);
        _dayToInt.put("Saturday", -6);
    }
}