package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/5/18.
 */

public class Banner extends Base implements Parcelable {
    public String bigImg;
    public String img;
    public String url;
    public int source;
    public int type;
    public String searchKey;
    public long skuId;

    public Banner() {
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
        dest.writeInt(this.source);
        dest.writeInt(this.type);
        dest.writeString(this.searchKey);
        dest.writeLong(this.skuId);
    }

    protected Banner(Parcel in) {
        this.bigImg = in.readString();
        this.img = in.readString();
        this.url = in.readString();
        this.source = in.readInt();
        this.type = in.readInt();
        this.searchKey = in.readString();
        this.skuId = in.readLong();
    }

    public static final Creator<Banner> CREATOR = new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };
}
