package com.miko.zd.anquanjianchanative;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.miko.zd.anquanjianchanative.Adapter.RcycAdapterExpendDetail;
import com.miko.zd.anquanjianchanative.Adapter.RcycAdapterHistory;
import com.miko.zd.anquanjianchanative.Adapter.RcycAdapterPreviewDetail;
import com.miko.zd.anquanjianchanative.Adapter.RcycAdapterUnsaveDetail;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.HistoryCheckedBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.HistoryItemBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean1;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean2;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean3;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.PathRealm;
import com.miko.zd.anquanjianchanative.Utils.BitmapUtils;
import com.miko.zd.anquanjianchanative.Utils.PdfUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class PreviewReportActivity extends AppCompatActivity {

    public static final int TYPE_UNSAVE = 0;
    public static final int TYPE_PREVIEW = 1;
    public static final int TYPE_HISTORY = 2;

    private static int FTITLE_SIZE = 18;
    private static int TTITLE_SIZE = 8;
    private static int STITLE_SIZE = 9;

    private ProgressDialog dialog;

    private RealmList<HistoryItemBean> historyItems;
    private RcycAdapterPreviewDetail adapter;
    private RecyclerView recyclerView;
    private TextView tvRoom, tvDept, tvPrincipal, tvSave;
    String dept, room, principal, user, date;
    int order;
    RcycAdapterHistory adapterHistory;

    private int currGrad, currParent, currSon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_report);
        SharedPreferences sp = getSharedPreferences("TempUser", Context.MODE_PRIVATE);
        user = sp.getString("user", null);
        date = sp.getString("date", null);
        sp = getSharedPreferences("TempCheckInfo", Context.MODE_PRIVATE);
        order = sp.getInt("order", -3);
        dept = sp.getString("dept", null);
        principal = sp.getString("principal", null);
        room = sp.getString("room", null);
        if (getIntent().getIntExtra("type", -1) == TYPE_PREVIEW) {
            initViewPreView();
        }
        else if(getIntent().getIntExtra("type", -1) == TYPE_HISTORY){
            initViewUnSave();
            tvSave.setVisibility(View.GONE);
        }
        else if(getIntent().getIntExtra("type", -1) == TYPE_UNSAVE){
            initViewUnSave();
            tvSave.setText("继续编辑");
            tvSave.setOnClickListener(v -> startActivity(new Intent(this,CheckItemActivity.class)));
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 1:
                    dialog.show();
                    break;
                case 2:
                    dialog.dismiss();
                    Toast.makeText(PreviewReportActivity.this, "保存成功\n已生成Xml,Pdf文件\n保存于SD卡根目录SDAnQuanJianCha文件夹下", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
    private void initViewPreView() {
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("正在生成本地文件,请稍候");
        recyclerView = (RecyclerView) findViewById(R.id.id_rcyc_end);

        tvDept = (TextView) findViewById(R.id.id_tv_dept);
        tvDept.setText("院系：" + dept);

        tvPrincipal = (TextView) findViewById(R.id.id_tv_principal);
        tvPrincipal.setText("负责人：" + principal);

        tvRoom = (TextView) findViewById(R.id.id_tv_room);
        tvRoom.setText("实验室：" + room);

        tvSave = (TextView) findViewById(R.id.id_tv_save);
        MyHandler handler = new MyHandler();
        RealmResults<HistoryItemBean> hiss = Realm.getDefaultInstance().where(HistoryItemBean.class)
                .equalTo("order", order).findAll();
        historyItems = new RealmList<>();
        for (HistoryItemBean h : hiss) {
            if ((h.getPathList().size() > 1) || !h.getNote().equals("") || h.getCheckBox() != -1)
                historyItems.add(h);
        }
        tvSave.setOnClickListener(v -> {
            for (int i = 0; i < historyItems.size(); i++) {
                if (historyItems.get(i).getCheckBox() == -1) {
                    Toast.makeText(PreviewReportActivity.this, "存在一个或多个条目未选择层级\n您必须为每一个条目选择一个检查结果", Toast.LENGTH_LONG).show();
                    recyclerView.smoothScrollToPosition(i);
                    return;
                }
            }
            new AlertDialog.Builder(v.getContext()).setPositiveButton("确认", (dialog, which) -> new Thread(() -> {
                Message msg = new Message();
                msg.arg1 = 1;
                handler.sendMessage(msg);
                Document pdfDoc = new Document(PageSize.A4, 36, 36, 36, 36);

                FileOutputStream pdfFile = null;
                try {
                    pdfFile = new FileOutputStream(new File(Environment.getExternalStorageDirectory() +
                            "/SDAnQuanJianCha/" + user + "_"
                            + date + "_" + dept + "_" + room + ".pdf"));
                    PdfWriter.getInstance(pdfDoc, pdfFile);
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }
                pdfDoc.open(); // 打开 Document 文档

                try {
                    Paragraph p1 = PdfUtils.getBParagraph(FTITLE_SIZE, "\n\n山东大学实验室检查报告\n\n");
                    p1.setAlignment(Element.ALIGN_CENTER);
                    pdfDoc.add(p1);
                    pdfDoc.add(PdfUtils.getBParagraph(STITLE_SIZE, "院系：" + dept));
                    pdfDoc.add(PdfUtils.getBParagraph(STITLE_SIZE, "实验室：" + room));
                    pdfDoc.add(PdfUtils.getBParagraph(STITLE_SIZE, "负责人：" + principal));
                    pdfDoc.add(PdfUtils.getBParagraph(STITLE_SIZE, "检查人：" + user));
                    pdfDoc.add(PdfUtils.getBParagraph(STITLE_SIZE, "检查时间：" + date + "\n"));

                } catch (IOException | DocumentException e) {
                    e.printStackTrace();
                }
                RealmResults<HistoryItemBean> hiss1 = Realm.getDefaultInstance().where(HistoryItemBean.class)
                        .equalTo("order", order).findAll();
                RealmList<HistoryItemBean> historyItems2 = new RealmList<>();
                for (HistoryItemBean h : hiss1) {
                    if ((h.getPathList().size() > 1) || !h.getNote().equals("") || h.getCheckBox() != -1)
                        historyItems2.add(h);
                }
                for (HistoryItemBean his : historyItems2) {
                    String[] splits = his.getSerialNum().split("\\.");
                    if (currGrad != Integer.parseInt(splits[0])) {
                        currGrad = Integer.parseInt(splits[0]);
                        try {
                            ItemBean1 itemBean1 = Realm.getDefaultInstance().where(ItemBean1.class)
                                    .equalTo("serialNum", splits[0]).findFirst();
                            pdfDoc.add(PdfUtils.getBParagraph(TTITLE_SIZE, "\n" + itemBean1.getSerialNum()
                                    + "   " + itemBean1.getItemName()));
                        } catch (DocumentException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (currParent != Integer.parseInt(splits[1])) {
                        currParent = Integer.parseInt(splits[1]);
                        try {
                            ItemBean2 itemBean2 = Realm.getDefaultInstance().where(ItemBean2.class)
                                    .equalTo("serialNum", splits[0] + "." + splits[1]).findFirst();
                            pdfDoc.add(PdfUtils.getBParagraph(TTITLE_SIZE, itemBean2.getSerialNum()
                                    + "   " + itemBean2.getItemName()));
                        } catch (DocumentException | IOException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        ItemBean3 itemBean3 = Realm.getDefaultInstance().where(ItemBean3.class)
                                .equalTo("serialNum", splits[0] + "." + splits[1] + "." + splits[2]).findFirst();

                        pdfDoc.add(PdfUtils.getBParagraph(TTITLE_SIZE, itemBean3.getSerialNum()
                                + "   " + itemBean3.getItemName()));
                        String cbString;
                        int cb = his.getCheckBox();
                        if (cb == 1) {
                            cbString = "符合";
                        } else if (cb == 2) {
                            cbString = "不符合";
                        } else {
                            cbString = "不适合";
                        }
                        pdfDoc.add(PdfUtils.getParagraph(TTITLE_SIZE, "检查结果："
                                + cbString));

                        pdfDoc.add(PdfUtils.getParagraph(TTITLE_SIZE, "详细信息："
                                + his.getNote()));
                        for (PathRealm path : his.getPathList()) {
                            if (path.getPath().equals("miko")) {
                                continue;
                            }
                            int ran = 1+new Random().nextInt(1000000);

                            BitmapUtils.compressPicture(path.getPath(),Environment.getExternalStorageDirectory() +
                                    "/SDAnQuanJianCha/image/"+String.valueOf(ran)+".jpg");
                            Image image = null;
                            try {
                                image = Image.getInstance(Environment.getExternalStorageDirectory() +
                                        "/SDAnQuanJianCha/image/"+String.valueOf(ran)+".jpg");
                            } catch (BadElementException | IOException e) {
                                e.printStackTrace();
                            }
                            if (image == null) {
                                continue;
                            }
                            image.scaleToFit(400, 400);
                            pdfDoc.add(image);
                        }
                        pdfDoc.add(PdfUtils.getParagraph(10, "  \n"));

                    } catch (DocumentException | IOException e) {
                        e.printStackTrace();
                    }
                }
                pdfDoc.close();
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(realm1 -> {
                    HistoryCheckedBean history = realm1.where(HistoryCheckedBean.class)
                            .equalTo("order", order).findFirst();
                    history.setSavad(true);
                });
                Message msg2 = new Message();
                msg2.arg1 = 2;
                handler.sendMessage(msg2);
                startActivity(new Intent(this, HistoryActivity.class));
            }).start()).setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).setMessage("保存之后无法更改，确认保存？").create().show();
        });
        adapter = new RcycAdapterPreviewDetail(historyItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initViewUnSave() {
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        recyclerView = (RecyclerView) findViewById(R.id.id_rcyc_end);

        tvDept = (TextView) findViewById(R.id.id_tv_dept);
        tvDept.setText("院系：" + dept);

        tvPrincipal = (TextView) findViewById(R.id.id_tv_principal);
        tvPrincipal.setText("负责人：" + principal);

        tvRoom = (TextView) findViewById(R.id.id_tv_room);
        tvRoom.setText("实验室：" + room);

        tvSave = (TextView) findViewById(R.id.id_tv_save);
        RealmResults<HistoryItemBean> hiss = Realm.getDefaultInstance().where(HistoryItemBean.class)
                .equalTo("order", order).findAll();
        historyItems = new RealmList<>();
        for (HistoryItemBean h : hiss) {
            if ((h.getPathList().size() > 1) || !h.getNote().equals("") || h.getCheckBox() != -1)
                historyItems.add(h);
        }
        RcycAdapterUnsaveDetail adapterUnsaveDetail = new RcycAdapterUnsaveDetail(historyItems, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterUnsaveDetail);
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
                    adapter.adapters[data.getIntExtra("position", -1)]
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
                            + "/" + "localTempImgDir" + "/" + RcycAdapterPreviewDetail.curPhotoName + ".jpg");
                    pathList2.add(1,path2);
                    realm2.commitTransaction();
                    adapter.adapters[RcycAdapterPreviewDetail.currPostion].notifyItemInserted(1);
                    break;
                case 100:
                    if (data.getStringArrayListExtra("path") != null) {
                        Realm realm1 = Realm.getDefaultInstance();
                        RealmList<PathRealm> pathList1 = realm1.where(HistoryItemBean.class)
                                .equalTo("serialNum", data.getStringExtra("serialNum"))
                                .equalTo("order", order).findFirst().getPathList();
                        for (String s : data.getStringArrayListExtra("path")) {
                            realm1.executeTransaction(realm21 -> {
                                PathRealm p = realm1.createObject(PathRealm.class);
                                p.setPath(s);
                                pathList1.add(1, p);
                            });
                        }
                        adapter.adapters[data.getIntExtra("position", -1)]
                                .notifyItemRangeInserted(1, data.getStringArrayListExtra("path").size());
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
