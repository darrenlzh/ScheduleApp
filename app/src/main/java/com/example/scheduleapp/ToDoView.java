package com.example.scheduleapp;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoView extends AppCompatActivity{

    private ArrayList<Task> _todolist;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todoview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        setTitle("To Do");

//        _todolist = new ArrayList<>();
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.dayVertical);
//        DatabaseHelper db = DatabaseHelper.getInstance(getContext());
////        db.clearData();
//        Cursor cur = db.getAllData();
//        if (cur.getCount() != 0) {
//            while (cur.moveToNext()) {
//                String foundDay;
//                foundDay = cur.getString(4);
//                if (foundDay.equals(""))
//                    _todolist.add(new Task(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3), "", ""));
//            }
//        }
//        for (Task task : _array) {
//            String buff;
//            System.out.println(task._time);
//            if (task._time.equals("")) {
//                buff = "All Day     ";
//            } else {
//                buff = task._time + "       ";
//            }
//            buff = buff + task._title;
//            Button button = new Button(getContext());
//            button.setId(_array.indexOf(task));
//            button.setAllCaps(false);
//            button.setText(buff);
//            button.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
//            button.setPadding(25, button.getPaddingTop(), button.getPaddingRight(), button.getPaddingBottom());
//            linearLayout.addView(button);
//            button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Button b = (Button) v;
//                    Task task = _array.get(b.getId());
//                    AlertDialog alert = new AlertDialog.Builder(getContext()).create();
//                    alert.setTitle(task._title);
//                    String output;
//                    if (task._time.equals("")) {
//                        output = "All Day\n\n";
//                    } else {
//                        output = "Time: " + task._time + "\n\n";
//                    }
//                    if (!task._location.equals("")) {
//                        output = output + "Location: " + task._location + "\n\n";
//                    }
//                    if (!task._desc.equals("")) {
//                        output = output + "Notes: " + task._desc;
//                    }
//                    alert.setMessage(output);
//                    alert.setButton(AlertDialog.BUTTON_NEUTRAL, "BACK",
//                            new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            });
//                    alert.show();
//                }
//            });
//
//        }
//
//        db.close();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
