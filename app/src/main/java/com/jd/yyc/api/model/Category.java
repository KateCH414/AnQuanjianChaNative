package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/5/27.
 */

public class Category extends Base implements Parcelable {
    public String fatherCategory;
    public String Classification;
    public String Count;
    public String Name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fatherCategory);
        dest.writeString(this.Classification);
        dest.writeString(this.Count);
        dest.writeString(this.Name);
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.fatherCategory = in.readString();
        this.Classification = in.readString();
        this.Count = in.readString();
        this.Name = in.readString();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
