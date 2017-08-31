package com.wyf.daily;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.CalendarContract;

import java.util.ArrayList;

public class EventsDao{
    private SQLiteDatabase mDB;

    public EventsDao(Context context){
        EventsDBHelper mHelper = new EventsDBHelper(context);
        mDB = mHelper.getReadableDatabase();
    }

    public void insert(Event event){
        String sql = "insert into Events (Event,Date,TimeS,TimeE,Pattern) values('"+event.getEvent()+"'," +
                "'"+event.getDate()+"','"+event.getTime_s()+"','"+event.getTime_e()+"','"+event.getPattern()+"')";
        mDB.execSQL(sql);
    }

    public void update(String item,String value,int id){
        String sql = "update Events set '"+item+"' = '"+value+"’"+id;
        mDB.execSQL(sql);
    }

    public void delete(int id){
        String sql = "delete from Events where id ="+id;
        mDB.execSQL(sql);
    }

    public ArrayList<Event> AllEvents(Context context){
        String sql = "select * from Events";
        EventsDBHelper helper = new EventsDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ArrayList<Event> AllEvents = new ArrayList<Event>();
        Event event = null;
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            event = new Event(cursor.getString(cursor.getColumnIndex("Event")),
                    cursor.getString(cursor.getColumnIndex("Date")),
                    cursor.getString(cursor.getColumnIndex("TimeS")),
                    cursor.getString(cursor.getColumnIndex("TimeE")),
                    cursor.getInt(cursor.getColumnIndex("Pattern")));
            AllEvents.add(event);
        }
        return AllEvents;
    }

//    public static EventsDBHelper getInstance(Context context, int version) {
//        if (version > 0 && mHelper == null) {
//            mHelper = new EventsDBHelper(context, version);
//        } else if (mHelper == null) {
//            mHelper = new EventsDBHelper(context);
//        }
//        return mHelper;
//    }
//
//    public SQLiteDatabase openReadLink() {
//        if (mDB == null || mDB.isOpen() != true) {
//            mDB = mHelper.getReadableDatabase();
//        }
//        return mDB;
//    }
//
//    public SQLiteDatabase openWriteLink() {
//        if (mDB == null || mDB.isOpen() != true) {
//            mDB = mHelper.getWritableDatabase();
//        }
//        return mDB;
//    }
//
//    public void closeLink() {
//        if (mDB != null && mDB.isOpen() == true) {
//            mDB.close();
//            mDB = null;
//        }
//    }
}
