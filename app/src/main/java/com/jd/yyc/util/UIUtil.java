package com.jd.yyc.util;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * UI工具类
 */
public class UIUtil {

    /**
     * 设置ViewGroup中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setViewGroupLayoutParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width, height);
        view.setLayoutParams(lp);
    }

    /**
     * 设置RelativeLayout中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setRelativeLayoutParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;

        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    public static void setRelativeLayoutParams2(View view, int width, int height) {
        if (width == 0 || height == 0) return;

        AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 设置MarginLayoutParams中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setMarginLayoutParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(width, height);
        view.setLayoutParams(lp);
    }


    /**
     * 设置FrameLayout中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setFrameLayoutParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 设置AbsListView中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setAbsListViewParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;

        AbsListView.LayoutParams lp = (AbsListView.LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 设置LinearLayout中的view宽高
     *
     * @param view   :视图
     * @param width  :宽
     * @param height :高
     */
    public static void setLinearLayoutParams(View view, int width, int height) {
        if (width == 0 || height == 0) return;

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
        lp.width = width;
        lp.height = height;
        view.setLayoutParams(lp);
    }

    /**
     * 设置html格式的颜色值
     *
     * @param text  需要设置的文本
     * @param color 需要设置的颜色：#ffffff
     * @return
     */
    public static String setHtmlColor(String text, String color) {
        return "<font color=\"" + color + "\">" + text + "</font>";
    }

}
