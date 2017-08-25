package com.jd.yyc.api.model;

import android.text.format.DateUtils;

import com.jd.project.lib.andlib.utils.DateFormatUtil;
import com.jd.yyc.R;
import com.jd.yyc.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class CouponModel extends Base {
    private String id; // id
    private String key; //优惠券的密码
    private String actKey; //活动加密key
    private Long actRuleId; //活动规则id
    private int type; //京券=0，东券=1，运费券=2（couponType=2和couponStyle=2才是运费券）
    private String quota; //限额  如果是京券，该值=0，如果是东券，改值=使用该券的最低金额
    private String discount; //优惠金额，面值
    private long beginTime; //开始时间
    private long endTime;   //结束时间
    private int state; //
    private int couponLimitType;
    private String couponLimitInfo; //仅可购买河北地区部分商品

    private String limitCat; //限品类
    private String limitPlatform; //限平台
    private int tag; //0-正常，1-即将过期
    private String useUrl; //自己使用url
    private Long batchId;
    private int areaId;  //区域id
    private String areaName; //区域名称


    private int couponType; //14 15 已领取 16  已领完 17  已抢完  优惠券状态

    private boolean isYours; //是否在你在账户里
    private boolean isGet; //是否已经领取


    public String getId(){
        return  id == null?"":id;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }


    public String getCouponLimitInfo() {
        return couponLimitInfo==null?"":couponLimitInfo;
    }

    public String getQuota() {
        return quota ==null?"":quota;
    }

    public String getDiscount() {
        return discount==null?"":discount;
    }

    public String getActKey() {
        return actKey==null?"":actKey;
    }

    public Long getActRuleId() {
        return actRuleId==null?0:actRuleId;
    }

    public boolean isYours(){
        return couponType == 14 || couponType == 15;
    }

    public boolean isGet(){
        return isGet;
    }

    public void setGet(boolean isGet){
        this.isGet = isGet;
    }

    public String getTagStr(){
        String tag = "";
        switch (type){
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

    public String getAreaName(){
        return areaName == null?"":areaName;
    }

    public String getDateStr(){
        String st = DateFormatUtil.dateFormat(DateFormatUtil.yyyy_MM_dd_2,beginTime);
        String et = DateFormatUtil.dateFormat(DateFormatUtil.yyyy_MM_dd_2,endTime);
        return ResourceUtil.getString(R.string.date_quantum,st,et);
    }

    public boolean showGetBtn(){
        if(isYours()||isGet()){
            return false;
        }else {
            return true;
        }
    }



    public static CouponModel getDemo() {
        return new CouponModel();
    }

    public static List<CouponModel> getDemoList() {
        List<CouponModel> couponModelList = new ArrayList<>();
        couponModelList.add(getDemo());
        couponModelList.add(getDemo());
        return couponModelList;
    }

}
