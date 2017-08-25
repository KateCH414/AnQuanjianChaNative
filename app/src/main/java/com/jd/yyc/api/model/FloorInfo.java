package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by zhangweifeng1 on 2017/5/24.
 */

public class FloorInfo extends Base implements Parcelable {
    public String name;//楼层标签名称
    public ArrayList<Sku> skuList;//楼层商品

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeTypedList(this.skuList);
    }

    public FloorInfo() {
    }

    protected FloorInfo(Parcel in) {
        this.name = in.readString();
        this.skuList = in.createTypedArrayList(Sku.CREATOR);
    }

    public static final Parcelable.Creator<FloorInfo> CREATOR = new Parcelable.Creator<FloorInfo>() {
        @Override
        public FloorInfo createFromParcel(Parcel source) {
            return new FloorInfo(source);
        }

        @Override
        public FloorInfo[] newArray(int size) {
            return new FloorInfo[size];
        }
    };
}
