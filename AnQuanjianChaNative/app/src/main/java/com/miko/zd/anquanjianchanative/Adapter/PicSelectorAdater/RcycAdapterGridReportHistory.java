package com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater;
/*
 * Created by zd on 2016/10/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.miko.zd.anquanjianchanative.Adapter.MyViewHolderGridReport;
import com.miko.zd.anquanjianchanative.Model.BizPicGrid.BizLoadAdapterImage;
import com.miko.zd.anquanjianchanative.R;

import java.util.ArrayList;

public class RcycAdapterGridReportHistory extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Bitmap> mData;
    private BizLoadAdapterImage mLoadAdapterImage;
    public RcycAdapterGridReportHistory(ArrayList<Bitmap> mData, Context context) {
        this.mData = mData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolderGridReport(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rcyc_grid_report, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolderGridReport) holder).imageView.setImageBitmap(mData.get(position));
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
