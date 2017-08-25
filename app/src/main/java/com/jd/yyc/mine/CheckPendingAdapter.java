package com.jd.yyc.mine;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.yyc.R;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.mine.adapter.CheckPendingImageAdapter;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.widget.banner.HorizontalRecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by jiahongbin on 2017/5/25.
 */

public class CheckPendingAdapter extends RecyclerAdapter<Sku, YYCViewHolder> {

    private ArrayList<Banner> bannerList = new ArrayList<>();

    public CheckPendingAdapter(Context mContext) {
        super(mContext);

    }
    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new CheckPendingViewHolder(View.inflate(mContext, R.layout.check_pending,null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        ((CheckPendingViewHolder) holder).onBind(bannerList);


    }


    @Override
    public int getItemCount() {
        return super.getItemCount()+3;

    }

    @Override
    public Sku getItem(int position) {
        return super.getItem(position);
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

    @OnClick(R.id.bt_checkPending_buyAgain)
    void toLBS() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        Toast.makeText(mContext,"再次购买",Toast.LENGTH_LONG).show();
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


}
