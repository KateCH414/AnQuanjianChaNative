package com.jd.yyc.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.jd.yyc.util.ScreenUtil;

/**
 *
 */

public class MaxScrollView extends ScrollView {


    public MaxScrollView(Context context) {
        super(context);
    }

    public MaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View child = getChildAt(0);
        child.measure(widthMeasureSpec, heightMeasureSpec);
        int width = child.getMeasuredWidth();
        int height = Math.min(child.getMeasuredHeight(), ScreenUtil.dip2px(435));
        setMeasuredDimension(width, height);
    }
}
