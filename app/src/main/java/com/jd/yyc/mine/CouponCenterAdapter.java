package com.jd.yyc.mine;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.CouponVO;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.jd.yyc.util.Util.dateToString;
import static com.jd.yyc.util.Util.longToDate;

/**
 * Created by jiahongbin on 2017/6/12.
 */

public class CouponCenterAdapter extends RecyclerAdapter<CouponVO, YYCViewHolder> implements Contants {
    public Map<String, String> Couponstatu = new HashMap<>();

    public CouponCenterAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new CouponCenterViewHolder(View.inflate(mContext, R.layout.mycoupon, null));
    }


    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public static void setCoupontype() {

    }

    public void setMapData(Map<String, String> coupon) {
        Couponstatu.clear();
        Couponstatu.putAll(coupon);
        notifyDataSetChanged();
    }

    public class CouponCenterViewHolder extends YYCViewHolder<CouponVO> {


        @InjectView(R.id.type)
        TextView type;

        @InjectView(R.id.tv_coupon_zone)
        TextView tv_coupon_zone;
        @InjectView(R.id.area)
        TextView area;

        @InjectView(R.id.beginTime)
        TextView beginTime;


        @InjectView(R.id.quta)
        TextView quta;

        @InjectView(R.id.goods_zone)
        TextView goods_zone;

        @InjectView(R.id.iv_receive_coupon)
        ImageView iv_receive_coupon;

        @InjectView(R.id.discunt)
        TextView discunt;

        @InjectView(R.id.receive)
        TextView receive;
        String typecoupon;
        String coupponValue;
        public CouponCenterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);


        }


        public void onBind(final CouponVO data) {
            Set<String> keys = Couponstatu.keySet();
            for (String key : keys) {
                if (key.equals(data.actRuleId + "")) {
                    coupponValue = Couponstatu.get(key);
                    // Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
                    if (coupponValue.equals("14") || coupponValue.equals("15")) {
                        receive.setVisibility(View.GONE);
                        iv_receive_coupon.setVisibility(View.VISIBLE);
                    } else {
                        receive.setVisibility(View.VISIBLE);
                        iv_receive_coupon.setVisibility(View.GONE);
                    }
                    break;
                }
            }

            //  Toast.makeText(mContext,coupontype,Toast.LENGTH_SHORT).show();
            String begintime = dateToString(longToDate(data.beginTime, "yyyy-MM-dd"), "yyyy.MM.dd");
            String endtime = dateToString(longToDate(data.endTime, "yyyy-MM-dd"), "yyyy.MM.dd");
            beginTime.setText(begintime + "-" + endtime);
            final String actkey = data.actKey;
            final long actId = data.getActRuleId();
            //  type.setText(data.getType());
            quta.setText("满" + data.getQuota() + "元可用");
            discunt.setText("减¥" + data.getDiscount());
            goods_zone.setText(data.limitCat);
            if (CheckTool.isEmpty(data.limitCat)) {
                area.setVisibility(View.GONE);
            }
            tv_coupon_zone.setText(data.areaName + "区域商家商品可用");
            String use_url = data.useUrl;
            int couponType = data.type;
            if (couponType == 0) {
                type.setText("京券");
            }
            if (couponType == 1) {
                type.setText("东券");
            }
            if (couponType == 2) {
                type.setText("运费券");
            }
            receive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Util.isFastDoubleClick()) {
                        return;
                    }
                    receiveCoupon(actkey, actId);
                }

            });

        }

        private void receiveCoupon(String key, long ruleId) {
            Boolean ss;
            HashMap<String, String> params = new HashMap<>();
            params.put("key", key);
            params.put("ruleId", ruleId + "");
            Network.getRequestBuilder(Util.createUrl("coupon/ask", ""))
                    .params(params)
                    .success(new Success() {

                        public void success(String model) {

                            try {
                                Gson gson = new Gson();
                                ResultObject<Boolean> result = gson.fromJson(model, new TypeToken<ResultObject<Boolean>>() {
                                }.getType());
                                // FIXME: 2017/6/8
                                //      relationShip = result.data;
                                // relationShip = false;
                                if (result != null) {
                                    if (result.success && result.data != null && result.data) {
                                        Toast.makeText(mContext, "领取成功", Toast.LENGTH_SHORT).show();
                                        receive.setVisibility(View.GONE);
                                        iv_receive_coupon.setVisibility(View.VISIBLE);
                                    } else {
                                        Toast.makeText(mContext, result.msg, Toast.LENGTH_SHORT).show();
                                        //  receive.setVisibility(View.GONE);
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    })

                    .error(new Error() {
                        @Override
                        public void Error(int statusCode, String errorMessage, Throwable t) {
                            // System.out.println("errorMessage=" + errorMessage);
                            Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .post();
        }

        @Override
        public void onNoDoubleCLick(View v) {
            super.onNoDoubleCLick(v);
        }




    }


}
