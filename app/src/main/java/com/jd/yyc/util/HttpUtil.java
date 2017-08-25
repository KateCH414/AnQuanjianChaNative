package com.jd.yyc.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.api.CallBack;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.api.model.Price;
import com.jd.yyc.api.model.Result;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SkuPrice;

import java.util.HashMap;

/**
 * Created by zhangweifeng1 on 2017/5/26.
 */

public class HttpUtil {
    /**
     * 获取sku价格
     */
    public static void getSkuPrice(String skuIds, final CallBack<Price> callBack) {
        HashMap<String, String> params = new HashMap<>();
        params.put("skuIds", skuIds);
        Network.getRequestBuilder(Util.createUrl("sku/price", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            Result<Price> result = gson.fromJson(model, new TypeToken<Result<Price>>() {
                            }.getType());
                            if (result != null && callBack != null) {
                                callBack.onSuccess(result.data);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                    }
                })
                .post();
    }

    /**
     * 是否建立采购关系
     */
    public static void checkRelation(long venderId, final CallBack<Boolean> callBack) {
        HashMap<String, String> params = new HashMap<>();
        params.put("venderId", venderId + "");
        Network.getRequestBuilder(Util.createUrl("user/checkRelation", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<Boolean> result = gson.fromJson(model, new TypeToken<ResultObject<Boolean>>() {
                            }.getType());
                            if (result != null && result.data) {//已经建立采购关系
                                callBack.onSuccess(result.data);
                            } else {
                                callBack.onSuccess(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.onFailure(200, "数据错误");
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        callBack.onFailure(statusCode, errorMessage);
                    }
                })
                .post();
    }
}
