package com.jd.yyc.mine;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.mine.adapter.CouponPagerAdapter;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import butterknife.InjectView;

import static com.jd.yyc.base.YYCApplication.context;


/**
 * Created by jiahongbin on 2017/6/4.
 */

public class CouponActivity extends NoActionBarActivity implements Contants, View.OnClickListener, MineCouponFragment.CouponNumCallback {


    @InjectView(R.id.tabs_coupon)
    TabLayout tabs;
    @InjectView(R.id.vpager_coupon)
    ViewPager vPager;
    @InjectView(R.id.back_view)
    ImageView backBtn;
    @InjectView(R.id.bar_title)
    TextView title;

    TextView tv_tab_first;
    TextView tv_tab_second;
    TextView tv_tab_third;

    @Override
    public int getContentView() {
        return R.layout.coupon;
    }

    public static void launch(Context context) {

        Intent intent = new Intent(context, CouponActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        backBtn.setOnClickListener(this);

        title.setText("优惠券");
        vPager.setOffscreenPageLimit(2);
        CouponPagerAdapter adapter = new CouponPagerAdapter(getSupportFragmentManager(), this);
        vPager.setAdapter(adapter);
        tabs.setupWithViewPager(vPager);
        View v1 = LayoutInflater.from(context).inflate(R.layout.act_tab_view, null);
        View v2 = LayoutInflater.from(context).inflate(R.layout.act_tab_view, null);
        View v3 = LayoutInflater.from(context).inflate(R.layout.act_tab_view, null);
        tv_tab_first = (TextView) v1.findViewById(R.id.tab_title);
        tv_tab_second = (TextView) v2.findViewById(R.id.tab_title);
        tv_tab_third = (TextView) v3.findViewById(R.id.tab_title);
        tabs.getTabAt(0).setCustomView(v1);
        tabs.getTabAt(1).setCustomView(v2);
        tabs.getTabAt(2).setCustomView(v3);
        // tabs.getTabAt(0).

        tv_tab_first.setText("未使用");
        tv_tab_second.setText("已使用");
        tv_tab_third.setText("已过期");
        ClickInterfaceParam param = new ClickInterfaceParam();
        param.event_id = "yjc_android_201706262|57";
        param.page_name = "我的优惠券";
        JDMaUtil.sendClickData(param);

        PvInterfaceParam params = new PvInterfaceParam();
        params.page_id = "0019";
        params.page_name = "我的优惠券";
        JDMaUtil.sendPVData(params);

        //  tabs.setSelectedTabIndicatorColor(Color.rgb(200, 22, 35) );
        // tabs.setTabTextColors(Color.rgb(102, 102, 102), Color.rgb(200, 22, 35));
        tv_tab_first.setTextColor(Color.rgb(200, 22, 35));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) (tab.getCustomView()).findViewById(R.id.tab_title)).setTextColor(Color.rgb(200, 22, 35));
                if (tab.getPosition() == 1) {
                    ClickInterfaceParam param = new ClickInterfaceParam();
                    param.event_id = "yjc_android_201706262|55";
                    param.page_name = "我的优惠券";
                    JDMaUtil.sendClickData(param);
                } else if (tab.getPosition() == 2) {
                    ClickInterfaceParam param = new ClickInterfaceParam();
                    param.event_id = "yjc_android_201706262|56";
                    param.page_name = "我的优惠券";
                    JDMaUtil.sendClickData(param);
                } else if (tab.getPosition() == 0) {
                    ClickInterfaceParam param = new ClickInterfaceParam();
                    param.event_id = "yjc_android_201706262|57";
                    param.page_name = "我的优惠券";
                    JDMaUtil.sendClickData(param);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) (tab.getCustomView()).findViewById(R.id.tab_title)).setTextColor(Color.rgb(102, 102, 102));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                ((TextView) (tab.getCustomView()).findViewById(R.id.tab_title)).setTextColor(Color.rgb(200, 22, 35));
            }
        });
    }

    public void setTabCount(int count, int type) {
        if (type == 0) {
            tv_tab_first.setText("未使用" + "(" + count + ")");
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_view:
                finish();
                break;
        }

    }


    @Override
    public void setCouponNum(int typeorde, int num) {
        if (typeorde == 0) {
            //  tv_tab_first.setTextColor(Color.rgb(200, 22, 35));
            tv_tab_first.setText("未使用" + "(" + num + ")");
            //    Toast.makeText(mContext, "未使用" + "(" + num + ")", Toast.LENGTH_SHORT).show();
        } else if (typeorde == 1) {
            //   tv_tab_second.setTextColor(Color.rgb(200, 22, 35));
            // Toast.makeText(mContext, "已使用" + "(" + num + ")", Toast.LENGTH_SHORT).show();
            tv_tab_second.setText("已使用" + "(" + num + ")");
        } else if (typeorde == 2) {
            //  Toast.makeText(mContext, "已过期" + "(" + num + ")", Toast.LENGTH_SHORT).show();
            //    tv_tab_third.setTextColor(Color.rgb(200, 22, 35));
            tv_tab_third.setText("已过期" + "(" + num + ")");
        }
    }


}
