package com.miko.zd.anquanjianchanative;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miko.zd.anquanjianchanative.Bean.RealmBean.HistoryCheckedBean;
import com.miko.zd.anquanjianchanative.Biz.RealmDBHelper;

import io.realm.Realm;

public class NewCheckActivity extends AppCompatActivity {

    private EditText edDept, edRoom, edPrincipal;
    private Button btNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_check);
        initView();
        initEvent();
    }

    private void initEvent() {
        btNext.setOnClickListener(v -> {
            String dept = edDept.getText().toString();
            String room = edRoom.getText().toString();
            String principal = edPrincipal.getText().toString();
            if (dept.equals("") || room.equals("") || principal.equals("")) {
                Toast.makeText(this, "请补全信息", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sp = getSharedPreferences("TempCheckInfo", Context.MODE_PRIVATE);
                int order = new RealmDBHelper().getLastOrder()+1;
                sp.edit().putString("dept", dept).putString("room", room).putString("principal", principal)
                        .putInt("order",order).apply();
                sp = getSharedPreferences("TempUser",Context.MODE_PRIVATE);
                String user = sp.getString("user",null);
                String date = sp.getString("date",null);
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(realm1 -> {
                    HistoryCheckedBean history = realm1.createObject(HistoryCheckedBean.class,order);
                    history.setSavad(false);
                    history.setPrincipal(principal);
                    history.setRoom(room);
                    history.setUser(user);
                    history.setDept(dept);
                    history.setDate(date);
                });
                startActivity(new Intent(this, CheckItemActivity.class));
            }
        });
    }

    private void initView() {
        btNext = (Button) findViewById(R.id.id_bt_next);
        edDept = (EditText) findViewById(R.id.id_ed_dept);
        edRoom = (EditText) findViewById(R.id.id_ed_room);
        edPrincipal = (EditText) findViewById(R.id.id_ed_principal);
    }
}
