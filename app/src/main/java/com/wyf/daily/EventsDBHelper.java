package com.wyf.daily;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EventsDBHelper extends SQLiteOpenHelper{
    private static final int db_version = 1;
    private static final String table_name = "Events";
    private static final String db_name = "events.db";
    private static EventsDBHelper mHelper = null;
    private SQLiteDatabase mDB = null;

    private EventsDBHelper(Context context){
        super(context,db_name,null,db_version);
    }

    private EventsDBHelper(Context context,int version){
        super(context,db_name,null,version);
    }

    public static EventsDBHelper getInstance(Context context,int version){
        if(version > 0 && mHelper == null){
            mHelper = new EventsDBHelper(context,version);
        }else if(mHelper == null){
            mHelper = new EventsDBHelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase openReadLink(){
        if(mDB == null || mDB.isOpen() != true){
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    public SQLiteDatabase openWriteLink(){
        if(mDB == null || mDB.isOpen() != true){
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public void  closeLink(){
        if(mDB != null && mDB.isOpen() == true){
            mDB.close();
            mDB = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log
        Log.d("EventsDBHelper","onCreate");
        String drop_sql = "DROP TABLE IF EXISTS " + table_name + ";";
        db.execSQL(drop_sql);
        String create_sql = "CREATE TABLE IF NOT EXISTS " + table_name + "("+" _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Event VARCHAR(40) NOT NULL,Date VARCHAR(20) NOT NULL," +
                "TimeS VARCHAR(15) NOT NULL,TimeE VARCHAR(15) NOT NULL,Pattern INTEGER NOT NULL"+");";
        //Log
        Log.d("EventsDBHelper","create_sql:"+create_sql);
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(Event event){
        ArrayList<Event>eventArray = new ArrayList<Event>();
        eventArray.add(event);
        return insert(eventArray);
    }

    public long insert(ArrayList<Event> eventArray){
        long result = -1;
        for(int i = 0;i < eventArray.size();i++){
            Event event = eventArray.get(i);
            ArrayList<Event> tempArray = new ArrayList<Event>();
            //如果存在同样的事件，则更新
            // ！！！由于事件名允许更改，为避免导致重复，准备重写update方法（以id为思路）。
//            if(event.getEvent() != null && event.getEvent().length()>0){
//                String condition = String.format("name='%s'",event.getEvent());
//                tempArray = query(condition);
//                if(tempArray.size() > 0){
//                    update(event,condition);
//                    result = tempArray.get(0).rowid;
//                    continue;
//                }
//            }
            //单纯添加
            ContentValues cv = new ContentValues();
            cv.put("Event",event.getEvent());
            cv.put("Date",event.getDate());
            cv.put("TimeS",event.getTime_s());
            cv.put("TimeE",event.getTime_e());
            cv.put("Pattern",event.getPattern());
            result = mDB.insert(table_name,"",cv);
            if(result == -1){
                return result;
            }
        }
        return result;
    }

//    public int update(Event event,String condition){
//
//    }
//
//    public int update(Event event){
//
//    }

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
}