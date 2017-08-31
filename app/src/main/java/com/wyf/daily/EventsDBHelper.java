package com.wyf.daily;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class EventsDBHelper extends SQLiteOpenHelper{
    private static final int db_version = 1;
    private static final String table_name = "Events";
    private SQLiteDatabase db;

    public EventsDBHelper(Context context){
        super(context,"events.db",null,db_version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + table_name + "("+" _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Event VARCHAR(40) NOT NULL,Date VARCHAR(20) NOT NULL," +
                "TimeS VARCHAR(15) NOT NULL,TimeE VARCHAR(15) NOT NULL,Pattern INTEGER NOT NULL"+");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor query(){
        SQLiteDatabase db =getWritableDatabase();
        Cursor c=db.query(table_name, null, null, null, null, null, null);
        return c;
    }

    public void onClose() {
        if (db != null) {
            db.close();
        }
    }
}