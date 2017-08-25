package com.miko.zd.anquanjianchanative;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.miko.zd.anquanjianchanative.Bean.ItemTreeBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean1;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean2;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean3;
import com.miko.zd.anquanjianchanative.Bean.TextBean;
import com.miko.zd.anquanjianchanative.Biz.RealmDBHelper;
import com.miko.zd.anquanjianchanative.JpushBroadcastReceiver.MyBroadcastReceiver;
import com.miko.zd.anquanjianchanative.Utils.Utils;

import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;




public class MainActivity extends AppCompatActivity {
    private Button btLogin;
    private EditText edUserName;
    private TextView tvYear, tvMonth, tvDay;
    private LinearLayout lnChooseData;
    public static ArrayList<ItemTreeBean> itemTree;
    private MyBroadcastReceiver receiver;
    private RealmDBHelper dbHelper;
    private ProgressDialog dialog;
    private final int MSG_BEGIN_LOGIN = 0;
    private final int MSG_END_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        initReceiver();
        initView();
        initEvent();
        initData(this);
    }

    private void initReceiver() {
        receiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.jpush.android.intent.REGISTRATION");
        intentFilter.addAction("cn.jpush.android.intent.MESSAGE_RECEIVED");
        intentFilter.addAction("cn.jpush.android.intent.NOTIFICATION_RECEIVED");
        intentFilter.addAction("cn.jpush.android.intent.NOTIFICATION_OPENED");
        intentFilter.addAction("cn.jpush.android.intent.CONNECTION");
        intentFilter.addCategory("com.miko.zd.anquanjianchanative");
        registerReceiver(receiver, intentFilter);
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.INTERNET,
                                android.Manifest.permission_group.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        0);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private void initEvent() {
        Calendar calendar = Calendar.getInstance();
        lnChooseData.setOnClickListener(v -> new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            tvYear.setText(year + "年");
            tvDay.setText(dayOfMonth + "日");
            tvMonth.setText(monthOfYear + "月");
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)).show());
        btLogin.setOnClickListener(v -> {
            if (edUserName.getText().toString().equals("")) {
                Toast.makeText(this, "姓名不得为空", Toast.LENGTH_SHORT).show();
            } else if (tvMonth.getText().equals("")) {
                Toast.makeText(this, "请选择检查的日期", Toast.LENGTH_SHORT).show();
            } else {
                MyHandler handler = new MyHandler();
                new Thread(() -> {
                    Message msg1 = new Message();
                    msg1.arg1 = MSG_BEGIN_LOGIN;
                    handler.sendMessage(msg1);
                    SharedPreferences sp = getSharedPreferences("TempUser", Context.MODE_PRIVATE);
                    sp.edit().putString("user", edUserName.getText().toString()).
                            putString("date", tvYear.getText().toString()
                                    + tvMonth.getText().toString() + tvDay.getText().toString())
                            .apply();
                    initData();
                    Message msg2 = new Message();
                    msg2.arg1 = MSG_END_LOGIN;
                    handler.sendMessage(msg2);
                }).start();
            }
        });
    }

    private class MyHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case MSG_BEGIN_LOGIN:
                    dialog.show();
                    break;
                case MSG_END_LOGIN:
                    dialog.dismiss();
                    startActivity(new Intent(MainActivity.this, HistoryActivity.class));
                    break;
            }
        }
    }
    private void initData() {
        dbHelper = new RealmDBHelper();
        if(dbHelper.hadAddedItems()){
            return;
        }
        try {
            InputStream is = getAssets().open("data.txt");
            InputStreamReader isReader = new InputStreamReader(is, "gbk");
            BufferedReader buffreader = new BufferedReader(isReader);
            String line;
            while ((line = buffreader.readLine()) != null) {
                String [] splits = line.split(" ");
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(realm1 -> {
                    if(Utils.charSize(splits[0],'.')==0){
                        ItemBean1 itemBean = realm1.createObject(ItemBean1.class);
                        itemBean.setItemName(splits[1]);
                        itemBean.setSerialNum(splits[0]);
                    }else if(Utils.charSize(splits[0],'.')==1){
                        ItemBean2 itemBean = realm1.createObject(ItemBean2.class);
                        itemBean.setItemName(splits[1]);
                        itemBean.setSerialNum(splits[0]);
                    }else{
                        ItemBean3 itemBean = realm1.createObject(ItemBean3.class);
                        itemBean.setItemName(splits[1]);
                        itemBean.setSerialNum(splits[0]);
                    }
                 });
                realm.close();
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initData(Context context) {
        ArrayList<TextBean> flist = new ArrayList<TextBean>();
        ArrayList<TextBean> slist = new ArrayList<TextBean>();
        ArrayList<TextBean> tlist = new ArrayList<TextBean>();
        if (itemTree != null) {
            return;
        }

        itemTree = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("data.txt");
            InputStreamReader isReader = new InputStreamReader(is, "gbk");
            BufferedReader buffreader = new BufferedReader(isReader);
            String line;
            while ((line = buffreader.readLine()) != null) {
                if (Utils.charSize(line.split(" ")[0], '.') == 0) {
                    flist.add(new TextBean(line.split(" ")[0], line.split(" ")[1]));
                } else if (Utils.charSize(line.split(" ")[0], '.') == 1) {
                    slist.add(new TextBean(line.split(" ")[0], line.split(" ")[1]));
                } else if (Utils.charSize(line.split(" ")[0], '.') == 2) {
                    tlist.add(new TextBean(line.split(" ")[0], line.split(" ")[1]));
                }
            }
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (TextBean t : flist) {
            ArrayList<ItemTreeBean> sitemList = new ArrayList<ItemTreeBean>();
            for (TextBean stext : slist) {
                if (stext.getC().split("\\.")[0].equals(t.getC())) {

                    ArrayList<ItemTreeBean> titemList = new ArrayList<ItemTreeBean>();
                    for (TextBean ttext : tlist) {
                        if ((ttext.getC().split("\\.")[0].equals(stext.getC().split("\\.")[0])) &&
                                (ttext.getC().split("\\.")[1]).equals(stext.getC().split("\\.")[1])) {
                            titemList.add(new ItemTreeBean(Integer.parseInt(ttext.getC().split("\\.")[2]) - 1,
                                    Integer.parseInt(ttext.getC().split("\\.")[1]) - 1, ttext.getContent()));
                        }
                    }
                    sitemList.add(new ItemTreeBean(Integer.parseInt(stext.getC().split("\\.")[1]) - 1, Integer.parseInt(stext.getC().split("\\.")[0]) - 1, stext.getContent(), titemList));
                }
            }
            itemTree.add(new ItemTreeBean(Integer.parseInt(t.getC()) - 1, -1, t.getContent(), sitemList));
        }

    }

    private void initView() {
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("登录中,请稍候...");
        btLogin = (Button) findViewById(R.id.id_bt_login);
        edUserName = (EditText) findViewById(R.id.id_ed_user);
        lnChooseData = (LinearLayout) findViewById(R.id.id_ln_data);
        tvDay = (TextView) findViewById(R.id.id_tv_day_login);
        tvMonth = (TextView) findViewById(R.id.id_tv_month_login);
        tvYear = (TextView) findViewById(R.id.id_tv_year_login);
    }

    public static void reLoadTree(Context context) {
        itemTree = null;
        initData(context);
    }
}