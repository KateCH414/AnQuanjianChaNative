package com.miko.zd.anquanjianchanative.Adapter;/*
 * Created by zd on 2016/10/18.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.R;


public class MyViewHolderExpendDetail extends RecyclerView.ViewHolder {
    public TextView tvItemName;
    public ImageView ivUpClose;
    public RecyclerView recyclerView;
    public CardView cardView;
    public MyViewHolderExpendDetail(View itemView) {
        super(itemView);
        ivUpClose = (ImageView) itemView.findViewById(R.id.id_iv_down_item_a);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.id_rcyc_item);
        tvItemName = (TextView) itemView.findViewById(R.id.id_tv_itemName_item);
        cardView = (CardView) itemView.findViewById(R.id.id_cardView);
    }
}
