package com.jd.yyc.refreshfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.project.lib.andlib.pulltorefresh.PtrHeader;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Result;
import com.jd.yyc.api.model.YYCBase;
import com.jd.yyc.base.CommonFragment;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.event.EventRefreshRecyclerView;
import com.jd.yyc.util.FileUtil;
import com.jd.yyc.util.ToastUtil;

import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wf on 15/7/3.
 */
public abstract class BaseRefreshFragment<T> extends CommonFragment
        implements OnCreateRecyclerListener2 {

    @InjectView(R.id.refresh_layout)
    PtrFrameLayout refreshLayout;
    @InjectView(android.R.id.list)
    RecyclerView recyclerView;
    @InjectView(android.R.id.empty)
    EmptyView emptyView;

    public String TAG;
    protected int PAGE = 1;//分页页码
    protected long TS = 0;//预留
    protected String cachepath;//api缓存路径
    protected RecyclerAdapter adapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected boolean isLoadAll = false;
    private boolean needCache = false;
    private OnLoadMoreScrollImpl onLoadMoreScroll;


    protected View.OnClickListener refreshClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {//先不带点击图片刷新
//            emptyView.setVisibility(View.GONE);
//            setRefreshing(true);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = ((Object) this).getClass().getSimpleName();
        //缓存配置
//        String uid = SharePreferenceUtil.getUid(mContext);
        cachepath = getActivity().getCacheDir() + "/cache_" + "_" + TAG;
        EventBus.getDefault().register(this);
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_swipe_recyclerview;
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        return new ListLayoutManager(mContext);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = createAdapter();
        layoutManager = createLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        onLoadMoreScroll = new OnLoadMoreScrollImpl(layoutManager, refreshLayout, this);
        recyclerView.addOnScrollListener(onLoadMoreScroll);

        //刷新
        PtrHeader header = new PtrHeader(mContext);
        refreshLayout.setHeaderView(header);
        refreshLayout.addPtrUIHandler(header);
        refreshLayout.disableWhenHorizontalMove(true);
        refreshLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);//原生框架的该方法判断view能否滑动无效
//                return checkCanRefresh();
                return !ViewCompat.canScrollVertically(recyclerView, -1);
            }


            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                onRefresh();
            }
        });

    }

    //该方法有自己实现的可以判断能否滑动
    public boolean checkCanRefresh() {
        if (layoutManager.getItemCount() == 0) return true;
        int firstVisiblePosition = -1;
        if (layoutManager instanceof LinearLayoutManager) {
            firstVisiblePosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }

        if (firstVisiblePosition == 0) {
            View firstVisibleView = layoutManager.findViewByPosition(firstVisiblePosition);
            int top = firstVisibleView.getTop();
            return top >= 0;
        } else {
            return false;
        }

        //it's also work.(but I can't test enough)
        //since support library 22.1, suggest using ViewCompat.canScrollVertically()
        //but not recyclerView reference
        //return !ViewCompat.canScrollVertically(recyclerView, -1);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(EventRefreshRecyclerView event) {
        if (event != null && adapter.isEmpty()) {
            setRefreshing(true);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (needCache) {
            //设置缓存
            String data = getDataCache();
            if (data != null) {
                onSuccess(data);
            }
        }
        setRefreshing(true);
    }


    /**
     * fragment启动默认执行网络请求
     */
    @Override
    public void onPreDoNet() {
        PAGE = 1;
        TS = 0;
        doNet();
    }

    public void onRefresh() {
        PAGE = 1;
        TS = 0;
        doNet();
    }

    /**
     * 域名设置，默认取主域名
     */
    @Override
    public String getBaseUrl() {
        return null;
    }

    /**
     * 请求参数
     */
    @Override
    public Map<String, String> getParams() {
        return null;
    }

    /**
     * 请求路径
     */
    @Override
    public String getPath() {
        return null;
    }


    /**
     * 执行网络请求
     */
    @Override
    public void doNet() {
        emptyView.setVisibility(View.GONE);

        Network.getRequestBuilder(createUrl())
                .params(getParams())
                .tag(TAG)
                .headers(getHeaders())
                .success(new Success() {

                    @Override
                    public void success(String model) {
                        setRefreshing(false);
                        try {
                            Gson gson = new Gson();
                            YYCBase result = gson.fromJson(model, new TypeToken<YYCBase>() {
                            }.getType());
                            if (result != null) {
                                if (result.code == YYCBase.STATUS_SUCCESS) {
                                    BaseRefreshFragment.this.onSuccess(model);
                                } else {
                                    BaseRefreshFragment.this.onFailure(result.code, result.msg);
                                }
                            } else {
                                BaseRefreshFragment.this.onFailure(YYCBase.STATUS_NO_DATA, model);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            BaseRefreshFragment.this.onFailure(YYCBase.STATUS_NO_DATA, model);
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        setRefreshing(false);
                        try {
                            BaseRefreshFragment.this.onFailure(statusCode, errorMessage);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .post();
    }


    /**
     * header设置
     */
    @Override
    public Map<String, String> getHeaders() {
        return null;
    }

    /**
     * 是否需要上拉加载
     */
    @Override
    public void setCanLoadMore(boolean canLoadMore) {
        if (adapter != null) {
            adapter.setCanLoadMore(canLoadMore);
        }
    }


    @Override
    public void onLoadMore() {
        if (adapter.canLoadMore() && !adapter.isLoading() && !refreshLayout.isRefreshing()) {
            PAGE++;
            adapter.setLoadingMore();
            doNet();
        }
    }

    /**
     * 设置接口回调成功后空布局和加载更多的状态
     */
    public void setSuccessStatus(List data) {
        setSuccessStatus(data, 0);
    }

    /**
     * 设置接口回调成功后空布局和加载更多的状态,带入总页数
     */
    public void setSuccessStatus(List data, long pageCount) {
        if (PAGE == 1) {
            if (adapter.isEmpty() && (data == null || data.isEmpty()) && emptyView != null) {
                adapter.setLoadAll();
                isLoadAll = true;
                emptyView.showEmpty(YYCBase.STATUS_NO_DATA, null, null);
                return;
            } else {
                adapter.clear();
                emptyView.setVisibility(View.GONE);
                notifyDataSetChanged();
//                setDataCache(data.toString());//需要对象序列化，待测试
            }
        }
        isLoadAll = false;
        int positionStart = adapter.getItemCount() - 1;
        if (data == null || data.isEmpty() || PAGE >= pageCount) {
            adapter.setLoadAll();
            isLoadAll = true;
        } else {
            isLoadAll = false;
            adapter.setLoadMore();
            adapter.notifyItemRangeInserted(positionStart, data.size());
        }
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void onFailure(int statusCode, String message) {
        if (PAGE > 1) {
            PAGE--;
            if (statusCode == Result.STATUS_NO_DATA) {
                adapter.setLoadAll();
                isLoadAll = true;
            } else {
                isLoadAll = false;
                adapter.setLoadMore();
                ToastUtil.show(mContext, message);
            }
        } else {
            if (adapter.isEmpty()) {
                getEmptyView().showEmpty(statusCode, message, refreshClick);
                adapter.setLoadAll();
                isLoadAll = true;
            } else {
                isLoadAll = false;
                adapter.setLoadMore();
                ToastUtil.show(mContext, message);
            }
        }
    }


    @Override
    public String getDataCache() {
        try {
            String t = (String) FileUtil.readFile(cachepath);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setDataCache(String data) {
        FileUtil.writeFile(cachepath, data, false);
    }

    public String createUrl() {
        StringBuilder url = new StringBuilder("");
        if (TextUtils.isEmpty(getBaseUrl())) {
            url.append(HttpContants.BaseUrl);
        } else {
            url.append(getBaseUrl());
        }
        url.append("/");
        url.append(getPath());
        return url.toString();
    }

    public void setData(List<?> data) {
        if (adapter != null) {
            adapter.setData(data);
        }
    }


    /**
     * 是否需要缓存
     */
    public void needCache(boolean needCache) {
        this.needCache = needCache;
    }

    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void clear() {
        if (adapter != null) {
            adapter.clear();
        }
    }

    public RecyclerAdapter getAdapter() {
        return adapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public EmptyView getEmptyView() {
        return emptyView;
    }

    /**
     * 是否禁止下拉刷新
     */
    public void setRefreshEnabled(boolean enabled) {
        if (refreshLayout != null) {
            refreshLayout.setEnabled(enabled);
        }
    }

    public PtrFrameLayout getRefreshLayout() {
        return refreshLayout;
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshing) {
            refreshLayout.autoRefresh(true);
        } else {
            refreshLayout.refreshComplete();
        }
    }

}
