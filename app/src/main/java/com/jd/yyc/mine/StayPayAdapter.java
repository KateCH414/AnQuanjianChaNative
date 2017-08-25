package com.jd.yyc.mine;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.mine.adapter.StayPayimageAdapter;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.widget.banner.HorizontalRecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jiahongbin on 2017/5/25.
 */

public class StayPayAdapter extends RecyclerAdapter<Sku, YYCViewHolder> {

    private ArrayList<Banner> bannerList = new ArrayList<>();
    public StayPayAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new StayPayViewHolder(View.inflate(mContext, R.layout.staypay,null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {

    }

    public class StayPayViewHolder extends YYCViewHolder<ArrayList<Banner>>{
        @InjectView(R.id.rv_StayPay)
        HorizontalRecyclerView rv_StayPay;


        @InjectView(R.id.tv_stayPay_shopName)
        TextView tv_stayPay_shopName;

        @InjectView(R.id.tv_stayPay_time)
        TextView tv_stayPay_time;

        @InjectView(R.id.tv_stayPay_realPay)
        TextView tv_stayPay_realPay;

        @InjectView(R.id.bt_stayPay_buyAgain)
        Button bt_stayPay_buyAgain;

        StayPayimageAdapter stayPayimageAdapter;


        public StayPayViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            stayPayimageAdapter=new StayPayimageAdapter(mContext);
            rv_StayPay.setAdapter(stayPayimageAdapter);
            rv_StayPay.getRecyclerView().addItemDecoration(new stayPayItemDecoration(mContext));
        }

        @Override
        public void onBind(ArrayList<Banner> data) {
            super.onBind(data);
        }

        @Override
        public void onNoDoubleCLick(View v) {
            super.onNoDoubleCLick(v);
        }
    }

    @Override
    public int getItemCount() {
        return super.getItemCount()+3;
    }

    @Override
    public Sku getItem(int position) {
        return super.getItem(position);
    }


    class stayPayItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public stayPayItemDecoration(Context context) {
            itemSize = ScreenUtil.dip2px(context, 1);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);

            if (holder.getAdapterPosition() == 0) {
                outRect.set(0, itemSize, 0, 0);
            } else if (parent.getAdapter().getItemCount() - 1 == holder.getAdapterPosition()) {
                outRect.set(itemSize, itemSize, 0, 0);
            } else {
                outRect.set(itemSize, itemSize, 0, 0);
            }
        }
    }

}
