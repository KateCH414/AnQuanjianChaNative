package com.miko.zd.anquanjianchanative.Biz;
/*
 * Created by zd on 2016/11/1.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BizDBHelper extends SQLiteOpenHelper implements IBizDBInitItem{

    private static final String DATABASENAME = "RecordDB";
    private static final int VERSION = 2;
    private Context context;

    public BizDBHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS RecordTable(userName varchar(50),date varchar(10),orderNum INT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ItemTable(serialNum varchar(7),itemName varchar(300));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String checkString(String str) {
        String returnStr;
        if (str.contains("'")) {//判断字符串是否含有单引号
            returnStr = str.replace("'", "''");
            str = returnStr;
        }
        return str;
    }


    public void insertRecord(String userName, String date, int order) {
        getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS RecordTable(userName varchar(50),date varchar(10),orderNum INT);");
        ContentValues cv = new ContentValues();
        cv.put("userName", userName);
        cv.put("date", date);
        cv.put("orderNum", order);
        getWritableDatabase().insert("RecordTable", "date", cv);
    }

    public int getOrder() {
        getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS RecordTable(userName varchar(50),date varchar(10),orderNum INT);");
        Cursor cursor = getReadableDatabase().rawQuery("select*from RecordTable", null);
        int max = 0;
        while (cursor.moveToNext()) {
            if (cursor.getInt(2) > max) {
                max = cursor.getInt(2);
            }
        }
        return max + 1;
    }

    @Override
    public boolean hadInited() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS ItemTable(serialNum varchar(7),itemName varchar(300));");
        return db.rawQuery("select*from ItemTable",null).getCount()>0;
    }

    @Override
    public void insertItem(String serialNum, String itemName) {
        getWritableDatabase().execSQL("CREATE TABLE IF NOT EXISTS ItemTable(serialNum varchar(7),itemName varchar(300));");
        ContentValues cv = new ContentValues();
        cv.put("serialNum",serialNum);
        cv.put("itemName",itemName);
        getWritableDatabase().insert("ItemTable",null,cv);
    }
}
