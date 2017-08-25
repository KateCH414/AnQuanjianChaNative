package com.jd.yyc.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created by wf on 14-4-21.
 * Fragment基类
 * 提供一个布局id即可创建一个fragment
 */
public abstract class CommonFragment extends Fragment {

    protected String TAG;

    /**
     * 全局activity
     */
    protected FragmentActivity mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = ((Object) this).getClass().getSimpleName();
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getContentView() != 0) {
            View view=inflater.inflate(getContentView(), container, false);
            ButterKnife.inject(this, view);
            return view;
        }
        return null;
    }

    protected FragmentActivity getTopActivity() {
        return mContext;
    }


    public abstract int getContentView();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
