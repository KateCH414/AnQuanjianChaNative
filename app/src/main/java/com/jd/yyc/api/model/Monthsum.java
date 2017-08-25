package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/13.
 */

public class Monthsum extends Base implements Parcelable {


    public MonthPay monthPay;//月结待付款

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.monthPay, flags);
    }

    public Monthsum() {
    }

    protected Monthsum(Parcel in) {
        this.monthPay = in.readParcelable(MonthPay.class.getClassLoader());
    }

    public static final Creator<Monthsum> CREATOR = new Creator<Monthsum>() {
        @Override
        public Monthsum createFromParcel(Parcel source) {
            return new Monthsum(source);
        }

        @Override
        public Monthsum[] newArray(int size) {
            return new Monthsum[size];
        }
    };
}
