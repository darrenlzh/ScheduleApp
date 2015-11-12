package com.example.scheduleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by darrenlim on 11/11/15.
 */
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

        return inflatedView;
    }
    public void setText(String text){

    }

}