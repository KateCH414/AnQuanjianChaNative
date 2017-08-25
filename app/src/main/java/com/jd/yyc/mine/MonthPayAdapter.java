package com.jd.yyc.mine;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SkuNum;
import com.jd.yyc.api.model.YaoOrder;
import com.jd.yyc.api.model.YaoOrderSku;
import com.jd.yyc.cartView.CartViewActivity;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.mine.adapter.CheckPendingImageAdapter;
import com.jd.yyc.mine.adapter.MonthPayImageAdapter;
import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;
import com.jd.yyc.refreshfragment.LoadingDialogUtil;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.ui.activity.web.YYCWebActivity;
import com.jd.yyc.ui.util.DialogUtil;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ResourceUtil;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.UrlUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by jiahongbin on 2017/5/30.
 */

public class MonthPayAdapter extends RecyclerAdapter<YaoOrder, YYCViewHolder> implements Contants {

    private MineGoodsAdapter.EmptyCallback emptyCallback;

    public MonthPayAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new MonthPayViewHolder(View.inflate(mContext, R.layout.mine_monthpay,null));
    }

    public void setEmptyCallback(MineGoodsAdapter.EmptyCallback emptyCallback) {
        this.emptyCallback = emptyCallback;
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {

        holder.onBind(position, getItem(position));
    }

    private void buyAgain(final List<YaoOrderSku> list) {
        if (CheckTool.isEmpty(list))
            return;
        List<SkuNum> skuNumList = new ArrayList<>();
        final List<YaoOrderSku> noYaoOrderSku = new ArrayList<>(); //无货的YaoOrderSku
        final List<YaoOrderSku> yaoOrderSkuList = new ArrayList<>(); //有货的YaoOrderSku
        for (YaoOrderSku yaoOrderSku : list) {
            SkuNum skuNum = new SkuNum(yaoOrderSku.getSkuId(), yaoOrderSku.getSkuNum());
            skuNumList.add(skuNum);
        }

        SkuNum.canAddCart(mContext, skuNumList, new SkuNum.SkuNumCallback() {
            @Override
            public void requestCallback(List<SkuNum> sList) {
                if (CheckTool.isEmpty(sList))
                    return;

                for (int i = 0; i < sList.size(); i++) {
                    for (int j = 0; j < list.size(); j++) {
                        if (sList.get(i).getSkuId().equals(list.get(j).getSkuId())) {
                            if (sList.get(i).isResult()) {
                                yaoOrderSkuList.add(list.get(j));
                            } else {
                                noYaoOrderSku.add(list.get(j));
                            }
                        }
                    }
                }
                List<SkuNum> skuList = new ArrayList<>();
                for (YaoOrderSku yaoOrderSku : yaoOrderSkuList) {
                    SkuNum skuNum = new SkuNum(yaoOrderSku.getSkuId(), yaoOrderSku.getSkuNum());
                    skuList.add(skuNum);
                }

                if (noYaoOrderSku.size() == 0) {
                    SkuNum.AddCartList(mContext, skuList, new SkuNum.SkuNumCallback() {
                        @Override
                        public void requestCallback(List<SkuNum> ssList) {
                            if (!CheckTool.isEmpty(ssList)) {
                                CartViewActivity.launch(mContext);
                            }
                        }

                        @Override
                        public void onFailed(String msg) {
                            ToastUtil.show("暂时无法购买");
                        }
                    });
                } else {
                    showSkuNoAddDialog(noYaoOrderSku, skuList);
                }


            }

            @Override
            public void onFailed(String msg) {
                ToastUtil.show("暂时无法购买");
            }
        });
    }

    // 商品无货时，不能添加购物车时弹出dialog
    private void showSkuNoAddDialog(List<YaoOrderSku> noList, final List<SkuNum> list) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_no_sku_add_cart, null);
        ImageView closeIcon = (ImageView) view.findViewById(R.id.close_icon);
        TextView title = (TextView) view.findViewById(R.id.title);
        final TextView desc = (TextView) view.findViewById(R.id.desc);
        View bottomLine = view.findViewById(R.id.btn_line);
        TextView cancelBtn = (TextView) view.findViewById(R.id.cancel_btn);
        TextView confirmBtn = (TextView) view.findViewById(R.id.confirm_btn);
        RecyclerView noAddSkuList = (RecyclerView) view.findViewById(R.id.no_add_sku_list);

        final Dialog dialog = DialogUtil.buildDialog((Activity) mContext, view, Gravity.BOTTOM, R.anim.appear_from_bottom, true);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        noAddSkuList.setLayoutManager(manager);
        CheckPendingImageAdapter imageAdapter = new CheckPendingImageAdapter(mContext);
        noAddSkuList.setAdapter(imageAdapter);
        imageAdapter.setData(noList);
        title.setText(CheckTool.isEmpty(list) ? "以下商品全部无货" : "以下商品已经卖光了");
        desc.setVisibility(CheckTool.isEmpty(list) ? View.GONE : View.VISIBLE);
        desc.setText("先将其他有货商品加入购物车？");
        cancelBtn.setText(CheckTool.isEmpty(list) ? "返回" : "我再想想");
        bottomLine.setVisibility(CheckTool.isEmpty(list) ? View.GONE : View.VISIBLE);
        confirmBtn.setVisibility(CheckTool.isEmpty(list) ? View.GONE : View.VISIBLE);
        confirmBtn.setText("加购物车");
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                SkuNum.AddCartList(mContext, list, new SkuNum.SkuNumCallback() {
                    @Override
                    public void requestCallback(List<SkuNum> ssList) {
                        if (!CheckTool.isEmpty(ssList)) {
                            CartViewActivity.launch(mContext);
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtil.show("暂时无法购买");
                    }
                });
            }
        });

        dialog.show();
    }


    public class MonthPayViewHolder extends YYCViewHolder<YaoOrder> {
        TextView tv_ckeckPending_shopName;
        TextView tv_checkPeding_time;
        TextView tv_checkPending_realPay;
        Button bt_checkPending_buyAgain;
        RecyclerView rv_CheckPending_Image;
        MonthPayImageAdapter monthPayImageAdapter;

        RelativeLayout item_shop;
        public MonthPayViewHolder(View item){
            super(item);
            ButterKnife.inject(this, itemView);
            tv_ckeckPending_shopName = (TextView) item.findViewById(R.id.tv_ckeckPending_shopName);
            tv_checkPeding_time=(TextView) item.findViewById(R.id.tv_checkPeding_time);
            rv_CheckPending_Image = (RecyclerView) item.findViewById(R.id.rv_CheckPending_Image);
            tv_checkPending_realPay=(TextView) item.findViewById(R.id.tv_checkPending_realPay);
            bt_checkPending_buyAgain=(Button) item.findViewById(R.id.bt_checkPending_buyAgain);
            item_shop = (RelativeLayout) item.findViewById(R.id.item_shop);
            monthPayImageAdapter=new MonthPayImageAdapter(mContext);
            rv_CheckPending_Image.setAdapter(monthPayImageAdapter);
            //   rv_CheckPending_Image.getRecyclerView().addItemDecoration(new checkPendingItemDecoration(mContext));
        }
        public void onBind(final int position, final YaoOrder data) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            // sd.setTimeZone(TimeZone.getTimeZone("GMT+8"));//**TimeZone时区，加上这句话就解决啦**
            // String time = sd.format(new Date(dt));//ps.将毫秒转化为时分秒格式

            // return sd.format(new java.sql.Timestamp(date));
            tv_ckeckPending_shopName.setText(data.getVenderName());
            tv_checkPeding_time.setText(sd.format(new java.sql.Timestamp(data.getPurchaseTime())));
            tv_checkPending_realPay.setText(ResourceUtil.getString(R.string.sku_price, data.getAmountPay()));
            LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv_CheckPending_Image.setLayoutManager(manager);
            monthPayImageAdapter.getList().clear();
            monthPayImageAdapter.setData(data.getYaoOrderSkus());
            monthPayImageAdapter.notifyDataSetChanged();
            bt_checkPending_buyAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // buyAgain(data.getYaoOrderSkus());
                    if (data.isShowPersonOrCompanyPay()) {
                        LoadingDialogUtil.show(mContext);
                        UrlUtil.getLoginStatusUrl(UrlUtil.getOrderPayUrl(data.getPurchaseId(), data.getAmountPay()), new UrlUtil.LoginStatusCallback() {
                            @Override
                            public void onSuccess(String url) {
                                LoadingDialogUtil.close();
                                YYCWebActivity.launch(mContext, url, "");
                            }

                            @Override
                            public void onFailed(String msg) {
                                LoadingDialogUtil.close();
                                ToastUtil.show("暂时无法支付");
                            }
                        });
                    } else if (data.isShowMonthPay()) {
                        DialogUtil.showDialog(mContext, "额度付款", "确认支付吗？", "确定", "取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NetLoading.getInstance().orderConfirm(mContext, data.getPurchaseId(), new RequestCallback<ResultObject<Boolean>>() {
                                    @Override
                                    public void requestCallBack(boolean success, ResultObject<Boolean> result, String err) {
                                        if (success && result.success && result.data != null && result.data) {
                                            ToastUtil.show("付款成功");
                                            getList().remove(position);
                                            if (getList().size() == 0 && emptyCallback != null)
                                                emptyCallback.showEmpty();
                                            notifyDataSetChanged();
                                        } else {
                                            ToastUtil.show("付款失败");
                                        }
                                    }
                                });
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                    }


                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoadingDialogUtil.show(mContext);
                    UrlUtil.getLoginStatusUrl(UrlUtil.getOrderDetailUrl(data.getPurchaseId()), new UrlUtil.LoginStatusCallback() {
                        @Override
                        public void onSuccess(String url) {
                            LoadingDialogUtil.close();
                            YYCWebActivity.launch(mContext, url, "");
                        }

                        @Override
                        public void onFailed(String msg) {
                            LoadingDialogUtil.close();
                            ToastUtil.show("暂时无法查看采购单详情");
                        }
                    });
                }
            });
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }

    class checkPendingItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public checkPendingItemDecoration(Context context) {
            itemSize = ScreenUtil.dip2px(context, 1);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);

            if (holder.getAdapterPosition() == 0) {
                outRect.set(0, itemSize, 0, 0);
            } else if (parent.getAdapter().getItemCount() - 1 == holder.getAdapterPosition()) {
                outRect.set(itemSize, itemSize, 0, 0);
            } else {
                outRect.set(itemSize, itemSize, 0, 0);
            }
        }
    }

}
