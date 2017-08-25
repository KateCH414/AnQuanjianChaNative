package com.jd.yyc.constants;


/**
 * class- 埋点相关数据
 */

public interface JDMaContants {
    //-----------------------站点 参数---------------------------
    static final String TEST_SITE = "cs08";
    static final String OFFICIAL_SITE = "JA2017_311760";


    //-----------------------PV 参数---------------------------

    static final String PAGE_CART_NAME = "购物车页面";
    static final String PAGE_CART_ID = "0006";

    static final String PAGE_MINE_NAME = "个人中心";
    static final String PAGE_MINE_ID = "0016";

    static final String PAGE_ORDER_LIST_NAME = "订单列表页";
    static final String PAGE_ORDER_LIST_ID = "0020";

    static final String PAGE_ORDER_PAY_NAME = "待付款";
    static final String PAGE_ORDER_PAY_ID = "0021";

    static final String PAGE_ORDER_RECEIVE_NAME = "待收货";
    static final String PAGE_ORDER_RECEIVE_ID = "0022";

    static final String PAGE_ORDER_CHECK_NAME = "待审核";
    static final String PAGE_ORDER_CHECK_ID = "0023";

    static final String PAGE_ORDER_CONFIRM_NAME = "待确认";
    static final String PAGE_ORDER_CONFIRM_ID = "0024";


    //-----------------------click 参数---------------------------
    static final String CART_SUBMIT_ID = "yjc_android_201706262|23"; //购物车页面结算ID
    static final String CART_SKU_DETAIL_ID = "yjc_android_201706262|24"; //购物车页面点击进入商品详情ID
    static final String CART_PLUS_ID = "yjc_android_201706262|25"; //购物车商品数量“+”号ID
    static final String CART_SUB_ID = "yjc_android_201706262|26"; //购物车商品数量“-”号ID
    static final String CART_EDIT_ID = "yjc_android_201706262|27"; //购物编辑按钮ID
    static final String CART_DELETE_ID = "yjc_android_201706262|29"; //购物车删除按钮ID

    static final String ME_ORDER_LIST_ID = "yjc_android_201706262|48"; //个人中心---查看全部订单ID
    static final String ME_ORDER_CHECK_LIST_ID = "yjc_android_201706262|49"; //个人中心---待审核  ID
    static final String ME_ORDER_CONFIRM_LIST_ID = "yjc_android_201706262|50"; //个人中心---待确认  ID
    static final String ME_ORDER_PAY_LIST_ID = "yjc_android_201706262|51"; //个人中心---待支付  ID
    static final String ME_ORDER_RECEIVE_LIST_ID = "yjc_android_201706262|52"; //个人中心---待收货  ID

    static final String ME_ORDER_CONFIRM_ID = "yjc_android_201706262|58"; //订单列表---确认按钮 ID
    static final String ME_ORDER_BUY_AGAIN_ID = "yjc_android_201706262|59"; //订单列表---再次购买按钮 ID
    static final String ME_ORDER_PAY_ID = "yjc_android_201706262|60"; //订单列表---去支付按钮 ID



}
