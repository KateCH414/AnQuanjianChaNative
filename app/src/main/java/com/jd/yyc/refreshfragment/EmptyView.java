package com.jd.yyc.refreshfragment;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.Result;

/**
 * Created by wf on 14-4-1.
 * 提示空布局
 */
public class EmptyView extends FrameLayout {

    private ImageView imageView;

    private TextView textView;

    private TextView button;
    private TextView netCheck;
    private View rootView;

    private int defaultEmptyId = R.drawable.ic_default_net_error;
    private String emptyMsg = "没有内容!";

    private OnClickListener checkNetClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //网络检测先关闭
//            NetCheckService.getInstance().startCheck((FragmentActivity) getContext(), netCheck);
        }
    };

    private OnClickListener feedback = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //反馈预留
        }
    };

    public EmptyView(Context context) {
        super(context);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext())
                .inflate(R.layout.layout_recylerview_empty, this);
        imageView = (ImageView) findViewById(R.id.empty_image);
        textView = (TextView) findViewById(R.id.empty_text);
        button = (TextView) findViewById(R.id.empty_btn);
        netCheck = (TextView) findViewById(R.id.check_tip);
        rootView = findViewById(R.id.root);
    }

    public void setRootBackGround(@ColorRes int color) {
        rootView.setBackgroundResource(color);
    }

    public void setTextColor(int resId) {
        textView.setTextColor(resId);
    }

    public void setEmptyText(int resId) {
        textView.setText(getContext().getString(resId));
    }

    public void setEmptyText(String text) {
        textView.setText(text);
    }

    public ImageView getEmptyImage() {
        return imageView;
    }

    public void setEmptyImage(int resId) {
        imageView.setImageResource(resId);
    }

    public TextView getEmptyButton() {
        return button;
    }

    public TextView getNetCheck() {
        return netCheck;
    }

    public TextView getEmptyText(){
        return textView;
    }

    public View getRootView() {
        return rootView;
    }

    public void setButtonText(String txt) {
        button.setText(txt);
        button.setVisibility(VISIBLE);
    }

    public void setCheckTipText(String txt) {
        netCheck.setVisibility(VISIBLE);
        netCheck.setText(txt);
    }

    public void setBtnClickListener(OnClickListener listener) {
        button.setOnClickListener(listener);
    }


    public void setTipClickListener(OnClickListener listener) {
        netCheck.setOnClickListener(listener);
    }

    public void setBackgroundColor(int color) {
        rootView.setBackgroundColor(color);
    }

    public void showEmpty(int statusCode, String errorMessage, OnClickListener refreshListener) {
        setVisibility(VISIBLE);
        setOnClickListener(null);
        if (statusCode == Result.STATUS_NO_DATA) {
            imageView.setVisibility(VISIBLE);
            imageView.setImageResource(defaultEmptyId);
            textView.setText(emptyMsg);
            netCheck.setVisibility(View.GONE);
            button.setVisibility(GONE);
        } else if (statusCode == Result.STATUS_NO_NET || statusCode == Result.STATUS_TIME_OUT) {
            imageView.setImageResource(R.drawable.ic_default_net_error);
            textView.setText(errorMessage);
            button.setVisibility(VISIBLE);
            netCheck.setVisibility(GONE);//暂时不需要网络检测
            button.setText("刷新");
            netCheck.setText("检测网络");
            button.setOnClickListener(refreshListener);
            netCheck.setOnClickListener(checkNetClick);
        } else {
            imageView.setImageResource(R.drawable.ic_default_net_error);
            textView.setText(errorMessage);
            button.setVisibility(GONE);//暂时不需要反馈
            netCheck.setVisibility(GONE);
            button.setText("我要反馈");
            button.setOnClickListener(feedback);
            setOnClickListener(refreshListener);
        }
    }

    public void setDefaultEmptyId(@DrawableRes int emptyId) {
        this.defaultEmptyId = emptyId;
    }

    public void setEmptyMsg(String emptyMsg) {
        this.emptyMsg = emptyMsg;
    }
}
