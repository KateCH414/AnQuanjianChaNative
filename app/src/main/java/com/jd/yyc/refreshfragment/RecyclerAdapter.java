package com.jd.yyc.refreshfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wf on 15/3/18.
 * 带加载更多布局的Adapter
 */
public abstract class RecyclerAdapter<T, VH extends YYCViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LOAD_MORE_FOOTER = 100;
    public RecyclerView recyclerView;
    protected Context mContext;//上下文

    private LinkedList<T> mListItems = new LinkedList<>();//数据列表

    private boolean canLoadMore = true;//是否能加载更多

    private boolean isLoadNoData = false;//是否加载完所有数据

    private boolean isHorizontalRefresh = false;//是否横向刷新

    private LoadMoreLayout.State mState = LoadMoreLayout.State.STATE_DEFAULT;//默认加载状态

    private LoadMoreLayout loadMoreLayout;
    private boolean needShow = true;

    public RecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public int getItemCount() {
        if (canLoadMore && !isLoadNoData) {
            return mListItems.size() + 1;
        }
        return mListItems.size();
    }

    /**
     * @param position
     * @return 当前位置对象
     */
    public T getItem(int position) {
        if (canLoadMore && !isLoadNoData && position == getItemCount() - 1) {
            return null;
        }
        return mListItems.get(position);
    }


    @Override
    public int getItemViewType(int position) {
        if (canLoadMore && !isLoadNoData && position == getItemCount() - 1) {
            return LOAD_MORE_FOOTER;
        }

        return super.getItemViewType(position);
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOAD_MORE_FOOTER) {
            return onCreateViewHolder1(parent);
        }
        return onCreateViewHolder2(parent, viewType);
    }


    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            if (getItemViewType(position) == LOAD_MORE_FOOTER) {
                onBindViewHolder1(((FooterViewHolder) holder));
            } else {
                onBindViewHolder2(((VH) holder), position);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract VH onCreateViewHolder2(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder2(VH holder, int position);


    public FooterViewHolder onCreateViewHolder1(ViewGroup parent) {
//        if (isHorizontalRefresh) {
//            return new FooterViewHolder(new HorizontalLoadMoreLayout(parent.getContext()));
//        }
        loadMoreLayout = new LoadMoreLayout(parent.getContext());
        loadMoreLayout.setShowFooter(needShow);
        return new FooterViewHolder(loadMoreLayout);
    }

    public void onBindViewHolder1(FooterViewHolder holder) {
        if (isHorizontalRefresh) {
//            holder.horizontalLoadMoreLayout.setState(mState);
        } else {
            holder.loadMoreLayout.setState(mState);
        }
    }

    public List<T> getList() {

        return mListItems;
    }

    /**
     * @return 是否为空
     */
    public boolean isEmpty() {
        return mListItems.size() == 0;
    }

    /**
     * 清理数据域
     */
    public void clear() {
        mListItems.clear();
    }

    /**
     * 为数据域添加多个数据
     *
     * @param data
     */
    public void setData(List<T> data) {
        if (data != null) {
            mListItems.addAll(data);
        }
    }

    /**
     * @param data
     */
    public void setData(T data) {
        if (data != null) {
            mListItems.add(data);
        }
    }

    /**
     * @param position
     * @return 移除当前位置对象
     */
    public T removeItem(int position) {
        return mListItems.remove(position);
    }

    /**
     * 将数据添加到数据域头部
     *
     * @param data
     */
    public void setDataFirst(T data) {
        if (data != null) {
            mListItems.addFirst(data);
        }
    }

    /**
     * @param data
     * @return 是否成功移除该对象
     */
    public boolean removeItem(T data) {
        if (data != null) {
            return mListItems.remove(data);
        }
        return false;
    }

    /**
     * 将数据添加到数据域头部
     *
     * @param data
     */
    public void setDataFirst(List<T> data) {
        if (data != null) {
            for (int i = data.size() - 1; i >= 0; i--) {
                T t = data.get(i);
                mListItems.addFirst(t);
            }
        }
    }

    /**
     * 设置底部为加载更多
     */
    public void setLoadMore() {
        if (canLoadMore) {
            mState = LoadMoreLayout.State.STATE_LOAD_MORE;
            isLoadNoData = false;
            notifyItemChanged(getItemCount() - 1);
        }
    }

    /**
     * 设置底部为加载中...
     */
    public void setLoadingMore() {
        if (canLoadMore) {
            mState = LoadMoreLayout.State.STATE_LOADING;
            notifyItemChanged(getItemCount() - 1);
        }
    }

    /**
     * 设置底部为加载全部 并移除布局
     */
    public void setLoadAll() {
        if (canLoadMore && !isLoadNoData) {
            mState = LoadMoreLayout.State.STATE_LOAD_ALL;
            int position = getItemCount();
            notifyItemRemoved(position - 1);
            isLoadNoData = true;
        }
    }


    /**
     * @return 是否正在加载中
     */
    public boolean isLoading() {
        if (canLoadMore) {
            return mState == LoadMoreLayout.State.STATE_LOADING;
        }
        return false;
    }

    public void setNeedShowFooter(boolean needShow) {
        this.needShow = needShow;
    }

    /**
     * 设置是否需要footer
     *
     * @param canLoadMore
     */
    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    public boolean canLoadMore() {
        return canLoadMore;
    }

    /**
     * 设置是否横向刷新
     *
     * @param isHorizontalRefresh
     */
    public void setHorizontalRefresh(boolean isHorizontalRefresh) {
        this.isHorizontalRefresh = isHorizontalRefresh;
    }


    /**
     * 加载更多Footer
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreLayout loadMoreLayout;
//        public HorizontalLoadMoreLayout horizontalLoadMoreLayout;

        FooterViewHolder(View itemView) {
            super(itemView);
            if (isHorizontalRefresh) {
//                this.horizontalLoadMoreLayout = (HorizontalLoadMoreLayout) itemView;
            } else {
                this.loadMoreLayout = (LoadMoreLayout) itemView;
            }
        }
    }
}




