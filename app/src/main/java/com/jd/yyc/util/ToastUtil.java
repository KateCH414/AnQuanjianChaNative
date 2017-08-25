package com.jd.yyc.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.jd.yyc.base.YYCApplication;

/**
 * Toast提示工具类
 */
public class ToastUtil {

    private static final int TOAST_DURATION = Toast.LENGTH_SHORT;


    /**
     * 显示Toast
     *
     * @param context : 上下文
     * @param msg     ：提示信息
     */
    public static void show(Context context, String msg) {
        show(context, msg, TOAST_DURATION);
    }

    public static void show(String msg) {
        show(YYCApplication.context, msg, TOAST_DURATION);
    }

    /**
     * 显示Toast
     *
     * @param context : 上下文
     * @param msgId   : 提示信息id
     */
    public static void show(Context context, int msgId) {
        show(context, msgId, TOAST_DURATION);
    }

    /**
     * 显示Toast
     *
     * @param context  : 上下文
     * @param msg      : 提示信息
     * @param duration ：显示时长
     */
    public static void show(Context context, String msg, int duration) {
        if (context != null) {
            Toast.makeText(context, msg, duration).show();
        }
    }

    /**
     * 显示Toast
     *
     * @param context  : 上下文
     * @param msgId    : 提示信息id
     * @param duration ：显示时长
     */
    public static void show(Context context, int msgId, int duration) {
        if (context != null) {
            Toast.makeText(context, msgId, duration).show();
        }
    }

    public static void show(Context context, View view) {
        Toast t = new Toast(context);
        t.setDuration(Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.setView(view);
        t.show();
    }
}
