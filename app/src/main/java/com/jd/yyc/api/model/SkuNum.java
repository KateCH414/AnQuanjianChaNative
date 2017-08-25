package com.jd.yyc.api.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;

import java.util.List;

/**
 *
 */

public class SkuNum extends Base implements Parcelable {
    private Long skuId;//商品 skuid
    private int num;//数量
    private boolean result;


    public SkuNum(Long skuId, int num) {
        this.skuId = skuId;
        this.num = num;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Long getSkuId() {
        return skuId == null ? 0 : skuId;
    }

    public int getNum() {
        return num;
    }

    //商品是否可以加入购物车(含数量)
    public static void canAddCart(Context context, List<SkuNum> list, final SkuNumCallback callback) {
        NetLoading.getInstance().canAddCart(context, list, new RequestCallback<ResultObject<List<SkuNum>>>() {
            @Override
            public void requestCallBack(boolean success, ResultObject<List<SkuNum>> result, String err) {
                if (success && result.success) {
                    callback.requestCallback(result.data);
                } else {
                    callback.onFailed(result != null ? result.msg : err);
                }
            }
        });
    }

    //批量添加购物车接口（包含sku数量）
    public static void AddCartList(Context context, List<SkuNum> list, final SkuNumCallback callback) {
        NetLoading.getInstance().addCartList(context, list, new RequestCallback<ResultObject<List<SkuNum>>>() {
            @Override
            public void requestCallBack(boolean success, ResultObject<List<SkuNum>> result, String err) {
                if (success && result.success) {
                    callback.requestCallback(result.data);
                } else {
                    callback.onFailed(result != null ? result.msg : err);
                }
            }
        });
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.skuId);
        dest.writeInt(this.num);
    }

    public SkuNum() {
    }

    protected SkuNum(Parcel in) {
        this.skuId = (Long) in.readValue(Long.class.getClassLoader());
        this.num = in.readInt();
    }

    public static final Creator<SkuNum> CREATOR = new Creator<SkuNum>() {
        @Override
        public SkuNum createFromParcel(Parcel source) {
            return new SkuNum(source);
        }

        @Override
        public SkuNum[] newArray(int size) {
            return new SkuNum[size];
        }
    };

    public interface SkuNumCallback {
        void requestCallback(List<SkuNum> skuNumList);

        void onFailed(String msg);
    }
}
