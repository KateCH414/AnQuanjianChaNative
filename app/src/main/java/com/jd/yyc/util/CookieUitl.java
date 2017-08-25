package com.jd.yyc.util;

import com.jd.yyc.login.UserUtil;

import jd.wjlogin_sdk.common.WJLoginHelper;

/**
 * Created by jiahongbin on 2017/6/13.
 */

public class CookieUitl {

    private WJLoginHelper helper;


    public String getCookie() {
        try {

            helper = UserUtil.getWJLoginHelper();

        } catch (Throwable e) {

        }


        String a2 = helper.getA2();
        String pin = helper.getPin();
        StringBuilder sb = new StringBuilder();
        sb.append("pin=");
        sb.append(pin);
        sb.append(";wskey=");
        sb.append(a2);
        return sb.toString();

    }
}
