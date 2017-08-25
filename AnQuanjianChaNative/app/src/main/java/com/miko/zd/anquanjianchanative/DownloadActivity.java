package com.miko.zd.anquanjianchanative;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        WebView webView = (WebView) findViewById(R.id.id_webView);
        Log.i("url",getIntent().getStringExtra("url"));
        if (getIntent().getStringExtra("url") == null) {
            Toast.makeText(this, "ffff", Toast.LENGTH_SHORT).show();
            return;
        }
        webView.loadUrl(getIntent().getStringExtra("url"));
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }
}

class DownLoadThread extends
        Thread {
    private String downLoadUrl;
    private Context context;
    private FileOutputStream out = null;
    private File downLoadFile = null;
    private File sdCardFile = null;
    private InputStream in = null;

    public DownLoadThread(String downLoadUrl, Context context, Listener listener) {
        super();
        this.downLoadUrl = downLoadUrl;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(downLoadUrl);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setDoInput(true);// 如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 true。
            conn.setDoOutput(true);// 如果打算使用
            in = conn.getInputStream();
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Toast.makeText(context, "SD卡不可用！", Toast.LENGTH_SHORT).show();
                return;
            }
            downLoadFile = Environment.getExternalStorageDirectory();
            sdCardFile = new File(downLoadFile, "download.apk");
            out = new FileOutputStream(sdCardFile);
            byte[] b = new byte[1024];
            int len;
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface Listener {
        public void onEnd();
    }
}
