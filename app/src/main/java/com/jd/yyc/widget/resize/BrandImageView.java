package com.jd.yyc.widget.resize;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by zhangweifeng1 on 2017/6/17.
 */

public class BrandImageView extends AppCompatImageView {

    public BrandImageView(Context context) {
        super(context);
    }

    public BrandImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BrandImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth() * 65 / 84);
    }
}
