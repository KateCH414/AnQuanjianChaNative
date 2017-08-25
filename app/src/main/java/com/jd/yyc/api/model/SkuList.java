package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zhangweifeng1 on 2017/6/2.
 */

public class SkuList extends PagingBase implements Parcelable {
    public List<Sku> list;

    public SkuList() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    protected SkuList(Parcel in) {
        this.list = in.createTypedArrayList(Sku.CREATOR);
    }

    public static final Creator<SkuList> CREATOR = new Creator<SkuList>() {
        @Override
        public SkuList createFromParcel(Parcel source) {
            return new SkuList(source);
        }

        @Override
        public SkuList[] newArray(int size) {
            return new SkuList[size];
        }
    };
}
