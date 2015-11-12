package com.example.scheduleapp;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.PriorityQueue;

public class DayView extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.dayview, container, false);

        TextView dayOfMonth = (TextView) inflatedView.findViewById(R.id.dayOfMonth);
        TextView monthName = (TextView) inflatedView.findViewById(R.id.dayOfWeek);
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MMM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        String month = format.format(calendar.getTime());
        String day = dayFormat.format(calendar.getTime());
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        dayOfMonth.setText(mDay+"");
        monthName.setText(day);
        LinearLayout linearLayout = (LinearLayout)inflatedView.findViewById(R.id.dayVertical);
        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
        Cursor cur = db.getAllData();
        if(cur.getCount() != 0) {
            String buff;
            while(cur.moveToNext()) {
                if(cur.getString(5).equals("")){
                    buff = "All Day     ";
                }
                else {
                    buff = cur.getString(5) + "       ";
                }
                buff = buff + cur.getString(1);
                Button button = new Button(getContext());
                button.setAllCaps(false);
                button.setText(buff);
                button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                button.setPadding(25,button.getPaddingTop(), button.getPaddingRight(), button.getPaddingBottom());
                linearLayout.addView(button);
            }
        }
        db.close();

        return inflatedView;
    }
    public void setText(String text){

    }

}