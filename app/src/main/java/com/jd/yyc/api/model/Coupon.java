package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/5.
 */

public class Coupon extends Base implements Parcelable {

    public Expired expired;//

    public Unused unused;//未使用


    public String discount; //金额

    public String areaName; //区域名称
    public Long beginTime;//开始时间
    public Long endTime;//截止时间
    public String quota; //限额

    public String type;//类型


    public String limitCat;//限制地区

    public Coupon() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.expired, flags);
        dest.writeParcelable(this.unused, flags);
        dest.writeString(this.discount);
        dest.writeString(this.areaName);
        dest.writeValue(this.beginTime);
        dest.writeValue(this.endTime);
        dest.writeString(this.quota);
        dest.writeString(this.type);
        dest.writeString(this.limitCat);
    }

    protected Coupon(Parcel in) {
        this.expired = in.readParcelable(Expired.class.getClassLoader());
        this.unused = in.readParcelable(Unused.class.getClassLoader());
        this.discount = in.readString();
        this.areaName = in.readString();
        this.beginTime = (Long) in.readValue(Long.class.getClassLoader());
        this.endTime = (Long) in.readValue(Long.class.getClassLoader());
        this.quota = in.readString();
        this.type = in.readString();
        this.limitCat = in.readString();
    }

    public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel source) {
            return new Coupon(source);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}
