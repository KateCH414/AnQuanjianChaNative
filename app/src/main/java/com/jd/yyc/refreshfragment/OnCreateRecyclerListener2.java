package com.jd.yyc.refreshfragment;


import android.support.v7.widget.RecyclerView;

import com.jd.yyc.api.model.Result;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by wf on 15/7/3.
 */
public interface OnCreateRecyclerListener2 {

    void onPreDoNet();

    void doNet();

    Map<String, String> getHeaders();

    Map<String, String> getParams();

    String getBaseUrl();

    String getPath();


    void setCanLoadMore(boolean canLoadMore);

    void onLoadMore();

    RecyclerAdapter createAdapter();

    RecyclerView.LayoutManager createLayoutManager();

    String getDataCache();

    void setDataCache(String data);

    void onSuccess(String data);

    void onFailure(int statusCode, String message);
}

