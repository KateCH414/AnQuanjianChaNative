package com.miko.zd.anquanjianchanative.Adapter;
/*
 * Created by zd on 2016/10/18.
 */

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean2;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.OpenedItemBean;
import com.miko.zd.anquanjianchanative.R;

import io.realm.Realm;
import io.realm.RealmResults;

public class RcycAdapterDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_OPNE = 0;
    private final static int TYPE_CLOSE = 1;
    private int order;
    public RcycAdapterExpendDetail adapter;
    private Context context;
    private RealmResults<ItemBean2> parentItemList;

    public RcycAdapterDetail(RealmResults<ItemBean2> parentItemBeans, Context context) {
        this.context = context;
        this.parentItemList = parentItemBeans;
        this.order = context.getSharedPreferences("TempCheckInfo", Context.MODE_PRIVATE).getInt("order", -1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_CLOSE)
            return new MyViewHolderDetail(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rcyc_detail_checkitem, parent, false));
        else
            return new MyViewHolderExpendDetail(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_rcyc_detail_expend_checkitem, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return isOpen(position) ? TYPE_OPNE : TYPE_CLOSE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolderDetail) {
            ((MyViewHolderDetail) holder).ivExpendDown.setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_expanddown));
            ((MyViewHolderDetail) holder).tvItemName.setText(parentItemList.get(position).getSerialNum()
                    + "   " + parentItemList.get(position).getItemName());
            holder.itemView.setOnClickListener(v -> {
                Realm realm = Realm.getDefaultInstance();
                OpenedItemBean isOpenedBean = realm.where(OpenedItemBean.class).equalTo("order", order).
                        equalTo("serialNum", parentItemList.get(position)
                                .getSerialNum()).findFirst();
                /**为空则当前状态为关闭*/
                if (isOpenedBean == null) {
                    ((MyViewHolderDetail) holder).ivExpendDown.setImageBitmap(BitmapFactory.
                            decodeResource(context.getResources(), R.mipmap.ic_closeup));
                    realm.executeTransaction(realm1 -> {
                        OpenedItemBean itemBean = realm1.createObject(OpenedItemBean.class);
                        itemBean.setOpen(true);
                        itemBean.setOrder(order);
                        itemBean.setSerialNum(parentItemList.get(position).getSerialNum());
                    });
                    /**当前状态为开启*/
                } else if (isOpenedBean.isOpen()) {
                    ((MyViewHolderDetail) holder).ivExpendDown.setImageBitmap(BitmapFactory
                            .decodeResource(context.getResources(), R.mipmap.ic_expanddown));
                    realm.executeTransaction(realm1 -> isOpenedBean.setOpen(false));
                } else if(!isOpenedBean.isOpen()){
                    ((MyViewHolderDetail) holder).ivExpendDown.setImageBitmap(BitmapFactory.
                            decodeResource(context.getResources(), R.mipmap.ic_closeup));
                    realm.executeTransaction(realm1 -> isOpenedBean.setOpen(true));
                }
                notifyItemChanged(position);
            });
        } else {
            ((MyViewHolderExpendDetail) holder).tvItemName.setText(parentItemList.get(position).getSerialNum()
                    + "   " + parentItemList.get(position).getItemName());
            ((MyViewHolderExpendDetail) holder).cardView.setOnClickListener(v -> {
                Realm realm = Realm.getDefaultInstance();
                OpenedItemBean isOpenedBean = realm.where(OpenedItemBean.class).equalTo("order", order).
                        equalTo("serialNum", parentItemList.get(position)
                                .getSerialNum()).findFirst();
                realm.executeTransaction(realm12 -> isOpenedBean.setOpen(false));
                notifyItemChanged(position);
            });
            adapter = new RcycAdapterExpendDetail(parentItemList.get(position), context);
            ((MyViewHolderExpendDetail) holder).recyclerView.setAdapter(adapter);
            ((MyViewHolderExpendDetail) holder).ivUpClose.setImageBitmap(BitmapFactory.decodeResource(holder.itemView.getResources(), R.mipmap.ic_closeup));
            ((MyViewHolderExpendDetail) holder).recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    @Override
    public int getItemCount() {
        return parentItemList.size();
    }

    public interface OnItemClickListener {
        void onClick(int pos);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private boolean isOpen(int pos) {
        Realm realm = Realm.getDefaultInstance();
        OpenedItemBean isOpenedBean = realm.where(OpenedItemBean.class).equalTo("order", order).
                equalTo("serialNum", parentItemList.get(pos).getSerialNum()).findFirst();
        if (isOpenedBean != null && isOpenedBean.isOpen()) {
            return true;
        } else
            return false;
    }

    private void changeOpened(int pos, boolean open) {
        Realm realm = Realm.getDefaultInstance();
        OpenedItemBean isOpenedBean = realm.where(OpenedItemBean.class).equalTo("order", order).
                equalTo("serialNum", parentItemList.get(pos).getSerialNum()).findFirst();
        if (isOpenedBean == null) {
            realm.executeTransaction(realm1 -> {
                OpenedItemBean o = realm1.createObject(OpenedItemBean.class);
                o.setOrder(order);
                o.setSerialNum(parentItemList.get(pos).getSerialNum());
                o.setOpen(open);
            });
        } else {
            isOpenedBean.setOpen(open);
        }
    }
}
