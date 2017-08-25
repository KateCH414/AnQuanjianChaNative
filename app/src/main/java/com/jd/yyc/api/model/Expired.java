package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jiahongbin on 2017/6/5.
 */

public class Expired extends Base implements Parcelable {

    public Integer totalCount;

    public ArrayList<CouponModel> expired;//已过期

    public Expired() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected Expired(Parcel in) {
    }

    public static final Creator<Expired> CREATOR = new Creator<Expired>() {
        @Override
        public Expired createFromParcel(Parcel source) {
            return new Expired(source);
        }

        @Override
        public Expired[] newArray(int size) {
            return new Expired[size];
        }
    };
}
