package com.jd.yyc.splash;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jd.yyc.R;
import com.jd.yyc.base.CommonActivity;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.home.HomeActivity;
import com.jd.yyc.util.SharePreferenceUtil;
import com.jd.yyc.welcome.WelcomeActivity;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by zhangweifeng1 on 2017/6/12.
 */

public class SplashActivity extends CommonActivity {
    Timer timer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        timer = new Timer(true);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                jump();
            }
        };
        timer.schedule(timerTask, 1000);
    }

    void jump() {
        boolean isFirst = SharePreferenceUtil.getFirstStart(mContext);
        if (isFirst) {
            WelcomeActivity.launch(mContext);
        } else {
            HomeActivity.launch(mContext);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
