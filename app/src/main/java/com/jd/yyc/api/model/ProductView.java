package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/1.
 */

public class ProductView extends Base implements Parcelable {

    public Long skuId;//商品id
    public Long venderId ;//商家id
    public Long num;//数量
    public String name;//商品名称
    public String imgUrl;//图片地址

    public String price;//价格
    public int checkType;//勾选状态，0未勾选，1勾选
    public int stockNum;//库存
    public boolean hasStock;//是否有库存
    public String priceStatusEnum;//价格是否有效
    public boolean canBuy;//是否可买

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.skuId);
        dest.writeValue(this.venderId);
        dest.writeValue(this.num);
        dest.writeString(this.name);
        dest.writeString(this.imgUrl);
        dest.writeString(this.price);
        dest.writeInt(this.checkType);
        dest.writeInt(this.stockNum);
        dest.writeByte(this.hasStock ? (byte) 1 : (byte) 0);
        dest.writeString(this.priceStatusEnum);
        dest.writeByte(this.canBuy ? (byte) 1 : (byte) 0);
    }

    public ProductView() {
    }

    protected ProductView(Parcel in) {
        this.skuId = (Long) in.readValue(Long.class.getClassLoader());
        this.venderId = (Long) in.readValue(Long.class.getClassLoader());
        this.num = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.imgUrl = in.readString();
        this.price = in.readString();
        this.checkType = in.readInt();
        this.stockNum = in.readInt();
        this.hasStock = in.readByte() != 0;
        this.priceStatusEnum = in.readString();
        this.canBuy = in.readByte() != 0;
    }

    public static final Creator<ProductView> CREATOR = new Creator<ProductView>() {
        @Override
        public ProductView createFromParcel(Parcel source) {
            return new ProductView(source);
        }

        @Override
        public ProductView[] newArray(int size) {
            return new ProductView[size];
        }
    };
}
