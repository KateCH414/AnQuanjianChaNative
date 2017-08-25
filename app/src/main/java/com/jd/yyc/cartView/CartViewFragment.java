package com.jd.yyc.cartView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.diegocarloslima.fgelv.lib.FloatingGroupExpandableListView;
import com.diegocarloslima.fgelv.lib.WrapperExpandableListAdapter;
import com.jd.project.lib.andlib.pulltorefresh.PtrHeader;
import com.jd.yyc.R;
import com.jd.yyc.api.model.ProductModel;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.VendorModel;
import com.jd.yyc.base.CommonFragment;
import com.jd.yyc.cartView.adapter.CartAdapter;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.event.EventCartNum;
import com.jd.yyc.event.EventHome;
import com.jd.yyc.event.EventLoginSuccess;
import com.jd.yyc.event.EventSkuPrice;
import com.jd.yyc.home.HomeActivity;
import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;
import com.jd.yyc.refreshfragment.EmptyView;
import com.jd.yyc.refreshfragment.LoadingDialogUtil;
import com.jd.yyc.refreshfragment.OnLoadMoreListViewScrollImpl;
import com.jd.yyc.ui.activity.web.YYCWebActivity;
import com.jd.yyc.ui.util.DialogUtil;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ResourceUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.UrlUtil;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 *购物车fragment
 */

public class CartViewFragment extends CommonFragment implements View.OnClickListener, Contants, CartAdapter.ProgressCallback {
    public static final String BACK = "back";

    @InjectView(R.id.refresh_layout)
    PtrFrameLayout refreshLayout;
    @InjectView(R.id.cart_list)
    FloatingGroupExpandableListView cartList;
    @InjectView(android.R.id.empty)
    EmptyView emptyView;
    @InjectView(R.id.back_btn)
    ImageView backBtn;
    @InjectView(R.id.tv_cartView_title)
    TextView tvCartViewTitle;
    @InjectView(R.id.tv_cartView_finish)
    TextView tvCartViewFinish;
    @InjectView(R.id.all_check)
    ImageView allCheck;
    @InjectView(R.id.check_txt)
    TextView checkTxt;
    @InjectView(R.id.delete_btn)
    TextView deleteBtn;
    @InjectView(R.id.sum_txt)
    TextView sumTxt;
    @InjectView(R.id.total_price)
    TextView totalPrice;
    @InjectView(R.id.extra_txt)
    TextView extraTxt;
    @InjectView(R.id.submit_btn)
    TextView submitBtn;
    @InjectView(R.id.progress_view)
    ProgressBar progressView;
    @InjectView(R.id.submit_layout)
    RelativeLayout submitLayout;
    @InjectView(R.id.bottom_layout)
    RelativeLayout bottomLayout;


    private CartAdapter adapter;
    private boolean isEdit; //是否是编辑模式

    private Dialog dialog;


    public static CartViewFragment newInstance(boolean showRightBtn) { //showRight:是否显示左上角的返回键
        CartViewFragment fragment = new CartViewFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(BACK, showRightBtn);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        PvInterfaceParam param = new PvInterfaceParam();
        param.page_name = PAGE_CART_NAME;
        param.page_id = PAGE_CART_ID;
        JDMaUtil.sendPVData(param);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_cartview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emptyView.setRootBackGround(R.color.background);
        backBtn.setVisibility(getArguments().getBoolean(BACK, false) ? View.VISIBLE : View.GONE);
        backBtn.setOnClickListener(this);
        cartList.setOnScrollListener(new OnLoadMoreListViewScrollImpl(refreshLayout));
        PtrHeader header = new PtrHeader(mContext);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.disableWhenHorizontalMove(true);
        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                getDate(true);
            }
        });

        adapter = new CartAdapter(getTopActivity());
        adapter.setProgressCallback(this);
        WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(adapter);
        cartList.setAdapter(wrapperAdapter);
        tvCartViewFinish.setOnClickListener(this);
        allCheck.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    //设置空置页面
    public void setEmptyViewNoData() {
        emptyView.getEmptyImage().setVisibility(View.VISIBLE);
        emptyView.getEmptyImage().setImageResource(R.drawable.cart_empty);
        emptyView.getEmptyText().setVisibility(View.VISIBLE);
        emptyView.getEmptyText().setText("您的购物车空空如也\n" + "快去首页买买买~");
        emptyView.getEmptyButton().setVisibility(View.VISIBLE);
        emptyView.getEmptyButton().setTextColor(getResources().getColor(R.color.dominant_color));
        emptyView.getEmptyButton().setBackgroundResource(R.drawable.cart_empty_btn_bg);
        emptyView.getEmptyButton().setText("去首页逛逛");
        emptyView.getEmptyButton().setOnClickListener(this);
    }

    //设置网络错误页面
    public void setEmptyViewNetErr() {
        emptyView.getEmptyImage().setVisibility(View.VISIBLE);
        emptyView.getEmptyImage().setImageResource(R.drawable.ic_default_net_error);
        emptyView.getEmptyText().setVisibility(View.GONE);
        emptyView.getEmptyButton().setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDate(true);
    }

    public void getDate(boolean showLoadingDialog) {
        NetLoading.getInstance().getCartList(getTopActivity(), showLoadingDialog, new RequestCallback<ResultObject<List<VendorModel>>>() {
            @Override
            public void requestCallBack(boolean success, ResultObject<List<VendorModel>> result, String err) {
                refreshLayout.refreshComplete();
                if (success && result != null && result.success) {
                    adapter.setData(result.data);
                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                        cartList.expandGroup(i);
                    }
                    cartList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            return true;
                        }
                    });
                } else if (result == null) {
                    adapter.setData(null);
                    setEmptyViewNetErr();
                } else {
                    ToastUtil.show(result.msg);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventSkuPrice event) {
        setEditStatus();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void setEdit() {
        if (isEdit) {
            isEdit = false;
            adapter.setEdit(isEdit);
            setEditStatus();
        }
    }

    //设置底部的状态
    private void setEditStatus() {
        tvCartViewFinish.setVisibility(adapter.getData().size() > 0 ? View.VISIBLE : View.GONE);
        bottomLayout.setVisibility(adapter.getData().size() > 0 ? View.VISIBLE : View.GONE);
        if (adapter.getData().size() == 0)
            setEmptyViewNoData();
        emptyView.setVisibility(adapter.getData().size() > 0 ? View.GONE : View.VISIBLE);
        if (isEdit) {
            tvCartViewFinish.setText(R.string.complete);
            allCheck.setVisibility(View.VISIBLE);
            checkTxt.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            sumTxt.setVisibility(View.GONE);
            totalPrice.setVisibility(View.GONE);
            extraTxt.setVisibility(View.GONE);
            submitLayout.setVisibility(View.GONE);
            allCheck.setSelected(adapter.hasAllCheck(isEdit));
        } else {
            tvCartViewFinish.setText(R.string.edit);
            allCheck.setVisibility(View.GONE);
            checkTxt.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
            sumTxt.setVisibility(View.VISIBLE);
            totalPrice.setVisibility(View.VISIBLE);
            extraTxt.setVisibility(View.VISIBLE);
            submitLayout.setVisibility(View.VISIBLE);
            totalPrice.setText(ResourceUtil.getString(R.string.sku_price, adapter.getAllPrice()));
            submitBtn.setText(ResourceUtil.getString(R.string.submit_txt, adapter.getSelectSkuNum()));
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                getTopActivity().finish();
                break;
            case R.id.tv_cartView_finish:
                isEdit = !isEdit;
                adapter.setEdit(isEdit);
                setEditStatus();
                if (isEdit) {
                    ClickInterfaceParam param = new ClickInterfaceParam();
                    param.event_id = CART_EDIT_ID;
                    param.page_name = PAGE_CART_NAME;
                    JDMaUtil.sendClickData(param);
                }
                break;
            case R.id.all_check:
                v.setSelected(!v.isSelected());
                setSkuCheckStatus(v.isSelected());
                break;
            case R.id.delete_btn:
                if (CheckTool.isEmpty(adapter.getCheckedSkuIdList())) {
                    ToastUtil.show("您还没有选择商品哦");
                } else {
                    DialogUtil.showDialog(getTopActivity(), "删除商品", "确定要删除选中的商品嘛？", "删除", "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ClickInterfaceParam param = new ClickInterfaceParam();
                            param.event_id = CART_DELETE_ID;
                            param.page_name = PAGE_CART_NAME;
                            JDMaUtil.sendClickData(param);
                            NetLoading.getInstance().deleteSku(mContext, adapter.getCheckedSkuIdList(), new RequestCallback<ResultObject<List<VendorModel>>>() {
                                @Override
                                public void requestCallBack(boolean success, ResultObject<List<VendorModel>> result, String err) {
                                    if (success && result != null && result.success) {
                                        adapter.setData(result.data);
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

                }
                break;
            case R.id.submit_btn:
                int num = adapter.getCheckVendorNum();
                if (num == 0) {
                    ToastUtil.show("没有选中的商品");
                } else if (num > 1) {
                    ToastUtil.show("只可选择同一店铺商品，请取消勾选");
                } else {
                    ClickInterfaceParam param = new ClickInterfaceParam();
                    param.event_id = CART_SUBMIT_ID;
                    param.page_name = PAGE_CART_NAME;
                    JDMaUtil.sendClickData(param);
                    orderSub(adapter.getCheckVendorId());
//                    List<String> skuName = adapter.getStockNotEnough();
//                    if (!CheckTool.isEmpty(skuName)) {
//                        ToastUtil.show(StringUtil.getListStr(skuName) + "库存量不足");
//                    } else {
//                        orderSub(adapter.getCheckVendorId());
//                    }
                }
                break;
            case R.id.close_img:
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                break;
            case R.id.empty_btn:
                HomeActivity.launch(mContext);
                EventBus.getDefault().post(new EventHome());
                break;
        }
    }

    /**
     * 订单提交
     *
     * @param vendorId 店铺id
     */
    private void orderSub(final Long vendorId) {
        LoadingDialogUtil.show(mContext);
        UrlUtil.getLoginStatusUrl(UrlUtil.getWriteOrderUrl(vendorId), new UrlUtil.LoginStatusCallback() {
            @Override
            public void onSuccess(String url) {
                LoadingDialogUtil.close();
                YYCWebActivity.launch(getTopActivity(), url, "");
            }

            @Override
            public void onFailed(String msg) {
                LoadingDialogUtil.close();
                ToastUtil.show("暂时无法结算");
            }
        });
    }

    //设置购物车的编辑模式
    private void setSkuCheckStatus(boolean isEditCheck) {
        for (VendorModel vendorModel : adapter.getData()) {
            for (ProductModel productModel : vendorModel.getSku()) {
                productModel.setEditCheck(isEditCheck);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void onEvent(EventLoginSuccess event) {
        getDate(false);
    }

    @Override
    public void showProgress(boolean show) {
        submitBtn.setVisibility(show ? View.GONE : View.VISIBLE);
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
