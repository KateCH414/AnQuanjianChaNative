package com.jd.yyc.util;

import android.content.Context;

/**
 * Created by zhangweifeng1 on 2017/6/9.
 */
public class SharePreferenceUtil extends com.jd.project.lib.andlib.utils.SharePreferenceUtil {
    public static final String LBSNAME = "lbsName";
    public static final String ISFIRST = "isFirst";
    public static final String SELECT_ADDRESS = "select_address";
    public static final String LOGIN_ACCOUNT = "loginAccount";
    /**
     * 城市
     *
     * @param lbsName
     */
    public static void setLbsName(Context context, String lbsName) {
        setValue(context, LBSNAME, lbsName);
    }

    public static String getLbsName(Context context) {
        return getStringValue(context, LBSNAME);
    }

    /**
     * 第一次打开app
     *
     * @param isFirst
     */
    public static void setFirstStart(Context context, boolean isFirst) {
        setValue(context, ISFIRST, isFirst);
    }

    public static Boolean getFirstStart(Context context) {
        return getBooleanValue(context, ISFIRST, true);
    }

    /**
     * 保存选中地址
     *
     * @param context
     * @param address
     */
    public static void setSelectAddress(Context context, String address) {
        setValue(context, SELECT_ADDRESS, address);
    }

    /**
     * 获取选中地址
     *
     * @param context
     * @return
     */
    public static String getSelectAddress(Context context) {
        return getStringValue(context, SELECT_ADDRESS);
    }

    public static void setLoginAccount(Context context, String account) {
        setValue(context, LOGIN_ACCOUNT, account);
    }

    public static String getLoginAccount(Context context) {
        return getStringValue(context, LOGIN_ACCOUNT);
    }

}
