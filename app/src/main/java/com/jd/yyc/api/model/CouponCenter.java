package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jiahongbin on 2017/6/19.
 */

public class CouponCenter extends Base implements Parcelable {
    public int areaId;
    public String areaName;
    public ArrayList<CouponVO> coupons;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.areaId);
        dest.writeString(this.areaName);
        dest.writeTypedList(this.coupons);
    }

    public CouponCenter() {
    }

    protected CouponCenter(Parcel in) {
        this.areaId = in.readInt();
        this.areaName = in.readString();
        this.coupons = in.createTypedArrayList(CouponVO.CREATOR);
    }

    public static final Creator<CouponCenter> CREATOR = new Creator<CouponCenter>() {
        @Override
        public CouponCenter createFromParcel(Parcel source) {
            return new CouponCenter(source);
        }

        @Override
        public CouponCenter[] newArray(int size) {
            return new CouponCenter[size];
        }
    };
}
