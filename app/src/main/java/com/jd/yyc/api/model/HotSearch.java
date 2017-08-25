package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/6/6.
 */

public class HotSearch extends Base implements Parcelable {
    public String name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public HotSearch() {
    }

    protected HotSearch(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<HotSearch> CREATOR = new Parcelable.Creator<HotSearch>() {
        @Override
        public HotSearch createFromParcel(Parcel source) {
            return new HotSearch(source);
        }

        @Override
        public HotSearch[] newArray(int size) {
            return new HotSearch[size];
        }
    };
}
