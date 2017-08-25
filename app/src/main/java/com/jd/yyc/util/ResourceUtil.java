package com.jd.yyc.util;

import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.jd.yyc.base.YYCApplication;

public class ResourceUtil {

    public static String getString(@StringRes int res, Object... args) {
        return YYCApplication.context.getResources().getString(res, args);
    }

    @ColorInt
    public static int getColor(@ColorRes int res) {
        return ContextCompat.getColor(YYCApplication.context, res);
    }

    public static int getTextSize(@DimenRes int res) {
        return YYCApplication.context.getResources().getDimensionPixelSize(res);
    }
}
