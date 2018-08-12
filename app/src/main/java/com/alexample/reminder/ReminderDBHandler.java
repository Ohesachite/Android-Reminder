package com.alexample.reminder;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;

public class ReminderDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reminders.db";
    public static final String TABLE_REMINDERS = "reminders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DESCRIPTION = "description";
    private static final String LOG_TAG = "log_tag";

    public ReminderDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_REMINDERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT " +
                ");";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDERS);
        onCreate(sqLiteDatabase);
    }

    public void addReminder(Reminder reminder){
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, reminder.get_date());
        values.put(COLUMN_TIME, reminder.get_time());
        values.put(COLUMN_DESCRIPTION, reminder.get_description());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_REMINDERS, null, values);
        db.close();
    }

    public void deleteReminder(String date, String time, String description){
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_REMINDERS + " WHERE " +
                COLUMN_DATE + "=\"" + date + "\"" +
                " AND " + COLUMN_TIME + "=\"" + time + "\"" +
                " AND " + COLUMN_DESCRIPTION + "=\"" + description + "\"";
        Log.i(LOG_TAG, query);
        db.execSQL(query);
        db.close();
    }

    public ArrayList<String> databaseToStringArrayList(){
        ArrayList<String> list = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_REMINDERS;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("date")) != null){
                String s = "Date: " + c.getString(c.getColumnIndex("date")) +
                        " Time: " + c.getString(c.getColumnIndex("time")) +
                        " Description: " + c.getString(c.getColumnIndex("description"));
                list.add(s);
                Log.i(LOG_TAG, s);
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }

    public ArrayList<Reminder> databaseToReminderArrayList(){
        ArrayList<Reminder> list = new ArrayList<Reminder>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_REMINDERS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("date")) != null){
                list.add(new Reminder(c.getString(c.getColumnIndex("date")),
                        c.getString(c.getColumnIndex("time")),
                        c.getString(c.getColumnIndex("description"))
                ));
            }
            c.moveToNext();
        }
        db.close();
        return list;
    }
}
