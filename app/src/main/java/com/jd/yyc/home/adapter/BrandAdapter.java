package com.jd.yyc.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.api.model.Brand;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.search.SearchResultActivity;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.UIUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/5/19.
 */

public class BrandAdapter extends RecyclerAdapter<Brand, YYCViewHolder> {

    public BrandAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new BrandViewHolder(View.inflate(mContext, R.layout.item_brand_small, null));
    }


    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }


    class BrandViewHolder extends YYCViewHolder<Brand> {
        @InjectView(R.id.brand_img)
        ImageView brandImg;

        public BrandViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(Brand data) {
            super.onBind(data);
            Glide.with(mContext).load(HttpContants.BasePicUrl + data.img).placeholder(R.drawable.default_pic).into(brandImg);
        }

        @Override
        public void onNoDoubleCLick(View v) {
            if (!TextUtils.isEmpty(data.searchKey)) {
                SearchResultActivity.launch(mContext, data.searchKey);

                //埋点
                ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
                clickInterfaceParam.page_id = "0012";
                clickInterfaceParam.page_name = "首页";
                clickInterfaceParam.event_id = "yjc_android_201706262|44";
                JDMaUtil.sendClickData(clickInterfaceParam);
            }
        }
    }
}
