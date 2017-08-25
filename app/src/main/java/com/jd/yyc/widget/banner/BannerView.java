package com.jd.yyc.widget.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.jd.yyc.R;
import com.jd.yyc.util.UIUtil;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;


/**
 * Created by wf on 16/1/4.
 * 无限轮播View
 */
public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener, Runnable {

    public static final String TAG = BannerView.class.getSimpleName();
    public static final int AUTO_SCROLL_INTERVAL = 1000 * 3;
    int currentItem = 0;
    private OnIndexListener onIndexListener;
    private int mNextPage = -1;
    //记录上一次滑动的positionOffsetPixels值
    private int lastValue = -1;
    private int lastPostition = -1;
    private ViewPager viewPager;
    private BannerAdapter bannerAdapter;
    private CircleIndicator pageIndicator;
    boolean isLeft;

    private OnPageChangedListener onPageChangedListener;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pageIndicator = (CircleIndicator) findViewById(R.id.page_indicator);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void run() {
        if (viewPager != null) {
            viewPager.setCurrentItem(mNextPage, true);
            if (bannerAdapter != null && onIndexListener != null) {
                onIndexListener.onIndex(mNextPage % bannerAdapter.getRealChildCount());
            }
        }
    }

    /**
     * @param bannerAdapter
     */
    public void setAdapter(BannerAdapter bannerAdapter) {
        this.bannerAdapter = bannerAdapter;
        viewPager.setAdapter(bannerAdapter);
        pageIndicator.setViewPager(viewPager, bannerAdapter.getRealChildCount());
        bannerAdapter.notifyDataSetChanged();
        //真实元素>1时 开启无限自动轮播
        if (bannerAdapter.getRealChildCount() > 1) {
            mNextPage = Integer.MAX_VALUE / 2 + 1;
            viewPager.setOffscreenPageLimit(2);
            viewPager.setCurrentItem(mNextPage);
            removeCallbacks(this);
            if (bannerAdapter.getRealChildCount() > 1) {
                postDelayed(this, AUTO_SCROLL_INTERVAL);
            }
        }
    }

    public void setLayoutParams(int width, int height) {
        UIUtil.setFrameLayoutParams(viewPager, width, height);
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(this);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(this);
        if (bannerAdapter != null && bannerAdapter.getRealChildCount() > 1) {
            postDelayed(this, AUTO_SCROLL_INTERVAL);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset != 0) {
            if (lastValue >= positionOffsetPixels) {
                //右滑
                isLeft = false;
            } else if (lastValue < positionOffsetPixels) {
                //左滑
                isLeft = true;
            }
        }
        lastValue = positionOffsetPixels;
    }

    @Override
    public void onPageSelected(int i) {
        mNextPage = i + 1;
        currentItem = i % bannerAdapter.getRealChildCount();

//        if (lastPostition != currentItem) {
//            if (isLeft) {
//                //左滑埋点
//                ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
//                clickInterfaceParam.page_id = "0012";
//                clickInterfaceParam.page_name = "首页";
//                clickInterfaceParam.event_id="yjc_android_201706262|38";
//                JDMaUtil.sendClickData(clickInterfaceParam);
//            } else {
//                //右滑埋点
//                ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
//                clickInterfaceParam.page_id = "0012";
//                clickInterfaceParam.page_name = "首页";
//                clickInterfaceParam.event_id="yjc_android_201706262|39";
//                JDMaUtil.sendClickData(clickInterfaceParam);
//            }
//            lastPostition = i % bannerAdapter.getRealChildCount();
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            removeCallbacks(this);
        } else if (state == ViewPager.SCROLL_STATE_IDLE) {
            postDelayed(this, AUTO_SCROLL_INTERVAL);
        }
    }


    public CircleIndicator getPageIndicator() {
        return pageIndicator;
    }

    public void setOnIndexListener(OnIndexListener onIndexListener) {
        this.onIndexListener = onIndexListener;
    }

    public void setOnPageChangedListener(OnPageChangedListener onPageChangedListener) {
        this.onPageChangedListener = onPageChangedListener;
    }

    public interface OnIndexListener {
        void onIndex(int index);
    }

    public interface OnPageChangedListener {
        void onPageChange(int page);
    }
}
