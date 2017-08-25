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

import com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater.RcycAdapterGridReportHistory;
import com.miko.zd.anquanjianchanative.Bean.HistoryItem;
import com.miko.zd.anquanjianchanative.MainActivity;
import com.miko.zd.anquanjianchanative.R;

import java.util.ArrayList;

import static com.miko.zd.anquanjianchanative.MainActivity.itemTree;

public class RcycAdapterHistory extends RecyclerView.Adapter<MyViewHolderExpendItem> {
    private ArrayList<HistoryItem> mData;
    public RcycAdapterGridReportHistory[] adapter;

    /**
     * 相机无法getIntent
     */
    public static int curGrad, curParent, curPosition;
    public static String curPhotoName;

    Context context;

    public RcycAdapterHistory(ArrayList<HistoryItem> mData, Context context) {
        this.mData = mData;
        this.context = context;
        adapter = new RcycAdapterGridReportHistory[mData.size()];
    }

    @Override
    public MyViewHolderExpendItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolderExpendItem(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rcyc_edit_detail_check, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolderExpendItem holder, int pos) {
        int gradIndex = mData.get(pos).getG();
        int parent = mData.get(pos).getP();
        int position = mData.get(pos).getI();
        if(itemTree == null){
            MainActivity.reLoadTree(context);
        }
        holder.tvItemName.setText(String.valueOf(gradIndex + 1) + "." + String.valueOf(parent + 1)
                + "." + String.valueOf(position + 1) + " " + itemTree.get(gradIndex).getItemList()
                .get(parent).getItemList().get(position).getItemName());

        holder.edNote.setFocusable(false);
        holder.edNote.setClickable(false);
        if (!mData.get(pos).getNote().equals("")) {
            holder.edNote.setText(mData.get(pos).getNote());
        }

        if (mData.get(pos).getCb() != -1) {
            if (mData.get(pos).getCb() == 1) {
                holder.cb1.setChecked(true);
            } else if (mData.get(pos).getCb() == 2) {
                holder.cb2.setChecked(true);
            } else if (mData.get(pos).getCb() == 3) {
                holder.cb3.setChecked(true);
            }
        }

        holder.cb1.setClickable(false);
        holder.cb1.setFocusable(false);

        holder.cb2.setClickable(false);
        holder.cb2.setFocusable(false);

        holder.cb3.setClickable(false);
        holder.cb3.setFocusable(false);


        adapter[position] = new RcycAdapterGridReportHistory(mData.get(pos).getBtPic(), context);
       /* adapter[position].setOnItemClickListener(new RcycAdapterGridReportHistory.OnItemClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(context, ViewPicPreview.class);
                intent.putExtra("TYPE",ViewPicPreview.CLICK_PREVIEW_HISTORY);
                intent.putExtra("bitmap",mData.get(pos).getBtPic());
                context.startActivity(intent);
            }
        });*/
        holder.recyclerView.setAdapter(adapter[position]);
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
