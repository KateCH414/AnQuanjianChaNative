package com.jd.yyc.constants;


public interface Contants extends HttpContants, JDMaContants {


    int ORDER_ALL = 0;  //全部
    int ORDER_WILL_CHECK = 1; // 待审核
    int ORDER_WILL_CONFIRM = 2;  //待确认
    int ORDER_REJECT = 3;  //已驳回
    int ORDER_CANCELED = 4;  //已取消
    int ORDER_WILL_PAY = 5;  //待支付
    int ORDER_WILL_RECEIVE = 6; //待收货
    int ORDER_DONE = 7; //已完成
    int ORDER_FAILED = 8;  // 订单失效
    int ORDER_WILL_SEND = 9;  //待发货

    int REAL_STATUS_ORDER_WILL_CHECK = 0;  //待审核
    int REAL_STATUS_ORDER_WILL_CONFIRM = 5;  //待确认
    int REAL_STATUS_ORDER_REJECT = -2;  //已驳回
    int REAL_STATUS_ORDER_CANCELED = -1;  //已取消
    int REAL_STATUS_ORDER_WILL_PAY = 1;  //待支付
    int REAL_STATUS_ORDER_WILL_RECEIVE = 3;  //待收货
    int REAL_STATUS_ORDER_DONE = 4;  //已完成
    int REAL_STATUS_ORDER_FAILED = -3;  //订单失效
    int REAL_STATUS_ORDER_WILL_SEND = 2; //待发货

    int COUPON_USE = 0; //未使用
    int COUPON_USED = 1; //已使用
    int COUPON_USEDATA = 2; //已过期

    String WRITE_ORDER = "http://yao.m.jd.com/writeOrder.html"; //填写订单
    String ORDER_DETAIL = "http://yao.m.jd.com/orderDetail.html"; //采购单详情
    String ORDER_PAY = "http://yao.m.jd.com/orderPay.html"; //收银台


    //1：首页领劵中心;2:主页购物车;3:独立购物车页面;4:个人中心;5:个人中心优惠券;6:个人中心月结待付款;7:待审核;8:待确认;9:待付款;10:待收货;11:全部采购单
    int LOGIN_FROM_COUPON = 1;
    int LOGIN_FROM_HOME_CART = 2;
    int LOGIN_FROM_ALONE_CART = 3;
    int LOGIN_FROM_USER_CENTER = 4;
    int LOGIN_FROM_MINE_COUPON = 5;
    int LOGIN_FROM_MINE_MONTH = 6;
    int LOGIN_FROM_MINE_CHECK = 7;
    int LOGIN_FROM_MINE_CONFIRM = 8;
    int LOGIN_FROM_MINE_WAIPAY = 9;
    int LOGIN_FROM_MINE_RECEIVE = 10;
    int LOGIN_FROM_MINE_TOTAL = 11;
}
