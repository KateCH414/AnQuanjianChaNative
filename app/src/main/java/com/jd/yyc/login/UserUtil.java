package com.jd.yyc.login;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import com.jd.yyc.base.YYCApplication;

import jd.wjlogin_sdk.common.DevelopType;
import jd.wjlogin_sdk.common.WJLoginHelper;
import jd.wjlogin_sdk.common.listener.OnCommonCallback;
import jd.wjlogin_sdk.model.ClientInfo;
import jd.wjlogin_sdk.model.FailResult;

/**
 * 构造单例helper,开发中建议使用单例模式。
 *
 * @author Administrator
 */

public class UserUtil {

    private static ClientInfo clientInfo;
    private static WJLoginHelper helper;

    public synchronized static ClientInfo getClientInfo() {
        clientInfo = new ClientInfo();
        /*******传入字段非NULL***************/
        /**APP ID,统一登录后台分配的**/
        clientInfo.setDwAppID((short) 340);
        /**客户端类型，android 写死**/
        clientInfo.setClientType("android");
        /**设备系统版本**/
        clientInfo.setOsVer(Build.VERSION.RELEASE);
        /**接入客户端版本，这里为了测试写死的，实际获取用@see{getAppVersionName}**/

        String VersionName=getAppVersionName();
        clientInfo.setDwAppClientVer(VersionName);
        /**设备分辨率***/
        Display display = ((WindowManager) YYCApplication.getInstance().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
             //   getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        clientInfo.setScreen(display.getHeight() + "*" + display.getWidth());
        /**接入APP名字**/
        clientInfo.setAppName("医药城");
        /**位置**/
        clientInfo.setArea(" ");
        /**设备UUID**/

        clientInfo.setUuid("866304029114104-1436c633866");
        clientInfo.setDwGetSig(1);
        /**设备imei,从V2.1.0才开始有**/
        clientInfo.setDeviceId(getDeviceId());
        /**设备SimSerialNumber,从V2.1.0才开始有**/
        clientInfo.setSimSerialNumber(getSimSerialNumber());
        /**设备品牌**/


        clientInfo.setDeviceBrand(spilitSubString(Build.MANUFACTURER, 30).replaceAll(" ", ""));
        /**设备型号**/
        clientInfo.setDeviceModel(spilitSubString((Build.MODEL), 30).replaceAll(" ", ""));
        /**设备名称**/
        clientInfo.setDeviceName(spilitSubString((Build.PRODUCT), 30).replaceAll(" ", ""));
        /**预留字段**/
        clientInfo.setReserve("");
        return clientInfo;
    }

    private static PackageInfo getPackageInfo() {
        try {
            Context cxt = YYCApplication.getInstance();
            PackageInfo packageInfo = null;
            if (cxt != null) {
                packageInfo = cxt.getPackageManager().getPackageInfo(cxt.getPackageName(), 0);
            }
            return packageInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAppVersionName() {
        PackageInfo packageInfo = getPackageInfo();
        if (null == packageInfo) {
            return "";
        }
        return packageInfo.versionName;
    }

    public static String getDeviceId() {
        try {
            TelephonyManager tm = (TelephonyManager) YYCApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            return imei == null ? "" : imei;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 取得SimSerialNumber
     */
    public static String getSimSerialNumber() {
        try {
            TelephonyManager tm = (TelephonyManager) YYCApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
            String simNo = tm.getSimSerialNumber();
            return simNo == null ? "" : simNo;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 按指定长度截取串
     *
     * @return
     * @url value
     * @url length
     */
    private static String spilitSubString(String value, int length) {
        try {
            if (value != null && value.length() > length) {
                value = value.substring(0, length);
            }
        } catch (Exception e) {

        }
        return value;
    }

    /**
     * 刷新A2
     */
    public static void refreshA2() {
        WJLoginHelper helper = getWJLoginHelper();
        if (helper != null) {
            if (helper.isExistsA2()) {
                // A2未过期
                if (!helper.checkA2TimeOut()) {
                    // A2需要刷新
                    if (helper.checkA2IsNeedRefresh()) {
                        helper.refreshA2(new OnCommonCallback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError(String s) {

                            }

                            @Override
                            public void onFail(FailResult failResult) {

                            }
                        });
                    }
                }
            }
        }
    }

    /**
     * 单例模式的helper
     *
     * @return
     */
    public synchronized static WJLoginHelper getWJLoginHelper() {
        if (helper == null) {
            helper = new WJLoginHelper(YYCApplication.getInstance(), getClientInfo());
            helper.setDevelop(DevelopType.PRODUCT); // 设置为开发环境
            helper.createGuid();
        }
        return helper;
    }

}
