package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/11/13.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.AbstractList;

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder>
{
    protected Context mContext;
    protected int mLayoutId;
    protected AbstractList<T> mDatas;
    protected LayoutInflater mInflater;


    public CommonAdapter(Context context, int layoutId,  AbstractList<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        CommonViewHolder viewHolder = CommonViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position)
    {
        convert(holder, mDatas.get(position),position);
    }

    public abstract void convert(CommonViewHolder holder, T t,int position);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }
}
