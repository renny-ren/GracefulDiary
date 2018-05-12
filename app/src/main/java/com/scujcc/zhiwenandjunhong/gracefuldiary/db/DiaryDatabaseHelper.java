package com.scujcc.zhiwenandjunhong.gracefuldiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by junhongren on 12/5/2018.
 */

public class DiaryDatabaseHelper extends SQLiteOpenHelper{
    public static final String create_diary = "create table Diary(" +
            "id integer primary key autoincrement," +
            "title text" +
            "content text" +
            "mood text" +
            "weather text" +
            "date text" +
            "tag text";

    public DiaryDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_diary);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Diary");
        onCreate(db);
    }
}
