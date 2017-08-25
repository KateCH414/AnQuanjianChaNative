package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class SearchHead extends Base implements Parcelable {
    public SearchSummary Summary;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.Summary);
    }

    public SearchHead() {
    }

    protected SearchHead(Parcel in) {
        this.Summary = (SearchSummary) in.readSerializable();
    }

    public static final Parcelable.Creator<SearchHead> CREATOR = new Parcelable.Creator<SearchHead>() {
        @Override
        public SearchHead createFromParcel(Parcel source) {
            return new SearchHead(source);
        }

        @Override
        public SearchHead[] newArray(int size) {
            return new SearchHead[size];
        }
    };
}
