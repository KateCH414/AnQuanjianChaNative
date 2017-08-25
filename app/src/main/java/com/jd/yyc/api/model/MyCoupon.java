package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahongbin on 2017/6/12.
 */

public class MyCoupon extends Base implements Parcelable {

    public List<CouponVO> list;
    public int totalCount;

    /*   public Unused unused;
    public Used used;*/

    public MyCoupon() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.list);
        dest.writeInt(this.totalCount);
    }

    protected MyCoupon(Parcel in) {
        this.list = new ArrayList<CouponVO>();
        in.readList(this.list, CouponVO.class.getClassLoader());
        this.totalCount = in.readInt();
    }

    public static final Creator<MyCoupon> CREATOR = new Creator<MyCoupon>() {
        @Override
        public MyCoupon createFromParcel(Parcel source) {
            return new MyCoupon(source);
        }

        @Override
        public MyCoupon[] newArray(int size) {
            return new MyCoupon[size];
        }
    };
}
