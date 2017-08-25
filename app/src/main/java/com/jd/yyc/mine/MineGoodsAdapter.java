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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jd.project.lib.andlib.utils.DateFormatUtil;
import com.jd.yyc.R;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SkuNum;
import com.jd.yyc.api.model.YaoOrder;
import com.jd.yyc.api.model.YaoOrderSku;
import com.jd.yyc.cartView.CartViewActivity;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.mine.adapter.CheckPendingImageAdapter;
import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;
import com.jd.yyc.refreshfragment.LoadingDialogUtil;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.search.SearchResultActivity;
import com.jd.yyc.ui.activity.web.YYCWebActivity;
import com.jd.yyc.ui.util.DialogUtil;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ResourceUtil;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.UrlUtil;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 *
 */

public class MineGoodsAdapter extends RecyclerAdapter<YaoOrder, YYCViewHolder> implements Contants {

    private EmptyCallback emptyCallback;

    public MineGoodsAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {

//        if (viewType == CHECKPENDING) {
//            return new CheckPendingViewHolder(View.inflate(mContext, R.layout.check_pending, null));
//        }
//        if (viewType == CONFIRM) {
//            return new ConfirmViewHolder(View.inflate(mContext, R.layout.confirm, null));
//        }
//        if (viewType == STAYPAY) {
//            return new StayPayViewHolder(View.inflate(mContext, R.layout.staypay, null));
//        }
//        if (viewType == GOODSRECEIVE) {
//            return new GoodsReceiveViewHolder(View.inflate(mContext, R.layout.goodsreceive, null));
//        }

        return new OrderViewHolder(View.inflate(mContext, R.layout.check_pending, null));

    }

    public void setEmptyCallback(EmptyCallback emptyCallback) {
        this.emptyCallback = emptyCallback;
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(position, getItem(position));

    }

    public String getCheckPendingTxt(int type, String rejectStr) {
        if (type == REAL_STATUS_ORDER_WILL_CHECK) {
            return "您的采购单正在审核中，请耐心等待";
        } else if (type == REAL_STATUS_ORDER_REJECT) {
            return "驳回原因：" + (CheckTool.isEmpty(rejectStr) ? "暂无" : rejectStr);
        } else if (type == REAL_STATUS_ORDER_WILL_RECEIVE) {
            return "尊敬的客户，您的商品已经在配送途中";
        }
        if (type == REAL_STATUS_ORDER_DONE) {
            return "订单已经完成，感谢您在京东医药城购物，欢迎您对本次交易及所购商品进行评价";
        } else {
            return "";
        }
    }

    //订单的状态
    public String getOrderStatus(int type) {
        String txt = "";
        switch (type) {
            case REAL_STATUS_ORDER_WILL_CHECK:
                txt = "待审核";
                break;
            case REAL_STATUS_ORDER_WILL_CONFIRM:
                txt = "待确认";

                break;
            case REAL_STATUS_ORDER_REJECT:
                txt = "已驳回";
                break;
            case REAL_STATUS_ORDER_CANCELED:
                txt = "已取消";
                break;
            case REAL_STATUS_ORDER_WILL_PAY:
                txt = "待付款";

                break;
            case REAL_STATUS_ORDER_WILL_RECEIVE:
                txt = "待收货";

                break;
            case REAL_STATUS_ORDER_DONE:
                txt = "已完成";
                break;
            case REAL_STATUS_ORDER_FAILED:
                txt = "已失效";
                break;
            case REAL_STATUS_ORDER_WILL_SEND:
                txt = "待发货";
                break;
        }
        return txt;
    }

    public String getBtn2Txt(int type, boolean isShowMonthPay, boolean isPay) {
        String txt = "";
        switch (type) {
            case REAL_STATUS_ORDER_WILL_CHECK:
            case REAL_STATUS_ORDER_WILL_CONFIRM:
            case REAL_STATUS_ORDER_REJECT:
            case REAL_STATUS_ORDER_CANCELED:
            case REAL_STATUS_ORDER_DONE:
            case REAL_STATUS_ORDER_FAILED:
            case REAL_STATUS_ORDER_WILL_SEND:
                txt = "再次购买";
                break;
            case REAL_STATUS_ORDER_WILL_RECEIVE:
                if (isPay) {
                    txt = "去支付";
                } else {
                    txt = "再次购买";
                }
                break;
            case REAL_STATUS_ORDER_WILL_PAY:
                if (isShowMonthPay) {
                    txt = "额度付款";
                } else if (isPay) {
                    txt = "去支付";
                }
                break;
        }
        return txt;
    }

    public boolean getBtn2Visibility(int type, boolean isShowMonthPay, boolean isPay) {
        if (type == REAL_STATUS_ORDER_WILL_PAY && !isShowMonthPay && !isPay) {
            return false;
        } else {
            return true;
        }
    }

    public boolean getOrderStatusSelect(int type) {
        boolean flag = false;
        switch (type) {
            case REAL_STATUS_ORDER_WILL_CHECK: //审查
            case REAL_STATUS_ORDER_WILL_CONFIRM:  //确认
            case REAL_STATUS_ORDER_WILL_PAY: //待支付
            case REAL_STATUS_ORDER_WILL_RECEIVE:  //待收货
            case REAL_STATUS_ORDER_DONE: //已完成
            case REAL_STATUS_ORDER_WILL_SEND: //待发货
            case REAL_STATUS_ORDER_REJECT:  //已驳回
            case REAL_STATUS_ORDER_CANCELED:  //已取消
            case REAL_STATUS_ORDER_FAILED:  //订单失效
                flag = true;
                break;

            //        flag = false;
            //    break;
        }
        return flag;
    }

    public String getBtn1Txt(int type, boolean showMothPay) {
        String txt = "";
        switch (type) {
            case REAL_STATUS_ORDER_WILL_CONFIRM:
                txt = "确认";
                break;
            case REAL_STATUS_ORDER_WILL_RECEIVE:
                txt = "确认收货";
                break;
        }
        return txt;
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

    //再次购买
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

    public class OrderViewHolder extends YYCViewHolder<YaoOrder> {
        @InjectView(R.id.vendor_name)
        TextView vendorName;
        @InjectView(R.id.tv_checkPeding_time1)
        TextView tvCheckPedingTime1;
        @InjectView(R.id.checkPending_line)
        View checkPendingLine;
        @InjectView(R.id.checkPending_title)
        TextView checkPendingTitle;
        @InjectView(R.id.tv_checkPeding_time)
        TextView tvCheckPedingTime;
        @InjectView(R.id.checkPending_layout)
        RelativeLayout checkPendingLayout;
        @InjectView(R.id.sku_list)
        RecyclerView skuList;
        @InjectView(R.id.tv_checkPending_realPay)
        TextView tvCheckPendingRealPay;
        @InjectView(R.id.right_btn1)
        TextView rightBtn1;
        @InjectView(R.id.right_btn2)
        TextView rightBtn2;
        @InjectView(R.id.order_status)
        TextView orderStatus;

        public OrderViewHolder(View item) {
            super(item);
            ButterKnife.inject(this, itemView);
        }

        public void onBind(final int position, final YaoOrder data) {
            vendorName.setText(data.getVenderName());
            tvCheckPedingTime1.setVisibility(data.showCheckPendingLayout(data.getRealStatus()) ? View.GONE : View.VISIBLE);
            checkPendingLine.setVisibility(data.showCheckPendingLayout(data.getRealStatus()) ? View.VISIBLE : View.GONE);
            checkPendingLayout.setVisibility(data.showCheckPendingLayout(data.getRealStatus()) ? View.VISIBLE : View.GONE);
            checkPendingTitle.setText(getCheckPendingTxt(data.getRealStatus(), data.getAuditComment()));
            tvCheckPedingTime1.setText(DateFormatUtil.dateFormat(DateFormatUtil.yyyy_MM_dd_HH_mm, data.getPurchaseTime()));
            tvCheckPedingTime.setText(DateFormatUtil.dateFormat(DateFormatUtil.yyyy_MM_dd_HH_mm, data.getPurchaseTime()));
            orderStatus.setSelected(getOrderStatusSelect(data.getRealStatus()));
            orderStatus.setText(getOrderStatus(data.getRealStatus()));
            tvCheckPendingRealPay.setText(ResourceUtil.getString(R.string.sku_price, data.getAmountPay()));
            rightBtn1.setVisibility(((data.getRealStatus() == REAL_STATUS_ORDER_WILL_RECEIVE && data.getPayType() == 3) || (data.getRealStatus() == REAL_STATUS_ORDER_WILL_CONFIRM && data.isShowConfirm())) ? View.VISIBLE : View.GONE);
            rightBtn1.setText(getBtn1Txt(data.getRealStatus(), data.isShowMonthPay()));
            rightBtn2.setVisibility(getBtn2Visibility(data.getRealStatus(), data.isShowMonthPay(), data.isShowPersonOrCompanyPay()) ? View.VISIBLE : View.GONE);
            rightBtn2.setText(getBtn2Txt(data.getRealStatus(), data.isShowMonthPay(), data.isShowPersonOrCompanyPay()));
            final LinearLayoutManager manager = new LinearLayoutManager(mContext);
            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
            skuList.setLayoutManager(manager);
            CheckPendingImageAdapter imageAdapter = new CheckPendingImageAdapter(mContext);
            imageAdapter.setCanLoadMore(false);
            skuList.setAdapter(imageAdapter);
            imageAdapter.setData(data.getYaoOrderSkus());
            vendorName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchResultActivity.launch(mContext, data.getVenderName());
                }
            });
            rightBtn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (data.getRealStatus()) {
                        case REAL_STATUS_ORDER_WILL_CONFIRM:

                            //埋点
                            ClickInterfaceParam param = new ClickInterfaceParam();
                            param.event_id = ME_ORDER_CONFIRM_ID;
                            param.page_name = PAGE_ORDER_LIST_NAME;
                            param.ord = data.getPurchaseId() + "";
                            JDMaUtil.sendClickData(param);

                            NetLoading.getInstance().orderConfirm(mContext, data.getPurchaseId(), new RequestCallback<ResultObject<Boolean>>() {
                                @Override
                                public void requestCallBack(boolean success, ResultObject<Boolean> result, String err) {
                                    if (success && result.success && result.data != null && result.data) {
                                        ToastUtil.show("确认成功");
                                        getList().remove(position);
                                        if (getList().size() == 0 && emptyCallback != null)
                                            emptyCallback.showEmpty();
                                        notifyDataSetChanged();
                                    } else {
                                        ToastUtil.show("确认失败");
                                    }
                                }
                            });
                            break;
                        case REAL_STATUS_ORDER_WILL_RECEIVE:
                            NetLoading.getInstance().confirmReceive(mContext, data.getOrderId(), new RequestCallback<ResultObject<Boolean>>() {
                                @Override
                                public void requestCallBack(boolean success, ResultObject<Boolean> result, String err) {
                                    if (success && result.success && result.data != null && result.data) {
                                        ToastUtil.show("确认收货成功");
                                        getList().remove(position);
                                        if (getList().size() == 0 && emptyCallback != null)
                                            emptyCallback.showEmpty();
                                        notifyDataSetChanged();
                                    } else {

                                    }
                                }
                            });
                            break;
                    }
                }
            });

            rightBtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (data.getRealStatus()) {
                        case REAL_STATUS_ORDER_WILL_CHECK:
                        case REAL_STATUS_ORDER_WILL_CONFIRM:
                        case REAL_STATUS_ORDER_REJECT:
                        case REAL_STATUS_ORDER_CANCELED:
                        case REAL_STATUS_ORDER_DONE:
                        case REAL_STATUS_ORDER_FAILED:
                        case REAL_STATUS_ORDER_WILL_SEND:
                            //埋点
                            ClickInterfaceParam param = new ClickInterfaceParam();
                            param.event_id = ME_ORDER_BUY_AGAIN_ID;
                            param.page_name = PAGE_ORDER_LIST_NAME;
                            param.ord = data.getPurchaseId() + "";
                            JDMaUtil.sendClickData(param);

                            buyAgain(data.getYaoOrderSkus());
//                            ToastUtil.show("再次购买");
                            break;
                        case REAL_STATUS_ORDER_WILL_RECEIVE:
                            if (data.isShowPersonOrCompanyPay()) {
                                //埋点 去支付
                                param = new ClickInterfaceParam();
                                param.event_id = ME_ORDER_PAY_ID;
                                param.page_name = PAGE_ORDER_LIST_NAME;
                                param.ord = data.getPurchaseId() + "";
                                JDMaUtil.sendClickData(param);

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
                            } else {
                                buyAgain(data.getYaoOrderSkus());
                            }
                            break;
                        case REAL_STATUS_ORDER_WILL_PAY:
                            if (data.isShowPersonOrCompanyPay()) {
                                //埋点 去支付
                                param = new ClickInterfaceParam();
                                param.event_id = ME_ORDER_PAY_ID;
                                param.page_name = PAGE_ORDER_LIST_NAME;
                                param.ord = data.getPurchaseId() + "";
                                JDMaUtil.sendClickData(param);

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

                            break;
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



    class goodsReceiveItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public goodsReceiveItemDecoration(Context context) {
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

    public interface EmptyCallback {
        public void showEmpty();
    }

}
