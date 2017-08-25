package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class SearchContent extends Base implements Parcelable {

    public String factory;
    public String img;
    public String shop_name;
    public String ware_name;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.factory);
        dest.writeString(this.img);
        dest.writeString(this.shop_name);
        dest.writeString(this.ware_name);
    }

    public SearchContent() {
    }

    protected SearchContent(Parcel in) {
        this.factory = in.readString();
        this.img = in.readString();
        this.shop_name = in.readString();
        this.ware_name = in.readString();
    }

    public static final Parcelable.Creator<SearchContent> CREATOR = new Parcelable.Creator<SearchContent>() {
        @Override
        public SearchContent createFromParcel(Parcel source) {
            return new SearchContent(source);
        }

        @Override
        public SearchContent[] newArray(int size) {
            return new SearchContent[size];
        }
    };
}
