package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 店铺实体
 */

public class SkuSum extends Base implements Parcelable {

    private Float totalPrice; //总价格
    private int checkedItemCnt; //选中商品项的数量
    @SerializedName("productView")
    private List<ProductModel> skuList;//商品对象
    private Long venderId;//店铺id
    private String venderName;//店铺名称
    public int total;
    private int limit;
    private int checkedTotal;

    public int getCheckedItemCnt() {
        return checkedItemCnt;
    }

    public void setCheckedItemCnt(int checkedItemCnt) {
        this.checkedItemCnt = checkedItemCnt;
    }



    public Long getVenderId() {
        return venderId;
    }

    public void setVenderId(Long venderId) {
        this.venderId = venderId;
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCheckedTotal() {
        return checkedTotal;
    }

    public void setCheckedTotal(int checkedTotal) {
        this.checkedTotal = checkedTotal;
    }

    public static Creator<SkuSum> getCREATOR() {
        return CREATOR;
    }


    public SkuSum() {
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ProductModel> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ProductModel> skuList) {
        this.skuList = skuList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.totalPrice);
        dest.writeInt(this.checkedItemCnt);
        dest.writeTypedList(this.skuList);
        dest.writeValue(this.venderId);
        dest.writeString(this.venderName);
        dest.writeInt(this.total);
        dest.writeInt(this.limit);
        dest.writeInt(this.checkedTotal);
    }

    protected SkuSum(Parcel in) {
        this.totalPrice = (Float) in.readValue(Float.class.getClassLoader());
        this.checkedItemCnt = in.readInt();
        this.skuList = in.createTypedArrayList(ProductModel.CREATOR);
        this.venderId = (Long) in.readValue(Long.class.getClassLoader());
        this.venderName = in.readString();
        this.total = in.readInt();
        this.limit = in.readInt();
        this.checkedTotal = in.readInt();
    }

    public static final Creator<SkuSum> CREATOR = new Creator<SkuSum>() {
        @Override
        public SkuSum createFromParcel(Parcel source) {
            return new SkuSum(source);
        }

        @Override
        public SkuSum[] newArray(int size) {
            return new SkuSum[size];
        }
    };
}
