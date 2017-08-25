package com.jd.yyc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.PvInterfaceParam;

;

/**
 * item页底部的webview
 */
public class ItemWebView extends WebView {
    public float oldY;
    private int t;
    private float oldX;

    public ItemWebView(Context context) {
        super(context);
    }

    public ItemWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float Y = ev.getY();
                float Ys = Y - oldY;
                float X = ev.getX();

                //滑动到顶部让父控件重新获得触摸事件
                Log.e("ttt", "Ys=" + Ys + "    t:" + t);
                if (Ys > 0 && t == 0) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);

                }

                break;

            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                oldY = ev.getY();
                oldX = ev.getX();

                break;

            case MotionEvent.ACTION_UP:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.t = t;
        PvInterfaceParam param = new PvInterfaceParam();
        param.page_name = "更多商品详情页";
        param.page_id = "0003";
        JDMaUtil.sendPVData(param);
        super.onScrollChanged(l, t, oldl, oldt);
    }

}
