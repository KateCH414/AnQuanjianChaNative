package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jiahongbin on 2017/6/1.
 */

public class Cart extends Base implements Parcelable {

    public ArrayList<CartView> cartView;//购物车信息

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.cartView);
    }

    public Cart() {
    }

    protected Cart(Parcel in) {
        this.cartView = new ArrayList<CartView>();
        in.readList(this.cartView, CartView.class.getClassLoader());
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };
}
