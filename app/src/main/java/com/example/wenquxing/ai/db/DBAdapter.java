package com.example.wenquxing.ai.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.wenquxing.ai.group.Exsuper;


public class DBAdapter {
    private static final String DB_NAME="exsuper.db";
    private static final String DB_TABLE="exsuperinfo";
    private static final int DB_VERSION=1;

    private static final String KEY_ID="_id";//
    private static final String KEY_NAME="sname";//
    private static final String KEY_SCO="scontext";//
    private static final String KEY_SST="sstar";//
    private static final String KEY_SFI="sfinish";//
    private static final String KEY_SLO="sloty";//
    private static final String KEY_TIM="timedata";//
    private static final String KEY_COM="complite";//
    private static final String KEY_TYP="type";//

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;


    public long insert(Exsuper exsuper){
        ContentValues newValues=new ContentValues();

        newValues.put(KEY_NAME,exsuper.getSname());
        //newValues.put(KEY_ID,exsuper.getid());
        newValues.put(KEY_TIM,exsuper.getTimedata());
        newValues.put(KEY_COM,exsuper.getcomplite());
        newValues.put(KEY_SCO,exsuper.getScontext());
        newValues.put(KEY_SFI,exsuper.getSfinish());
        newValues.put(KEY_SLO,exsuper.getSloty());
        newValues.put(KEY_SST,exsuper.getSstar());
        newValues.put(KEY_TYP,exsuper.getType());

        return db.insert(DB_TABLE,null,newValues);//insert(DB_TABLE,null,newValues);
    }
    public long deleteAllData(){
        return db.delete(DB_TABLE,null,null);
    }
    public long deleteOneData(long id){
        return db.delete(DB_TABLE,KEY_ID+"=",null);
    }
    public Exsuper[] queryAllData(){
        Cursor results=db.query(DB_TABLE,new String[]{KEY_ID,KEY_NAME,KEY_COM,KEY_SCO,KEY_SFI,KEY_SLO,KEY_SST,KEY_TIM,KEY_TYP},
                null,null,null,null,null);
        return ConvertToExsuper(results);
    }
    public Exsuper[] queryOneDate(int timedate,int type){
        Cursor results=db.query(DB_TABLE,new String[]{KEY_ID,KEY_NAME,KEY_COM,KEY_SCO,KEY_SFI,KEY_SLO,KEY_SST,KEY_TIM,KEY_TYP},
                KEY_TIM+"="+timedate +" and "+KEY_TYP+"="+type,null,null,null,null);
        return ConvertToExsuper(results);
    }
    public long updateOneData(long id, Exsuper exsuper){
        ContentValues updateValues=new ContentValues();
        updateValues.put(KEY_NAME,exsuper.getSname());
        //updateValues.put(KEY_ID,exsuper.getid());
        updateValues.put(KEY_TIM,exsuper.getTimedata());
        updateValues.put(KEY_COM,exsuper.getcomplite());
        updateValues.put(KEY_SCO,exsuper.getScontext());
        updateValues.put(KEY_SFI,exsuper.getSfinish());
        updateValues.put(KEY_SLO,exsuper.getSloty());
        updateValues.put(KEY_SST,exsuper.getSstar());
        updateValues.put(KEY_TYP,exsuper.getType());

        return db.update(DB_TABLE,updateValues,KEY_ID+"="+id,null);
    }

    private Exsuper[] ConvertToExsuper(Cursor cursor){
        int resultCounts=cursor.getCount();
        if(resultCounts==0||!cursor.moveToFirst()){
            return null;
        }
        Exsuper[] exsupers=new Exsuper[resultCounts];
        for(int i=0;i<resultCounts;i++){
            exsupers[i]=new Exsuper();
            exsupers[i].setSid(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            exsupers[i].setType(cursor.getInt(cursor.getColumnIndex(KEY_TYP)));
            exsupers[i].setSstar(cursor.getInt(cursor.getColumnIndex(KEY_SST)));
            exsupers[i].setSfinish(cursor.getInt(cursor.getColumnIndex(KEY_SFI)));
            exsupers[i].setTimedata(cursor.getInt(cursor.getColumnIndex(KEY_TIM)));
            exsupers[i].setSname(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            exsupers[i].setScontext(cursor.getString(cursor.getColumnIndex(KEY_SCO)));
            exsupers[i].setcomplite1(cursor.getInt(cursor.getColumnIndex(KEY_COM)));
            exsupers[i].setSloty(cursor.getString(cursor.getColumnIndex(KEY_SLO)));
            cursor.moveToNext();
        }
        return exsupers;
    }
    private static class DBOpenHelper extends SQLiteOpenHelper{
        public DBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }
        String DB_CREATE= "CREATE TABLE "+DB_TABLE+"("+KEY_ID+" INTEGER PRIMARY KEY autoincrement, "+KEY_NAME+" txet not null, "+KEY_SCO+" text not null,"+KEY_SST+" integer, "+KEY_SFI+" integer, "+KEY_SLO+" text not null, "+KEY_TIM+" integer, "+KEY_TYP+" interger,"+KEY_COM+" interger );";
        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL(DB_CREATE);
            onCreate(_db);
        }

    }
    public DBAdapter(Context _context){
        context=_context;
    }

    public void open()throws SQLiteException{
        dbOpenHelper=new DBOpenHelper(context,DB_NAME,null,DB_VERSION);
        try{
            db=dbOpenHelper.getWritableDatabase();
        }catch(SQLiteException ex){
            db=dbOpenHelper.getWritableDatabase();
        }
    }
    public void close(){
        if(db!=null){
            db.close();
            db=null;
        }
    }
}