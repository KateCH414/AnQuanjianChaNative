package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/6.
 */

public class Relative extends Base implements Parcelable {

    public String attrName;//是否是标准单位

    public Relative() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected Relative(Parcel in) {
    }

    public static final Creator<Relative> CREATOR = new Creator<Relative>() {
        @Override
        public Relative createFromParcel(Parcel source) {
            return new Relative(source);
        }

        @Override
        public Relative[] newArray(int size) {
            return new Relative[size];
        }
    };
}
