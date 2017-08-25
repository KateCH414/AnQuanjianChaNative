package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by jiahongbin on 2017/6/3.
 */

public class YaoOrderSku  extends Base implements Parcelable {
    private Long skuId;
    private String wareName;
    private Float purchasePrice;
    private List<WareImage> imageList;
    private String mainImgUrl;
    private String fullMainImgUrl;
    private Integer skuNum;

    public Long getSkuId() {
        return skuId == null ? 0 : skuId;
    }

    public Integer getSkuNum() {
        return skuNum == null ? 0 : skuNum;
    }

    public String getMainImgUrl(){
        return mainImgUrl==null?"":mainImgUrl;
    }

    public String getFullMainImgUrl() {
        return fullMainImgUrl == null ? "" : fullMainImgUrl;
    }

    public String getWareName(){
        return wareName == null?"":wareName;
    }
















    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.skuId);
        dest.writeString(this.wareName);
        dest.writeValue(this.purchasePrice);
        dest.writeTypedList(this.imageList);
        dest.writeString(this.mainImgUrl);
        dest.writeValue(this.skuNum);
    }

    public YaoOrderSku() {
    }

    protected YaoOrderSku(Parcel in) {
        this.skuId = (Long) in.readValue(Long.class.getClassLoader());
        this.wareName = in.readString();
        this.purchasePrice = (Float) in.readValue(Float.class.getClassLoader());
        this.imageList = in.createTypedArrayList(WareImage.CREATOR);
        this.mainImgUrl = in.readString();
        this.skuNum = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<YaoOrderSku> CREATOR = new Creator<YaoOrderSku>() {
        @Override
        public YaoOrderSku createFromParcel(Parcel source) {
            return new YaoOrderSku(source);
        }

        @Override
        public YaoOrderSku[] newArray(int size) {
            return new YaoOrderSku[size];
        }
    };
}
