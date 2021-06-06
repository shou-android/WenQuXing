package com.example.wenquxing.Utils;

import android.annotation.SuppressLint;
import android.database.Cursor;

import com.example.wenquxing.SQL.DatabaseOpenHelper;
import com.example.wenquxing.javabean.Event;

import java.util.ArrayList;
import java.util.Date;

public class EventInfoGetterAndSetter {

    public static ArrayList<Event> GetEvent(DatabaseOpenHelper helper){
        ArrayList<Event> ret = new ArrayList<>();
        //TODO:从数据库中获取数据
        @SuppressLint("Recycle")
        Cursor event = helper.getReadableDatabase().query("Event", new String[]{"*"}, null, null, null, null, null);
        while(event.moveToNext()){
            String content = event.getString(event.getColumnIndex("Content"));
            long Time = event.getLong(event.getColumnIndex("Time"));

            ret.add(new Event(content, new Date(Time)));
        }
        return ret;
    }
}
