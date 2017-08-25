package com.jd.yyc.api.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class CouponStatusModel extends Base implements Parcelable {
    private String id; //优惠券的编号
    private int state;  //已领取，未领取  14 15 已领取 16  已领完 17  已抢完

    public String getId() {
        return id == null?"":id;
    }

    public int getState() {
        return state;
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.state);
    }

    public CouponStatusModel() {
    }

    protected CouponStatusModel(Parcel in) {
        this.id = in.readString();
        this.state = in.readInt();
    }

    public static final Parcelable.Creator<CouponStatusModel> CREATOR = new Parcelable.Creator<CouponStatusModel>() {
        @Override
        public CouponStatusModel createFromParcel(Parcel source) {
            return new CouponStatusModel(source);
        }

        @Override
        public CouponStatusModel[] newArray(int size) {
            return new CouponStatusModel[size];
        }
    };


    public static void getCouponStatus(Context context, List<Long> ids, final CouponStatusCallback callback) {

        NetLoading.getInstance().getCouponStatus(context, ids, new RequestCallback<ResultObject<List<CouponStatusModel>>>() {
            @Override
            public void requestCallBack(boolean success, ResultObject<List<CouponStatusModel>> result, String err) {
                if (success&&result.success) {
                    callback.requestCallback(result.data);
                } else {
                    callback.onFailed(result != null ?result.msg :err);
                }
            }
        });

    }

    public interface CouponStatusCallback {
        void requestCallback( List<CouponStatusModel> couponStatusModels);

        void onFailed(String msg);
    }

}
