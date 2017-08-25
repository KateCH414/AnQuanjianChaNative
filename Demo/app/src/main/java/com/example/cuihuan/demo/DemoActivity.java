package com.example.cuihuan.demo;


        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.annotation.SuppressLint;
        import android.view.KeyEvent;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class main extends AppCompatActivity {
    private WebView myWebView = null;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        // 打开网页
        myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://www.irunker.com/");
        //设置可自由缩放网页、JS生效
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        // 修改默认在WebView中打开链接
        myWebView.setWebViewClient(new WebViewClient());
    }

    // 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack())
        {
            // 返回键退回
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
