package com.jd.yyc.ui.activity.web;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.web.WebActivity;
import com.jd.web.WebFragment;
import com.jd.yyc.R;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.event.EventLoginSuccess;
import com.jd.yyc.event.EventWebActFinish;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.UrlUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import de.greenrobot.event.EventBus;


public class YYCWebActivity extends WebActivity implements View.OnClickListener, Contants {
    private ImageView backBtn;
    private ImageView closeIcon;
    private TextView titleTxt;

    private String titleStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTranslucentStatus();
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        EventBus.getDefault().register(this);
    }

    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setStatusBarColor() {
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.dominant_color));
    }

    /**
     * 启动 Web 容器页面
     *
     * @param from
     * @param url  URL 链接
     */
    public static void launch(@NonNull Context from, @NonNull String url, String title) {
        launch(from, url, title, false);
    }

    public static void launch(@NonNull Context from, @NonNull String url, String title, boolean showCloseIcon) {
        Intent intent = new Intent(from.getApplicationContext(), YYCWebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("show_close_icon", showCloseIcon);
        from.startActivity(intent);
    }

    @Override
    protected WebFragment getFragment() {
        return YYCWebFragment.newInstance(getIntent().getStringExtra("url"));
    }

    @Override
    protected void initView() {
        backBtn = (ImageView) findViewById(R.id.back_btn);
        closeIcon = (ImageView) findViewById(R.id.close_icon);
        titleTxt = (TextView) findViewById(R.id.title_txt);
        closeIcon.setVisibility(getIntent().getBooleanExtra("show_close_icon", false) ? View.VISIBLE : View.GONE);
        closeIcon.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleStr = getIntent().getStringExtra("title");
        if (!CheckTool.isEmpty(titleStr))
            titleTxt.setText(titleStr);
    }

    @Override
    public int getContentId() {
        return R.layout.activity_yyc_web;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_btn:
                onBackPressed();
                break;
            case R.id.close_icon:
                finish();
                break;
        }

    }

    @Override
    public void onWebViewTitleChanged(String title) {
        this.titleTxt.setText(title);
        titleStr = title;
    }

    @Override
    public void onBackPressed() {
        if (titleStr != null && titleStr.trim().equals("使用优惠卷") && mWebFragment != null && mWebFragment.mWebView != null) {
            mWebFragment.mWebView.loadUrl("javascript:appCouponGoBack()");
            return;
        }
        super.onBackPressed();
    }

    public void onEvent(EventWebActFinish event) {
        finish();
    }

    public void onEvent(EventLoginSuccess event) {
        if (!CheckTool.isEmpty(event.needLoginUrl)) {
            UrlUtil.getLoginStatusUrl(event.needLoginUrl, new UrlUtil.LoginStatusCallback() {
                @Override
                public void onSuccess(String url) {
                    mWebFragment.mWebView.loadUrl(url);
                }

                @Override
                public void onFailed(String msg) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
