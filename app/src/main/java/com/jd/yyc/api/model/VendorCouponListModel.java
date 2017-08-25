package com.jd.yyc.api.model;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;
import com.jd.yyc.util.CheckTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 店铺实体
 */

public class VendorCouponListModel extends Base {
    private List<CouponModel> available;
    private List<CouponModel> unavailable;

    public List<CouponModel> getAvailableCouponList(){
        if(available == null)
            return new ArrayList<>();
        return available;
    }


    public static void getVendorCouponList(Context context, Long vendorId, final VendorCouponListCallback callback) {
        NetLoading.getInstance().getVendorCouponList(context, vendorId, new RequestCallback<ResultObject<VendorCouponListModel>>() {
            @Override
            public void requestCallBack(boolean success, ResultObject<VendorCouponListModel> result, String err) {
                if (success && result.success) {
                    callback.requestCallback(result.data);
                } else {
                    callback.onFailed(result != null?result.msg :err);
                }
            }
        });

    }


    public interface VendorCouponListCallback {
        void requestCallback(VendorCouponListModel vendorCouponListModel);

        void onFailed(String msg);
    }

}
