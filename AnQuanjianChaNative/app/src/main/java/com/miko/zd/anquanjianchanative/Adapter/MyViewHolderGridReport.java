package com.miko.zd.anquanjianchanative.Adapter;/*
 * Created by zd on 2016/10/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.miko.zd.anquanjianchanative.R;


public class MyViewHolderGridReport extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public MyViewHolderGridReport(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.id_iv_pic_item_grid);
    }
}
