package com.jd.yyc.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.jd.yyc.base.YYCApplication;

/**
 * 设备工具类
 */
public class DeviceUtil {


    //客户端版本号
    public static String getVersionName() {
        PackageManager manager = YYCApplication.context.getPackageManager();
        ApplicationInfo info = YYCApplication.context.getApplicationInfo();
        try {
            return manager.getPackageInfo(info.packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknow";
        }
    }

    public static String getChannelName() {
        String channelName = null;
        try {
            PackageManager packageManager = YYCApplication.context.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(YYCApplication.context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("");
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }

    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) YYCApplication.context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }


}
