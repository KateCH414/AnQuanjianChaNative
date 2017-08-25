package com.jd.yyc.mine;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jd.project.lib.andlib.utils.Logger;
import com.jd.yyc.R;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.ScreenUtil;

/**
 * Created by jiahongbin on 2017/5/30.
 */

public class TotalFragment  extends BaseRefreshFragment {

    public static int type;

    @Override
    public RecyclerAdapter createAdapter() {
        return  new TotalAdapter(mContext);
    }

    @Override
    public void onSuccess(String data) {

    }

    public static TotalFragment newInstance(int type) {
        TotalFragment fragment = new TotalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }





    @Override
    public int getContentView() {
        return R.layout.fragment_mine_total;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getInt("type");
        }
        Logger.e("type",type+"");



        getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext));
        setCanLoadMore(false);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    class DividerItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public DividerItemDecoration(Context context) {
            itemSize = ScreenUtil.dip2px(context, 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);

            if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 1) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, itemSize, 0, itemSize);
            }
        }
    }

}
