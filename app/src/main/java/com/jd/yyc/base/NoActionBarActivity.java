package com.jd.yyc.base;

import android.app.ActivityManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jd.yyc.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.List;

import butterknife.ButterKnife;


public abstract class NoActionBarActivity extends CommonActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTranslucentStatus();
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        optContentView();
        optFragment();
    }

    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           //5.0 全透明实现
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.dominant_color));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 全透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setStatusBarColor() {
        SystemBarTintManager tintManager = new SystemBarTintManager(mContext);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.dominant_color));
    }

    @Override
    public final void setContentView(View view) {
        super.setContentView(view);
    }

    /**
     * 处理内容布局
     */
    public void optContentView() {
        int contentResID = getContentView();
        if (contentResID != 0) {
            setContentView(contentResID);
            ButterKnife.inject(this);
        }
    }

    /**
     * 处理切片
     */
    public void optFragment() {
        Fragment fragment = getContentFragment();
        if (fragment != null) {
            if (getContentView() == 0) {
                setContentView(R.layout.activity_empty);
                ButterKnife.inject(this);
            }
            replace(fragment);
        }
    }

    /**
     * @return 从子类获取切片对象
     */
    public Fragment getContentFragment() {
        return null;
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }


    public boolean isTopActivity() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
        if (runningTaskInfos != null) {
            String topActivity = runningTaskInfos.get(0).topActivity.getClassName();
            System.out.println("topActivity = " + topActivity);
            return getClass().getName().equals(topActivity);
        }
        return false;
    }

}
