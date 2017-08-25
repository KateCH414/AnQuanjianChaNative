package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/6.
 */

public class Shop  extends Base implements Parcelable {

    public Long venderId;//商品id
    public Long id;//shop_id
    public String title;//店铺名称

    public Shop() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.venderId);
        dest.writeValue(this.id);
        dest.writeString(this.title);
    }

    protected Shop(Parcel in) {
        this.venderId = (Long) in.readValue(Long.class.getClassLoader());
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}
