package com.jd.yyc.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jd.project.lib.andlib.utils.Logger;
import com.jd.yyc.R;
import com.jd.yyc.event.EventCommon;
import com.jingdong.jdma.JDMaInterface;

import java.util.List;

import de.greenrobot.event.EventBus;


/**
 * 基类
 * 封装一系列对fragment操作的api
 */
public abstract class CommonActivity extends AppCompatActivity {

    protected String TAG;
    protected AppCompatActivity mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TAG = ((Object) this).getClass().getSimpleName();
        Log.i(TAG, "onCreate");
        mContext = this;
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    /**
     * @return 从子类获取布局资源
     */
    public int getContentView() {
        return 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        JDMaInterface.onResume(this);
        Logger.i(TAG, "onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JDMaInterface.onPause();  //加入这个sdk方法
        Logger.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Logger.i(TAG, "onDestroy");
    }

    @Override
    public void onBackPressed() {
        //这里加try catch是为避免快速返回出现的bug
        try {
            super.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .remove(fragment)
                .commitAllowingStateLoss();
    }

    public void show(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commitAllowingStateLoss();
    }

    public void hide(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commitAllowingStateLoss();
    }

    public void add(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commitAllowingStateLoss();
    }

    public void add(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, tag)
                .commitAllowingStateLoss();
    }

    public void add(int id, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(id, fragment, tag)
                .commitAllowingStateLoss();
    }

    public void replace(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commitAllowingStateLoss();
    }

    public void replace(int id, Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment, tag)
                .commitAllowingStateLoss();
    }

    public void replace(int id, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(id, fragment)
                .commitAllowingStateLoss();
    }


    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public void onEvent(EventCommon event) {

    }

}
