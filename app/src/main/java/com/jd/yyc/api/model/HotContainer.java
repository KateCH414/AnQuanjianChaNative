package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by zhangweifeng1 on 2017/6/6.
 */

public class HotContainer implements Parcelable {

    public ArrayList<HotSearch> hotWords;

    public ArrayList<HotSearch> channels;

    public HotContainer() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.hotWords);
        dest.writeTypedList(this.channels);
    }

    protected HotContainer(Parcel in) {
        this.hotWords = in.createTypedArrayList(HotSearch.CREATOR);
        this.channels = in.createTypedArrayList(HotSearch.CREATOR);
    }

    public static final Creator<HotContainer> CREATOR = new Creator<HotContainer>() {
        @Override
        public HotContainer createFromParcel(Parcel source) {
            return new HotContainer(source);
        }

        @Override
        public HotContainer[] newArray(int size) {
            return new HotContainer[size];
        }
    };
}
