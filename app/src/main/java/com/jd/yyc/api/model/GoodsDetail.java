package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/8.
 */

public class GoodsDetail extends Base implements Parcelable {
    SkuDetailPrice [] SkuDetailPrice;//数组

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.SkuDetailPrice, flags);
    }

    public GoodsDetail() {
    }

    protected GoodsDetail(Parcel in) {
        this.SkuDetailPrice = in.createTypedArray(com.jd.yyc.api.model.SkuDetailPrice.CREATOR);
    }

    public static final Creator<GoodsDetail> CREATOR = new Creator<GoodsDetail>() {
        @Override
        public GoodsDetail createFromParcel(Parcel source) {
            return new GoodsDetail(source);
        }

        @Override
        public GoodsDetail[] newArray(int size) {
            return new GoodsDetail[size];
        }
    };
}
