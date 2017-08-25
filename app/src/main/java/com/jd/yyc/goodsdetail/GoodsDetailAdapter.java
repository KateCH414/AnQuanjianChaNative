package com.jd.yyc.goodsdetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.jd.yyc.R;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;

import java.util.ArrayList;

/**
 * Created by jiahongbin on 2017/5/26.
 */

class GoodsDetailAdapter extends RecyclerAdapter<Object, YYCViewHolder> {

    public GoodsDetailAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return  new GoodsDetailViewHolder(View.inflate(mContext, R.layout.goods_detail, null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return super.getItemCount()+1;
    }

    public class GoodsDetailViewHolder extends YYCViewHolder<ArrayList<Banner>> {


        public GoodsDetailViewHolder(View itemView) {
            super(itemView);

        }

        @Override
        public void onBind(final ArrayList<Banner> data) {
            super.onBind(data);
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }


}
