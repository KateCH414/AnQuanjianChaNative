package com.jd.yyc.widget.banner;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.jd.yyc.R;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.UIUtil;


/**
 * Created by zhangweifeng on 15-8-28.
 */
public class HorizontalRecyclerView extends LinearLayout {

    private RecyclerView recyclerView;

    public HorizontalRecyclerView(Context context) {
        super(context);
        init();
    }

    public HorizontalRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_recycleview, this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }


    public void setAdapter(RecyclerAdapter adapter) {
        if (recyclerView.getAdapter() != null) {
            recyclerView.swapAdapter(adapter, true);
        } else {
            recyclerView.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }

    public void setHeight(int height) {
        UIUtil.setLinearLayoutParams(recyclerView, ScreenUtil.getScreenWidth(getContext()), height);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
