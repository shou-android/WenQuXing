package com.example.wenquxing.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.Editable;

import com.example.wenquxing.SQL.DatabaseOpenHelper;
import com.example.wenquxing.javabean.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class EventInfoGetterAndSetter {

    public static ArrayList<Event> GetAllEvent(DatabaseOpenHelper helper){
        ArrayList<Event> ret = new ArrayList<>();
        //TODO:从数据库中获取数据
        @SuppressLint("Recycle")
        Cursor event = helper.getReadableDatabase().query("Event", new String[]{"*"}, null, null, null, null, null);
        while(event.moveToNext()){
            String content = event.getString(event.getColumnIndex("Content"));
            long Time = event.getLong(event.getColumnIndex("Time"));
            int id = event.getInt(event.getColumnIndex("EventID"));
            ret.add(new Event(content, new Date(Time), id));
        }
        return ret;
    }

    public static void SetEvent(DatabaseOpenHelper helper, String EventContent, Date Time){
        //获取新id
        int InsertID = 0;
        @SuppressLint("Recycle")
        Cursor event = helper.getReadableDatabase().query("Event", new String[]{"MAX(EventID) MAXID"}, null, null, null, null, null);
        if(event.moveToFirst()){
            InsertID = event.getInt(event.getColumnIndex("MAXID")) + 1;
        }
        //设置并插入到数据库中
        ContentValues values = new ContentValues();
        values.put("EventID", InsertID);
        values.put("Content", EventContent);
        values.put("Time", Time.getTime());
        helper.getWritableDatabase().insert("Event", "Time", values);
    }

    public static void DeleteEvent(DatabaseOpenHelper helper, Integer ID){
        helper.getWritableDatabase().delete("Event", "EventID=?", new String[]{String.valueOf(ID)});
    }

    public static void UpdateEvent(DatabaseOpenHelper helper, Integer ID, String EventContent, Date Time){
        DeleteEvent(helper, ID);
        SetEvent(helper, EventContent, Time);
    }
}
