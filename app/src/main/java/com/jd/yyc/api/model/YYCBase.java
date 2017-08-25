package com.jd.yyc.api.model;

/**
 * Created by zhangweifeng1 on 2017/6/5.
 */

public class YYCBase extends Base {
    public final static int STATUS_SUCCESS = 1;
    public final static int STATUS_FAILURE = 0;
    public final static int STATUS_NO_DATA = 20000;
    public final static int STATUS_TIME_OUT = -1;
    public final static int STATUS_NO_NET = -2;


    public boolean success;
    public int code;
    public String msg;
}
