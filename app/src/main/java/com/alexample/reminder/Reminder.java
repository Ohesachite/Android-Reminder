package com.alexample.reminder;

public class Reminder {

    private int _id;
    private String _date;
    private String _time;
    private String _description;

    public Reminder() {
    }

    public Reminder(String date, String time, String description){
        this._date = date;
        this._time = time;
        this._description = description;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public void set_time(String _time) {
        this._time = _time;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public int get_id() {
        return _id;
    }

    public String get_date() {
        return _date;
    }

    public String get_time() {
        return _time;
    }

    public String get_description() {
        return _description;
    }
}
