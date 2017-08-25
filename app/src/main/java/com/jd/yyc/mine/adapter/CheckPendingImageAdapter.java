package com.jd.yyc.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.YaoOrderSku;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.ImageUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by jiahongbin on 2017/5/25.
 */

public class CheckPendingImageAdapter extends RecyclerAdapter<YaoOrderSku, YYCViewHolder> {


    public CheckPendingImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new CheckPendingImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.checkpending_image, parent, false));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));

    }




    public class CheckPendingImageViewHolder  extends YYCViewHolder<YaoOrderSku>{

        @InjectView(R.id.sku_pic)
        ImageView skuPic;

        public CheckPendingImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }


        public void onBind(final YaoOrderSku data) {
            ImageUtil.getInstance().loadSmallImage(mContext, skuPic, HttpContants.BasePicUrl + data.getFullMainImgUrl());
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }

}
