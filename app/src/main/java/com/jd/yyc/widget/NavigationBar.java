package com.jd.yyc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jd.yyc.R;
import com.jd.yyc.event.EventOnHomeRefresh;

import de.greenrobot.event.EventBus;

/**
 * 首页底部导航栏，能与viewpager联动
 * 并提供多次点击同一个tab的会调
 */
public class NavigationBar extends LinearLayout implements View.OnClickListener {

    public static final String TAG = NavigationBar.class.getSimpleName();

    private TabChangeListener tabChangeListener;
    private TabClickListener tabClickListener;

    public NavigationBar(Context context) {
        super(context);
    }

    public NavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.setOnClickListener(this);
        }
    }

    public void onCheckPosition(int position) {
        if (position < getChildCount() && position >= 0) {
            getChildAt(position).performClick();
        }
    }

    @Override
    public void onClick(View v) {
        //选中状态
        if (v.isSelected()) {
            switch (v.getId()) {
                case R.id.btn_home:
                    EventBus.getDefault().post(new EventOnHomeRefresh(EventOnHomeRefresh.HOME));
                    break;
                case R.id.btn_category:
                    EventBus.getDefault().post(new EventOnHomeRefresh(EventOnHomeRefresh.CATEGORY));
                    break;
                case R.id.btn_shopcar:
                    EventBus.getDefault().post(new EventOnHomeRefresh(EventOnHomeRefresh.SHOPINGCAR));
                    break;
                case R.id.btn_profile:
                    break;
            }
        } else {//非选中状态

            int selectPosition = 0;
            if (v.getId() == R.id.btn_home) {
                selectPosition = 0;
            } else if (v.getId() == R.id.btn_category) {
                selectPosition = 1;
            } else if (v.getId() == R.id.btn_shopcar) {
                selectPosition = 2;

            } else if (v.getId() == R.id.btn_profile) {
                selectPosition = 3;
            }
            if (tabClickListener == null || !tabClickListener.onTabClick(selectPosition))
                return;

            if (tabChangeListener != null) {
                switch (v.getId()) {
                    case R.id.btn_home:
                        tabChangeListener.onTabChange(0);
                        break;
                    case R.id.btn_category:
                        tabChangeListener.onTabChange(1);
                        EventBus.getDefault().post(new EventOnHomeRefresh(EventOnHomeRefresh.CATEGORY));
                        break;
                    case R.id.btn_shopcar:
                        tabChangeListener.onTabChange(2);
                        break;
                    case R.id.btn_profile:
                        tabChangeListener.onTabChange(3);
                        break;
                }
            }

            //先将其他按钮置为初始状态
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                view.setSelected(false);
            }
            v.setSelected(true);
        }
    }

    public void setTabChangeListener(TabChangeListener tabChangeListener) {
        this.tabChangeListener = tabChangeListener;
    }

    public void setTabClickListener(TabClickListener tabClickListener) {
        this.tabClickListener = tabClickListener;
    }

    public interface TabChangeListener {
        void onTabChange(int position);
    }

    public interface TabClickListener {
        boolean onTabClick(int position);
    }


}
