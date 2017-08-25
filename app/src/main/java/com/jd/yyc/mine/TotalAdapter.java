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
import com.jd.yyc.mine.adapter.CheckPendingImageAdapter;
import com.jd.yyc.mine.adapter.StayPayimageAdapter;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.widget.banner.HorizontalRecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by jiahongbin on 2017/5/30.
 */

public class TotalAdapter  extends RecyclerAdapter<Object, YYCViewHolder> {
    public static final int STAYPAY = 1;//待付款
    public static final int CHECKpENDING = 2;//待审核


    public TotalAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (viewType == STAYPAY) {
            return new StayPayViewHolder(View.inflate(mContext, R.layout.staypay, null));
        } else if (viewType == CHECKpENDING) {
            return new CheckPendingViewHolder(View.inflate(mContext, R.layout.check_pending, null));}
        return null;
    }

    public class CheckPendingViewHolder extends YYCViewHolder<ArrayList<Banner>>{
        TextView tv_ckeckPending_shopName;
        TextView tv_checkPeding_time;

        TextView tv_checkPending_realPay;
        Button bt_checkPending_buyAgain;
        HorizontalRecyclerView rv_CheckPending_Image;
        CheckPendingImageAdapter checkPendingImageAdapter;
        public CheckPendingViewHolder(View item){
            super(item);
            ButterKnife.inject(this, itemView);

            tv_ckeckPending_shopName=(TextView) item.findViewById(R.id.tv_ckeckPending_shopName);

            tv_checkPeding_time=(TextView) item.findViewById(R.id.tv_checkPeding_time);
            rv_CheckPending_Image=(HorizontalRecyclerView) item.findViewById(R.id.rv_CheckPending_Image);
            tv_checkPending_realPay=(TextView) item.findViewById(R.id.tv_checkPending_realPay);
            bt_checkPending_buyAgain=(Button) item.findViewById(R.id.bt_checkPending_buyAgain);
            checkPendingImageAdapter=new CheckPendingImageAdapter(mContext);


            rv_CheckPending_Image.setAdapter(checkPendingImageAdapter);
            rv_CheckPending_Image.getRecyclerView().addItemDecoration(new checkPendingItemDecoration(mContext));


        }

        @Override
        public void onBind(ArrayList<Banner> data) {
            super.onBind(data);
        }
        @Override
        public void onNoDoubleCLick(View v) {

        }
    }

    class checkPendingItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public checkPendingItemDecoration(Context context) {
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


    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return super.getItemCount()+2;
    }
    @Override
    public int getItemViewType(int position) {
        return 1;
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
