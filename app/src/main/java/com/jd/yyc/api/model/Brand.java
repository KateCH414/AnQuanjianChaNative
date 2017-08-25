package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/5/25.
 */

public class Brand extends Base implements Parcelable {
    public String bigImg;
    public String img;
    public String url;
    public String searchKey;

    public Brand() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bigImg);
        dest.writeString(this.img);
        dest.writeString(this.url);
        dest.writeString(this.searchKey);
    }

    protected Brand(Parcel in) {
        this.bigImg = in.readString();
        this.img = in.readString();
        this.url = in.readString();
        this.searchKey = in.readString();
    }

    public static final Creator<Brand> CREATOR = new Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel source) {
            return new Brand(source);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };
}
