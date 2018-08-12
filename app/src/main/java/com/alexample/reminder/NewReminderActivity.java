package com.alexample.reminder;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;

public class NewReminderActivity extends AppCompatActivity {


    public static final String REMINDER_DATE = "reminder_date";
    public static final String REMINDER_TIME = "reminder_time";
    public static final String REMINDER_DESC = "reminder_desc";
    //private ArrayList<String> Description;
    private static String d = "Select Date";
    private static String t = "Select Time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_reminder);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //Description = intent.getStringArrayListExtra(MainActivity.EXTRA_ARRAY);

        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }

    public void saveReminder(View v){
        Intent intent = new Intent(this, MainActivity.class);
        EditText descrip = (EditText) findViewById(R.id.description);
        String date = d;
        String time = t;
        String des = descrip.getText().toString();
        intent.putExtra(REMINDER_DATE, date);
        intent.putExtra(REMINDER_TIME, time);
        intent.putExtra(REMINDER_DESC, des);
        startActivity(intent);
        finish();
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            if(!t.equals("Select Time")){
                String[] hAndM = t.split(":");
                hour = Integer.parseInt(hAndM[0]);
                minute = Integer.parseInt(hAndM[1]);
            }

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            if(minute < 10)
                t = hourOfDay + ":0" + minute;
            else
                t = hourOfDay + ":" + minute;
        }

    }

    public void showTimePickerDialog(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
        Button time = (Button) findViewById(R.id.time);
        time.setText(t);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            if(!d.equals("Select Date")){
                String[] ymd = d.split("/");
                year = Integer.parseInt(ymd[0]);
                month = Integer.parseInt(ymd[1]);
                day = Integer.parseInt(ymd[2]);
            }

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            d = year + "/" + month + "/" + day;
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
        Button date = (Button) findViewById(R.id.day);
        date.setText(d);
    }
}
