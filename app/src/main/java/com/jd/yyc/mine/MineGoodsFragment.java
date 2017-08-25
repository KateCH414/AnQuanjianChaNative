package com.jd.yyc.mine;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.yyc.R;
import com.jd.yyc.api.model.MineGoods;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.YYCBase;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.recyclerview.SpacesItemDecoration;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的 - 订单fragment
 */

public class MineGoodsFragment extends BaseRefreshFragment implements Contants, MineGoodsAdapter.EmptyCallback {
    public static final String TYPE = "type";
    public static final String PADDING = "padding";


    public final static int all = 0;//全部
    public static int type;//1,待审核，2，待确认，5，待付款，6，待收货，0全部

    private boolean firstLaunch = true;

    public static MineGoodsFragment newInstance(int type) {
        MineGoodsFragment fragment = new MineGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MineGoodsFragment newInstance(int type, boolean topPadding) {
        MineGoodsFragment fragment = new MineGoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, type);
        bundle.putBoolean(PADDING, topPadding);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public Map<String, String> getParams() {

        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("status", getArguments().getInt(TYPE) + "");
        hashMap.put("page", PAGE + "");
        hashMap.put("pageSize", 20 + "");
        return hashMap;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (firstLaunch) {
            firstLaunch = false;
        } else {
            onRefresh();
        }
    }

    @Override
    public String getPath() {
        return "order/pageList";
    }

    @Override
    public RecyclerAdapter createAdapter() {
        MineGoodsAdapter adapter = new MineGoodsAdapter(mContext);
        adapter.setEmptyCallback(this);
        return adapter;
    }

    @Override
    public void onSuccess(String data) {
        setMineGoodsData(data);
    }


    private void setMineGoodsData(String data) {
        try {
            Gson gson = new Gson();
            ResultObject<MineGoods> result = gson.fromJson(data, new TypeToken<ResultObject<MineGoods>>() {
            }.getType());

            if (result != null && result.data != null) {
                setSuccessStatus(result.data.getList(), result.data.totalPage);
//                if(PAGE == 1)
//                    getAdapter().getList().clear();
                setData(result.data.getList());
                notifyDataSetChanged();
            } else {
                setSuccessStatus(null, 0);
                getEmptyView().setEmptyImage(R.drawable.noticeordersempty);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_mine_goodsreceive;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getEmptyView().setDefaultEmptyId(R.drawable.order_empty);
        getEmptyView().setEmptyMsg("暂无采购单");
        getRecyclerView().addItemDecoration(new SpacesItemDecoration(ScreenUtil.dip2px(10)));
        if (getArguments().getBoolean(PADDING, false)) {
            getRecyclerView().setPadding(0, ScreenUtil.dip2px(10), 0, 0);
            getRecyclerView().setClipToPadding(false);
        }
    }

    @Override
    public void showEmpty() {
        getEmptyView().showEmpty(YYCBase.STATUS_NO_DATA, null, null);
    }
}


