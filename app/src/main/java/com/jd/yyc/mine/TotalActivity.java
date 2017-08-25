package com.jd.yyc.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.mine.adapter.PurchasePagerAdapter;
import com.jd.yyc.ui.widget.TabInfo;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 *我的-全部订单页面
 */

public class TotalActivity extends NoActionBarActivity implements Contants, View.OnClickListener {
    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.vpager)
    ViewPager vPager;
    @InjectView(R.id.back_view)
    ImageView backBtn;
    @InjectView(R.id.bar_title)
    TextView title;

    public static void launch(Context context) {
        Intent intent = new Intent(context, TotalActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.mine_total;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backBtn.setOnClickListener(this);
        title.setText("我的采购单");
        vPager = (ViewPager) findViewById(R.id.vpager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        List<TabInfo> tabInfos = new ArrayList<>();
        tabInfos.add(new TabInfo("全部", MineGoodsFragment.newInstance(ORDER_ALL, true)));
        tabInfos.add(new TabInfo("待审核", MineGoodsFragment.newInstance(Contants.ORDER_WILL_CHECK, true)));
        //    tabInfos.add(new TabInfo("待确认", MineGoodsFragment.newInstance(ORDER_WILL_CONFIRM, true)));
        tabInfos.add(new TabInfo("已驳回", MineGoodsFragment.newInstance(ORDER_REJECT, true)));
        //   tabInfos.add(new TabInfo("已取消", MineGoodsFragment.newInstance(ORDER_CANCELED, true)));
        tabInfos.add(new TabInfo("待付款", MineGoodsFragment.newInstance(ORDER_WILL_PAY, true)));
        tabInfos.add(new TabInfo("待收货", MineGoodsFragment.newInstance(ORDER_WILL_RECEIVE, true)));
        tabInfos.add(new TabInfo("已完成", MineGoodsFragment.newInstance(ORDER_DONE, true)));
        //    tabInfos.add(new TabInfo("已失效", MineGoodsFragment.newInstance(ORDER_FAILED, true)));
        //   tabInfos.add(new TabInfo("待发货", MineGoodsFragment.newInstance(ORDER_WILL_SEND, true)));
        PurchasePagerAdapter adapter = new PurchasePagerAdapter(getSupportFragmentManager(), this, tabInfos);
        vPager.setAdapter(adapter);
        tabs.setupWithViewPager(vPager);
        sendPV();
//        for (int i = 0; i < tabs.getTabCount(); i++) {
//            TabLayout.Tab tab = tabs.getTabAt(i);
//            tab.setCustomView(adapter.getTabView(i));
//        }
    }

    private void sendPV() {
        PvInterfaceParam param = new PvInterfaceParam();
        param.page_name = PAGE_ORDER_LIST_NAME;
        param.page_id = PAGE_ORDER_LIST_ID;
        JDMaUtil.sendPVData(param);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_view:
                finish();
                break;
        }
    }
}
