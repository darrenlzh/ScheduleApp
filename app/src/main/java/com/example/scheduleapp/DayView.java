package com.example.scheduleapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.PriorityQueue;

public class DayView extends Fragment {
    private ArrayList<Task> _array;
//    private ArrayList<Task> _todolist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.dayview, container, false);
        _array = new ArrayList<>();
//        _todolist = new ArrayList<>();
        TextView dayOfMonth = (TextView) inflatedView.findViewById(R.id.dayOfMonth);
        TextView dayofWeek = (TextView) inflatedView.findViewById(R.id.dayOfWeek);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        String day = dayFormat.format(MainActivity._calendar.getTime());
        int mDay = MainActivity._calendar.get(Calendar.DAY_OF_MONTH);

        dayOfMonth.setText(mDay + "");
        dayofWeek.setText(day);
        LinearLayout linearLayout = (LinearLayout) inflatedView.findViewById(R.id.dayVertical);
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
//        db.clearData();
        Cursor cur = db.getAllData();
        if (cur.getCount() != 0) {
            while (cur.moveToNext()) {
                String foundDay;
                foundDay = cur.getString(4);
                if (foundDay.equals("")) {
//                    _todolist.add(new Task(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), "", ""));
                    continue;
                }
                foundDay = foundDay.substring(0, foundDay.indexOf(' '));
                if (foundDay.equals(mDay + "")) {
                    _array.add(new Task(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4), cur.getString(5)));
                }
            }
        }
        for (Task task : _array) {
            String buff;
            System.out.println(task._time);
            if (task._time.equals("")) {
                buff = "All Day     ";
            } else {
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
                    if (!task._location.equals("")) showLocation.setText(task._location);
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
//                    RelativeLayout r = (RelativeLayout) view.findViewById(R.id.showTaskDialogContainer);
//                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) r.getLayoutParams();
//                    if(r)
                    alert.setView(view);
                    alert.show();
                }
            });

        }

        db.close();

        return inflatedView;
    }


}