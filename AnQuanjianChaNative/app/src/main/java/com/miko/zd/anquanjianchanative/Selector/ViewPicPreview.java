package com.miko.zd.anquanjianchanative.Selector;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.miko.zd.anquanjianchanative.R;

import java.util.ArrayList;

import z.dotloopviewpagerlibrary.DotLoopViewpager;


public class ViewPicPreview extends AppCompatActivity implements IViewPicPreview {
    private ArrayList<String> path = null;
    private ProgressDialog dialog;
    public static int CLICK_PREVIEW = 0;
    public static int COMPLETE_PREVIEW = 1;
    public static int DELETE_PREVIEW = 2;

    public int type;
    public Button btPositive;
    DotLoopViewpager<String> mDlvpDotLoopViewpager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_preview);
        type = getIntent().getIntExtra("TYPE", -1);
        initView();
        initEvent();
    }

    void initView() {
        path = getIntent().getStringArrayListExtra("path");
        if (path == null) {
            Toast.makeText(this, "路径不存在", Toast.LENGTH_LONG).show();
            finish();
        }

        mDlvpDotLoopViewpager = (DotLoopViewpager) findViewById(R.id.id_vp);
        mDlvpDotLoopViewpager.setonBindImageAndClickListener(new DotLoopViewpager.onBindImageAndClickListener<String>() {
            @Override
            public void onClick(String bean) {

            }

            @Override
            public void onBind(String bean, ImageView imageView) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(bean));
                imageView.setAdjustViewBounds(true);
            }
        });
        mDlvpDotLoopViewpager.setData(path);
        //设置监听
        FrameLayout.LayoutParams params = mDlvpDotLoopViewpager.getIndicatorCotainerLayoutParams();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        mDlvpDotLoopViewpager.setIndicatorCotainerLayoutParams(params);
        //设置小圆点之间的距离,dp值
        mDlvpDotLoopViewpager.setSpaceDip(20);
        //设置指示器的selector,默认是红色小圆点和灰色小圆点,你可以自己建立一个selector文件,然后设置上去,定制性非常高,你想设置什么样的图片都可以
        //设置指示器的直径,默认是WRAP_CONTENT,自己根据需要可以调整
        mDlvpDotLoopViewpager.setIndicatordiameter(150);
        mDlvpDotLoopViewpager.setScaleType(ImageView.ScaleType.FIT_CENTER);

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        btPositive = (Button) findViewById(R.id.id_bt_confirm_preview);
        if (type == CLICK_PREVIEW) {
            btPositive.setVisibility(View.GONE);
        }
        if(type == DELETE_PREVIEW){
            btPositive.setText("移除");
        }
    }

    void initEvent() {
        findViewById(R.id.id_lin_back_preview).setOnClickListener(v -> finish());
        if (type == COMPLETE_PREVIEW)
            btPositive.setOnClickListener(v -> {

            });

    }


    @Override
    public void showProgress(String title) {
        dialog.setTitle(title);
        dialog.show();
    }

    @Override
    public void showProgress(String title, int max) {
        dialog.setTitle(title);
        dialog.setMax(max);
        dialog.show();
    }

    @Override
    public void updateProgress(int progress) {
        dialog.setProgress(progress);
    }

    @Override
    public void closeProgress() {
        dialog.dismiss();
    }

    @Override
    public void changeTitle(String title) {
        dialog.setTitle(title);
    }

    @Override
    public void closePreview() {
        finish();
    }
}
