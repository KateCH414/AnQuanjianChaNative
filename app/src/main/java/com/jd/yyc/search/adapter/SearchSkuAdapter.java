package com.jd.yyc.search.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.CallBack;
import com.jd.yyc.api.model.PromotionBaseVo;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SearchSku;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.api.model.VendorModel;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.goodsdetail.GoodsDetailActivity;
import com.jd.yyc.login.PortalActivity;
import com.jd.yyc.refreshfragment.LoadingDialogUtil;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.HttpUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.widget.PriceView;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangweifeng1 on 2017/5/31.
 */

public class SearchSkuAdapter extends RecyclerAdapter<SearchSku, YYCViewHolder> {

    private String imgDomain;

    public SearchSkuAdapter(Context mContext) {
        super(mContext);
    }


    public void setImgDomain(String imgDomain) {
        this.imgDomain = imgDomain;
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new ShopViewHolder(View.inflate(mContext, R.layout.item_search_sku, null));
    }


    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public class ShopViewHolder extends YYCViewHolder<SearchSku> {
        @InjectView(R.id.sku_img)
        ImageView skuImag;
        @InjectView(R.id.no_sku)
        TextView noSku;
        @InjectView(R.id.sku_name)
        TextView skuName;
        @InjectView(R.id.add_shopcar)
        ImageView addShopcar;
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

        @OnClick(R.id.add_shopcar)
        void add_shopcar() {
            if (data == null) {
                return;
            }

            if (Util.isLogin()) {
                final Dialog dialog = LoadingDialogUtil.createLoadingDialog(mContext, "");

                HttpUtil.checkRelation(data.vender_id, new CallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isRelation) {
                        LoadingDialogUtil.closeDialog(dialog);
                        if (isRelation) {
                            addShopCar(data.sku_id, 1);
                        } else {
                            ToastUtil.show(mContext, "尚未建立采购关系");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String errorMessage) {
                        ToastUtil.show(mContext, errorMessage);
                        LoadingDialogUtil.closeDialog(dialog);
                    }
                });
            } else {
                ToastUtil.show(mContext, "请登录后添加购物车");
            }


        }

        @Override
        public void onBind(final SearchSku data) {
            super.onBind(data);
            if (data != null && data.Content != null) {
                Glide.with(mContext).load(HttpContants.BasePicUrl + imgDomain + "/img/" + data.Content.img)
                        .placeholder(R.drawable.default_pic)
                        .into(skuImag);
                skuName.setText(data.Content.ware_name);
                skuShop.setText(data.Content.shop_name);
                skuCompany.setText(data.Content.factory);
                priceView.setPrice(data.myPrice);
//                noSku.setVisibility(data.stock > 0 ? View.GONE : View.VISIBLE);
            } else {
                priceView.setPrice(null);
            }
        }

        @Override
        public void onNoDoubleCLick(View v) {
            GoodsDetailActivity.launch(mContext, data.sku_id);
        }
    }

    private void addShopCar(long skuId, long num) {
        HashMap<String, String> params = new HashMap<>();
        params.put("skuId", skuId + "");
        params.put("num", num + "");
        Network.getRequestBuilder(Util.createUrl("cart/add", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<VendorModel> result = gson.fromJson(model, new TypeToken<ResultObject<VendorModel>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                ToastUtil.show(mContext, "商品已加入购物车");
                            } else {
                                ToastUtil.show(mContext, "无法加入购物车");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).post();
    }
}
