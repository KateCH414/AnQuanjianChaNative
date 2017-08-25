package com.jd.yyc.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jd.yyc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangweifeng on 2017/6/21.
 */

public class CommonDialogFragment extends DialogFragment {
    @InjectView(R.id.title)
    TextView dialogTitleView;
    @InjectView(R.id.content)
    TextView dialogContentView;
    @InjectView(R.id.button1)
    TextView button1;
    @InjectView(R.id.button2)
    TextView button2;
    private OnClickListener onClickListener;
    String title;
    String content;
    String buttonText1;
    String buttonText2;

    public static CommonDialogFragment newInstance(String title, String content, String buttonText1, String buttonText2) {

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("content", content);
        args.putString("buttonText1", buttonText1);
        args.putString("buttonText2", buttonText2);
        CommonDialogFragment fragment = new CommonDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static CommonDialogFragment newInstance(String title, String content) {
        return newInstance(title, content, "取消", "确定");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            content = bundle.getString("content");
            buttonText1 = bundle.getString("buttonText1");
            buttonText2 = bundle.getString("buttonText2");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogStyle);
        View view = View.inflate(getActivity(), R.layout.common_dialog, null);
        ButterKnife.inject(this, view);

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
            dialogTitleView.setText(title);
            dialogContentView.setText(content);
        }

        if (!TextUtils.isEmpty(buttonText1)) {
            button1.setText(buttonText1);
        }

        if (!TextUtils.isEmpty(buttonText2)) {
            button2.setText(buttonText2);
        }

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @OnClick(R.id.button1)
    void onClickButton1() {
        if (onClickListener != null) {
            onClickListener.onClick(0);
        }
        dismiss();
    }

    @OnClick(R.id.button2)
    void onClickButton2() {
        if (onClickListener != null) {
            onClickListener.onClick(1);
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public interface OnClickListener {
        void onClick(int position);
    }
}
