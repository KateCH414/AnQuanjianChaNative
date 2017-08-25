package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/11/13.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommonViewHolder extends RecyclerView.ViewHolder{
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    public CommonViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }
    public static CommonViewHolder get(Context context, ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        CommonViewHolder holder = new CommonViewHolder(context, itemView, parent);
        return holder;
    }


    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public CommonViewHolder setOnClickListener(View.OnClickListener listener){
        itemView.setOnClickListener(listener);
        return this;
    }

    public CommonViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }
    public CommonViewHolder setText(int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
}
