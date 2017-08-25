package com.jd.yyc.event;

/**
 * Created by zhangweifeng on 16-2-23.
 */
public class EventOnHomeRefresh {
    public static final int HOME = 1;
    public static final int CATEGORY = 2;
    public static final int SHOPINGCAR = 3;
    public int type;

    public EventOnHomeRefresh(int type) {
        this.type = type;
    }
}
