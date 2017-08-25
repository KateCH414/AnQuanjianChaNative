package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahongbin on 2017/6/8.
 */

public class SkuDetailPrice extends Base implements Parcelable {
    public Long skuId;//商品 skuid
    public Float price;//批发价
    public Float promotionPrice;//秒杀价格
    public Float retailPrice;//零售价

    public List<PromotionBaseVo> promotionBaseList;//促销信息

    public SkuDetailPrice() {
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
        dest.writeList(this.promotionBaseList);
    }

    protected SkuDetailPrice(Parcel in) {
        this.skuId = (Long) in.readValue(Long.class.getClassLoader());
        this.price = (Float) in.readValue(Float.class.getClassLoader());
        this.promotionPrice = (Float) in.readValue(Float.class.getClassLoader());
        this.retailPrice = (Float) in.readValue(Float.class.getClassLoader());
        this.promotionBaseList = new ArrayList<PromotionBaseVo>();
        in.readList(this.promotionBaseList, PromotionBaseVo.class.getClassLoader());
    }

    public static final Creator<SkuDetailPrice> CREATOR = new Creator<SkuDetailPrice>() {
        @Override
        public SkuDetailPrice createFromParcel(Parcel source) {
            return new SkuDetailPrice(source);
        }

        @Override
        public SkuDetailPrice[] newArray(int size) {
            return new SkuDetailPrice[size];
        }
    };
}
