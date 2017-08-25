package com.jd.yyc.cartView.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.ProductModel;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SkuPrice;
import com.jd.yyc.api.model.VendorModel;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.event.EventCartNum;
import com.jd.yyc.event.EventSkuPrice;
import com.jd.yyc.goodsdetail.GoodsDetailActivity;
import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;
import com.jd.yyc.search.SearchResultActivity;
import com.jd.yyc.ui.util.DialogUtil;
import com.jd.yyc.ui.util.SoftKeyboardUtil;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ImageUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 购物车adapter
 */
public class CartAdapter extends BaseExpandableListAdapter implements Contants {

    private boolean isEdit; //是否是编辑模式
    private Context mContext;
    private List<VendorModel> vendorList = new ArrayList<>(); //店铺实体列表
    private ProgressCallback progressCallback;


    public CartAdapter(Context context) {
        mContext = context;
    }

    public void setProgressCallback(ProgressCallback progressCallback) {
        this.progressCallback = progressCallback;
    }
    public void setEdit(boolean edit) {
        this.isEdit = edit;
        if (isEdit) {
            clearEditCheckStatus();
        }
        notifyDataSetChanged();
    }

    //清楚编辑模式的选中状态
    private void clearEditCheckStatus() {
        for (VendorModel vendorModel : vendorList) {
            for (ProductModel productModel : vendorModel.getSku()) {
                productModel.setEditCheck(false);
            }
        }
    }

    public void setData(List<VendorModel> list) {
        if (list == null)
            list = new ArrayList<>();
        for (VendorModel v : list) {
            for (VendorModel v1 : vendorList) {
                if (v.getVenderId().equals(v1.getVenderId())) {
                    v.setScanAllSku(v1.isScanAllSku());
                    break;
                }
            }
        }
        this.vendorList.clear();
        this.vendorList.addAll(list);
        EventBus.getDefault().post(new EventSkuPrice());
        notifyDataSetChanged();
        getPrice();
    }

    public List<VendorModel> getData(){
        return vendorList;
    }

    /**
     * 重新获取商品价格
     */
    public void getPrice(){
        for(final VendorModel vendorModel : vendorList){
            SkuPrice.getPriceList(mContext, vendorModel.getSkuIdList(), new SkuPrice.PriceCallback() {
                @Override
                public void requestCallback(SkuPrice skuPrice, List<SkuPrice> skuPriceList) {
                    vendorModel.setSkuPrice(skuPriceList);
                    notifyDataSetChanged();
                    EventBus.getDefault().post(new EventSkuPrice());
                }

                @Override
                public void onFailed(String msg) {

                }
            });
        }
    }

    @Override
    public int getGroupCount() {
        return vendorList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return vendorList.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof GroupViewHolder)) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_group_item, parent, false);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }

        final VendorModel vendorModel = vendorList.get(groupPosition);
        holder.coupon.setText(R.string.coupon);
        holder.coupon.setVisibility(View.GONE);
        holder.groupCheck.setSelected(vendorModel.hasCheck(isEdit));
        holder.vendorName.setText(vendorModel.getVenderName());
        holder.scanOtherSku.setText(vendorModel.getScanOtherStr());
        holder.scanOtherSku.setSelected(vendorModel.isScanAllSku());
        holder.scanOtherSku.setVisibility(vendorModel.showScanOtherLayout() ? View.VISIBLE : View.GONE);
        holder.divider.setVisibility(vendorModel.showScanOtherLayout() ? View.VISIBLE : View.GONE);
        final GroupViewHolder finalHolder = holder;
        holder.vendorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit)
                    return;
                SearchResultActivity.launch(mContext, vendorModel.getVenderName());
            }
        });
        holder.scanOtherSku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vendorModel.setScanAllSku(!vendorModel.isScanAllSku());
                notifyDataSetChanged();
            }
        });
        holder.groupCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = 0;
                if (v.isSelected()) {
                    status = 0;
                } else {
                    status = 1;
                }

                if (isEdit) { //编辑模式
                    for (ProductModel productModel : vendorModel.getSku()) {
                        productModel.setEditCheck(!v.isSelected());
                    }
                    EventBus.getDefault().post(new EventSkuPrice());
                    notifyDataSetChanged();
                } else {
                    if (status == 1 && !hasSkuChecked(vendorList.get(groupPosition)) && getCheckVendorNum() >= 1) {
                        ToastUtil.show("只可选择同一店铺商品，请取消勾选");
                        return;
                    }
                    Map<String, Integer> map = new Hashtable<String, Integer>();
                    for (ProductModel productModel : vendorModel.getSku()) {
                        map.put(productModel.getSkuId() + "", status);
                    }
                    try {
                        NetLoading.getInstance().changeSkuCheckStatus(mContext, map, new RequestCallback<ResultObject<List<VendorModel>>>() {
                            @Override
                            public void requestCallBack(boolean success, ResultObject<List<VendorModel>> result, String err) {
                                if (success && result != null && result.success) {
                                    setData(result.data);
                                } else {
                                    ToastUtil.show(result != null ? result.msg : "网络异常请稍后重试");
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return convertView;
    }

    //有收起展开模式
    @Override
    public int getChildrenCount(int groupPosition) {
        if (vendorList.get(groupPosition).getSkuSize() > 5 && !vendorList.get(groupPosition).isScanAllSku()) {
            return 5;
        } else {
            return vendorList.get(groupPosition).getSkuSize();
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return vendorList.get(groupPosition).getSku().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null || !(convertView.getTag() instanceof ChildViewHolder)) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_child_item, parent, false);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        final ProductModel sku = vendorList.get(groupPosition).getSku().get(childPosition);
        holder.childItemCheck.setSelected(isEdit ? sku.hasEditCheck() : sku.hasCheck());
        if (isEdit) {
            holder.childItemCheck.setVisibility(View.VISIBLE);
        } else {
            holder.childItemCheck.setVisibility(sku.isNoSkuOrOnline() ? View.INVISIBLE : View.VISIBLE);
        }
        holder.noSku.setVisibility(sku.isNoSkuOrOnline() ? View.VISIBLE : View.GONE);
        holder.noSku.setText(sku.getNoSkuOrOnlineStr());
        holder.skuName.setText(sku.getName());
        holder.skuPrice.setText(sku.getPriceStr());
//        holder.skuLinePrice.setVisibility(sku.isSecKill() ? View.VISIBLE : View.GONE);
//        holder.skuLinePrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.skuLinePrice.setText(sku.getLinePriceStr());
        holder.subIcon.setEnabled(sku.getNum() >1);
        holder.skuNum.setText(sku.getNum() + "");
        holder.plusIcon.setEnabled(sku.getNum() < sku.getStockNum() && sku.getNum() < 999);
        holder.bottomView.setVisibility(childPosition == getChildrenCount(groupPosition) - 1 ? View.VISIBLE : View.GONE);
        ImageUtil.getInstance().loadSmallImage(mContext, holder.skuPic, Contants.BasePicUrl + sku.getImgUrl());
        holder.skuNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSkuNumChangeDialog((int)sku.getNum(),sku.getStockNum(),sku.getSkuId());
            }
        });
        holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogUtil.showDialog(mContext, "删除商品", "确定要删除该商品嘛？", "删除", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        List<Long> ids = new ArrayList<Long>();
                        ids.add(sku.getSkuId());
                        NetLoading.getInstance().deleteSku(mContext, ids, new RequestCallback<ResultObject<List<VendorModel>>>() {
                            @Override
                            public void requestCallBack(boolean success, ResultObject<List<VendorModel>> result, String err) {
                                if (success && result != null && result.success) {
                                    setData(result.data);
                                    EventBus.getDefault().post(new EventCartNum()); //修改购物车图标的数量
                                } else {
                                    ToastUtil.show(result != null ? result.msg : "网络异常请稍后重试");
                                }
                            }
                        });
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });
        holder.subIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/6/5 数量减一
                if (Util.isFastDoubleClick()) {
                    return;
                }
                ClickInterfaceParam param = new ClickInterfaceParam();
                param.event_id = CART_SUB_ID;
                param.page_name = PAGE_CART_NAME;
                JDMaUtil.sendClickData(param);
                sku.setNum(sku.getNum() - 1);
                notifyDataSetChanged();
                showProgress(true);
                NetLoading.getInstance().changeSkuNum(mContext, sku.getSkuId(), (int) sku.getNum(), new RequestCallback<ResultObject<List<VendorModel>>>() {
                    @Override
                    public void requestCallBack(boolean success, ResultObject<List<VendorModel>> result, String err) {
                        showProgress(false);
                        if (success && result != null && result.success) {
                            setData(result.data);
                            EventBus.getDefault().post(new EventCartNum()); //修改购物车图标的数量
                        } else {
                            sku.setNum(sku.getNum() + 1);
                            notifyDataSetChanged();
                            ToastUtil.show(result != null ? result.msg : "网络异常请稍后重试");
                        }
                    }
                });
            }
        });

        holder.childItemCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isFastDoubleClick()) {
                    return;
                }
                int status = v.isSelected()?0:1;

                if (isEdit) {
                    sku.setEditCheck(!v.isSelected());
                    EventBus.getDefault().post(new EventSkuPrice());
                    notifyDataSetChanged();
                } else {
                    if (status == 1 && getCheckVendorNum() >= 1 && !hasSkuChecked(vendorList.get(groupPosition))) {
                        ToastUtil.show("只可选择同一店铺商品，请取消勾选");
                        return;
                    }
                    Map<String, Integer> map = new Hashtable<String, Integer>();
                    map.put(sku.getSkuId() + "", status);
                    try {
                        NetLoading.getInstance().changeSkuCheckStatus(mContext, map, new RequestCallback<ResultObject<List<VendorModel>>>() {
                            @Override
                            public void requestCallBack(boolean success, ResultObject<List<VendorModel>> result, String err) {
                                if (success && result != null && result.success) {
                                    setData(result.data);
                                } else {
                                    ToastUtil.show(result != null ? result.msg : "网络异常请稍后重试");
                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        holder.plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.isFastDoubleClick()) {
                    return;
                }
                if (sku.getNum() >= 999) {
                    ToastUtil.show("每种商品最多购买999件");
                    return;
                }
                ClickInterfaceParam param = new ClickInterfaceParam();
                param.event_id = CART_PLUS_ID;
                param.page_name = PAGE_CART_NAME;
                JDMaUtil.sendClickData(param);
                sku.setNum(sku.getNum() + 1);
                notifyDataSetChanged();
                showProgress(true);
                NetLoading.getInstance().changeSkuNum(mContext, sku.getSkuId(), (int) sku.getNum(), new RequestCallback<ResultObject<List<VendorModel>>>() {
                    @Override
                    public void requestCallBack(boolean success, ResultObject<List<VendorModel>> result, String err) {
                        showProgress(false);
                        if (success && result != null && result.success) {
                            setData(result.data);
                            EventBus.getDefault().post(new EventCartNum()); //修改购物车图标的数量
                        } else {
                            sku.setNum(sku.getNum() - 1);
                            notifyDataSetChanged();
                            ToastUtil.show(result != null ? result.msg : "网络异常请稍后重试");
                        }
                    }
                });
            }
        });
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit)
                    return;

                //埋点
                ClickInterfaceParam param = new ClickInterfaceParam();
                param.event_id = CART_SKU_DETAIL_ID;
                param.page_name = PAGE_CART_NAME;
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("item_first_cate_id", sku.getCat1() + "");
                map.put("item_second_cate_id", sku.getCat2() + "");
                map.put("item_third_cate_id", sku.getCat3() + "");
                param.sku = sku.getSkuId() + "";
                param.map = map;
                JDMaUtil.sendClickData(param);
                GoodsDetailActivity.launch(mContext, sku.getSkuId());
            }
        });
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private void showProgress(boolean show) {
        if (progressCallback != null)
            progressCallback.showProgress(show);
    }


    //修改商品数量弹窗
    private void showSkuNumChangeDialog(int num , final int stockNum , final long skuId) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_sku_num_change, null);
        final ImageView subIcon = (ImageView) view.findViewById(R.id.sub_icon);
        final EditText skuNum = (EditText) view.findViewById(R.id.sku_num);
        final ImageView plusIcon = (ImageView) view.findViewById(R.id.plus_icon);
        TextView cancelBtn = (TextView) view.findViewById(R.id.cancel_btn);
        TextView confirmBtn = (TextView) view.findViewById(R.id.confirm_btn);

        final Dialog dialog = DialogUtil.buildDialog((Activity) mContext, view, Gravity.CENTER, 0, true);
        skuNum.setText(num+"");
        skuNum.setSelection(skuNum.getText().toString().trim().length());
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        skuNum.requestFocus();
        SoftKeyboardUtil.openSoftKeyboard(skuNum);
        subIcon.setEnabled(num > 1);
        plusIcon.setEnabled(num < stockNum && num < 999);
        subIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (!CheckTool.isEmpty(skuNum.getText().toString().trim()))
                    num = Integer.valueOf(skuNum.getText().toString().trim());

                num--;
                skuNum.setText(num + "");
                skuNum.setSelection(skuNum.getText().toString().trim().length());
                subIcon.setEnabled(num > 1);
            }
        });

        plusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = 0;
                if (!CheckTool.isEmpty(skuNum.getText().toString().trim()))
                    num = Integer.valueOf(skuNum.getText().toString().trim());

                num++;
                skuNum.setText(num + "");
                skuNum.setSelection(skuNum.getText().toString().trim().length());
                plusIcon.setEnabled(num < stockNum && num < 999);
            }
        });



        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        skuNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String numStr = skuNum.getText().toString().trim();
                if (CheckTool.isEmpty(numStr)) {
                    subIcon.setEnabled(false);
                    return ;
                }
                int num = Integer.valueOf(numStr);
                if (num <1) {
                    num = 1;
                    skuNum.setText(num+"");
                    skuNum.setSelection(skuNum.getText().toString().trim().length());
                    return ;
                }

                if (num > 999) {
                    num = 999;
                    skuNum.setText(num+"");
                    skuNum.setSelection(skuNum.getText().toString().trim().length());
                    ToastUtil.show("每种商品最多购买999件");
                    return ;
                }
                subIcon.setEnabled(num>1);
                plusIcon.setEnabled(num < stockNum && num < 999);

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numStr = skuNum.getText().toString().trim();
                if (CheckTool.isEmpty(numStr)) {
                    ToastUtil.show("数量不能为空");
                    return ;
                }

                int num = Integer.valueOf(numStr);
                if (num < 0) {
                    ToastUtil.show("数量不能为负数");
                    return ;
                }

                if (num > 999) {
                    ToastUtil.show("每种商品最多购买999件");
                    return ;
                }

                if (num > stockNum) {
                    ToastUtil.show("库存数量不足");
                    return;
                }
                showProgress(true);
                NetLoading.getInstance().changeSkuNum(mContext, skuId, num, new RequestCallback<ResultObject<List<VendorModel>>>() {
                    @Override
                    public void requestCallBack(boolean success, ResultObject<List<VendorModel>> result, String err) {
                        showProgress(false);
                        dialog.dismiss();
                        if (success && result != null && result.success) {
                            setData(result.data);
                            EventBus.getDefault().post(new EventCartNum()); //修改购物车图标的数量
                        } else {
                            ToastUtil.show(result != null ? result.msg : "网络异常请稍后重试");
                        }
                    }
                });
            }
        });

        dialog.show();
    }

    private class GroupViewHolder {
        public View rootView;
        public ImageView groupCheck;  //选中图标
        public TextView vendorName;
        public TextView coupon;
        public TextView scanOtherSku;
        public View divider;

        public GroupViewHolder(View view) {
            rootView = view;
            scanOtherSku = (TextView) view.findViewById(R.id.scan_other_sku);
            divider = view.findViewById(R.id.divider);
            groupCheck = (ImageView) view.findViewById(R.id.group_check);
            vendorName = (TextView) view.findViewById(R.id.vendor_name);
            coupon = (TextView) view.findViewById(R.id.coupon);
        }
    }

    public float getAllPrice() {
        float price = 0;
        for (VendorModel vendorModel : vendorList) {
            price += vendorModel.getTotalPrice();
//            for (ProductModel sku : vendorModel.getSku()) {
//                if (sku.hasCheck())
//                    price += sku.getPrice() * sku.getNum();
//            }
        }
        return price;
    }

    public List<Long> getSkuIdList(){
        List<Long> ids = new ArrayList<>();
        for(VendorModel vendorModel:vendorList){
            for(ProductModel productModel :vendorModel.getSku()){
                ids.add(productModel.getSkuId());
            }
        }
        return ids;
    }

    //获取选中商品id的集合
    public List<Long> getCheckedSkuIdList(){
        List<Long> ids = new ArrayList<>();
        for(VendorModel vendorModel:vendorList){
            for(ProductModel productModel :vendorModel.getSku()){
                if (productModel.hasEditCheck())
                    ids.add(productModel.getSkuId());
            }
        }
        return ids;
    }

    //获取选中店铺的数量
    public int getCheckVendorNum(){
        int num = 0;
        for(VendorModel vendorModel:vendorList){
            for(ProductModel productModel :vendorModel.getSku()){
                if(productModel.hasCheck()){
                    num++;
                    break;
                }
            }
        }
        return num;
    }

    //检查该店铺是否有选中的商品
    public boolean hasSkuChecked(VendorModel vendorModel) {
        for (ProductModel productModel : vendorModel.getSku()) {
            if (productModel.hasCheck())
                return true;
        }

        return false;
    }

    //获取选中店铺的id
    public Long getCheckVendorId() {
        for (VendorModel vendorModel : vendorList) {
            for (ProductModel productModel : vendorModel.getSku()) {
                if (productModel.hasCheck()) {
                    return vendorModel.getVenderId();
                }
            }
        }
        return 0l;
    }

    public List<String> getStockNotEnough() {
        List<String> skuNames = new ArrayList<>();
        boolean flag = false;
        for (VendorModel vendorModel : vendorList) {
            for (ProductModel productModel : vendorModel.getSku()) {
                if (productModel.hasCheck()) {
                    flag = true;
                    if (productModel.getNum() > productModel.getStockNum())
                        skuNames.add(productModel.getName());
                }
            }
            if (flag)
                break;
        }
        return skuNames;
    }

    //获取选中商品的数量
    public int getSelectSkuNum() {
        int num = 0;
        for (VendorModel vendorModel : vendorList) {
            for (ProductModel sku : vendorModel.getSku()) {
                if (sku.hasCheck())
                    num += sku.getNum();
            }
        }
        return num;
    }

    /**
     * 不同模式下，商品是否是全选状态
     *
     * @param isEdit 是否是编辑模式
     * @return
     */
    public boolean hasAllCheck(boolean isEdit) {
        if (isEdit) {
            for (VendorModel vendorModel : vendorList) {
                for (ProductModel sku : vendorModel.getSku()) {
                    if (!sku.hasEditCheck()) {
                        return false;
                    }
                }
            }
        } else {
            for (VendorModel vendorModel : vendorList) {
                for (ProductModel sku : vendorModel.getSku()) {
                    if (!sku.hasCheck()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void setAllCheck(boolean check) {
        // TODO: 2017/6/5 设置所有商品的选择状态
    }

    private class ChildViewHolder {

        public View rootView;
        public ImageView childItemCheck;
        public ImageView skuPic;
        public TextView noSku;
        public TextView skuName;
        public TextView skuPrice;
        public TextView skuLinePrice;
        public ImageView subIcon; //减号
        public TextView skuNum;
        public ImageView plusIcon; //加号
        public TextView salePromotionTxt;
        public LinearLayout salePromotionLayout;
        public View bottomView;

        public ChildViewHolder(View view) {
            rootView = view;
            childItemCheck = (ImageView) view.findViewById(R.id.child_item_check);
            skuPic = (ImageView) view.findViewById(R.id.sku_pic);
            noSku = (TextView) view.findViewById(R.id.no_sku);
            skuName = (TextView) view.findViewById(R.id.sku_name);
            skuPrice = (TextView) view.findViewById(R.id.sku_price);
            skuLinePrice = (TextView) view.findViewById(R.id.sku_line_price);
            subIcon = (ImageView) view.findViewById(R.id.sub_icon);
            skuNum = (TextView) view.findViewById(R.id.sku_num);
            plusIcon = (ImageView) view.findViewById(R.id.plus_icon);
            salePromotionLayout = (LinearLayout) view.findViewById(R.id.sale_promotion_layout);
            salePromotionTxt = (TextView) view.findViewById(R.id.sale_promotion_txt);
            bottomView = view.findViewById(R.id.bottom_view);

        }

    }

    public interface ProgressCallback {
        public void showProgress(boolean show);
    }

}
