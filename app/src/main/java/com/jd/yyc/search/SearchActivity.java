package com.jd.yyc.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.api.model.HotContainer;
import com.jd.yyc.api.model.Result;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.category.SkuFragment;
import com.jd.yyc.home.HomeAdapter;
import com.jd.yyc.home.adapter.ShopAdapter;
import com.jd.yyc.lbs.LbsActivity;
import com.jd.yyc.popup.CommonPopupWindow;
import com.jd.yyc.refreshfragment.ListLayoutManager;
import com.jd.yyc.search.adapter.FilterAdapter;
import com.jd.yyc.search.adapter.HistoryAdapter;
import com.jd.yyc.search.adapter.HotAdapter;
import com.jd.yyc.search.db.HistoryDBHelper;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jd.yyc.widget.banner.HorizontalRecyclerView;
import com.jd.yyc.widget.flow.FlowLayout;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by zhangweifeng on 2017/6/6.
 */

public class SearchActivity extends NoActionBarActivity {
    @InjectView(R.id.search_edittext)
    EditText searchEdit;
    @InjectView(R.id.btn_clear)
    ImageView clearView;
    @InjectView(R.id.horizontal_recycleview)
    HorizontalRecyclerView hotRecyclerView;
    @InjectView(R.id.empty_history)
    TextView emptyHistory;
    @InjectView(R.id.history_ll)
    LinearLayout historyLL;
    @InjectView(R.id.history_recyclerview)
    RecyclerView historyRecyclerview;
    @InjectView(R.id.del_history)
    TextView delHistory;


    private String lastKey;
    private HotAdapter hotAdapter;
    private HistoryAdapter historyAdapter;
    private ArrayList<String> historyKeys;


    public static void launch(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    public static void launch(Context context, String key) {
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lastKey = getIntent().getStringExtra("key");
        if (!TextUtils.isEmpty(lastKey)) {
            searchEdit.setText(lastKey);
        }

        initHot();
        getHistoryData();
        initHistory();
        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!Util.isFastDoubleClick()) {
                        search();
                    }
                    return true;
                }
                return false;
            }
        });

        //搜索埋点
        PvInterfaceParam pvInterfaceParam = new PvInterfaceParam();
        pvInterfaceParam.page_id = "0014";
        pvInterfaceParam.page_name = "搜索页面";
        if (!TextUtils.isEmpty(lastKey)) {
            pvInterfaceParam.map=new HashMap<>();
            pvInterfaceParam.map.put("refer_kwd", lastKey);
        }
        JDMaUtil.sendPVData(pvInterfaceParam);
    }

    private void initHot() {
        hotAdapter = new HotAdapter(mContext);
        hotAdapter.setHotClickCallback(new HotAdapter.OnHotClickCallback() {
            @Override
            public void onHotClick(String key) {
                searchEdit.setText(key);
                search();
            }
        });
        hotAdapter.setCanLoadMore(false);
        hotRecyclerView.setAdapter(hotAdapter);
        getHotData();
    }

    private void initHistory() {
        historyAdapter = new HistoryAdapter(mContext);
        historyAdapter.setHistoryClickCallback(new HistoryAdapter.OnHistoryClickCallback() {
            @Override
            public void onHistoryClick(String key) {
                searchEdit.setText(key);
                search();
            }

            @Override
            public void onClearClick(int position) {
                if (historyAdapter.getItemCount() == 0) {
                    setHistoryNoData();
                }
            }
        });
        historyAdapter.setCanLoadMore(false);
        historyRecyclerview.setLayoutManager(new ListLayoutManager(mContext));
        historyRecyclerview.addItemDecoration(new DividerItemDecoration(mContext));
        historyRecyclerview.setAdapter(historyAdapter);
        if (historyKeys != null && !historyKeys.isEmpty()) {
            historyAdapter.setData(historyKeys);
        } else {
            setHistoryNoData();


        }
    }

    private void getHotData() {
        Network.getRequestBuilder(Util.createUrl("home/hotAndChannel", ""))
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<HotContainer> result = gson.fromJson(model, new TypeToken<ResultObject<HotContainer>>() {
                            }.getType());
                            if (result != null && result.data != null && result.data.hotWords != null) {
                                hotAdapter.clear();
                                hotAdapter.setData(result.data.hotWords);
                                hotAdapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .post();
    }

    private void getHistoryData() {
        historyKeys = HistoryDBHelper.getHistoryKeys(mContext);
    }


    @OnClick(R.id.search)
    void search() {
        String key = searchEdit.getText().toString();
        if (!TextUtils.isEmpty(key)) {
            //埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0014";
            clickInterfaceParam.page_name = "搜索页面";
            clickInterfaceParam.event_id = "yjc_android_201706262|47";
            clickInterfaceParam.map = new HashMap<>();
            clickInterfaceParam.map.put("kwd", key);
            JDMaUtil.sendClickData(clickInterfaceParam);

            HistoryDBHelper.add(mContext, key);
            SearchResultActivity.launch(mContext, key);
            finish();
        }
    }

    @OnClick(R.id.del_history)
    void clearAllHistory() {
        HistoryDBHelper.clear(mContext);
        setHistoryNoData();
    }

    @OnClick(R.id.btn_clear)
    void onClear() {
        searchEdit.getText().clear();
        clearView.setVisibility(View.GONE);
    }

    @OnClick(R.id.back_view)
    void onCancel() {
        Util.hideInput(mContext, searchEdit);
        finish();
    }

    @OnTextChanged(value = R.id.search_edittext, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextAfterChanged(Editable s) {
        if (s.length() != 0) {
            clearView.setVisibility(View.VISIBLE);
        } else {
            clearView.performClick();
        }
    }

    private void setHistoryNoData() {
        historyLL.setVisibility(View.GONE);
        emptyHistory.setVisibility(View.VISIBLE);
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                Util.hideInput(mContext, searchEdit);
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
