package com.jd.yyc.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jd.yyc.api.model.AddressBean;
import com.jd.yyc.api.model.CouponStatusModel;
import com.jd.yyc.api.model.MineGoods;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SkuNum;
import com.jd.yyc.api.model.SkuPrice;
import com.jd.yyc.api.model.VendorCouponListModel;
import com.jd.yyc.api.model.VendorModel;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.util.CheckTool;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class NetLoading implements HttpContants {

    // 登录  http://yaoser.jd.com/cart/changeNum

    public static final String CART_CAN_ADD = BaseUrl + "/sku/isAddCart";    //商品是否可以加入购物车
    public static final String CART_CAN_ADD_NUM = BaseUrl + "/sku/isAddCartBatch";    //商品是否可以加入购物车(含数量)
    public static final String CART_ADD_LIST = BaseUrl + "/cart/addBatch";    //批量添加购物车接口
    public static final String CART_ADD_LIST_NUM = BaseUrl + "/cart/addBatchNum";    //批量添加购物车接口（包含sku数量）
    public static final String CART_LIST = BaseUrl + "/cart/list";    //购物车列表
    public static final String CART_DELETE = BaseUrl + "/cart/delete";    //购物车删除
    public static final String CART_SKU_NUM = BaseUrl + "/cart/mnum";    //购物车数量

    public static final String SKU_PRICE = BaseUrl + "/sku/price";  //sku价格接口
    public static final String SKU_CHANGE_NUM = BaseUrl + "/cart/changeNum";  //修改购物车数量
    public static final String SKU_CHECK_STATUS = BaseUrl + "/cart/changeCheckType";  //取消选中
    public static final String VENDOR_COUPON_LIST = BaseUrl + "/coupon/userCouponlist";  //店铺优惠券列表
    public static final String GET_COUPON = BaseUrl + "/coupon/ask";  //领取优惠券
    public static final String GET_COUPON_STATUS = BaseUrl + "/coupon/status";  //优惠券状态
    public static final String GET_ADDRESS_LIST = BaseUrl + "/confirm/address";  //收货地址列表
    public static final String CONFIRM_RECEIVE = BaseUrl + "/order/confirmReceipt";  //确认收货
    public static final String ORDER_CONFIRM = BaseUrl + "/order/confirmMonthPay";  //采购单确认
    public static final String ORDER_LIST = BaseUrl + "/order/pageList";  //订单分页app接口


    private static NetLoading loading;

    private NetLoading() {
    }

    public static synchronized NetLoading getInstance() {
        if (loading == null) {
            loading = new NetLoading();
        }
        return loading;
    }


    /**
     * 获取购物车列表接口
     * @param context
     * @param callback
     */
    public void getCartList(Context context, boolean showLoadingDialog, RequestCallback<ResultObject<List<VendorModel>>> callback) {

        Map<String, String> params = new Hashtable<>();
        params.put("clientType", "m");
        NetRequest.getInstance().post(context, CART_LIST, null, showLoadingDialog, callback);
    }

    /**
     * 获取商品价格接口
     * @param context
     * @param skuIds
     * @param callback
     */
    public void getSkuPriceList(Context context, List<Long> skuIds, RequestCallback<ResultObject<List<SkuPrice>>> callback) {
        if (CheckTool.isEmpty(skuIds))
            return;

        String idStr = "";
        for(Long id : skuIds ){
            idStr += id;
            idStr += ",";
        }
        idStr = idStr.substring(0,idStr.length()-1);
        Map<String, String> params = new Hashtable<>();
        params.put("skuIds", idStr);
        NetRequest.getInstance().post(context, SKU_PRICE, params, callback);
    }

    /**
     * 修改购物车商品在数量
     * @param context
     * @param skuId
     * @param num
     * @param callback
     */
    public void changeSkuNum(Context context, long skuId,int num, RequestCallback<ResultObject<List<VendorModel>>> callback) {

        Map<String, String> params = new Hashtable<>();
        params.put("skuId", skuId+"");
        params.put("num",num+"");
        NetRequest.getInstance().post(context, SKU_CHANGE_NUM, params, false, callback);
    }


    /**
     * 修改选中状态
     * @param context
     * @param map
     * @param callback
     * @throws Exception
     */
    public void changeSkuCheckStatus(Context context, Map<String ,Integer> map, RequestCallback<ResultObject<List<VendorModel>>> callback) throws Exception{
        JsonObject jsonObject = new JsonObject();
        for (String key :map.keySet()){
            jsonObject.addProperty(key+"", map.get(key));
        }
        Map<String, String> params = new Hashtable<>();

        params.put("skuAndType",jsonObject.toString());
        NetRequest.getInstance().post(context, SKU_CHECK_STATUS, params, true, callback);
    }


    /**
     * 删除购物车商品借口
     * @param context
     * @param ids
     * @param callback
     */
    public void deleteSku(Context context, List<Long> ids, RequestCallback<ResultObject<List<VendorModel>>> callback){
        if (CheckTool.isEmpty(ids))
            return;

        String idStr = "";
        for(Long id : ids ){
            idStr += id;
            idStr += ",";
        }
        idStr = idStr.substring(0,idStr.length()-1);
        Map<String, String> params = new Hashtable<>();
        params.put("skuIds", idStr);
        NetRequest.getInstance().post(context, CART_DELETE, params, true, callback);
    }

    /**
     * 获取店铺优惠券列表
     * @param context
     * @param vendorId
     * @param callback
     */
    public void getVendorCouponList(Context context, Long vendorId, RequestCallback<ResultObject<VendorCouponListModel>> callback){
        Map<String, String> params = new Hashtable<>();
        params.put("venderId", vendorId+"");
        NetRequest.getInstance().post(context, VENDOR_COUPON_LIST, params, true, callback);
    }

    /**
     * 领取优惠券
     * @param context
     * @param actKey
     * @param actRuleId
     * @param callback
     */
    public void getCoupon(Context context, String actKey ,Long actRuleId, RequestCallback<ResultObject<Boolean>> callback){
        Map<String, String> params = new Hashtable<>();
        params.put("key", actKey);
        params.put("ruleId", actRuleId+"");
        NetRequest.getInstance().post(context, GET_COUPON, params, callback);
    }

    /**
     * 获取优惠券的状态
     * @param context
     * @param actRuleIds
     * @param callback
     */
    public void getCouponStatus(Context context, List<Long> actRuleIds, RequestCallback<ResultObject<List<CouponStatusModel>>> callback){
        if (CheckTool.isEmpty(actRuleIds))
            return;

        String idStr = "";
        for(Long id : actRuleIds ){
            idStr += id;
            idStr += ",";
        }
        idStr = idStr.substring(0,idStr.length()-1);
        Map<String, String> params = new Hashtable<>();
        params.put("ids", idStr);
        NetRequest.getInstance().post(context, GET_COUPON_STATUS, params, callback);
    }

    /**
     * 获取地址列表
     * @param context
     * @param vendorId
     * @param callback
     */
    public void getAddressList(Context context, Long vendorId, RequestCallback<ResultObject<List<AddressBean>>> callback){

        Map<String, String> params = new Hashtable<>();
        params.put("venderId", vendorId+"");
        NetRequest.getInstance().post(context, GET_ADDRESS_LIST, params, callback);
    }

    /**
     * 确认收货
     *
     * @param context
     * @param orderId
     * @param callback
     */
    public void confirmReceive(Context context, Long orderId, RequestCallback<ResultObject<Boolean>> callback) {
        Map<String, String> params = new Hashtable<>();
        params.put("orderId", orderId + "");
        NetRequest.getInstance().post(context, CONFIRM_RECEIVE, params, true, callback);
    }

    /**
     * 采购单确认
     *
     * @param context
     * @param purchaseId
     * @param callback
     */
    public void orderConfirm(Context context, Long purchaseId, RequestCallback<ResultObject<Boolean>> callback) {
        Map<String, String> params = new Hashtable<>();
        params.put("purchaseId", purchaseId + "");
        NetRequest.getInstance().post(context, ORDER_CONFIRM, params, true, callback);
    }

    /**
     * 商品是否可以加入购物车(含数量)
     *
     * @param context
     * @param list
     * @param callback
     */
    public void canAddCart(Context context, List<SkuNum> list, RequestCallback<ResultObject<List<SkuNum>>> callback) {
        if (CheckTool.isEmpty(list))
            return;
        String str = new Gson().toJsonTree(list, new TypeToken<List<SkuNum>>() {
        }.getType()).getAsJsonArray().toString();
        Map<String, String> params = new Hashtable<>();
        params.put("skuJson", str);
        NetRequest.getInstance().post(context, CART_CAN_ADD_NUM, params, true, callback);
    }


    /**
     * 批量添加购物车接口（包含sku数量）
     *
     * @param context
     * @param list
     * @param callback
     */
    public void addCartList(Context context, List<SkuNum> list, RequestCallback<ResultObject<List<SkuNum>>> callback) {
        if (CheckTool.isEmpty(list))
            return;
        String str = new Gson().toJsonTree(list, new TypeToken<List<SkuNum>>() {
        }.getType()).getAsJsonArray().toString();
        Map<String, String> params = new Hashtable<>();
        params.put("skuJson", str);
        NetRequest.getInstance().post(context, CART_ADD_LIST_NUM, params, true, callback);
    }

    /**
     * 获取订单列表
     *
     * @param context
     * @param page
     * @param pageSize
     * @param status
     * @param callback
     */
    public void getOrderList(Context context, int page, int pageSize, int status, RequestCallback<ResultObject<MineGoods>> callback) {
        Map<String, String> params = new Hashtable<>();
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("status", status + "");
        NetRequest.getInstance().post(context, ORDER_LIST, params, false, callback);
    }

}
