package com.jd.yyc.event;

/**
 *
 */
public class EventLoginMessage {
    //1：首页领劵中心;2:主页购物车;3:独立购物车页面;4:个人中心

    public int type;

    public EventLoginMessage() {

    }

    public EventLoginMessage(int type) {
        this.type = type;
    }

}
