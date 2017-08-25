package com.jd.yyc.util;

import android.util.Log;

/**
 * LOG工具类
 */
public class L {


    public static final String TAG = "TAG";

    public static boolean mDebug = true;

    public static void initLog(boolean debug) {
        mDebug = debug;
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (mDebug) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (mDebug) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (mDebug) {
            Log.e(tag, msg);
        }
    }


}
