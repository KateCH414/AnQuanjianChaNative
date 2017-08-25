package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/10/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater.MyViewHolderChoose;
import com.miko.zd.anquanjianchanative.Bean.PdfBean;
import com.miko.zd.anquanjianchanative.R;

import java.util.ArrayList;

public class RcycAdapterPdf extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<PdfBean> mData;
    Context context;
    public int type = 0;

    public RcycAdapterPdf(ArrayList<PdfBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getType() == 1){
            type = 1;
        }
        else{
            type = 0;
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (type == 0) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rcyc_checked, parent, false));
        } else {
            return new MyViewHolderChoose(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rcyc_pdf_choose, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tvItemName.setText(mData.get(position).getName());
            if (onItemClickListener != null)
                ((MyViewHolder) holder).tvItemName.setOnClickListener(v -> onItemClickListener.onClick(position));
        } else {
            ((MyViewHolderChoose) holder).tvItemName.setText(mData.get(position).getName());
            ((MyViewHolderChoose) holder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mData.get(position).setChecked(isChecked);
                }
            });
        }
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
