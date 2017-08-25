package com.miko.zd.anquanjianchanative;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.Adapter.RcycAdapterDetail;
import com.miko.zd.anquanjianchanative.Adapter.RcycAdapterExpendDetail;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.HistoryItemBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean1;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean2;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.PathRealm;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CheckItemDetailActivity extends AppCompatActivity {

    private TextView tvTitle;
    private RecyclerView rcycView;
    private TextView tvCreateReport;
    private String serialNum;
    private RcycAdapterDetail adapter;
    private String title;
    private RealmResults<ItemBean2> parentItemBeans;
    private int order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item_detail);        serialNum = getIntent().getStringExtra("serialNum");
        order = getSharedPreferences("TempCheckInfo", Context.MODE_PRIVATE).getInt("order", -1);
        if (serialNum == null) {
            return;
        }
        initData();
        initAdapter();
        initView();
        initEvent();
    }

    private void initAdapter() {
        adapter = new RcycAdapterDetail(parentItemBeans, this);
        parentItemBeans.addChangeListener(element -> adapter.notifyDataSetChanged());
    }

    private void initData() {
        Realm realm = Realm.getDefaultInstance();
        title = realm.where(ItemBean1.class).equalTo("serialNum", serialNum).findFirst().getItemName();
        parentItemBeans = realm.where(ItemBean2.class)
                .beginsWith("serialNum", serialNum + ".").findAllAsync();
    }

    private void initEvent() {
        tvCreateReport.setOnClickListener(v -> {
            Intent intent = new Intent(this, PreviewReportActivity.class);
            intent.putExtra("type",PreviewReportActivity.TYPE_PREVIEW);
            startActivity(intent);
        });
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.id_tv_title);
        tvTitle.setText(title);
        rcycView = (RecyclerView) findViewById(R.id.id_rcycView);
        rcycView.setLayoutManager(new LinearLayoutManager(this));
        rcycView.setAdapter(adapter);
        tvCreateReport = (TextView) findViewById(R.id.id_tv_create_report);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 400:
                    String serialNum = data.getStringExtra("serialNum");
                    Realm realm = Realm.getDefaultInstance();
                    RealmList<PathRealm> pathList = realm.where(HistoryItemBean.class).equalTo("serialNum", serialNum)
                            .equalTo("order", order).findFirst().getPathList();
                    realm.executeTransaction(realm12 -> pathList.remove(data.getIntExtra("pos", 1)));
                    adapter.adapter.adapters[data.getIntExtra("position",-1)]
                            .notifyItemRemoved(data.getIntExtra("pos", 1));
                    break;
                case 200:
                    Realm realm2 = Realm.getDefaultInstance();
                    RealmList<PathRealm> pathList2 = realm2.where(HistoryItemBean.class)
                            .equalTo("serialNum", RcycAdapterExpendDetail.serialNum)
                            .equalTo("order", order).findFirst().getPathList();
                    realm2.beginTransaction();
                    PathRealm path2 = realm2.createObject(PathRealm.class);
                    path2.setPath(Environment.getExternalStorageDirectory()
                            + "/" + "localTempImgDir" + "/" + RcycAdapterExpendDetail.curPhotoName + ".jpg");
                    pathList2.add(1,path2);
                    realm2.commitTransaction();
                    adapter.adapter.adapters[RcycAdapterExpendDetail.currPostion].notifyItemInserted(1);
                    break;
                case 100:
                    if (data.getStringArrayListExtra("path") != null) {
                        Realm realm1 = Realm.getDefaultInstance();
                        RealmList<PathRealm> pathList1 = realm1.where(HistoryItemBean.class)
                                .equalTo("serialNum", data.getStringExtra("serialNum"))
                                .equalTo("order", order).findFirst().getPathList();
                        for (String s : data.getStringArrayListExtra("path")) {
                            realm1.executeTransaction(realm3 -> {
                                PathRealm p = realm1.createObject(PathRealm.class);
                                p.setPath(s);
                                pathList1.add(1, p);
                            });
                        }
                        adapter.adapter.adapters[data.getIntExtra("position",-1)]
                                .notifyItemRangeInserted(1,data.getStringArrayListExtra("path").size());
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
