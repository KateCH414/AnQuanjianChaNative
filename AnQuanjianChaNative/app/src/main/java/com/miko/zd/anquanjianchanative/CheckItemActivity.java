package com.miko.zd.anquanjianchanative;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.Adapter.CommonAdapter;
import com.miko.zd.anquanjianchanative.Adapter.CommonViewHolder;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean1;

import io.realm.Realm;
import io.realm.RealmResults;

public class CheckItemActivity extends AppCompatActivity {

    private RecyclerView rcycView;
    private TextView tvCreateReport;
    private RealmResults<ItemBean1> gradItemList;
    private CommonAdapter<ItemBean1> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_item);
        initDate();
        initView();
        initAdapter();
        initEvent();
    }

    private void initAdapter() {
        adapter = new CommonAdapter<ItemBean1>(this, R.layout.item_rcyc_checkitem
                , gradItemList) {
            @Override
            public void convert(CommonViewHolder holder, ItemBean1 itemBean1, int position) {
                holder.setText(R.id.id_tv_itemName_item, itemBean1.getSerialNum()
                        + "    " + itemBean1.getItemName());
                holder.setOnClickListener(v -> {
                    Intent intent = new Intent(CheckItemActivity.this, CheckItemDetailActivity.class);
                    intent.putExtra("serialNum", itemBean1.getSerialNum());
                    startActivity(intent);
                });
            }
        };
        rcycView.setAdapter(adapter);
        gradItemList.addChangeListener(element -> adapter.notifyDataSetChanged());
    }

    private void initDate() {
        Realm realm = Realm.getDefaultInstance();
        gradItemList = realm.where(ItemBean1.class).findAllAsync();
    }

    private void initEvent() {
        tvCreateReport.setOnClickListener(v -> {
            Intent intent = new Intent(this, PreviewReportActivity.class);
            intent.putExtra("type",PreviewReportActivity.TYPE_PREVIEW);
            startActivity(intent);
        });
    }

    private void initView() {
        rcycView = (RecyclerView) findViewById(R.id.id_rcycView);
        rcycView.setAdapter(adapter);
        rcycView.setLayoutManager(new LinearLayoutManager(this));
        tvCreateReport = (TextView) findViewById(R.id.id_tv_create_report);
    }
}