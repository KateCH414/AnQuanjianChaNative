package com.jd.yyc.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.YaoOrderSku;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.ImageUtil;


/**
 * Created by jiahongbin on 2017/5/25.
 */

public class MonthPayImageAdapter extends RecyclerAdapter<YaoOrderSku, YYCViewHolder> {


    public MonthPayImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new CheckPendingImageViewHolder(View.inflate(mContext, R.layout.checkpending_image,null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));

    }


    public class CheckPendingImageViewHolder extends YYCViewHolder<YaoOrderSku> {

        ImageView iv_checkPending_image;

        public CheckPendingImageViewHolder(View itemView) {
            super(itemView);
            iv_checkPending_image=(ImageView) itemView.findViewById(R.id.sku_pic);

        }


        public void onBind(final YaoOrderSku data) {
            ImageUtil.getInstance().loadSmallImage(mContext, iv_checkPending_image, HttpContants.BasePicUrl + data.getFullMainImgUrl());
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }

}
