package com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.miko.zd.anquanjianchanative.Utils.GridUtils.CommonViewHolder;

import java.util.ArrayList;

/**
 * Created by zd on 2016/4/30.
 */
public abstract class CommonAdapter<T> extends android.widget.BaseAdapter {
    private Context mContext;

    private int mItemLayoutId;
    private ArrayList<T> mData;

    public CommonAdapter(Context mContext, int mItemLayoutId, ArrayList<T> mData) {
        this.mContext = mContext;
        this.mItemLayoutId = mItemLayoutId;
        this.mData = mData;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //parent存在，第三个参数为true
        final CommonViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, mData.get(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(CommonViewHolder helper, T item);

    private CommonViewHolder getViewHolder(int position, View convertView,
                                           ViewGroup parent) {
        return CommonViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }
}
