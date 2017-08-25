package com.jd.yyc.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jiahongbin on 2017/5/26.
 */

public class StayPayimageAdapter extends RecyclerAdapter<Object, YYCViewHolder> {

    public StayPayimageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new StayPayImageViewHolder(View.inflate(mContext,R.layout.staypay_image,null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return super.getItemCount()+5;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    public class StayPayImageViewHolder  extends YYCViewHolder<ArrayList<Banner>>{
        @InjectView(R.id.iv_StayPay_image)
        ImageView iv_StayPay_image;

        public StayPayImageViewHolder(View itemView) {
            super(itemView);
            iv_StayPay_image=(ImageView)itemView.findViewById(R.id.iv_StayPay_image);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(ArrayList<Banner> data) {
            super.onBind(data);
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }

}
