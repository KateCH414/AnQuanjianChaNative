package com.miko.zd.anquanjianchanative;

import android.app.Application;
import android.content.res.Configuration;

import com.tencent.bugly.crashreport.CrashReport;

import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import cn.jpush.android.api.JPushInterface;
import io.realm.Realm;

@ReportsCrashes(
        httpMethod = HttpSender.Method.PUT,
        reportType = HttpSender.Type.JSON,
        formUri = "http://192.168.200.233:5984/acra-yyc/_design/acra-storage/_update/report",
        formUriBasicAuthLogin="admin",
        formUriBasicAuthPassword = "admin"
)



public class MyApp extends Application {
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "900058155", true);
        System.out.println("MyApp is called");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        Realm.init(this);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}