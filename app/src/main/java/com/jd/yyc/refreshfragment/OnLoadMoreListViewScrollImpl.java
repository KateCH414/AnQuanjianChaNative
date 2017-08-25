package com.jd.yyc.refreshfragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ExpandableListView;


public class OnLoadMoreListViewScrollImpl implements AbsListView.OnScrollListener {
    //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
    protected ViewGroup refreshLayout;

    public OnLoadMoreListViewScrollImpl(ViewGroup refreshLayout) {
        this.refreshLayout = refreshLayout;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int topRowVerticalPosition = (view == null || view.getChildCount() == 0) ? 0 : view.getChildAt(0).getTop();
        refreshLayout.setEnabled(topRowVerticalPosition >= 0);
    }
}