package com.example.task41.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

    private Context mContext;
    // 创建 comments 表的 SQL 语句
    // 创建 userbean 表
    String createUserBeanTable = "CREATE TABLE userbean (" +
            "name TEXT, " +
            "password TEXT" +
            ")";

    // 创建 plan 表
    String createPlanTable = "CREATE TABLE pln (" +
            "planid INTEGER PRIMARY KEY, " +
            "title TEXT, " +
            "jihua TEXT, " +
            "date TEXT " +
            ")";
    String createLeiBeanTable = "CREATE TABLE leibie (" +
            "name TEXT, " +
            "leibie TEXT" +
            ")";





    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(createUserBeanTable);
        sqLiteDatabase.execSQL(createPlanTable);
        sqLiteDatabase.execSQL(createLeiBeanTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}

