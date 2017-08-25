package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class MonthPay extends Base implements Parcelable {
    public int totalPage; // 总页数
    public int totalCount; //总数量
    private int currentPage; //当前页

    public List<YaoOrder> list;//订单集合

    public List<YaoOrder> getList() {
        return list == null ? new ArrayList<YaoOrder>() : list;
    }


    public MonthPay() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.totalPage);
        dest.writeInt(this.totalCount);
        dest.writeInt(this.currentPage);
        dest.writeTypedList(this.list);
    }

    protected MonthPay(Parcel in) {
        this.totalPage = in.readInt();
        this.totalCount = in.readInt();
        this.currentPage = in.readInt();
        this.list = in.createTypedArrayList(YaoOrder.CREATOR);
    }

    public static final Creator<MonthPay> CREATOR = new Creator<MonthPay>() {
        @Override
        public MonthPay createFromParcel(Parcel source) {
            return new MonthPay(source);
        }

        @Override
        public MonthPay[] newArray(int size) {
            return new MonthPay[size];
        }
    };
}
