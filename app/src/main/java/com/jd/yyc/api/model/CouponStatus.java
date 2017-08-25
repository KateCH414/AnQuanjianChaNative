package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/23.
 */

public class CouponStatus extends Base implements Parcelable {

    public String id;//优惠券id
    public String state;//优惠券状态

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.state);
    }

    public CouponStatus() {
    }

    protected CouponStatus(Parcel in) {
        this.id = in.readString();
        this.state = in.readString();
    }

    public static final Creator<CouponStatus> CREATOR = new Creator<CouponStatus>() {
        @Override
        public CouponStatus createFromParcel(Parcel source) {
            return new CouponStatus(source);
        }

        @Override
        public CouponStatus[] newArray(int size) {
            return new CouponStatus[size];
        }
    };
}
