package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/10/18.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.miko.zd.anquanjianchanative.Bean.ItemTreeBean;
import com.miko.zd.anquanjianchanative.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<ItemTreeBean> mData;

    public RecyclerViewAdapter(ArrayList<ItemTreeBean> mData) {
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rcyc_checkitem, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvItemName.setText(String.valueOf(mData.get(position).getIndex()+1)+". "+mData.get(position).getItemName());

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
