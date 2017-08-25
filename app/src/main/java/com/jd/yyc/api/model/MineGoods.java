package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class MineGoods extends Base implements Parcelable {
    public int totalPage; // 总页数
    public int totalCount; //总数量
    private int currentPage; //当前页

    private List<YaoOrder> list;//订单集合

    public List<YaoOrder> getList(){
        return list == null?new ArrayList<YaoOrder>():list;
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

    public MineGoods() {
    }

    protected MineGoods(Parcel in) {
        this.totalPage = in.readInt();
        this.totalCount = in.readInt();
        this.currentPage = in.readInt();
        this.list = in.createTypedArrayList(YaoOrder.CREATOR);
    }

    public static final Creator<MineGoods> CREATOR = new Creator<MineGoods>() {
        @Override
        public MineGoods createFromParcel(Parcel source) {
            return new MineGoods(source);
        }

        @Override
        public MineGoods[] newArray(int size) {
            return new MineGoods[size];
        }
    };
}
