package com.jd.yyc.refreshfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jd.yyc.R;


/**
 * Created by wf on 14-4-1.
 */
public class LoadMoreLayout extends LinearLayout {

    private State state;
    private TextView loadText;
    private ProgressBar progressBar;
    private LinearLayout layoutLoadMore;
    private boolean needShow = true;

    public LoadMoreLayout(Context context) {
        super(context);
        LayoutInflater.from(getContext()).inflate(R.layout.layout_recylerview_load_more, this);
        loadText = (TextView) findViewById(R.id.loading);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        layoutLoadMore = (LinearLayout) findViewById(R.id.layout_loadmore);
    }

    public void setShowFooter(boolean needShow) {
        this.needShow = needShow;
//        System.out.println("LoadMoreLayout.setShowFooter----" + needShow);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setState(State.STATE_DEFAULT);
        if (!needShow) {
            layoutLoadMore.setVisibility(INVISIBLE);
        } else {
            layoutLoadMore.setVisibility(VISIBLE);
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {

        if (!needShow) {
            layoutLoadMore.setVisibility(INVISIBLE);
        }


        this.state = state;
        setVisibility(state == State.STATE_DEFAULT ? GONE : VISIBLE);
        switch (state) {
            case STATE_DEFAULT:
                break;
            case STATE_LOAD_MORE:
                loadText.setText("加载更多");
                loadText.setVisibility(VISIBLE);
                progressBar.setVisibility(INVISIBLE);
                break;
            case STATE_LOADING:
                loadText.setText("正在努力加载...");
                loadText.setVisibility(VISIBLE);
                progressBar.setVisibility(VISIBLE);
                break;
            case STATE_LOAD_ALL:
                progressBar.setVisibility(INVISIBLE);
                loadText.setVisibility(INVISIBLE);
                break;
        }
    }

    public enum State {
        STATE_DEFAULT,
        STATE_LOAD_MORE,
        STATE_LOADING,
        STATE_LOAD_ALL
    }

}
