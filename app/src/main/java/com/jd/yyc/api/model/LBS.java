package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/5/26.
 */

public class LBS extends Base implements Parcelable {
    public long id;//区域id
    public String name;//区域名称

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    public LBS() {
    }

    protected LBS(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<LBS> CREATOR = new Parcelable.Creator<LBS>() {
        @Override
        public LBS createFromParcel(Parcel source) {
            return new LBS(source);
        }

        @Override
        public LBS[] newArray(int size) {
            return new LBS[size];
        }
    };
}
