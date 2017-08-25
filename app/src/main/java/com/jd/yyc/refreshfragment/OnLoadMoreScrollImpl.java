package com.jd.yyc.refreshfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jd.yyc.widget.swipe.SwipeRefreshLayout;

import in.srain.cube.views.ptr.PtrFrameLayout;


public class OnLoadMoreScrollImpl extends RecyclerView.OnScrollListener {
    //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
    protected boolean isSlidingToLast = true;//默认true
    protected RecyclerView.LayoutManager manager;
    protected OnCreateRecyclerListener2 listener;
    protected ViewGroup refreshLayout;


    public OnLoadMoreScrollImpl(RecyclerView.LayoutManager linearLayoutManager, ViewGroup refreshLayout, OnCreateRecyclerListener2 listener) {
        this.manager = linearLayoutManager;
        this.listener = listener;
        this.refreshLayout = refreshLayout;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        // 当不滚动时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            //获取最后一个完全显示的ItemPosition
            int lastVisibleItem = 0;
            if (manager instanceof LinearLayoutManager) {
                lastVisibleItem = ((LinearLayoutManager) manager).findLastCompletelyVisibleItemPosition();
            }
            int totalItemCount = manager.getItemCount();


            //预加载 成功时候返回
//            if (lastVisibleItem == totalItemCount - 5 && isSlidingToLast) {
//                listener.onLoadMore();
//                return;
//            }

            // 判断是否滚动到底部，并且是向右滚动
            if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                //加载更多功能的代码
                listener.onLoadMore();
            }
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
        if (dx >= 0 || dy >= 0) {
            //大于0表示，正在向右滚动
            isSlidingToLast = true;
        } else {
            //小于等于0 表示停止或向左滚动
            isSlidingToLast = false;
        }

//        if (isRefreshEnable) {
//            int topRowVerticalPosition = (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
//            refreshLayout.setEnabled(topRowVerticalPosition >= 0);
//        } else {
//            refreshLayout.setEnabled(false);
//        }
    }

}