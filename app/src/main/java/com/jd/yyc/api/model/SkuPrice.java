package com.jd.yyc.api.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;
import com.jd.yyc.util.CheckTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangweifeng1 on 2017/5/26.
 */

public class SkuPrice extends Base implements Parcelable {
    private Long skuId;//商品 skuid
    private Float price;//商品掌柜价格 (批发价)
    private Float promotionPrice; //促销价格
    private Float retailPrice; //建议零售价
    private String properties; //附加信息以json形式返回，如：{code:-1}   code = -5 或 -3 认证后可见； code = -6 建立采购关系可见
    private List<PromotionBaseModel> promotionList; //促销信息


    /**
     * 价格是否有直降
     * @return
     */
    public boolean priceDown(){
        boolean priceDown = false;
        if (!CheckTool.isEmpty(promotionList)) {
            for (PromotionBaseModel promotionBaseModel : promotionList) {
                if (promotionBaseModel.getPromotionType() == 3) { //3, "直降    7, "秒杀
                    priceDown = true;
                    break;
                }
            }
        }
        return priceDown;
    }

    //是否是秒杀价格
    public boolean secKill() {
        boolean secKill = false;
        if (!CheckTool.isEmpty(promotionList)) {
            for (PromotionBaseModel promotionBaseModel : promotionList) {
                if (promotionBaseModel.getPromotionType() == 7) { //3, "直降    7, "秒杀
                    secKill = true;
                    break;
                }
            }
        }
        return secKill;
    }

    public Long getSkuId(){
        return skuId == null?0:skuId;
    }

    public float getPrice(){
        return price == null ? 0 : price;
    }

    public float getPromotionPrice() {
        return promotionPrice == null ? 0 : promotionPrice;
    }

    public static void getPriceList(Context context, List<Long> ids, final PriceCallback priceCallback) {
        NetLoading.getInstance().getSkuPriceList(context, ids, new RequestCallback<ResultObject<List<SkuPrice>>>() {
            @Override
            public void requestCallBack(boolean success, ResultObject<List<SkuPrice>> result, String err) {
                if(success && result.success){
                    priceCallback.requestCallback(null,result.data);
                }else {
                    priceCallback.onFailed(result!=null?result.msg :err);
                }
            }
        });
    }


    public interface PriceCallback {
        void requestCallback(SkuPrice skuPrice, List<SkuPrice> skuPriceList);

        void onFailed(String msg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.skuId);
        dest.writeValue(this.price);
        dest.writeValue(this.promotionPrice);
        dest.writeValue(this.retailPrice);
        dest.writeString(this.properties);
        dest.writeList(this.promotionList);
    }

    public SkuPrice() {
    }

    protected SkuPrice(Parcel in) {
        this.skuId = (Long) in.readValue(Long.class.getClassLoader());
        this.price = (Float) in.readValue(Float.class.getClassLoader());
        this.promotionPrice = (Float) in.readValue(Float.class.getClassLoader());
        this.retailPrice = (Float) in.readValue(Float.class.getClassLoader());
        this.properties = in.readString();
        this.promotionList = new ArrayList<PromotionBaseModel>();
        in.readList(this.promotionList, PromotionBaseModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<SkuPrice> CREATOR = new Parcelable.Creator<SkuPrice>() {
        @Override
        public SkuPrice createFromParcel(Parcel source) {
            return new SkuPrice(source);
        }

        @Override
        public SkuPrice[] newArray(int size) {
            return new SkuPrice[size];
        }
    };
}
