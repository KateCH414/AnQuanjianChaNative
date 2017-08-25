package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/10/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miko.zd.anquanjianchanative.Bean.RealmBean.PathRealm;
import com.miko.zd.anquanjianchanative.Model.BizPicGrid.BizLoadAdapterImage;
import com.miko.zd.anquanjianchanative.R;

import io.realm.RealmList;

public class RcycAdapterGridReport extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int FOOT = 0;
    private static int ELSE = 1;
    private RealmList<PathRealm> mData;
    private BizLoadAdapterImage mLoadAdapterImage;
    private View viewGrid;
    public RcycAdapterGridReport(RealmList<PathRealm> mData, View viewGrid, Context context) {
        this.mLoadAdapterImage = new BizLoadAdapterImage(context,viewGrid);
        this.mData = mData;
        this.viewGrid = viewGrid;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == FOOT){
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcyc_footer_grid_report,parent,false));
        }
        return new MyViewHolderGridReport(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rcyc_grid_report, parent, false));
    }




    public static class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof MyViewHolderGridReport) {
            ((MyViewHolderGridReport) holder).imageView.setTag(mData.get(position).getPath());
            mLoadAdapterImage.setImageView(mData.get(position).getPath(), ((MyViewHolderGridReport) holder).imageView);
            ((MyViewHolderGridReport)holder).imageView.setOnClickListener(v -> onItemClickListener.onClick(position));
        }
        else {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onFooterClick());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onClick(int pos);
        void onFooterClick();
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return FOOT;
        }
        else{
            return ELSE;
        }
    }
}
