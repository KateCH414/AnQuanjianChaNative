package com.jd.yyc.api.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiahongbin on 2017/6/8.
 */

public class AddressBean extends Base implements Parcelable {

    public Long addressId;//仓库地址id
    public String userPin;//用户名pin
    public Long companyId;//公司id
    public Long provinceId;//省id
    public Long cityId; //市id
    public Long countyId;//县id
    public Long townId;//乡镇街道id
    public String provinceName;//省名称
    public String cityName;//市名称
    public String countyName;//县名称
    public String townName;//乡镇街道名称
    public String detailAddress;//详细地址
    public Long contactId;//联系人id
    public String contact;//联系人
    public String phone;//手机
    public String telphone;//座机
    public Boolean status;//地址有效性状态
    public String addressDetailForPage;//详细地址
    public boolean isSelect;


    public static AddressBean getDemo() {
        return new AddressBean();
    }


    public static List<AddressBean> getDemoList() {
        List<AddressBean> couponModelList = new ArrayList<>();
        couponModelList.add(getDemo());
        couponModelList.add(getDemo());
        couponModelList.add(getDemo());
        return couponModelList;
    }

    //
    public static  void getAddressList(Context context , Long vendorId , final AddressCallback callback){
        NetLoading.getInstance().getAddressList(context, vendorId, new RequestCallback<ResultObject<List<AddressBean>>>() {
            @Override
            public void requestCallBack(boolean success, ResultObject<List<AddressBean>> result, String err) {
                if(success && result.success){
                    callback.requestCallback(null,result.data);
                }else {
                    callback.onFailed(result!=null?result.msg:err);
                }
            }
        });
    }


    public interface AddressCallback {
        void requestCallback(AddressBean addressBean, List<AddressBean> addressList);

        void onFailed(String msg);
    }


    public AddressBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.addressId);
        dest.writeString(this.userPin);
        dest.writeValue(this.companyId);
        dest.writeValue(this.provinceId);
        dest.writeValue(this.cityId);
        dest.writeValue(this.countyId);
        dest.writeValue(this.townId);
        dest.writeString(this.provinceName);
        dest.writeString(this.cityName);
        dest.writeString(this.countyName);
        dest.writeString(this.townName);
        dest.writeString(this.detailAddress);
        dest.writeValue(this.contactId);
        dest.writeString(this.contact);
        dest.writeString(this.phone);
        dest.writeString(this.telphone);
        dest.writeValue(this.status);
        dest.writeString(this.addressDetailForPage);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
    }

    protected AddressBean(Parcel in) {
        this.addressId = (Long) in.readValue(Long.class.getClassLoader());
        this.userPin = in.readString();
        this.companyId = (Long) in.readValue(Long.class.getClassLoader());
        this.provinceId = (Long) in.readValue(Long.class.getClassLoader());
        this.cityId = (Long) in.readValue(Long.class.getClassLoader());
        this.countyId = (Long) in.readValue(Long.class.getClassLoader());
        this.townId = (Long) in.readValue(Long.class.getClassLoader());
        this.provinceName = in.readString();
        this.cityName = in.readString();
        this.countyName = in.readString();
        this.townName = in.readString();
        this.detailAddress = in.readString();
        this.contactId = (Long) in.readValue(Long.class.getClassLoader());
        this.contact = in.readString();
        this.phone = in.readString();
        this.telphone = in.readString();
        this.status = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.addressDetailForPage = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Creator<AddressBean> CREATOR = new Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
