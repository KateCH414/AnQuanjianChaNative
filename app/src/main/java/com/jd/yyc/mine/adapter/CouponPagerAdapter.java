package com.jd.yyc.mine.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.jd.yyc.mine.MineCouponFragment;
import com.jd.yyc.ui.widget.Info;

import java.util.ArrayList;
import java.util.List;

import static com.jd.yyc.constants.Contants.COUPON_USE;
import static com.jd.yyc.constants.Contants.COUPON_USED;
import static com.jd.yyc.constants.Contants.COUPON_USEDATA;

/**
 *
 */

public class CouponPagerAdapter extends FragmentStatePagerAdapter {
    private List<Info> tabInf = new ArrayList<>();
    private Context mContext;

    public CouponPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        this.tabInf.addAll(tabInf);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return MineCouponFragment.newInstance(COUPON_USE, true, mContext);
            case 1:
                return MineCouponFragment.newInstance(COUPON_USED, true, mContext);
            case 2:
                return MineCouponFragment.newInstance(COUPON_USEDATA, true, mContext);

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //   return tabInf.get(position).getTabName();
        return "";
    }


}
