package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/10/18.
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.miko.zd.anquanjianchanative.Bean.RealmBean.HistoryItemBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean3;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.PathRealm;
import com.miko.zd.anquanjianchanative.R;

import java.util.AbstractList;

import io.realm.Realm;
import io.realm.RealmList;

public class RcycAdapterUnsaveDetail extends RecyclerView.Adapter<MyViewHolderExpendItem> {
    private int order;
    private Context context;
    public RcycAdapterGridReport[] adapters;
    /**
     * 相机无法getIntent
     */
    public static String serialNum;
    public static String curPhotoName;
    public static int currPostion;
    private AbstractList<HistoryItemBean> historyBeans;

    public RcycAdapterUnsaveDetail(AbstractList<HistoryItemBean> historyBeans, Context context) {
        this.context = context;
        this.historyBeans = historyBeans;
        adapters = new RcycAdapterGridReport[historyBeans.size()];
        this.order = context.getSharedPreferences("TempCheckInfo", Context.MODE_PRIVATE).getInt("order", -1);
    }

    @Override
    public MyViewHolderExpendItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolderExpendItem(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rcyc_edit_detail_check, parent, false));
    }


    @Override
    public void onBindViewHolder(MyViewHolderExpendItem holder, int position) {
        Realm realm = Realm.getDefaultInstance();
        ItemBean3 itemBean3 = realm.where(ItemBean3.class)
                .equalTo("serialNum", historyBeans.get(position).getSerialNum()).findFirst();
        holder.edNote.setClickable(false);
        holder.edNote.setFocusable(false);
        holder.tvItemName.setText(itemBean3.getSerialNum()
                + "   " + itemBean3.getItemName());
        if (!historyBeans.get(position).getNote().equals("")) {
            holder.edNote.setText(historyBeans.get(position).getNote());
        }
        holder.cb1.setClickable(false);
        holder.cb1.setFocusable(false);

        holder.cb2.setClickable(false);
        holder.cb2.setFocusable(false);
        holder.cb3.setClickable(false);
        holder.cb3.setFocusable(false);
        if (historyBeans.get(position).getCheckBox() != -1) {
            if (historyBeans.get(position).getCheckBox() == 1) {
                holder.cb1.setChecked(true);
            } else if (historyBeans.get(position).getCheckBox() == 2) {
                holder.cb2.setChecked(true);
            } else if (historyBeans.get(position).getCheckBox() == 3) {
                holder.cb3.setChecked(true);
            }
        }
        RealmList<PathRealm> pathList = historyBeans.get(position).getPathList();

        adapters[position] = new RcycAdapterGridReport(pathList, holder.recyclerView, context);
        adapters[position].setOnItemClickListener(new RcycAdapterGridReport.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
            }

            @Override
            public void onFooterClick() {
            }
        });
        holder.recyclerView.setAdapter(adapters[position]);
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
        return historyBeans.size();
    }

    public interface OnItemClickListener {
        void onClick(int gp, int p, int pos);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
