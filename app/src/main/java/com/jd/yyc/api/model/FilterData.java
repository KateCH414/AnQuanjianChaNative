package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class FilterData extends Base implements Parcelable {
    public ArrayList<FilterJson> data;

    public FilterData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.data);
    }

    protected FilterData(Parcel in) {
        this.data = in.createTypedArrayList(FilterJson.CREATOR);
    }

    public static final Creator<FilterData> CREATOR = new Creator<FilterData>() {
        @Override
        public FilterData createFromParcel(Parcel source) {
            return new FilterData(source);
        }

        @Override
        public FilterData[] newArray(int size) {
            return new FilterData[size];
        }
    };
}
