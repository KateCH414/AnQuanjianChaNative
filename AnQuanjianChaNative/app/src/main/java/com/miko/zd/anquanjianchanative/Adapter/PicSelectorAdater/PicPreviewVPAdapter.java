package com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater;
/*
 * Created by zd on 2016/10/29.
 */

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class PicPreviewVPAdapter extends PagerAdapter {
    private List<ImageView> mList;

    public PicPreviewVPAdapter(List<ImageView> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));//添加页卡
        return mList.get(position);
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));//删除页卡
    }
}
