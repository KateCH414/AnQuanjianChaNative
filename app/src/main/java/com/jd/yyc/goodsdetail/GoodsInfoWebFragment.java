package com.jd.yyc.goodsdetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jd.yyc.R;
import com.jd.yyc.widget.ItemWebView;


/**
 * 图文详情webview的Fragment
 */
public class GoodsInfoWebFragment extends Fragment {
    public ItemWebView wv_detail;
    private WebSettings webSettings;
    private LayoutInflater inflater;
    private static final String URL = "URL";
    private String url;


    public static GoodsInfoWebFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(URL, url);
        GoodsInfoWebFragment fragment = new GoodsInfoWebFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        View rootView = inflater.inflate(R.layout.fragment_item_info_web, null);
        url = getArguments().getString(URL);
        initWebView(rootView);

        return rootView;
    }


    public void initWebView(View rootView) {
        //String url = "http://m.okhqb.com/item/description/1000334264.html?fromApp=true";
        wv_detail = (ItemWebView) rootView.findViewById(R.id.wv_detail);
        wv_detail.setFocusable(false);
        webSettings = wv_detail.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setBlockNetworkImage(true);
        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        wv_detail.setWebViewClient(new GoodsDetailWebViewClient());
        wv_detail.loadUrl(url);

    }

    private class GoodsDetailWebViewClient extends WebViewClient {

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            webSettings.setBlockNetworkImage(false);
        }


        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }
    }


    @Override
    public void onDestroy() {


        super.onDestroy();
        if (wv_detail != null) {

            wv_detail.removeAllViews();
            wv_detail.destroy();
            wv_detail = null;
        }

    }
}
