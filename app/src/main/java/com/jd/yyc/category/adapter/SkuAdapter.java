package com.jd.yyc.category.adapter;

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
import com.jd.yyc.api.model.LBS;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.api.model.VendorModel;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.goodsdetail.GoodsDetailActivity;
import com.jd.yyc.login.LoginActivity;
import com.jd.yyc.login.PortalActivity;
import com.jd.yyc.refreshfragment.LoadingDialogUtil;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.HttpUtil;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jd.yyc.widget.PriceView;
import com.jingdong.jdma.minterface.ClickInterfaceParam;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangweifeng1 on 2017/5/31.
 */

public class SkuAdapter extends RecyclerAdapter<Sku, YYCViewHolder> {
    public static final int CATEGORY = 1;
    public static final int SEARCH = 2;

    private int type = CATEGORY;

    public SkuAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (type == CATEGORY) {
            return new ShopViewHolder(View.inflate(mContext, R.layout.item_sku, null));
        } else if (type == SEARCH) {
            return new ShopViewHolder(View.inflate(mContext, R.layout.item_search_sku, null));
        }

        return new ShopViewHolder(View.inflate(mContext, R.layout.item_sku, null));
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
        @InjectView(R.id.add_shopcar)
        ImageView addShopcar;
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
                //埋点
                ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
                clickInterfaceParam.page_id = "0004";
                clickInterfaceParam.page_name = "分类列表页面";
                clickInterfaceParam.event_id = "yjc_android_201706262|19";
                clickInterfaceParam.sku = data.sku + "";
                clickInterfaceParam.map = new HashMap<>();
                clickInterfaceParam.map.put("item_first_cate_id", data.cat1 + "");
                clickInterfaceParam.map.put("item_second_cate_id", data.cat2 + "");
                clickInterfaceParam.map.put("item_third_cate_id", data.cat3 + "");
                JDMaUtil.sendClickData(clickInterfaceParam);


                final Dialog dialog = LoadingDialogUtil.createLoadingDialog(mContext, "");

                HttpUtil.checkRelation(data.venderId, new CallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isRelation) {
                        LoadingDialogUtil.closeDialog(dialog);
                        if (isRelation) {
                            addShopCar(data.sku, 1);
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
                //跳转登录页面
                PortalActivity.launch(mContext, Activity.RESULT_OK);
            }
        }

        @Override
        public void onBind(final Sku data) {
            super.onBind(data);
            Glide.with(mContext).load(HttpContants.BasePicUrl + data.fullMainImg).placeholder(R.drawable.default_pic).into(skuImag);
            skuName.setText(data.name);
            priceView.setPrice(data.myPrice);
//            noSku.setVisibility(data.stock > 0 ? View.GONE : View.VISIBLE);
        }

        @Override
        public void onNoDoubleCLick(View v) {
            GoodsDetailActivity.launch(mContext, data.sku);

            //埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0005";
            clickInterfaceParam.page_name = "分类列表页面";
            clickInterfaceParam.event_id = "yjc_android_201706262|20";
            clickInterfaceParam.sku = data.sku + "";
            clickInterfaceParam.map = new HashMap<>();
            clickInterfaceParam.map.put("item_first_cate_id", data.cat1 + "");
            clickInterfaceParam.map.put("item_second_cate_id", data.cat2 + "");
            clickInterfaceParam.map.put("item_third_cate_id", data.cat3 + "");
            JDMaUtil.sendClickData(clickInterfaceParam);

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
