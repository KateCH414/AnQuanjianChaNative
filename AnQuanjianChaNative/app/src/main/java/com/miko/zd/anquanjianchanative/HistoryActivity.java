package com.miko.zd.anquanjianchanative;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.miko.zd.anquanjianchanative.Adapter.CommonAdapter;
import com.miko.zd.anquanjianchanative.Adapter.CommonViewHolder;
import com.miko.zd.anquanjianchanative.Bean.JsonItem;
import com.miko.zd.anquanjianchanative.Bean.JsonRecord;
import com.miko.zd.anquanjianchanative.Bean.PdfBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.HistoryCheckedBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.HistoryItemBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.PathRealm;
import com.miko.zd.anquanjianchanative.Biz.RealmDBHelper;
import com.miko.zd.anquanjianchanative.Utils.PdfUtils;
import com.miko.zd.anquanjianchanative.Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class HistoryActivity extends AppCompatActivity {
    private TextView tvNoChecked;
    private TabLayout mTablayout;
    private RelativeLayout lnVp;
    private ViewPager viewPager;
    private ImageView ivAdd, ivShare;
    private RecyclerView rcycHistory, rcycPdf;
    private CommonAdapter adapterChecked;
    private CommonAdapter adapterPdf;

    private RealmDBHelper dbHelper;
    private RealmResults<HistoryCheckedBean> historyList;
    public ArrayList<PdfBean> pdfList = new ArrayList<>();

    public ProgressDialog dialog;
    public View vp1, vp2;
    private boolean isSharing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checked_dept);
        initView();
        initEvent();
        try {
            updateVersion();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAdapter() {
        Realm realm = Realm.getDefaultInstance();
        SharedPreferences sp = getSharedPreferences("TempUser", Context.MODE_PRIVATE);
        String userName = sp.getString("user", null);
        historyList = realm.where(HistoryCheckedBean.class).equalTo("user", userName)
                .findAllSorted("order", Sort.DESCENDING);
        adapterChecked = new CommonAdapter<HistoryCheckedBean>(HistoryActivity.this,
                R.layout.item_rcyc_history_pager1, historyList) {
            @Override
            public void convert(CommonViewHolder holder,
                                HistoryCheckedBean historyCheckedBean, int position) {
                TextView tvData = holder.getView(R.id.id_tv_date_item);
                tvData.setText(historyCheckedBean.getDate());
                TextView tvRoom = holder.getView(R.id.id_tv_room_item);
                tvRoom.setText("房间：" + historyCheckedBean.getRoom());
                TextView tvPrincipal = holder.getView(R.id.id_tv_principal_item);
                tvPrincipal.setText("负责人：" + historyCheckedBean.getPrincipal());
                TextView tvDept = holder.getView(R.id.id_tv_dept_item);
                tvDept.setText("院系：" + historyCheckedBean.getDept());
                TextView tvSaved = holder.getView(R.id.id_tv_saved_item);
                if (historyCheckedBean.isSavad()) {
                    tvSaved.setText("已保存");
                } else {
                    tvSaved.setText("未保存");
                }
                holder.setOnClickListener(v -> {
                    if (historyCheckedBean.isSavad()) {
                        SharedPreferences sp = getSharedPreferences("TempCheckInfo", Context.MODE_PRIVATE);
                        sp.edit().putInt("order", historyList.get(position).getOrder())
                                .putString("dept", historyList.get(position).getDept())
                                .putString("room", historyList.get(position).getRoom())
                                .putString("principal", historyList.get(position).getPrincipal())
                                .apply();
                        sp = getSharedPreferences("TempUser", Context.MODE_PRIVATE);
                        sp.edit().putString("date", historyList.get(position).getDate())
                                .putString("user", historyList.get(position).getUser()).apply();
                        Intent intent = new Intent(HistoryActivity.this, PreviewReportActivity.class);
                        intent.putExtra("type", PreviewReportActivity.TYPE_HISTORY);
                        startActivity(intent);
                    } else {
                        SharedPreferences sp = getSharedPreferences("TempCheckInfo", Context.MODE_PRIVATE);
                        sp.edit().putInt("order", historyList.get(position).getOrder())
                                .putString("dept", historyList.get(position).getDept())
                                .putString("room", historyList.get(position).getRoom())
                                .putString("principal", historyList.get(position).getPrincipal())
                                .apply();
                        sp = getSharedPreferences("TempUser", Context.MODE_PRIVATE);
                        sp.edit().putString("date", historyList.get(position).getDate())
                                .putString("user", historyList.get(position).getUser()).apply();
                        Intent intent = new Intent(HistoryActivity.this, PreviewReportActivity.class);
                        intent.putExtra("type", PreviewReportActivity.TYPE_UNSAVE);
                        startActivity(intent);
                    }
                });
            }
        };

        adapterPdf = new CommonAdapter<PdfBean>(HistoryActivity.this,
                R.layout.item_rcyc_pdf_choose, pdfList) {
            @Override
            public void convert(CommonViewHolder holder, PdfBean pdfBean, int position) {
                holder.setText(R.id.id_tv_pdfname_item, pdfBean.getName());
                holder.setOnClickListener(v -> Toast.makeText(mContext,
                        "pdf", Toast.LENGTH_SHORT).show());
                holder.setOnClickListener(v -> {
                    startActivity(PdfUtils.getPdfFileIntent(pdfBean.getPath()));
                });
            }
        };
        rcycHistory.setAdapter(adapterChecked);
        rcycPdf.setAdapter(adapterPdf);
        rcycHistory.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
        rcycPdf.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    dialog.show();
                    break;
                case 2:
                    dialog.dismiss();
                    initAdapter();
                    if (historyList.size() != 0) {
                        lnVp.setVisibility(View.VISIBLE);
                        tvNoChecked.setVisibility(View.GONE);
                        adapterChecked.notifyDataSetChanged();
                        adapterPdf.notifyDataSetChanged();
                        ArrayList<View> list = new ArrayList<>();
                        list.add(vp1);
                        list.add(vp2);
                        MyPagerAdapter adapter = new MyPagerAdapter(list);
                        viewPager.setAdapter(adapter);/*
                        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                            }

                            @Override
                            public void onPageSelected(int position) {
                                if (position == 1) {
                                    ivShare.setVisibility(View.VISIBLE);
                                } else
                                    ivShare.setVisibility(View.GONE);
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {

                            }
                        });*/
                        mTablayout.setupWithViewPager(viewPager);
                        mTablayout.getTabAt(0).setText("检查记录");
                        mTablayout.getTabAt(1).setText("PDF");
                    } else {
                        lnVp.setVisibility(View.GONE);
                        tvNoChecked.setVisibility(View.VISIBLE);
                        ivShare.setVisibility(View.GONE);
                    }
                    break;
                case 3:
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyHandler handler = new MyHandler();
        new Thread(() -> {
            Message msg1 = new Message();
            msg1.arg1 = 1;
            handler.sendMessage(msg1);
            initData();
            Message msg2 = new Message();
            msg2.arg1 = 2;
            handler.sendMessage(msg2);
        }).start();


    }

    private void initEvent() {
        ivAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewCheckActivity.class);
            startActivity(intent);
        });
        ivShare.setOnClickListener(v -> {
            if (!isSharing) {
                ivShare.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_done_white_48dp));
                isSharing = true;
                for (int i = 0; i < pdfList.size(); i++) {
                    pdfList.get(i).setType(1);
                }
                adapterPdf.notifyDataSetChanged();
            } else {
                ivShare.setImageBitmap(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_share_white_48dp));
                for (int i = 0; i < pdfList.size(); i++) {
                    pdfList.get(i).setType(0);
                }
                adapterPdf.notifyDataSetChanged();

                isSharing = false;
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.DEFAULT");

                ArrayList<Uri> imageUris = new ArrayList<>();

                for (PdfBean pdfBean : pdfList) {
                    if (pdfBean.isChecked()) {
                        imageUris.add(Uri.fromFile(new File(pdfBean.getPath()))); // Add your image URIs here
                    }
                }
                if (imageUris.size() == 0) {
                    return;
                }
                if (imageUris.size() == 1) {
                    intent.setType("application/pdf");
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(pdfList.get(0).getPath())));
                } else {
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                }
                startActivity(Intent.createChooser(intent, "分享到"));
            }
        });
    }

    private void initView() {
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("更新用户配置,请稍后");

        tvNoChecked = (TextView) findViewById(R.id.id_tv_hint_noChecked);
        tvNoChecked.setVisibility(View.GONE);
        lnVp = (RelativeLayout) findViewById(R.id.id_ln_vp);
        vp1 = LayoutInflater.from(this).inflate(R.layout.content_vp_history, null);
        vp2 = LayoutInflater.from(this).inflate(R.layout.content_vp_pdf, null);
        ivAdd = (ImageView) findViewById(R.id.id_iv_add);
        ivShare = (ImageView) findViewById(R.id.id_iv_share);
        ivShare.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_share_white_48dp));
        ivShare.setVisibility(View.GONE);
        viewPager = (ViewPager) findViewById(R.id.id_vp_checked);
        mTablayout = (TabLayout) findViewById(R.id.tabs);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);

        rcycHistory = (RecyclerView) vp1.findViewById(R.id.id_rcyc_checked);
        rcycPdf = (RecyclerView) vp2.findViewById(R.id.id_rcyc_pdf);

    }

    private void initData() {
        SharedPreferences sp = getSharedPreferences("TempUser", Context.MODE_PRIVATE);
        String userName = sp.getString("user", null);

        File dirPdf = new File(Environment.getExternalStorageDirectory() + "/SDAnQuanJianCha");
        if (!dirPdf.exists()) {
            dirPdf.mkdirs();
        }
        File[] pdfs = dirPdf.listFiles(pathname ->
                pathname.getName().contains("pdf") && pathname.getName().contains(userName));
        for (File f : pdfs) {
            PdfBean pdfBean = new PdfBean(f.getPath(), f.getName(), true);
            pdfList.add(pdfBean);
        }
        Collections.reverse(pdfList);
    }

    private void updateVersion() throws IOException {
        if (dbHelper == null) {
            dbHelper = new RealmDBHelper();
        }
        SharedPreferences sp = getSharedPreferences("TempUser", Context.MODE_PRIVATE);
        String userName = sp.getString("user", null);
        File dir = new File(Environment.getExternalStorageDirectory() + "/SDAnQuanJianCha");
        File dir1 = new File(Environment.getExternalStorageDirectory() + "/SDAnQuanJianCha/image");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (!dir1.exists()) {
            dir1.mkdirs();
        }
        /**根据 hadUpdate标志进行判断是否已经进行过更新*/
        File[] files = dir.listFiles(pathname -> pathname.getName().contains(userName)
                && pathname.getName().contains(".txt") && !pathname.getName().contains(".pdf")
                && !pathname.getName().contains("hadUpdate"));
        Realm realm = Realm.getDefaultInstance();
        int startIndex = dbHelper.getLastOrder() + 1;
        for (int i = startIndex; i < files.length + startIndex; i++) {
            String json = Utils.readTxt(files[i - startIndex]);
            JsonRecord jsonRecord = new Gson().fromJson(json, JsonRecord.class);
            realm.beginTransaction();
            HistoryCheckedBean checkedBean = realm.createObject(HistoryCheckedBean.class, i);
            checkedBean.setUser(jsonRecord.getInspector());
            checkedBean.setDate(jsonRecord.getDate());
            checkedBean.setDept(jsonRecord.getDept());
            checkedBean.setRoom(jsonRecord.getRoom());
            checkedBean.setPrincipal(jsonRecord.getPrincipal());
            checkedBean.setSavad(true);
            realm.commitTransaction();
            for (JsonItem item : jsonRecord.getItemList()) {
                realm.beginTransaction();
                HistoryItemBean itemBean = realm.createObject(HistoryItemBean.class);
                itemBean.setSerialNum(String.valueOf(item.getG() + 1) + "." +
                        String.valueOf(item.getP() + 1) + "." + String.valueOf(item.getI() + 1));
                itemBean.setOrder(i - startIndex);
                itemBean.setCheckBox(item.getCb());
                itemBean.setNote(item.getNote());
                RealmList<PathRealm> realms = new RealmList<>();
                /**将base64转储至sD/IMAGE*/
                PathRealm phead = realm.createObject(PathRealm.class);
                phead.setPath("miko");
                realms.add(phead);
                for (String s : item.getBase64Pic()) {
                    PathRealm p = realm.createObject(PathRealm.class);
                    int ran = 1 + new Random().nextInt(1000000);
                    Utils.StringToFile(s, String.valueOf(ran) + ".jpg", Environment.getExternalStorageDirectory() +
                            "/SDAnQuanJianCha/image");
                    p.setPath(Environment.getExternalStorageDirectory() +
                            "/SDAnQuanJianCha/image/" + String.valueOf(ran) + ".jpg");
                    realms.add(p);
                }
                itemBean.setPathList(realms);
                realm.commitTransaction();
            }
            String fileName = files[i - startIndex].getAbsolutePath();
            String newFileName = fileName.split("\\.")[0] + "_hadUpdate." + fileName.split("\\.")[1];
            files[i - startIndex].renameTo(new File(newFileName));
        }
        realm.close();
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }
    }
}
