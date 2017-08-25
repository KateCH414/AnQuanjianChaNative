package com.jd.yyc.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jd.yyc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by tangh on 14-9-6.
 */
public abstract class ActionBarActivity extends NoActionBarActivity {
    @InjectView(R.id.bar_title)
    TextView titleView;
    @InjectView(R.id.btn_right)
    Button rightView;
    @InjectView(R.id.constom_action_bar)
    RelativeLayout actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRightButton(rightView);
    }

    @Override
    public void optContentView() {
        setContentView(R.layout.activity_base);
        int contentResID = getContentView();
        if (contentResID != 0) {
            View contentView = View.inflate(mContext, contentResID, null);
            View containerView = findViewById(R.id.fragment_container);
            if (containerView != null) {
                ViewGroup parentView = ((ViewGroup) containerView.getParent());
                if (parentView != null) {
                    parentView.addView(contentView, 0,
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    parentView.removeView(containerView);
                }
            }
        }
        ButterKnife.inject(this);
    }

    public void optFragment() {
        Fragment fragment = getContentFragment();
        if (fragment != null) {
            replace(fragment);
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (titleView != null) {
            titleView.setText(title);
        }
    }


    public RelativeLayout getMActionBar() {
        return actionBar;
    }


    @OnClick(R.id.back_view)
    public void onBack() {
        finish();
    }

    @OnClick(R.id.btn_right)
    public void onRightButtonClick() {
    }

    public void initRightButton(Button btnRight) {

    }

    public TextView getTitleView() {
        return titleView;
    }

    public Button getRightView() {
        return rightView;
    }

}
