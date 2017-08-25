package com.jd.yyc.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jd.yyc.R;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.home.HomeActivity;
import com.jd.yyc.util.SharePreferenceUtil;
import com.jd.yyc.widget.banner.CircleIndicator;

import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/6/12.
 */

public class WelcomeActivity extends NoActionBarActivity implements ViewPager.OnPageChangeListener {

    @Override
    public int getContentView() {
        return R.layout.activity_welcome;
    }

    @InjectView(R.id.viewpager)
    ViewPager vp;
//    @InjectView(R.id.indicator_layout)
//    CircleIndicator indicatorLayout;

    public static void launch(Activity context) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private void initViews() {
        vp.setAdapter(new MyAdapter());
        vp.addOnPageChangeListener(this);
//        indicatorLayout.setViewPager(vp);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        //no op
    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(mContext, R.layout.layout_welcome_item, null);
            container.addView(view);

            ImageView image = (ImageView) view.findViewById(R.id.image);

            if (position == 0) {
                image.setImageResource(R.drawable.ic_welcome_1);
            } else if (position == 1) {
                image.setImageResource(R.drawable.ic_welcome_2);
            } else if (position == 2) {
                image.setImageResource(R.drawable.ic_welcome_3);
            } else if (position == 3) {
                image.setImageResource(R.drawable.ic_welcome_4);
                image.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        SharePreferenceUtil.setFirstStart(mContext, false);
                        HomeActivity.launch(mContext);
                        finish();
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        return false;
                    }
                });
            }

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
