package com.jd.yyc.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.CouponVO;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.jd.yyc.util.Util.dateToString;
import static com.jd.yyc.util.Util.longToDate;

/**
 * Created by jiahongbin on 2017/6/4.
 */

public class MineCouponAdapter extends RecyclerAdapter<CouponVO, YYCViewHolder> {

    public int type = 0;//0未使用，1已使用，2已过期


    public MineCouponAdapter(Context mContext, int type) {
        super(mContext);
        this.type = type;
    }


    public void setDataType(int type) {
        this.type = type;
    }


    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

        if (viewType == 0) {

            return new NOTUSEViewHolder(LayoutInflater.from(mContext).inflate(R.layout.not_use, parent, false));
        } else if (viewType == 1) {
            return new USEDViewHolder(LayoutInflater.from(mContext).inflate(R.layout.used, parent, false));
        } else if (viewType == 2) {
            return new USEDViewHolder(LayoutInflater.from(mContext).inflate(R.layout.used, parent, false));
        } else if (viewType == 3) {
            return new USEDTVViewHolder(LayoutInflater.from(mContext).inflate(R.layout.used_textview, parent, false));
        } else if (viewType == 4) {
            return new USEDTVViewHolder(LayoutInflater.from(mContext).inflate(R.layout.used_textview, parent, false));
        } else if (viewType == 5) {
            return new USEDTVViewHolder(LayoutInflater.from(mContext).inflate(R.layout.used_textview, parent, false));
            //   return new NOTUSETVTVViewHolder(LayoutInflater.from(mContext).inflate(R.layout.not_use_tv, parent, false));
        }
        return null;
    }


    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {

        holder.onBind(getItem(position));
    }

    public int getType() {
        return type;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && type == 2) {
            return 3;
        } else if (position == getItemCount() - 1 && type == 1) {
            return 4;
        } else if (position == getItemCount() - 1 && type == 0) {
            return 5;
        }

        return type;
    }


    public class NOTUSEViewHolder extends YYCViewHolder<CouponVO> {


        @InjectView(R.id.type)
        TextView type;

        @InjectView(R.id.area)
        TextView area;

        @InjectView(R.id.beginTime)
        TextView beginTime;

        @InjectView(R.id.bt_use)
        Button bt_use;

        @InjectView(R.id.quta)
        TextView quta;

        @InjectView(R.id.goods_zone)
        TextView goods_zone;


        @InjectView(R.id.discunt)
        TextView discunt;


        public NOTUSEViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);


        }


        public void onBind(CouponVO data) {


            String begintime = dateToString(longToDate(data.beginTime, "yyyy-MM-dd"), "yyyy.MM.dd");
            String endtime = dateToString(longToDate(data.endTime, "yyyy-MM-dd"), "yyyy.MM.dd");
            beginTime.setText(begintime + "-" + endtime);

            //  type.setText(data.getType());
            quta.setText("满" + data.getQuota() + "元可用");
            discunt.setText("减¥" + data.getDiscount());
            goods_zone.setText(data.limitCat);
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


        }

        @Override
        public void onNoDoubleCLick(View v) {
            super.onNoDoubleCLick(v);
        }
    }


    public class USEDTVViewHolder extends YYCViewHolder<CouponVO> {

        @InjectView(R.id.tv_center)
        TextView tv_center;

        public USEDTVViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);


        }


        public void onBind(CouponVO data) {
            tv_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickInterfaceParam param = new ClickInterfaceParam();
                    param.event_id = "yjc_android_201706262|54";
                    param.page_name = "我的优惠券";
                    JDMaUtil.sendClickData(param);
                    if (Util.isFastDoubleClick()) {
                        return;
                    }
                    CouponCenter.launch(mContext);
                }
            });

        }
        @Override
        public void onNoDoubleCLick(View v) {
            super.onNoDoubleCLick(v);
        }
    }


    public class USEDViewHolder extends YYCViewHolder<CouponVO> {


        @InjectView(R.id.type)
        TextView type;

        @InjectView(R.id.area)
        TextView area;

        @InjectView(R.id.beginTime)
        TextView beginTime;


        @InjectView(R.id.quta)
        TextView quta;

        @InjectView(R.id.goods_zone)
        TextView goods_zone;


        @InjectView(R.id.discunt)
        TextView discunt;

        public USEDViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);


        }


        public void onBind(CouponVO data) {


            String begintime = dateToString(longToDate(data.beginTime, "yyyy-MM-dd"), "yyyy.MM.dd");
            String endtime = dateToString(longToDate(data.endTime, "yyyy-MM-dd"), "yyyy.MM.dd");
            beginTime.setText(begintime + "-" + endtime);

            //  type.setText(data.getType());
            quta.setText("满" + data.getQuota() + "元可用");
            discunt.setText("减¥" + data.getDiscount());
            goods_zone.setText(data.limitCat);
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


        }

        @Override
        public void onNoDoubleCLick(View v) {
            super.onNoDoubleCLick(v);
        }
    }

    public class NOTUSETVTVViewHolder extends YYCViewHolder<CouponVO> {


        @InjectView(R.id.type)
        TextView type;

        @InjectView(R.id.area)
        TextView area;

        @InjectView(R.id.beginTime)
        TextView beginTime;


        @InjectView(R.id.quta)
        TextView quta;

        @InjectView(R.id.goods_zone)
        TextView goods_zone;


        @InjectView(R.id.discunt)
        TextView discunt;

        @InjectView(R.id.rl_center)
        RelativeLayout rl_center;

        @InjectView(R.id.tv_center_button)
        TextView tv_center;

        public NOTUSETVTVViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }

        @Override
        public void onBind(int position, CouponVO data) {
            super.onBind(position, data);
        }

        @Override
        public void onBind(CouponVO data) {
            super.onBind(data);

            tv_center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (Util.isFastDoubleClick()) {
                        return;
                    }
                    CouponCenter.launch(mContext);

                }
            });

            String begintime = dateToString(longToDate(data.beginTime, "yyyy-MM-dd"), "yyyy.MM.dd");
            String endtime = dateToString(longToDate(data.endTime, "yyyy-MM-dd"), "yyyy.MM.dd");
            beginTime.setText(begintime + "-" + endtime);

            //  type.setText(data.getType());
            quta.setText("满" + data.getQuota() + "元可用");
            discunt.setText("减¥" + data.getDiscount());
            goods_zone.setText(data.limitCat);
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


        }

        @Override
        public void onNoDoubleCLick(View v) {
            super.onNoDoubleCLick(v);
        }
    }


}





