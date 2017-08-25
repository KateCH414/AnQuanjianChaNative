package com.jd.yyc.home.banner;

import android.content.Context;
import android.view.View;

import com.jd.yyc.api.model.Banner;
import com.jd.yyc.widget.banner.BannerAdapter;


public class HomeBannerAdapter extends BannerAdapter<Banner> {

    public HomeBannerAdapter(Context context) {
        super(context);
    }

    @Override
    public View createItemView(Context context, Banner data, int position) {
        BannerItemView bannerItemView = new BannerItemView(context);
        bannerItemView.setData(data);
        return bannerItemView;
    }

}