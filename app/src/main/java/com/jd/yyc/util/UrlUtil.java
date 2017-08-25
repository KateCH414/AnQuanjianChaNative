package com.jd.yyc.util;

import com.google.gson.JsonObject;
import com.jd.yyc.R;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.login.UserUtil;

import jd.wjlogin_sdk.common.listener.OnReqJumpTokenCallback;
import jd.wjlogin_sdk.model.FailResult;

/**
 *
 */

public class UrlUtil implements Contants {
    public static String getWriteOrderUrl(Long shopId) {
        return ResourceUtil.getString(R.string.url, WRITE_ORDER, "shopId", shopId);
    }

    public static String getOrderDetailUrl(Long orderId) {
        return ResourceUtil.getString(R.string.url, ORDER_DETAIL, "purchaseId", orderId);
    }

    public static String getOrderPayUrl(Long orderId, Float sum) {
        return ResourceUtil.getString(R.string.url_1, ORDER_PAY, "purchaseId", orderId, "total", sum);
    }

    public static void getLoginStatusUrl(final String url, final LoginStatusCallback callback) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "to");
        jsonObject.addProperty("to", url);
        jsonObject.addProperty("app", "京药采");

        UserUtil.getWJLoginHelper().reqJumpToken(jsonObject.toString(), new OnReqJumpTokenCallback() {
            @Override
            public void onSuccess(String s, String s1) {
                callback.onSuccess(ResourceUtil.getString(R.string.login_status_url, s, s1, url));
            }

            @Override
            public void onError(String s) {
                callback.onFailed(s);
            }

            @Override
            public void onFail(FailResult failResult) {
                if (failResult == null)
                    return;
                callback.onSuccess(failResult.getMessage());
            }
        });
    }

    public interface LoginStatusCallback {
        void onSuccess(String url);

        void onFailed(String msg);
    }

}
