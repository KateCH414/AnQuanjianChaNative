package com.jd.yyc.widget.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * 无限轮播适配器
 *
 * @param <T>
 */
public abstract class BannerAdapter<T> extends PagerAdapter {

    private ArrayList<T> data = new ArrayList<>();

    private Context context;

    public BannerAdapter(Context context) {
        this.context = context;
    }

    /**
     * 元素个数>2时 数组无限大
     *
     * @return
     */
    @Override
    public int getCount() {
        if (data.size() > 1) {
            return Integer.MAX_VALUE;
        }

        return data.size();
    }

    /**
     * @return 真实的元素个数
     */
    public int getRealChildCount() {
        return data.size();
    }

    public void setData(ArrayList<T> data) {
        this.data.clear();
        if (data != null && !data.isEmpty()) {
            this.data.addAll(data);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int originPosition = position % data.size();
        T t = data.get(originPosition);
        View view = createItemView(context, t, originPosition);
        container.addView(view);
        return view;
    }

    public abstract View createItemView(Context context, T data, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}