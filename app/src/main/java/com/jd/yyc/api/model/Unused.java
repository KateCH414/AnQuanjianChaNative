package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jiahongbin on 2017/6/13.
 */

public class Unused extends Base implements Parcelable {

    public Integer totalCount;

    public ArrayList<CouponModel> expired;//未使用


    public Unused() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.totalCount);
        dest.writeList(this.expired);
    }

    protected Unused(Parcel in) {
        this.totalCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.expired = new ArrayList<CouponModel>();
        in.readList(this.expired, CouponModel.class.getClassLoader());
    }

    public static final Creator<Unused> CREATOR = new Creator<Unused>() {
        @Override
        public Unused createFromParcel(Parcel source) {
            return new Unused(source);
        }

        @Override
        public Unused[] newArray(int size) {
            return new Unused[size];
        }
    };
}
