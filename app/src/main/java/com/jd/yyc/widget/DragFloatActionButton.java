package com.jd.yyc.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;

import com.jd.yyc.util.ScreenUtil;


public class DragFloatActionButton extends AppCompatImageButton {

    private int screenWidth;
    private int screenHeight;
    private int screenWidthHalf;
    private int statusHeight;

    public DragFloatActionButton(Context context) {
        super(context);
        init();
    }

    public DragFloatActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragFloatActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        screenWidth = ScreenUtil.getScreenWidth(getContext());
        screenWidthHalf = screenWidth / 2;
        screenHeight = ScreenUtil.getScreenHeight(getContext()) - ScreenUtil.dip2px(getContext(), 70);
        statusHeight = ScreenUtil.getStatusHeight(getContext());
    }

    private int lastX;
    private int lastY;

    private boolean isDrag;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isDrag = false;
                getParent().requestDisallowInterceptTouchEvent(true);
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                isDrag = true;
                //计算手指移动了多少
                int dx = rawX - lastX;
                int dy = rawY - lastY;
                //这里修复一些华为手机无法触发点击事件的问题
                int distance = (int) Math.sqrt(dx * dx + dy * dy);
                if (distance <= 20) {
                    isDrag = false;
                    break;
                }
                float x = getX() + dx;
                float y = getY() + dy;
                //检测是否到达边缘 左上右下
                x = x < 0 ? 0 : x > screenWidth - getWidth() ? screenWidth - getWidth() : x;
                y = y < statusHeight ? statusHeight : y + getHeight() > screenHeight ? screenHeight - getHeight() : y;
                setX(x);
                setY(y);
                lastX = rawX;
                lastY = rawY;
                //Log.i("getX="+getX()+";getY="+getY()+";screenHeight="+screenHeight);
                break;
            case MotionEvent.ACTION_UP:
                if (isDrag) {
                    //恢复按压效果
                    setPressed(false);
                    if (rawX >= screenWidthHalf) {
                        animate().setInterpolator(new DecelerateInterpolator())
                                .setDuration(500)
                                .xBy(screenWidth - getWidth() - getX())
                                .start();
                    } else {
                        ObjectAnimator oa = ObjectAnimator.ofFloat(this, "x", getX(), 0);
                        oa.setInterpolator(new DecelerateInterpolator());
                        oa.setDuration(500);
                        oa.start();
                    }
                }
                break;
        }
        //如果是拖拽则消耗事件，否则正常传递即可。
        return isDrag || super.onTouchEvent(event);
    }
}