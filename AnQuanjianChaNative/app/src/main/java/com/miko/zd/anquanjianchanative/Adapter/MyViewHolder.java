package com.miko.zd.anquanjianchanative.Adapter;/*
 * Created by zd on 2016/10/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.R;


public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView tvItemName;
    public MyViewHolder(View itemView) {
        super(itemView);
        tvItemName = (TextView) itemView.findViewById(R.id.id_tv_itemName_item);
    }
}
