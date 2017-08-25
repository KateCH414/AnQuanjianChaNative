package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */

public class WareImage extends Base implements Parcelable {
    private String imagePath;
    private Integer imageIndex;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imagePath);
        dest.writeValue(this.imageIndex);
    }

    public WareImage() {
    }

    protected WareImage(Parcel in) {
        this.imagePath = in.readString();
        this.imageIndex = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<WareImage> CREATOR = new Creator<WareImage>() {
        @Override
        public WareImage createFromParcel(Parcel source) {
            return new WareImage(source);
        }

        @Override
        public WareImage[] newArray(int size) {
            return new WareImage[size];
        }
    };
}
