package com.miko.zd.anquanjianchanative.Adapter;/*
 * Created by zd on 2016/10/18.
 */

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.R;


public class MyViewHolderDetail extends RecyclerView.ViewHolder {
    public TextView tvItemName;
    public ImageView ivExpendDown;
    public MyViewHolderDetail(View itemView) {
        super(itemView);
        tvItemName = (TextView) itemView.findViewById(R.id.id_tv_itemName_item);
        ivExpendDown = (ImageView) itemView.findViewById(R.id.id_iv_down_item);
        ivExpendDown.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(),R.mipmap.ic_expanddown));
    }
}
