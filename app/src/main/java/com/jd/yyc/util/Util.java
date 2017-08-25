package com.jd.yyc.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.net.ParseException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jd.project.lib.andlib.utils.Logger;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.login.UserUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jd.wjlogin_sdk.common.WJLoginHelper;

/**
 * Created by wf on 14-7-23.
 */
public class Util {

    public static final String TAG = "Util";

    private static String mac = "";
    private static long lastClickTime;


    /**
     * 隐藏输入法键盘
     *
     * @param context
     * @param editText
     */
    public static void hideInput(Context context, EditText editText) {
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 显示输入法键盘
     *
     * @param context
     * @param editText
     */
    public static void showInput(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public static void toggleSoftInput(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, 0);
    }

    public static boolean isSoftInputActive(Activity context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive(editText);
    }

    /**
     * 重新计算listview高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }


    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 启动新的activity
     *
     * @param activity
     * @param intent
     * @param forResult   是否启用startActivityForResult方法
     * @param requestCode 请求码:若forResult为false时候，不需要使用startActivityForResult方法，此设置无效
     */
    public static void launch(Activity activity, Intent intent,
                              boolean forResult, int requestCode) {
        if (forResult) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivity(intent);
        }
    }

    /**
     * 判断android设备中是否有相应的应用来处理这个Intent。
     *
     * @param context
     * @param intent
     * @return
     */
    public static boolean hasIntentActivities(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager
                .queryIntentActivities(intent, 0);

        return list.size() > 0;
    }

    /**
     * 获取AndroidMenifest.xml中Application中的meta data
     *
     * @param context ：上下文
     * @param name    ：meta data的名称
     * @return：meta data的值
     */
    public static String getApplicationMetaData(Context context, String name) {
        String result = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            result = appInfo.metaData.getString(name);
            return result;
        } catch (Exception e) {
            Logger.e("Util", "getApplicationMetaData e[" + e + "]");
            e.printStackTrace();
        }
        return null;
    }


    public static String getSinaUid(Context context) {
        return Util.getApplicationMetaData(context, "SINA_UID");
    }

    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * 系统版本
     *
     * @return
     */
    public static String getSysVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取SDK版本
     *
     * @return
     */
    public static int getSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * IMEI
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId() + "";
    }

    /**
     * 返回当前程序名
     */
    public static String getAppLabel(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            return String.valueOf(pm.getApplicationLabel(context
                    .getApplicationInfo()));
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 返回mac地址
     */
    public static String getMacAddress(Context context) {
        try {
            if (TextUtils.isEmpty(mac)) {
                WifiManager wifi = (WifiManager) context
                        .getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                mac = info.getMacAddress();
                if (mac == null || mac.equalsIgnoreCase("null")) {
                    mac = "";
                }
            }
            return mac;
        } catch (Exception e) {
            return mac;
        }
    }

    /**
     * 返回手机号
     */
    public static String getPhoneNum(Context context) {
        try {
            //获取手机号码
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static boolean isHoneycomb() {
        // Can use static final constants like HONEYCOMB, declared in later
        // versions
        // of the OS since they are inlined at compile time. This is guaranteed
        // behavior.
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static boolean isHoneycombTablet(Context context) {
        return isHoneycomb() && isTablet(context);
    }

    /**
     * 从url中获取图片比例
     *
     * @param url
     * @return 数组 0为宽 1为高
     */
    public static int[] getScaleInUrl(String url) {
        try {
            String scaleString = url.substring(url.indexOf("!") + 1);
            String[] tmp = scaleString.split("x");
            int[] scale = new int[2];
            scale[0] = Integer.valueOf(tmp[0]);// 宽
            scale[1] = Integer.valueOf(tmp[1]);// 高
            return scale;
        } catch (Exception e) {
            return new int[]{1, 0};
        }
    }

    /**
     * 从url中获取图片宽：高比例
     */
    public static float getScale(String url, int defaultWidth, int defaultHeight) {
        if (!TextUtils.isEmpty(url)) {
            int index = url.indexOf("!");
            if (index != -1 && index != url.length()) {
                String scaleString = url.substring(url.indexOf("!") + 1);
                if (!TextUtils.isEmpty(scaleString)) {
                    String[] tmp = scaleString.split("x");
                    return Float.valueOf(tmp[0]) / Float.valueOf(tmp[1]);
                }
            }
        }
        return (float) defaultWidth / (float) defaultHeight;
    }

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastDoubleClick(int duration) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < duration) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isSlowFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1200) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean hasSmartBar() {
        try {
            // 新型号可用反射调用Build.hasSmartBar()
            Method method = Class.forName("android.os.Build").getMethod("hasSmartBar");
            return ((Boolean) method.invoke(null)).booleanValue();
        } catch (Exception e) {
        }
        // 反射不到Build.hasSmartBar(),则用Build.DEVICE判断
        if (Build.DEVICE.equals("mx2")) {
            return true;
        } else if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9")) {
            return false;
        }
        return false;
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /**
     * 创建请求URL
     */
    public static String createUrl(String path, String baseUrl) {
        StringBuilder url = new StringBuilder("");
        if (TextUtils.isEmpty(baseUrl)) {
            url.append(HttpContants.BaseUrl);
        } else {
            url.append(baseUrl);
        }
        url.append("/");
        url.append(path);
        return url.toString();
    }

    /**
     * 是否登录
     */

    public static boolean isLogin() {
        WJLoginHelper helper = UserUtil.getWJLoginHelper();

        // 本地无用户
        if (!helper.isExistsUserInfo()) {
            return false;
        }
        // A2不存在或已过期
        if (!helper.isExistsA2() || helper.checkA2TimeOut()) {
            if (helper.isNeedPwdInput()) {
                return false;
            }
        }
        return true;
    }
    public static String getUserAccount(Context context) {

        return SharePreferenceUtil.getLoginAccount(context);

    }


}
