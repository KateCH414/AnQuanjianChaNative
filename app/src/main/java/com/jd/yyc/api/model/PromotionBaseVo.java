package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 促销信息
 */

public class PromotionBaseVo extends Base implements Parcelable {
    public Long promotionId; //促销id
    public String description; //促销描述
    public String adWords; //促销广告词
    private String adLinkName; //广告词链接名称
    private String adLinkSrc; //    广告词链接地址
    private Integer limitTime; //限制购买次数
    public long beginTime; //促销活动开始时间
    public long endTime; //促销活动结束时间
    public Integer promotionType; //促销类型 见PRomotionTypeEnum
    private CetusPrice mustManPrice; //满赠促销的满多少元
    private CetusPrice addPrice;  //满赠促销加价购元
    private Integer maxChooseNum; //最多可选赠品数量
    private Double soldRate; //已售比例
    private Integer totalNum; //总库存
    private Integer saleNum; //已售库存


    public PromotionBaseVo(Long promotionId) {
        this.promotionId = promotionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.promotionId);
        dest.writeString(this.description);
        dest.writeString(this.adWords);
        dest.writeString(this.adLinkName);
        dest.writeString(this.adLinkSrc);
        dest.writeValue(this.limitTime);
        dest.writeLong(this.beginTime);
        dest.writeLong(this.endTime);
        dest.writeValue(this.promotionType);
        dest.writeParcelable(this.mustManPrice, flags);
        dest.writeParcelable(this.addPrice, flags);
        dest.writeValue(this.maxChooseNum);
        dest.writeValue(this.soldRate);
        dest.writeValue(this.totalNum);
        dest.writeValue(this.saleNum);
    }

    protected PromotionBaseVo(Parcel in) {
        this.promotionId = (Long) in.readValue(Long.class.getClassLoader());
        this.description = in.readString();
        this.adWords = in.readString();
        this.adLinkName = in.readString();
        this.adLinkSrc = in.readString();
        this.limitTime = (Integer) in.readValue(Integer.class.getClassLoader());
        this.beginTime = in.readLong();
        this.endTime = in.readLong();
        this.promotionType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.mustManPrice = in.readParcelable(CetusPrice.class.getClassLoader());
        this.addPrice = in.readParcelable(CetusPrice.class.getClassLoader());
        this.maxChooseNum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.soldRate = (Double) in.readValue(Double.class.getClassLoader());
        this.totalNum = (Integer) in.readValue(Integer.class.getClassLoader());
        this.saleNum = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<PromotionBaseVo> CREATOR = new Creator<PromotionBaseVo>() {
        @Override
        public PromotionBaseVo createFromParcel(Parcel source) {
            return new PromotionBaseVo(source);
        }

        @Override
        public PromotionBaseVo[] newArray(int size) {
            return new PromotionBaseVo[size];
        }
    };
}
