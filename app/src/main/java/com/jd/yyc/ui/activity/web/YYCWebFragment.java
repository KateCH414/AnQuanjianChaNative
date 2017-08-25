package com.jd.yyc.ui.activity.web;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.jd.web.WebFragment;

/**
 *
 */

public class YYCWebFragment extends WebFragment {


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url url 链接
     */
    public static YYCWebFragment newInstance(@NonNull String url) {
        YYCWebFragment fragment = new YYCWebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initWebView() {
        super.initWebView();
        // 在你真正需要js调用原生的时候使用此代码，否则强烈建议注释掉
        mWebView.addJavascriptInterface(new YYCJavaScriptInterface(getActivity()), "Android");
    }

}
