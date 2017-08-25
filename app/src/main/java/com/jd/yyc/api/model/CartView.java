package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jiahongbin on 2017/6/1.
 */

public class CartView extends Base implements Parcelable  {

    public ArrayList<Sku> sku;//商品对象

    public Long venderId;//店铺id

    public String venderName;//店铺名称


    public CartView() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.sku);
        dest.writeValue(this.venderId);
        dest.writeString(this.venderName);
    }

    protected CartView(Parcel in) {
        this.sku = in.createTypedArrayList(Sku.CREATOR);
        this.venderId = (Long) in.readValue(Long.class.getClassLoader());
        this.venderName = in.readString();
    }

    public static final Creator<CartView> CREATOR = new Creator<CartView>() {
        @Override
        public CartView createFromParcel(Parcel source) {
            return new CartView(source);
        }

        @Override
        public CartView[] newArray(int size) {
            return new CartView[size];
        }
    };
}
