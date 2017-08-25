package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class SearchObjCollection extends Base implements Parcelable {
    public List<Category> shop_id;
    public List<Category> cat1_id;

    public SearchObjCollection() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.shop_id);
        dest.writeTypedList(this.cat1_id);
    }

    protected SearchObjCollection(Parcel in) {
        this.shop_id = in.createTypedArrayList(Category.CREATOR);
        this.cat1_id = in.createTypedArrayList(Category.CREATOR);
    }

    public static final Creator<SearchObjCollection> CREATOR = new Creator<SearchObjCollection>() {
        @Override
        public SearchObjCollection createFromParcel(Parcel source) {
            return new SearchObjCollection(source);
        }

        @Override
        public SearchObjCollection[] newArray(int size) {
            return new SearchObjCollection[size];
        }
    };
}
