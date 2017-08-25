package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/10/18.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.miko.zd.anquanjianchanative.Bean.RecordItemBean;
import com.miko.zd.anquanjianchanative.R;
import com.miko.zd.anquanjianchanative.Selector.ViewPicPreview;
import com.miko.zd.anquanjianchanative.Selector.ViewPicSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import static com.miko.zd.anquanjianchanative.MainActivity.itemTree;

public class RcycAdapterEnd extends RecyclerView.Adapter<MyViewHolderExpendItem> {
    private ArrayList<RecordItemBean> mData;
    public RcycAdapterGridReport[] adapter;

    /**
     * 相机无法getIntent
     */
    public static int curGrad, curParent, curPosition;
    public static String curPhotoName;

    Context context;

    public RcycAdapterEnd(ArrayList<RecordItemBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
        adapter = new RcycAdapterGridReport[mData.size()];
    }

    @Override
    public MyViewHolderExpendItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolderExpendItem(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rcyc_edit_detail_check, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolderExpendItem holder, int pos) {
        int gradIndex = mData.get(pos).getGrad();
        int parent = mData.get(pos).getParent();
        int position = mData.get(pos).getIndex();
        holder.tvItemName.setText(String.valueOf(gradIndex + 1) + "." + String.valueOf(parent + 1)
                + "." + String.valueOf(position + 1) + " " + itemTree.get(gradIndex).getItemList()
                .get(parent).getItemList().get(position).getItemName());
        holder.edNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                        .get(position).setNote(s.toString());
            }
        });
        if (!itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                .get(position).getNote().equals("")) {
            holder.edNote.setText(itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                    .get(position).getNote());
        }
        holder.cb1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                        .get(position).setCb(1);
                holder.cb2.setChecked(false);
                holder.cb3.setChecked(false);
            } else {
                itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                        .get(position).setCb(-1);
            }
        });
        holder.cb2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                        .get(position).setCb(2);
                holder.cb1.setChecked(false);
                holder.cb3.setChecked(false);
            } else {
                itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                        .get(position).setCb(-1);
            }
        });

        holder.cb3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                        .get(position).setCb(3);
                holder.cb1.setChecked(false);
                holder.cb2.setChecked(false);
            } else {
                itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                        .get(position).setCb(-1);
            }
        });

        if (itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                .get(position).getCb() != -1) {
            if (itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                    .get(position).getCb() == 1) {
                holder.cb1.setChecked(true);
            } else if (itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                    .get(position).getCb() == 2) {
                holder.cb2.setChecked(true);
            } else if (itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                    .get(position).getCb() == 3) {
                holder.cb3.setChecked(true);
            }
        }

        /*adapter[pos] = new RcycAdapterGridReport(itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                .get(position).getPaths(), holder.recyclerView, context);*/
        adapter[pos].setOnItemClickListener(new RcycAdapterGridReport.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(context, ViewPicPreview.class);
                ArrayList<String> list = new ArrayList<>();
                list.add(itemTree.get(gradIndex).getItemList().get(parent).getItemList()
                        .get(position).getPaths().get(pos));
                intent.putExtra("grad", gradIndex);
                intent.putExtra("parent", parent);
                intent.putExtra("position", position);
                intent.putExtra("path", list);
                intent.putExtra("index", pos);
                intent.putExtra("TYPE", ViewPicPreview.DELETE_PREVIEW);
                ((Activity) context).startActivityForResult(intent, 400);
            }

            @Override
            public void onFooterClick() {
                String[] select = {"从图库选择", "拍一张"};
                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setItems(select, (dialog1, which) -> {
                            if (which == 0) {
                                Intent intent = new Intent(context, ViewPicSelector.class);
                                intent.putExtra("grad", gradIndex);
                                intent.putExtra("parent", parent);
                                intent.putExtra("position", position);

                                ((Activity) context).startActivityForResult(intent, 100);
                                dialog1.cancel();
                            } else {
                                File dir = new File(Environment.getExternalStorageDirectory() + "/" + "localTempImgDir");
                                if (!dir.exists()) dir.mkdirs();
                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                curPhotoName = String.valueOf(new Random().nextInt(1000000));
                                File f = new File(dir, curPhotoName + ".jpg");//localTempImgDir和localTempImageFileName是自己定义的名字
                                Uri u = Uri.fromFile(f);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                                curGrad = gradIndex;
                                curParent = parent;
                                curPosition = position;
                                ((Activity) context).startActivityForResult(intent, 200);
                                dialog1.cancel();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        holder.recyclerView.setAdapter(adapter[pos]);
        WrapContentLinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(linearLayoutManager);


    }


    public class WrapContentLinearLayoutManager extends LinearLayoutManager {
        public WrapContentLinearLayoutManager(Context context) {
            super(context);
        }

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
