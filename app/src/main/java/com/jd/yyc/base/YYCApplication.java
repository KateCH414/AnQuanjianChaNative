package com.jd.yyc.base;

import android.app.Application;
import android.content.Context;

import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.utils.Logger;
import com.jd.yyc.BuildConfig;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.constants.JDMaContants;
import com.jd.yyc.login.UserUtil;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.JDMaInterface;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * 创建 by zhaoqin on 2017/5/10.
 */

public class YYCApplication extends Application implements JDMaContants {

    private static YYCApplication instance;

    public static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        YYCApplication.instance = this;

        initNet();//初始化网络
        Logger.setIsPrintLog(BuildConfig.DEBUG); //初始化log工具
        setCookie();//设置cookie
        initJDMa(); //初始化埋点工具
    }


    public static YYCApplication getInstance() {
        return instance;
    }


    private void setCookie() {
        try {
            if (!Util.isLogin())
                return;
            String a2 = UserUtil.getWJLoginHelper().getA2();
            String pin = UserUtil.getWJLoginHelper().getPin();
            if (CheckTool.isEmpty(a2) || CheckTool.isEmpty(pin))
                return;
            StringBuilder sb = new StringBuilder();
            sb.append("pin=");
            sb.append(URLEncoder.encode(pin, "UTF-8"));
            sb.append(";wskey=");
            sb.append(a2);
            Network.setCookie(sb.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void initNet() {
        new Network.SingleBuilder(context)
                .baseUrl(HttpContants.BaseUrl)
                .build();
    }

    private void initJDMa() {
        JDMaInterface.acceptProtocal(true);
        JDMaInterface.setDebugMode(true);
        JDMaInterface.init(context, JDMaUtil.initMaCommonInfo());
    }
}
