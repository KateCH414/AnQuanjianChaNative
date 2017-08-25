package com.miko.zd.anquanjianchanative.Adapter;/*
 * Created by zd on 2016/10/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.R;


public class MyViewHolderExpendItem extends RecyclerView.ViewHolder {
    public TextView tvItemName;
    public EditText edNote;
    public CheckBox cb1,cb2,cb3;
    public RecyclerView recyclerView;
    public MyViewHolderExpendItem(View itemView) {
        super(itemView);
        tvItemName = (TextView) itemView.findViewById(R.id.id_tv_itemName_item);
        edNote = (EditText) itemView.findViewById(R.id.id_ed_note);
        cb1 = (CheckBox) itemView.findViewById(R.id.id_cb_first);
        cb2 = (CheckBox) itemView.findViewById(R.id.id_cb_second);
        cb3 = (CheckBox) itemView.findViewById(R.id.id_cb_third);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.id_rcyc_edit);
    }
}
