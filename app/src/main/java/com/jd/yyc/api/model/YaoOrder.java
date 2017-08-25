package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jd.yyc.constants.Contants;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class YaoOrder extends Base implements Parcelable, Contants {
    private Long purchaseId; //采购单编号
    private Long orderId; //订单号
    private String userPin; //经销商pin
    private Long venderId; //供应商编号
    private String venderName; //供应商名称
    private Integer status; //状态  0,待审核; 1待付款； 2 等出库； 3待收货; 4 已完成；-2已拒绝； -1已取消； -3已失效
    private Integer payType; //支付方式,	 3 线上支付; 4 月结支付; 5 线下支付; 货到付款;
    private String purchaseDesc; //经销商描述
    private Float amountSum; //总金额
    private Float amountReduce; //优惠金额
    private Float amountPay; //实际支付总金额
    private Long purchaseTime; //采购时间
    private Long orderTime; //下单时间
    private Long parentId; //父采购单编号,为-1时表示为顶级采购单
    private List<YaoOrderSku> yaoOrderSkus; //采购单sku
    private List<YaoOrder> children; //子采购单列表
    private String payUrl; //支付链接
    private String receiverName; //收货人姓名
    private String receiverPhone; //收货人手机号
    private String deliveryCompanyName; //快递公司名称
    private String deliveryId; //运单号
    private String receiverAddress; //收货地址
    private Integer invoiceType; //发票类型
    private Integer invoiceMethod; //开票方式
    private Integer changeFlag; //是否改变
    private String auditComment; //审核意见
    private Integer shipmentType; //配送方式
    private String invoiceCompanyName;
    private String invoiceTaxNo;
    private String invoiceAddress;
    private boolean canYaoBaitiao;  //能否使用药白条
    private String yaoBaitiaoDesc; //药白条状态描述
    private Integer yaoBaitiaoState; //药白条状态
    private Long orderCompleteTime; //订单完成时间
    private String totalCouponDiscount; // 优惠券优惠金额
    private Integer realStatus; //状态   0 待审核； 5 待确认；-2 已驳回； -1 已取消； 1 待支付； 2待发货；3 待收货； 4 已完成； -3 订单失效

    private boolean showPersonOrCompanyPay; //显示付款
    private boolean showMonthPay; //显示额度付款
    private boolean showConfirm; //显示确认

    public boolean isShowMonthPay() {
        return showMonthPay;
    }

    public boolean isShowPersonOrCompanyPay() {
        return showPersonOrCompanyPay;
    }

    public boolean isShowConfirm() {
        return showConfirm;
    }

    public int getPayType() {
        return payType == null ? 0 : payType;
    }

    public boolean showCheckPendingLayout(int realStatus) {
        if (realStatus == REAL_STATUS_ORDER_WILL_CHECK || realStatus == REAL_STATUS_ORDER_REJECT || realStatus == REAL_STATUS_ORDER_WILL_RECEIVE || realStatus == REAL_STATUS_ORDER_DONE) {
            return true;
        } else {
            return false;
        }
    }

    public int getRealStatus() {
        return realStatus == null ? -100 : realStatus;
    }

    public String getAuditComment() {
        return auditComment == null ? "" : auditComment;
    }

    public Long getPurchaseId() {
        return purchaseId == null ? 0 : purchaseId;
    }

    public Long getOrderId() {
        return orderId == null ? 0 : orderId;
    }

    public String getVenderName(){
        return venderName == null?"":venderName;
    }

    public Long getOrderTime(){
        return orderTime == null?0:orderTime;
    }

    public Long getPurchaseTime() {
        return purchaseTime == null ? 0 : purchaseTime;
    }

    public Float getAmountPay(){
        return amountPay == null?0:amountPay;
    }

    public List<YaoOrderSku> getYaoOrderSkus(){
        return yaoOrderSkus == null ? new ArrayList<YaoOrderSku>() :yaoOrderSkus;
    }


    public static List<YaoOrder> getDemolist() {

        List<YaoOrder> list = new ArrayList<>();
        list.add(new YaoOrder());
        list.add(new YaoOrder());
        list.add(new YaoOrder());
        list.add(new YaoOrder());
        return list;

    }





























    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.purchaseId);
        dest.writeValue(this.orderId);
        dest.writeString(this.userPin);
        dest.writeValue(this.venderId);
        dest.writeString(this.venderName);
        dest.writeValue(this.status);
        dest.writeValue(this.payType);
        dest.writeString(this.purchaseDesc);
        dest.writeValue(this.amountSum);
        dest.writeValue(this.amountReduce);
        dest.writeValue(this.amountPay);
        dest.writeValue(this.purchaseTime);
        dest.writeValue(this.orderTime);
        dest.writeValue(this.parentId);
        dest.writeTypedList(this.yaoOrderSkus);
        dest.writeList(this.children);
        dest.writeString(this.payUrl);
        dest.writeString(this.receiverName);
        dest.writeString(this.receiverPhone);
        dest.writeString(this.deliveryCompanyName);
        dest.writeString(this.deliveryId);
        dest.writeString(this.receiverAddress);
        dest.writeValue(this.invoiceType);
        dest.writeValue(this.invoiceMethod);
        dest.writeValue(this.changeFlag);
        dest.writeString(this.auditComment);
        dest.writeValue(this.shipmentType);
        dest.writeString(this.invoiceCompanyName);
        dest.writeString(this.invoiceTaxNo);
        dest.writeString(this.invoiceAddress);
        dest.writeByte(this.canYaoBaitiao ? (byte) 1 : (byte) 0);
        dest.writeString(this.yaoBaitiaoDesc);
        dest.writeValue(this.yaoBaitiaoState);
        dest.writeValue(this.orderCompleteTime);
        dest.writeString(this.totalCouponDiscount);
        dest.writeValue(this.realStatus);
    }

    public YaoOrder() {
    }

    protected YaoOrder(Parcel in) {
        this.purchaseId = (Long) in.readValue(Long.class.getClassLoader());
        this.orderId = (Long) in.readValue(Long.class.getClassLoader());
        this.userPin = in.readString();
        this.venderId = (Long) in.readValue(Long.class.getClassLoader());
        this.venderName = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.payType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.purchaseDesc = in.readString();
        this.amountSum = (Float) in.readValue(Float.class.getClassLoader());
        this.amountReduce = (Float) in.readValue(Float.class.getClassLoader());
        this.amountPay = (Float) in.readValue(Float.class.getClassLoader());
        this.purchaseTime = (Long) in.readValue(Long.class.getClassLoader());
        this.orderTime = (Long) in.readValue(Long.class.getClassLoader());
        this.parentId = (Long) in.readValue(Long.class.getClassLoader());
        this.yaoOrderSkus = in.createTypedArrayList(YaoOrderSku.CREATOR);
        this.children = new ArrayList<YaoOrder>();
        in.readList(this.children, YaoOrder.class.getClassLoader());
        this.payUrl = in.readString();
        this.receiverName = in.readString();
        this.receiverPhone = in.readString();
        this.deliveryCompanyName = in.readString();
        this.deliveryId = in.readString();
        this.receiverAddress = in.readString();
        this.invoiceType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.invoiceMethod = (Integer) in.readValue(Integer.class.getClassLoader());
        this.changeFlag = (Integer) in.readValue(Integer.class.getClassLoader());
        this.auditComment = in.readString();
        this.shipmentType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.invoiceCompanyName = in.readString();
        this.invoiceTaxNo = in.readString();
        this.invoiceAddress = in.readString();
        this.canYaoBaitiao = in.readByte() != 0;
        this.yaoBaitiaoDesc = in.readString();
        this.yaoBaitiaoState = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderCompleteTime = (Long) in.readValue(Long.class.getClassLoader());
        this.totalCouponDiscount = in.readString();
        this.realStatus = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<YaoOrder> CREATOR = new Parcelable.Creator<YaoOrder>() {
        @Override
        public YaoOrder createFromParcel(Parcel source) {
            return new YaoOrder(source);
        }

        @Override
        public YaoOrder[] newArray(int size) {
            return new YaoOrder[size];
        }
    };
}
