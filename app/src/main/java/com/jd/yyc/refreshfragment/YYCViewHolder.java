package com.jd.yyc.refreshfragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.yyc.util.Util;


/**
 * Created by wf on 15/7/4.
 */
public class YYCViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    public T data;

    public YYCViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!Util.isFastDoubleClick()) {
            onNoDoubleCLick(v);
        }
    }

    public void onNoDoubleCLick(View v) {

    }

    public void onBind(T data) {
        this.data = data;
    }

    public void onBind(int position ,T data) {
        this.data = data;
    }


}
