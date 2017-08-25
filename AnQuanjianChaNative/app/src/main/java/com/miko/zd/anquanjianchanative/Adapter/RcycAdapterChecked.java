package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/10/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.miko.zd.anquanjianchanative.Bean.JsonHistory;
import com.miko.zd.anquanjianchanative.R;

import java.util.ArrayList;

import static com.miko.zd.anquanjianchanative.Bean.JsonHistory.TYPE_UNSAVED;

public class RcycAdapterChecked extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<JsonHistory> mData;
    Context context ;
    public RcycAdapterChecked(ArrayList<JsonHistory> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rcyc_checked, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(mData.get(position).getType() == TYPE_UNSAVED){
            holder.tvItemName.setText("未保存的记录: "+mData.get(position).getDate()+", "+
                    mData.get(position).getDept()+"院系, "+mData.get(position).getRoom()+"房间" +" 的检查记录");
            holder.itemView.setOnClickListener(v -> onItemClickListener.onUnSClick(position));
        }else {
            holder.tvItemName.setText(mData.get(position).getDate() + ", " +
                    mData.get(position).getDept() + "院系, " + mData.get(position).getRoom() + "房间" + " 的检查记录");
            holder.itemView.setOnClickListener(v -> onItemClickListener.onSavedClick(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getType() == TYPE_UNSAVED){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onSavedClick(int pos);
        void onUnSClick(int pos);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
