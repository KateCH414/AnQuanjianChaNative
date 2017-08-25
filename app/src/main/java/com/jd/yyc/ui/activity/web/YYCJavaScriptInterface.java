package com.jd.yyc.ui.activity.web;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.jd.yyc.cartView.CartViewActivity;
import com.jd.yyc.event.EventHome;
import com.jd.yyc.event.EventWebActFinish;
import com.jd.yyc.home.HomeActivity;
import com.jd.yyc.login.LoginActivity;
import com.jd.yyc.login.PortalActivity;

import de.greenrobot.event.EventBus;

/**
 * JS 调用原生代码的接口
 * <p>
 * js调用 <a href='JavaScript:Android.toast(\""+child[n]+"\")'>"
 * <p>
 * Created by zhenguo on 6/9/17.
 */
public class YYCJavaScriptInterface {

    private Context context;
    private Handler maniHandler = new Handler(Looper.getMainLooper());

    public YYCJavaScriptInterface(Context context) {
        this.context = context;
    }

    /**
     * JS调用系统toast
     */
    @JavascriptInterface
    public void AppGoLogIn(String url) {
//        EventBus.getDefault().post(new EventWebActFinish());
        Intent intent = new Intent(context.getApplicationContext(), PortalActivity.class);
        intent.putExtra(LoginActivity.NEED_LOGIN_URL, url);
        context.startActivity(intent);
//        ToastUtil.show("需要登录");
    }

    @JavascriptInterface
    public void AppGoCart() {
        EventBus.getDefault().post(new EventWebActFinish());
        CartViewActivity.launch(context);
//        ToastUtil.show("去购物车");
    }

    @JavascriptInterface
    public void AppGoHome() {
        HomeActivity.launch(context);
        maniHandler.post(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new EventHome());
            }
        });
//        ToastUtil.show("去首页");
    }
    @JavascriptInterface
    public void toast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            Log.w("JavaScriptInterface", "msg is empty!");
            return;
        }
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    // add your interface

}
