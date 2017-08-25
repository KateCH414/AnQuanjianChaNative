package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/8.
 */

public class relationShip extends Base implements Parcelable {

    public static Boolean relationship;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.relationship);
    }

    public relationShip() {
    }

    protected relationShip(Parcel in) {
        this.relationship = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<relationShip> CREATOR = new Creator<relationShip>() {
        @Override
        public relationShip createFromParcel(Parcel source) {
            return new relationShip(source);
        }

        @Override
        public relationShip[] newArray(int size) {
            return new relationShip[size];
        }
    };
}
