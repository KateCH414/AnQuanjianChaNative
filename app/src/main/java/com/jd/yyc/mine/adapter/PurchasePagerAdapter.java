package com.jd.yyc.mine.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jd.yyc.ui.widget.TabInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class PurchasePagerAdapter extends FragmentStatePagerAdapter {
    private List<TabInfo> tabInfos = new ArrayList<>();
    private Context mContext;

    public PurchasePagerAdapter(FragmentManager fm, Context context, List<TabInfo> tabInfos) {
        super(fm);
        this.mContext = context;
        this.tabInfos.addAll(tabInfos);
    }


    @Override
    public Fragment getItem(int position) {
        return tabInfos.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return tabInfos.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabInfos.get(position).getTabName();
    }

}
