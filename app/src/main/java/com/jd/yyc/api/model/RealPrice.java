package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/6.
 */

public class RealPrice extends Base implements  Parcelable {

    public Float amount;//零售价

    public RealPrice() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.amount);
    }

    protected RealPrice(Parcel in) {
        this.amount = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Creator<RealPrice> CREATOR = new Creator<RealPrice>() {
        @Override
        public RealPrice createFromParcel(Parcel source) {
            return new RealPrice(source);
        }

        @Override
        public RealPrice[] newArray(int size) {
            return new RealPrice[size];
        }
    };
}
