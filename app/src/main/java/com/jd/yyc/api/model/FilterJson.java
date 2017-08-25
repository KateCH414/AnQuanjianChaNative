package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class FilterJson extends Base implements Parcelable {
    public String type;
    public ArrayList<FilterId> values;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeTypedList(this.values);
    }

    public FilterJson() {
    }

    protected FilterJson(Parcel in) {
        this.type = in.readString();
        this.values = in.createTypedArrayList(FilterId.CREATOR);
    }

    public static final Parcelable.Creator<FilterJson> CREATOR = new Parcelable.Creator<FilterJson>() {
        @Override
        public FilterJson createFromParcel(Parcel source) {
            return new FilterJson(source);
        }

        @Override
        public FilterJson[] newArray(int size) {
            return new FilterJson[size];
        }
    };
}
