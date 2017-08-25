package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class SearchSummary extends Base implements Parcelable {
    public String ResultCount;
    public PagingBase Page;

    public SearchSummary() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ResultCount);
        dest.writeParcelable(this.Page, flags);
    }

    protected SearchSummary(Parcel in) {
        this.ResultCount = in.readString();
        this.Page = in.readParcelable(PagingBase.class.getClassLoader());
    }

    public static final Creator<SearchSummary> CREATOR = new Creator<SearchSummary>() {
        @Override
        public SearchSummary createFromParcel(Parcel source) {
            return new SearchSummary(source);
        }

        @Override
        public SearchSummary[] newArray(int size) {
            return new SearchSummary[size];
        }
    };
}
