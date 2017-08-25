package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by zhangweifeng1 on 2017/5/24.
 */

public class Floor extends Base implements Parcelable {
    public long id;//楼层id
    public String name;//楼层名称
    public Integer areaId;//地区id
    public ArrayList<FloorInfo> floorInfos;//楼层信息

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeValue(this.areaId);
        dest.writeList(this.floorInfos);
    }

    public Floor() {
    }

    protected Floor(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.areaId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.floorInfos = new ArrayList<FloorInfo>();
        in.readList(this.floorInfos, FloorInfo.class.getClassLoader());
    }

    public static final Parcelable.Creator<Floor> CREATOR = new Parcelable.Creator<Floor>() {
        @Override
        public Floor createFromParcel(Parcel source) {
            return new Floor(source);
        }

        @Override
        public Floor[] newArray(int size) {
            return new Floor[size];
        }
    };
}
