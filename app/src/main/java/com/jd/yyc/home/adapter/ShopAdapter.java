package com.jd.yyc.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.goodsdetail.GoodsDetailActivity;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jd.yyc.widget.PriceView;
import com.jingdong.jdma.minterface.ClickInterfaceParam;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/5/18.
 */

public class ShopAdapter extends RecyclerAdapter<Sku, YYCViewHolder> {
    public ShopAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ShopViewHolder(View.inflate(mContext, R.layout.item_shop, null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public class ShopViewHolder extends YYCViewHolder<Sku> {
        @InjectView(R.id.sku_img)
        ImageView skuImag;
        @InjectView(R.id.no_sku)
        TextView noSku;
        @InjectView(R.id.sku_name)
        TextView skuName;
        @InjectView(R.id.sku_shop)
        TextView skuShop;
        @InjectView(R.id.sku_company)
        TextView skuCompany;
        @InjectView(R.id.price_view)
        PriceView priceView;


        public ShopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }


        @Override
        public void onBind(final Sku data) {
            super.onBind(data);
            Glide.with(mContext).load(HttpContants.BasePicUrl + data.fullMainImg).placeholder(R.drawable.default_pic).into(skuImag);
            skuName.setText(data.getName());
            skuShop.setText(data.getShopName());
            skuCompany.setText(data.getFactoryName());
            priceView.setPrice(data.myPrice);
//            noSku.setVisibility(data.stock > 0 ? View.GONE : View.VISIBLE);
        }

        @Override
        public void onNoDoubleCLick(View v) {
            GoodsDetailActivity.launch(mContext, data.sku);
            //埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0012";
            clickInterfaceParam.page_name = "首页";
            clickInterfaceParam.event_id = "yjc_android_201706262|42";
            clickInterfaceParam.shop = data.shopId + "";
            clickInterfaceParam.sku = data.sku + "";
            clickInterfaceParam.map = new HashMap<>();
            clickInterfaceParam.map.put("item_first_cate_id", data.cat1 + "");
            clickInterfaceParam.map.put("item_second_cate_id", data.cat2 + "");
            clickInterfaceParam.map.put("item_third_cate_id", data.cat3 + "");
            JDMaUtil.sendClickData(clickInterfaceParam);
        }
    }
}
