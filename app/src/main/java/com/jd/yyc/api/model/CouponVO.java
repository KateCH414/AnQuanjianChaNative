package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jd.project.lib.andlib.utils.DateFormatUtil;
import com.jd.yyc.R;
import com.jd.yyc.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class CouponVO extends Base implements Parcelable {
    public String id; // id
    public String key; //优惠券的密码
    public String actKey; //活动加密key
    public Long actRuleId; //活动规则id
    public int type; //京券=0，东券=1，运费券=2（couponType=2和couponStyle=2才是运费券）
    public String quota; //限额  如果是京券，该值=0，如果是东券，改值=使用该券的最低金额
    public String discount; //优惠金额，面值
    public Long beginTime; //开始时间
    public Long endTime;   //结束时间
    public int state; //
    public int couponLimitType;
    public String couponLimitInfo; //仅可购买河北地区部分商品

    public String limitCat; //限品类
    public String limitPlatform; //限平台
    public int tag; //0-正常，1-即将过期
    public String useUrl; //自己使用url
    public Long batchId;
    public int areaId;  //区域id
    public String areaName; //区域名称


    private int couponType; //14 15 已领取 16  已领完 17  已抢完  优惠券状态

    private boolean isYours; //是否在你在账户里
    private boolean isGet; //是否已经领取


    public String getId() {
        return id == null ? "" : id;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }


    public String getCouponLimitInfo() {
        return couponLimitInfo == null ? "" : couponLimitInfo;
    }

    public String getQuota() {
        return quota == null ? "" : quota;
    }

    public String getDiscount() {
        return discount == null ? "" : discount;
    }

    public String getActKey() {
        return actKey == null ? "" : actKey;
    }

    public Long getActRuleId() {
        return actRuleId == null ? 0 : actRuleId;
    }

    public boolean isYours() {
        return couponType == 14 || couponType == 15;
    }

    public boolean isGet() {
        return isGet;
    }

    public void setGet(boolean isGet) {
        this.isGet = isGet;
    }

    public String getTagStr() {
        String tag = "";
        switch (type) {
            case 0:
                tag = "京券";
                break;
            case 1:
                tag = "东券";
                break;
            case 2:
                tag = "运费券";
                break;
            default:
                tag = "京券";
                break;
        }
        return tag;
    }

    public String getAreaName() {
        return areaName == null ? "" : areaName;
    }

    public String getDateStr() {
        String st = DateFormatUtil.dateFormat(DateFormatUtil.yyyy_MM_dd_2, beginTime);
        String et = DateFormatUtil.dateFormat(DateFormatUtil.yyyy_MM_dd_2, endTime);
        return ResourceUtil.getString(R.string.date_quantum, st, et);
    }

    public boolean showGetBtn() {
        if (isYours() || isGet()) {
            return false;
        } else {
            return true;
        }
    }


    public static CouponVO getDemo() {
        return new CouponVO();
    }

    public static List<CouponVO> getDemoList() {
        List<CouponVO> couponModelList = new ArrayList<>();
        couponModelList.add(getDemo());
        couponModelList.add(getDemo());
        return couponModelList;
    }

    public CouponVO() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.actKey);
        dest.writeValue(this.actRuleId);
        dest.writeInt(this.type);
        dest.writeString(this.quota);
        dest.writeString(this.discount);
        dest.writeValue(this.beginTime);
        dest.writeValue(this.endTime);
        dest.writeInt(this.state);
        dest.writeInt(this.couponLimitType);
        dest.writeString(this.couponLimitInfo);
        dest.writeString(this.limitCat);
        dest.writeString(this.limitPlatform);
        dest.writeInt(this.tag);
        dest.writeString(this.useUrl);
        dest.writeValue(this.batchId);
        dest.writeInt(this.areaId);
        dest.writeString(this.areaName);
        dest.writeInt(this.couponType);
        dest.writeByte(this.isYours ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isGet ? (byte) 1 : (byte) 0);
    }

    protected CouponVO(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.actKey = in.readString();
        this.actRuleId = (Long) in.readValue(Long.class.getClassLoader());
        this.type = in.readInt();
        this.quota = in.readString();
        this.discount = in.readString();
        this.beginTime = (Long) in.readValue(Long.class.getClassLoader());
        this.endTime = (Long) in.readValue(Long.class.getClassLoader());
        this.state = in.readInt();
        this.couponLimitType = in.readInt();
        this.couponLimitInfo = in.readString();
        this.limitCat = in.readString();
        this.limitPlatform = in.readString();
        this.tag = in.readInt();
        this.useUrl = in.readString();
        this.batchId = (Long) in.readValue(Long.class.getClassLoader());
        this.areaId = in.readInt();
        this.areaName = in.readString();
        this.couponType = in.readInt();
        this.isYours = in.readByte() != 0;
        this.isGet = in.readByte() != 0;
    }

    public static final Creator<CouponVO> CREATOR = new Creator<CouponVO>() {
        @Override
        public CouponVO createFromParcel(Parcel source) {
            return new CouponVO(source);
        }

        @Override
        public CouponVO[] newArray(int size) {
            return new CouponVO[size];
        }
    };
}
