package com.jd.yyc.mine.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jd.yyc.R;
import com.jd.yyc.api.model.YaoOrderSku;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jiahongbin on 2017/5/26.
 */

public class GoodsReceiveimageAdapter extends RecyclerAdapter<YaoOrderSku, YYCViewHolder> {

    public GoodsReceiveimageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new GoodsReceiveimageViewHolder(View.inflate(mContext,R.layout.goodsreceive_image,null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }



    public class GoodsReceiveimageViewHolder  extends YYCViewHolder<YaoOrderSku>{
        @InjectView(R.id.iv_GoodsReceive_image)
        ImageView iv_GoodsReceive_image;

        public GoodsReceiveimageViewHolder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(YaoOrderSku data) {
//            Glide.with(mContext).load(HttpContants.BasePicUrl + data .mainImgUrl).into(iv_GoodsReceive_image);
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }

}
