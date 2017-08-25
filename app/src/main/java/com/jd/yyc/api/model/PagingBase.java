package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zhangweifeng1 on 2017/6/5.
 */

public class PagingBase extends Base implements Parcelable {
    public long totalPage;
    public long PageSize;
    public long pageSize;
    public long totalCount;
    public long PageCount;
    public long PageIndex;

    public PagingBase() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.totalPage);
        dest.writeLong(this.PageSize);
        dest.writeLong(this.pageSize);
        dest.writeLong(this.totalCount);
        dest.writeLong(this.PageCount);
        dest.writeLong(this.PageIndex);
    }

    protected PagingBase(Parcel in) {
        this.totalPage = in.readLong();
        this.PageSize = in.readLong();
        this.pageSize = in.readLong();
        this.totalCount = in.readLong();
        this.PageCount = in.readLong();
        this.PageIndex = in.readLong();
    }

    public static final Creator<PagingBase> CREATOR = new Creator<PagingBase>() {
        @Override
        public PagingBase createFromParcel(Parcel source) {
            return new PagingBase(source);
        }

        @Override
        public PagingBase[] newArray(int size) {
            return new PagingBase[size];
        }
    };
}
