package com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater;/*
 * Created by zd on 2016/10/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.R;


public class MyViewHolderChoose extends RecyclerView.ViewHolder {
    public TextView tvItemName;
    public CheckBox checkBox;
    public MyViewHolderChoose(View itemView) {
        super(itemView);
        tvItemName = (TextView) itemView.findViewById(R.id.id_tv_itemName_item);
        checkBox = (CheckBox) itemView.findViewById(R.id.id_cb_pdf);
    }
}
