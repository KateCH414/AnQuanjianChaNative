package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/6.
 */

public class GoodsInfo extends Base implements Parcelable {

    public Sku sku;//商品

    public SkuExt skuExt;//sku扩展属性

    public Shop shop;//shop


    public Sku getSku() {
        return sku == null ? new Sku() : sku;
    }

    public GoodsInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.sku, flags);
        dest.writeParcelable(this.skuExt, flags);
        dest.writeParcelable(this.shop, flags);
    }

    protected GoodsInfo(Parcel in) {
        this.sku = in.readParcelable(Sku.class.getClassLoader());
        this.skuExt = in.readParcelable(SkuExt.class.getClassLoader());
        this.shop = in.readParcelable(Shop.class.getClassLoader());
    }

    public static final Creator<GoodsInfo> CREATOR = new Creator<GoodsInfo>() {
        @Override
        public GoodsInfo createFromParcel(Parcel source) {
            return new GoodsInfo(source);
        }

        @Override
        public GoodsInfo[] newArray(int size) {
            return new GoodsInfo[size];
        }
    };
}
