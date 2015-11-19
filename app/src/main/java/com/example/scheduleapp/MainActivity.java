package com.example.scheduleapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    private ViewPager mViewPager;
    public DayView _dayView;
    public WeekView _weekView;
    public MonthView _monthView;

    public static HashMap<Integer, String> _numToMonth;
    public static int _lastFragInt;
    public static int _calenderDiff;
    protected static Calendar _calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(_numToMonth == null) { initHM(); }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(_lastFragInt);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), TaskMenu.class), 1);
            }
        });

        FloatingActionButton todo = (FloatingActionButton) findViewById(R.id.todofab);
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _lastFragInt = mViewPager.getCurrentItem();
                startActivityForResult(new Intent(getApplicationContext(), ToDoView.class),1);
            }
        });

        FloatingActionButton todayButton = (FloatingActionButton) findViewById(R.id.todayfab);
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_calenderDiff != 0) {
                    _lastFragInt = mViewPager.getCurrentItem();
                    _calendar.add(Calendar.DATE, _calenderDiff);
                    _calenderDiff = 0;
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        if(result == RESULT_OK) {
            this.finish();
            startActivity(getIntent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                case 0:
                    if(_dayView == null) {
                        return _dayView = new DayView();
                    }
                    else { return _dayView; }
                case 1:
                    if(_weekView == null) {
                        return _weekView = new WeekView();
                    }
                    else { return _weekView; }
                case 2:
                    if(_monthView == null) {
                        return _monthView = new MonthView();
                    }
                    else { return _monthView; }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Today";
                case 1:
                    return "This Week";
                case 2:
                    return "Monthly";
            }
            return null;
        }
    }

    public void nextDay(View v) {
        _calenderDiff += -1;
        _calendar.add(Calendar.DATE, 1);
        Fragment frg = _dayView;
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        frg = _weekView;
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    public void prevDay(View v) {
        _calenderDiff += 1;
        _calendar.add(Calendar.DATE, -1);
        Fragment frg = _dayView;
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        frg = _weekView;
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    public void nextWeek(View v) {
        _calenderDiff += -7;
        _calendar.add(Calendar.DATE, 7);
        Fragment frg = _weekView;
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        frg = _dayView;
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    public void prevWeek(View v) {
        _calenderDiff += 7;
        _calendar.add(Calendar.DATE, -7);
        Fragment frg = _weekView;
        final android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        frg = _dayView;
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }

    private void initHM() {
        _numToMonth = new HashMap<>();
        _numToMonth.put(1, "Jan");
        _numToMonth.put(2, "Feb");
        _numToMonth.put(3, "Mar");
        _numToMonth.put(4, "Apr");
        _numToMonth.put(5, "May");
        _numToMonth.put(6, "Jun");
        _numToMonth.put(7, "Jul");
        _numToMonth.put(8, "Aug");
        _numToMonth.put(9, "Sep");
        _numToMonth.put(10, "Oct");
        _numToMonth.put(11, "Nov");
        _numToMonth.put(12, "Dec");

    }

}