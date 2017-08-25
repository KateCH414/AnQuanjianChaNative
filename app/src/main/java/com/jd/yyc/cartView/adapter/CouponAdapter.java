package com.jd.yyc.cartView.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.CouponModel;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.ResourceUtil;
import com.jd.yyc.util.ToastUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 优惠券adapter
 */

public class CouponAdapter extends RecyclerAdapter<CouponModel, CouponAdapter.CouponHolder> {


    public CouponAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public CouponHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new CouponHolder(View.inflate(mContext, R.layout.item_coupon, null));
    }

    @Override
    public void onBindViewHolder2(CouponHolder holder, int position) {
        holder.onBind(position,getItem(position));
    }

    public class CouponHolder extends YYCViewHolder<CouponModel> {

        @InjectView(R.id.coupon_header_layout)
        LinearLayout couponHeaderLayout;
        @InjectView(R.id.coupon_txt)
        TextView couponTxt;
        @InjectView(R.id.your_coupon_txt)
        TextView yourCouponTxt;
        @InjectView(R.id.sub_price)
        TextView subPrice;
        @InjectView(R.id.can_use_price)
        TextView canUsePrice;
        @InjectView(R.id.coupon_tag)
        TextView couponTag;
        @InjectView(R.id.use_zone)
        TextView useZone;
        @InjectView(R.id.use_date)
        TextView useDate;
        @InjectView(R.id.get_btn)
        TextView getBtn;
        @InjectView(R.id.coupon_get)
        ImageView couponGet;
        @InjectView(R.id.item_coupon)
        LinearLayout itemCoupon;

        public CouponHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(int position, final CouponModel data) {
            super.onBind(position, data);
            if(showHeader(position)){
                couponHeaderLayout.setVisibility(View.VISIBLE);
                couponTxt.setText(data.isYours()?R.string.coupon_can_use:R.string.coupon_can_get);
                yourCouponTxt.setVisibility(data.isYours()?View.VISIBLE:View.GONE);
            }else {
                couponHeaderLayout.setVisibility(View.GONE);
            }
            subPrice.setText(ResourceUtil.getString(R.string.sub_price, data.getDiscount()));
            canUsePrice.setText(ResourceUtil.getString(R.string.can_use_price,data.getQuota()));
            couponTag.setText(data.getTagStr());
            useZone.setText(data.getCouponLimitInfo());
            useDate.setText(data.getDateStr());
            getBtn.setVisibility(data.showGetBtn()?View.VISIBLE:View.GONE);
            couponGet.setVisibility(data.isGet()?View.VISIBLE:View.GONE);
            getBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NetLoading.getInstance().getCoupon(mContext, data.getActKey(), data.getActRuleId(), new RequestCallback<ResultObject<Boolean>>() {
                        @Override
                        public void requestCallBack(boolean success, ResultObject<Boolean> result, String err) {
                            if(success&&result.data){
                                data.setGet(true);
                                notifyDataSetChanged();
                            }else {
                                ToastUtil.show(result != null ? result.msg :"领取失败");
                            }
                        }
                    });
                }
            });

        }

        @Override
        public void onBind(final CouponModel data) {
            super.onBind(data);


        }
    }

    public boolean showHeader(int position){
        if(position == 0)
            return true;
        if(getItem(position-1).isYours() == getItem(position).isYours()){
            return false;
        }else {
            return true;
        }
    }

}
