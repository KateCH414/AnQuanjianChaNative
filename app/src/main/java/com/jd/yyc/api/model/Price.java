package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zhangweifeng1 on 2017/6/8.
 */

public class Price extends Base implements Parcelable {
    public long skuId;
    public String price;//商品掌柜价格 (批发价)
    public String promotionPrice;//促销价格
    public String retailPrice;//零售价
    public List<PromotionBaseVo> promotionList;//促销信息
    public int status;     //1认证可见； 2建立采购关系可见； 0 正常显示
    public long currDate;  //当前时间

    public Price() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.skuId);
        dest.writeString(this.price);
        dest.writeString(this.promotionPrice);
        dest.writeString(this.retailPrice);
        dest.writeTypedList(this.promotionList);
        dest.writeInt(this.status);
        dest.writeLong(this.currDate);
    }

    protected Price(Parcel in) {
        this.skuId = in.readLong();
        this.price = in.readString();
        this.promotionPrice = in.readString();
        this.retailPrice = in.readString();
        this.promotionList = in.createTypedArrayList(PromotionBaseVo.CREATOR);
        this.status = in.readInt();
        this.currDate = in.readLong();
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}
