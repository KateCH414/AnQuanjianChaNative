package com.jd.yyc.search.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.jd.yyc.util.L;

import java.util.ArrayList;

/**
 * Created by tangh on 16/3/28.
 */
public class HistoryDBHelper {

    public static final String TAG = HistoryDBHelper.class.getSimpleName();

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "search.db";
    private static final String TABLE_NAME = "key";
    private static MySqlLiteHelper sqlLiteHelper = null;
    private static SQLiteDatabase db = null;

    public static synchronized void add(Context context, String key) {
        if (sqlLiteHelper == null) {
            sqlLiteHelper = new MySqlLiteHelper(context, DB_NAME, null, DB_VERSION);
            db = sqlLiteHelper.getWritableDatabase();
        }
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_NAME, new String[]{"key"}, "key = ?", new String[]{key}, null, null, null);
            if (cursor.moveToNext()) {
                L.i(TAG, "=====>已存在记录,删除后重新插入");
                int count = db.delete(TABLE_NAME, "key = ?", new String[]{key});
                if (count > 0) {
                    L.i(TAG, "=====>已存在记录,删除成功");
                }
            } else {
                L.i(TAG, "=====>不存在历史记录,直接插入新记录");
            }
            ContentValues values = new ContentValues();
            values.put("key", key);
            long id = db.insert(TABLE_NAME, null, values);
            if (id > 0) {
                L.i(TAG, "=====>成功插入一条记录");
            }

            cursor = db.rawQuery("select key from " + TABLE_NAME + " order by id asc limit 0,20 ", null);
            if (cursor.getCount() > 20) {
                if (cursor.moveToNext()){
                    long id_delete = db.delete(TABLE_NAME, "key = ?", new String[]{cursor.getString(0)});
                    L.i(TAG, "=====>成功删除第一条记录");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static synchronized void delete(Context context, String key) {
        if (sqlLiteHelper == null) {
            sqlLiteHelper = new MySqlLiteHelper(context, DB_NAME, null, DB_VERSION);
            db = sqlLiteHelper.getWritableDatabase();
        }
        try {
            int count = db.delete(TABLE_NAME, "key = ?", new String[]{key});
            if (count > 0) {
                L.i(TAG, "=====>成功删除记录:" + key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized ArrayList<String> getHistoryKeys(Context context) {
        if (sqlLiteHelper == null) {
            sqlLiteHelper = new MySqlLiteHelper(context, DB_NAME, null, DB_VERSION);
            db = sqlLiteHelper.getWritableDatabase();
        }
        Cursor cursor = null;
        ArrayList<String> data = new ArrayList<>();
        try {
            cursor = db.rawQuery("select key from " + TABLE_NAME + " order by id desc limit 0,20 ", null);
            while (cursor.moveToNext()) {
                data.add(cursor.getString(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return data;
    }


    public static synchronized void clear(Context context) {
        if (sqlLiteHelper == null) {
            sqlLiteHelper = new MySqlLiteHelper(context, DB_NAME, null, DB_VERSION);
            db = sqlLiteHelper.getWritableDatabase();
        }
        try {
            db.delete(TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final static class MySqlLiteHelper extends SQLiteOpenHelper {

        public MySqlLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table if not exists " +
                    TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT (0),key TEXT NOT NULL)"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}
