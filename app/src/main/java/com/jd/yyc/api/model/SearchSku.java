package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class SearchSku extends Base implements Parcelable {
    public SearchContent Content;
    public long shop_id;
    public long sku_id;
    public long vender_id;
    public Price myPrice;
    public float stock;

    public SearchSku() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.Content, flags);
        dest.writeLong(this.shop_id);
        dest.writeLong(this.sku_id);
        dest.writeLong(this.vender_id);
        dest.writeParcelable(this.myPrice, flags);
        dest.writeFloat(this.stock);
    }

    protected SearchSku(Parcel in) {
        this.Content = in.readParcelable(SearchContent.class.getClassLoader());
        this.shop_id = in.readLong();
        this.sku_id = in.readLong();
        this.vender_id = in.readLong();
        this.myPrice = in.readParcelable(Price.class.getClassLoader());
        this.stock = in.readFloat();
    }

    public static final Creator<SearchSku> CREATOR = new Creator<SearchSku>() {
        @Override
        public SearchSku createFromParcel(Parcel source) {
            return new SearchSku(source);
        }

        @Override
        public SearchSku[] newArray(int size) {
            return new SearchSku[size];
        }
    };
}
