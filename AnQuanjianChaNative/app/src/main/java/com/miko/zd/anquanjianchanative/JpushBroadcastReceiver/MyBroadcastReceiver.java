package com.miko.zd.anquanjianchanative.JpushBroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.miko.zd.anquanjianchanative.Bean.JPushBean;
import com.miko.zd.anquanjianchanative.DownloadActivity;
import com.miko.zd.anquanjianchanative.NewCheckActivity;
import com.miko.zd.anquanjianchanative.R;
import com.miko.zd.anquanjianchanative.Utils.Utils;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public static final String TAG = "MyBroadcastReceiver";
    public static int m = 1;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

        }
        else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.i("jrec","收到了自定义消息。息内容是：");
            Bundle bundle = intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            bundle = intent.getExtras();
            String content = bundle.getString(JPushInterface.EXTRA_ALERT);
            bundle = intent.getExtras();
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            bundle = intent.getExtras();

            JPushBean json = new Gson().fromJson(extras,JPushBean.class);
            if(json.getVersionCode()<= Utils.getVersionCode(context)){
                return;
            }
            Toast.makeText(context, "检测到新版本", Toast.LENGTH_LONG).show();
            int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Intent i = new Intent(context,DownloadActivity.class);
            i.putExtra("url",json.getUrl());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
            Notification baseNF = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("安全检查发现新版本").setContentText("版本："+json.getVersion())
                    .setDefaults(Notification.DEFAULT_VIBRATE).setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .build();

            NotificationManager nm =
                    (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            nm.notify(notificationId,baseNF);
        }else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, NewCheckActivity.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }
}