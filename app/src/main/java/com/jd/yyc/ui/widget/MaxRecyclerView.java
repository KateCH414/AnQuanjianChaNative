package com.jd.yyc.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jd.yyc.util.ScreenUtil;

/**
 *
 */

public class MaxRecyclerView extends RecyclerView {
    public MaxRecyclerView(Context context) {
        super(context);
    }

    public MaxRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        int width = getMeasuredWidth();
        int height = Math.min(getMeasuredHeight(), ScreenUtil.dip2px(435));
        setMeasuredDimension(width, height);
    }
}
