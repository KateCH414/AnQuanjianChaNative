package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class FilterId extends Base implements Parcelable {
    public String id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
    }

    public FilterId() {
    }

    protected FilterId(Parcel in) {
        this.id = in.readString();
    }

    public static final Parcelable.Creator<FilterId> CREATOR = new Parcelable.Creator<FilterId>() {
        @Override
        public FilterId createFromParcel(Parcel source) {
            return new FilterId(source);
        }

        @Override
        public FilterId[] newArray(int size) {
            return new FilterId[size];
        }
    };
}
