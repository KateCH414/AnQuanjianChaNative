package com.jd.yyc.search;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.RequestBuilder;
import com.jd.yyc.R;
import com.jd.yyc.api.CallBack;
import com.jd.yyc.api.model.Price;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.Search;
import com.jd.yyc.api.model.SearchHead;
import com.jd.yyc.api.model.SearchSku;
import com.jd.yyc.category.adapter.SkuAdapter;
import com.jd.yyc.event.EventOnScroll;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.search.adapter.SearchSkuAdapter;
import com.jd.yyc.util.HttpUtil;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;


/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class SearchFragment extends BaseRefreshFragment {
    private String key;
    private String filtTypeJson;
    private String sort = SearchResultActivity.SORT_SALES_30_DESC;
    private long currentPageSize;
    private long currentPageCount;


    private void getPrice(final List<SearchSku> searchSkus) {
        StringBuilder skuIds = new StringBuilder();
        if (searchSkus != null) {
            for (int i = 0; i < searchSkus.size(); i++) {
                SearchSku bean = searchSkus.get(i);
                if (bean != null) {
                    long id = bean.sku_id;
                    skuIds.append(id);
                    if (i != searchSkus.size() - 1) {
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
                for (int i = 0; i < searchSkus.size(); i++) {
                    SearchSku searchSku = searchSkus.get(i);
                    for (int j = 0; j < list.size(); j++) {
                        Price price = list.get(j);
                        if (price != null && searchSku != null && price.skuId == searchSku.sku_id) {
                            searchSku.myPrice = price;
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    public void setSearchKey(String key) {
        this.key = key;
    }

    public void setSearchParams(String key, String sort, String filtTypeJson) {
        this.key = key;
        this.filtTypeJson = filtTypeJson;
        this.sort = sort;
        if (getRefreshLayout().isRefreshing()) {
            setRefreshing(false);
        }
        RequestBuilder.removeCall(createUrl());
        setRefreshing(true);
    }


    @Override
    public RecyclerAdapter createAdapter() {
        return new SearchSkuAdapter(mContext);
    }


    @Override
    public String getPath() {
        return "search/index";
    }


    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> params = new HashMap<>();
        params.put("page", String.valueOf(PAGE));
        params.put("key", key);
        if (!TextUtils.isEmpty(filtTypeJson)) {
            params.put("filtTypeJson", filtTypeJson);
        }
        if (!TextUtils.isEmpty(sort)) {
            params.put("sort", sort);
        }
//        params.put("pageSize", String.valueOf(20));
        return params;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext));
        getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ((SearchResultActivity) mContext).pageView.setVisibility(View.VISIBLE);
                if (dy < 0 && currentPageSize > 0 && currentPageCount > 0) {
                    int firstVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
                    int pageIndex = firstVisibleItemPosition / (int) currentPageSize + 1;
                    ((SearchResultActivity) mContext).pageView.setText(pageIndex + "/" + currentPageCount);
                } else if (dy > 0 && currentPageSize > 0 && currentPageCount > 0) {
                    int lastVisibleItemPosition = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
                    int pageIndex = lastVisibleItemPosition / (int) currentPageSize + 1;
                    ((SearchResultActivity) mContext).pageView.setText(pageIndex + "/" + currentPageCount);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    ((SearchResultActivity) mContext).pageView.removeCallbacks(null);
                    ((SearchResultActivity) mContext).pageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((SearchResultActivity) mContext).pageView.setVisibility(View.GONE);
                        }
                    }, 3000);
                }
            }
        });
    }


    @Override
    public void onSuccess(String data) {
        try {
            getEmptyView().setVisibility(View.GONE);

            Gson gson = new Gson();
            ResultObject<Search> result = gson.fromJson(data, new TypeToken<ResultObject<Search>>() {
            }.getType());
            if (result != null && result.data != null && result.data.Paragraph != null) {
                if (result.data.Head != null && result.data.Head.Summary != null && result.data.Head.Summary.Page != null) {
                    setSuccessStatus(result.data.Paragraph, result.data.Head.Summary.Page.PageCount);
                } else {
                    setSuccessStatus(null, 0);
                }
                getEmptyView().setEmptyImage(R.drawable.search_empty);
                ((SearchSkuAdapter) getAdapter()).setImgDomain(result.data.imgDomain);
                setData(result.data.Paragraph);
                getPrice(result.data.Paragraph);
                notifyDataSetChanged();

                //搜索埋点
                PvInterfaceParam pvInterfaceParam = new PvInterfaceParam();
                pvInterfaceParam.page_id = "0015";
                pvInterfaceParam.page_name = "搜索结果页";
                pvInterfaceParam.map=new HashMap<>();
                pvInterfaceParam.map.put("kwd", key);
                pvInterfaceParam.map.put("page_num", PAGE+"");
                JDMaUtil.sendPVData(pvInterfaceParam);
            } else {//数据为空
                setSuccessStatus(null, 0);
                getEmptyView().setEmptyImage(R.drawable.search_empty);
            }

            //设置筛选数据
            if (((SearchResultActivity) mContext).isSearching && result != null && result.data != null && result.data.ObjCollection != null) {
                ((SearchResultActivity) mContext).setObjCollection(result.data.ObjCollection);
                ((SearchResultActivity) mContext).setPopupWindowData(result.data.ObjCollection);
            }
            ((SearchResultActivity) mContext).isSearching = false;

            if (result != null && result.data != null) {
                SearchHead head = result.data.Head;
                if (head != null && head.Summary != null && head.Summary.Page != null) {
                    long pageIndex = head.Summary.Page.PageIndex;
                    long pageCount = head.Summary.Page.PageCount;
                    currentPageSize = head.Summary.Page.PageSize;
                    currentPageCount = pageCount;
                    ((SearchResultActivity) mContext).pageView.setVisibility(View.VISIBLE);
                    ((SearchResultActivity) mContext).pageView.setText(pageIndex + "/" + pageCount);
                    ((SearchResultActivity) mContext).pageView.removeCallbacks(null);
                    ((SearchResultActivity) mContext).pageView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((SearchResultActivity) mContext).pageView.setVisibility(View.GONE);
                        }
                    }, 3000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, String message) {
        super.onFailure(statusCode, message);
        ((SearchResultActivity) mContext).isSearching = false;
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
