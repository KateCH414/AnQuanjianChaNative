package com.jd.yyc.category;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.yyc.R;
import com.jd.yyc.api.CallBack;
import com.jd.yyc.api.model.Floor;
import com.jd.yyc.api.model.Price;
import com.jd.yyc.api.model.Result;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SearchSku;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.api.model.SkuList;
import com.jd.yyc.category.adapter.SkuAdapter;
import com.jd.yyc.event.EventOnScroll;
import com.jd.yyc.lbs.LBSFragment;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.HttpUtil;
import com.jd.yyc.util.ScreenUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by zhangweifeng1 on 2017/5/31.
 */

public class SkuFragment extends BaseRefreshFragment {
    private long vCat1Id;
    private long vCat2Id;
    private long vCat3Id;


    @Override
    public RecyclerAdapter createAdapter() {
        return new SkuAdapter(mContext);
    }


    @Override
    public String getPath() {
        return "list/search";
    }

    public void setId(long cat1Id, long cat2Id, long cat3Id) {
        this.vCat1Id = cat1Id;
        this.vCat2Id = cat2Id;
        this.vCat3Id = cat3Id;
    }

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("currentPage", String.valueOf(PAGE));
//        params.put("pageSize", String.valueOf(20));
        if (vCat3Id > 0) {
            params.put("vCat3Id", vCat3Id + "");
        } else if (vCat2Id > 0) {
            params.put("vCat2Id", vCat2Id + "");
        } else {
            params.put("vCat1Id", vCat1Id + "");
        }
        return params;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext));
        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    EventBus.getDefault().post(new EventOnScroll());
                }
            }
        });
    }


    @Override
    public void onSuccess(String data) {
        getEmptyView().setVisibility(View.GONE);
        Gson gson = new Gson();
        ResultObject<SkuList> result = gson.fromJson(data, new TypeToken<ResultObject<SkuList>>() {
        }.getType());
        if (result != null && result.data != null) {
            setSuccessStatus(result.data.list, result.data.totalPage);
            getEmptyView().setEmptyImage(R.drawable.category_empty);
            setData(result.data.list);
            getPrice(result.data.list);
            notifyDataSetChanged();
        } else {//数据为空
            setSuccessStatus(null, 0);
            getEmptyView().setEmptyImage(R.drawable.category_empty);
        }

    }

    private void getPrice(final List<Sku> skus) {
        StringBuilder skuIds = new StringBuilder();
        if (skus != null) {
            for (int i = 0; i < skus.size(); i++) {
                Sku bean = skus.get(i);
                if (bean != null) {
                    long id = bean.sku;
                    skuIds.append(id);
                    if (i != skus.size() - 1) {
                        skuIds.append(",");
                    }
                }
            }
        }
        HttpUtil.getSkuPrice(skuIds.toString(), new CallBack<Price>() {
            @Override
            public void onSuccess(List<Price> list) {
                if (list == null) {
                    return;
                }
                for (int i = 0; i < skus.size(); i++) {
                    Sku sku = skus.get(i);
                    for (int j = 0; j < list.size(); j++) {
                        Price price = list.get(j);
                        if (price != null && sku != null && price.skuId == sku.sku) {
                            sku.myPrice = price;
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFailure(int statusCode, String message) {
        super.onFailure(statusCode, message);
        getEmptyView().setEmptyText("暂无商品");
    }

    class DividerItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public DividerItemDecoration(Context context) {
            itemSize = ScreenUtil.dip2px(context, 1);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, itemSize, 0, itemSize);
        }
    }
}
