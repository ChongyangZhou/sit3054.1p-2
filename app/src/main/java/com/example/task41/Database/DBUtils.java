package com.example.task41.Database;



import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.task41.Bean.RichengBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@SuppressLint("Range")
public class DBUtils {
    private String tableName = null;
    private int addId = 0;
    public static String username = null;
    public static String password = null;
    private List<RichengBean> list;
    private List<String> list1;
    private DbOpenHelper dbOpenHelper;
    public DBUtils(Context context){
        dbOpenHelper = new DbOpenHelper(context,"Plan",null,1);
    }
public long AddPlan(String title, String jihua, String date) {
    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("title", title);
    values.put("jihua", jihua);
    values.put("date", date);
    long id = db.insert("pln", null, values);
    db.close();
    return id;
}
    public List<RichengBean> QueryPlan() {
        List<RichengBean> richengList = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        String[] columns = {"planid", "title",  "jihua",  "date"};

        Cursor cursor = db.query("pln", columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int planId = cursor.getInt(cursor.getColumnIndex("planid"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String jihua = cursor.getString(cursor.getColumnIndex("jihua"));
                String planDateString = cursor.getString(cursor.getColumnIndex("date"));

                // 将日期字符串转换为日期对象
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date planDate = null;
                try {
                    planDate = dateFormat.parse(planDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                RichengBean richengBean = new RichengBean(planId, title,  jihua, planDate);
                richengList.add(richengBean);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        Collections.sort(richengList, new Comparator<RichengBean>() {
            @Override
            public int compare(RichengBean bean1, RichengBean bean2) {
                return bean2.getDate().compareTo(bean1.getDate()); // 按日期降序排序
            }
        });

        return richengList;
    }



public int UpdatePlan(String title, String jihua, String date,int id) {
    int a = 0;
    SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put("title",title);
    values.put("jihua", jihua);
    values.put("date", date);
    String whereClause = "planid = ?";
    String[] whereArgs = {String.valueOf(id)};
    a = db.update("pln", values, whereClause, whereArgs);

    db.close();
    return a;
}
    public int deletePlan(int planId) {
        int rowsAffected = 0;
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String whereClause = "planid = ?";
        String[] whereArgs = {String.valueOf(planId)};

        rowsAffected = db.delete("pln", whereClause, whereArgs);

        db.close();
        return rowsAffected;
    }


}

