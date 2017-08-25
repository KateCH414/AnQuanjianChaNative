package com.jd.yyc.search;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Category;
import com.jd.yyc.api.model.FilterData;
import com.jd.yyc.api.model.FilterId;
import com.jd.yyc.api.model.FilterJson;
import com.jd.yyc.api.model.HotContainer;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SearchObjCollection;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.popup.CommonPopupWindow;
import com.jd.yyc.refreshfragment.ListLayoutManager;
import com.jd.yyc.search.adapter.FilterAdapter;
import com.jd.yyc.search.adapter.FilterShowAdapter;
import com.jd.yyc.search.adapter.HistoryAdapter;
import com.jd.yyc.search.adapter.HotAdapter;
import com.jd.yyc.search.db.HistoryDBHelper;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jd.yyc.widget.banner.HorizontalRecyclerView;
import com.jd.yyc.widget.flow.FlowLayout;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by zhangweifeng1 on 2017/6/6.
 */

public class SearchResultActivity extends NoActionBarActivity implements CommonPopupWindow.ViewInterface {
    @InjectView(R.id.search_edittext)
    EditText searchEdit;
    @InjectView(R.id.btn_clear)
    ImageView clearView;
    @InjectView(R.id.page)
    public TextView pageView;

    //排序
    @InjectView(R.id.all_sort)
    TextView allSort;
    @InjectView(R.id.sale_sort)
    TextView saleSort;
    @InjectView(R.id.price_sort)
    TextView priceSort;
    @InjectView(R.id.sort_arrow)
    ImageView arrowView;

    //筛选展示
    @InjectView(R.id.filter_recycleview)
    HorizontalRecyclerView horizontalRecyclerView;
    FilterShowAdapter filterShowAdapter;

    //筛选
    @InjectView(R.id.filter)
    TextView filter;
    FlowLayout keysLayout;
    RecyclerView companyRecyclerView;
    TextView resetView;
    TextView confirmView;
    CommonPopupWindow popupWindow;
    private FilterAdapter filterAdapter;

    //搜索结果
    SearchFragment searchFragment;


    private SearchObjCollection objCollection;


    public final static String SORT_SALES_30_DESC = "sort_sales_30_desc";
    private final static String SORT_SKU_PRICE_ASC = "sort_sku_price_asc";
    private final static String SORT_SKU_PRICE_DESC = "sort_sku_price_desc";

    private String key = "";
    private String currentSort = SORT_SALES_30_DESC;//默认是销量排序，综合排序先去掉了
    private String currentFiltTypeJson;
    private View chooseView;//选中的分类View
    private Category chooseCategory;//选中的分类数据
    private ArrayList<Category> chooseCompanyList = new ArrayList<>();//选中的公司
    public boolean isSearching = true;
    public ArrayList<String> filterShowList = new ArrayList<>();//筛选展示的数据


    public static void launch(Context context, String key) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra("key", key);
        context.startActivity(intent);
    }


    @Override
    public int getContentView() {
        return R.layout.activity_search_result;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        key = getIntent().getStringExtra("key");
        searchEdit.setText(key);
        saleSort.setSelected(true);
        arrowView.setColorFilter(Color.parseColor("#F3F3F4"));
        initFilterShow();
        showFragment();

        //搜索埋点
        PvInterfaceParam pvInterfaceParam = new PvInterfaceParam();
        pvInterfaceParam.page_id = "0015";
        pvInterfaceParam.page_name = "搜索结果页";
        if (!TextUtils.isEmpty(key)) {
            pvInterfaceParam.map=new HashMap<>();
            pvInterfaceParam.map.put("kwd", key);
        }
        JDMaUtil.sendPVData(pvInterfaceParam);
    }

    private void initFilterShow() {
        filterShowAdapter = new FilterShowAdapter(mContext);
        horizontalRecyclerView.setAdapter(filterShowAdapter);
        horizontalRecyclerView.getRecyclerView().addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(ScreenUtil.dip2px(mContext, 10), 0, 0, 0);
            }
        });
    }

    private void setFilterShowData() {
        filterShowAdapter.clear();
        filterShowList.clear();
        if (chooseCompanyList.isEmpty() && chooseCategory == null) {
            filterShowAdapter.notifyDataSetChanged();
            horizontalRecyclerView.setVisibility(View.GONE);
            return;
        }

        if (chooseCategory != null) {
            filterShowList.add(chooseCategory.Name);
        }

        for (int i = 0; i < chooseCompanyList.size(); i++) {
            Category temp = chooseCompanyList.get(i);
            if (temp != null && !TextUtils.isEmpty(temp.Name)) {
                filterShowList.add(temp.Name);
            }
        }
        horizontalRecyclerView.setVisibility(View.VISIBLE);
        filterShowAdapter.setData(filterShowList);
        filterShowAdapter.notifyDataSetChanged();
    }

    private void initPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.layout_pop_filter)
                    .setWidthAndHeight(ScreenUtil.getScreenWidth(mContext) - ScreenUtil.dip2px(mContext, 50), ViewGroup.LayoutParams.MATCH_PARENT)
                    .setBackGroundLevel(0.5f)//取值范围0.0f-1.0f 值越小越暗
                    .setAnimationStyle(R.style.AnimRight)
                    .setViewOnclickListener(this)
                    .create();

            if (objCollection != null) {
                setPopupWindowData(objCollection);
            }
        }
        popupWindow.setBackGroundLevel(0.5f);
    }


    private void showFragment() {
        searchFragment = new SearchFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_search, searchFragment)
                .commitAllowingStateLoss();
        searchFragment.setSearchKey(key);
    }

    private void setSortAndFilterParams(String sort, String filtTypeJson) {
        if (searchFragment != null) {
            searchFragment.setSearchParams(key, sort, filtTypeJson);
        }
    }

    @OnClick(R.id.search_edittext)
    void search_edittext() {
        search();
    }


    @OnClick(R.id.search)
    void search() {
        isSearching = true;
        SearchActivity.launch(mContext, key);
        finish();
    }

    //重置搜索结果状态
    private void reSet() {
        isSearching = true;
        allSort.setSelected(true);
        currentSort = "";
        allSort.setSelected(true);
        saleSort.setSelected(false);
        priceSort.setSelected(false);
        arrowView.setColorFilter(Color.parseColor("#F3F3F4"));
        setSortAndFilterParams("", "");
        reSetPopupWindow();
    }

    @OnClick(R.id.all_sort)
    void onClickAllSort() {
        currentSort = "";
        allSort.setSelected(true);
        saleSort.setSelected(false);
        priceSort.setSelected(false);
        arrowView.setColorFilter(Color.parseColor("#F3F3F4"));
        setSortAndFilterParams("", currentFiltTypeJson);
    }

    @OnClick(R.id.sale_sort)
    void onClickSaleSort() {
        currentSort = SORT_SALES_30_DESC;
        allSort.setSelected(false);
        saleSort.setSelected(true);
        priceSort.setSelected(false);
        arrowView.setColorFilter(Color.parseColor("#F3F3F4"));
        setSortAndFilterParams(SORT_SALES_30_DESC, currentFiltTypeJson);
    }

    @OnClick(R.id.price_sort)
    void onClickPriceSort() {
        allSort.setSelected(false);
        saleSort.setSelected(false);
        priceSort.setSelected(true);
        arrowView.clearColorFilter();

        if (arrowView.getRotation() == 0) {
            currentSort = SORT_SKU_PRICE_DESC;
            arrowView.setRotation(180);
            setSortAndFilterParams(SORT_SKU_PRICE_DESC, currentFiltTypeJson);
        } else {
            currentSort = SORT_SKU_PRICE_ASC;
            arrowView.setRotation(0);
            setSortAndFilterParams(SORT_SKU_PRICE_ASC, currentFiltTypeJson);
        }
    }

    @OnClick(R.id.filter)
    void filter() {
        initPopupWindow();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.RIGHT, 0, 0);
    }

    public void setObjCollection(SearchObjCollection objCollection) {
        this.objCollection = objCollection;
    }


    private String creatFilterJson() {
        try {
            Gson gson = new Gson();
            FilterData filterData = new FilterData();
            filterData.data = new ArrayList<>();

            //分类
            if (chooseCategory != null) {
                FilterJson category = new FilterJson();
                category.type = "cat1_id";
                category.values = new ArrayList<>();
                FilterId filterId = new FilterId();
                filterId.id = chooseCategory.Classification;
                category.values.add(filterId);
                filterData.data.add(category);
            }

            //公司
            if (!chooseCompanyList.isEmpty()) {
                FilterJson company = new FilterJson();
                company.type = "shop_id";
                company.values = new ArrayList<>();
                for (int i = 0; i < chooseCompanyList.size(); i++) {
                    FilterId companyId = new FilterId();
                    companyId.id = chooseCompanyList.get(i).Classification;
                    company.values.add(companyId);
                }
                filterData.data.add(company);
            }

            String result = gson.toJson(filterData.data);
            if (!filterData.data.isEmpty()) {
                currentFiltTypeJson = result;
                return result;
            } else {
                currentFiltTypeJson = null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        //获得PopupWindow布局里的View
        keysLayout = (FlowLayout) view.findViewById(R.id.key_layout);
        companyRecyclerView = (RecyclerView) view.findViewById(R.id.company_recyclerView);
        //重置
        view.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reSetPopupWindow();
            }
        });
        //确认
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }

                chooseCompanyList.clear();
                if (filterAdapter != null) {
                    chooseCompanyList.addAll(filterAdapter.chooseList);
                }

                setFilterShowData();
                setSortAndFilterParams(currentSort, creatFilterJson());
            }
        });

        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

        companyRecyclerView.setLayoutManager(new ListLayoutManager(mContext));
        filterAdapter = new FilterAdapter(mContext);
        companyRecyclerView.setAdapter(filterAdapter);
        companyRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, ScreenUtil.dip2px(mContext, 10));
            }
        });
    }

    private void reSetPopupWindow() {
        if (chooseView != null) {
            chooseView.setSelected(false);
        }

        chooseView = null;
        chooseCategory = null;

        if (filterAdapter != null) {
            filterAdapter.reSet();
        }


    }

    public void setPopupWindowData(SearchObjCollection data) {
        if (data == null || popupWindow == null) {
            return;
        }
        //设置分类数据
        if (data.cat1_id != null) {
            keysLayout.setVisibility(View.VISIBLE);
            keysLayout.removeAllViews();
            for (int i = 0; i < data.cat1_id.size(); i++) {
                final View keyView = View.inflate(mContext, R.layout.layout_filter_category, null);
                final TextView textView = ((TextView) keyView.findViewById(R.id.category_tv));
                final Category category = data.cat1_id.get(i);
                if (category != null && !TextUtils.isEmpty(category.Name)) {
                    textView.setText(category.Name);
                }

                keyView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chooseView == keyView) {
                            keyView.setSelected(false);
                            chooseView = null;
                            chooseCategory = null;
                        } else {
                            keyView.setSelected(true);
                            if (chooseView != null) {
                                chooseView.setSelected(false);
                            }
                            chooseView = keyView;
                            chooseCategory = category;
                        }
                    }
                });
                FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int margin = ScreenUtil.dip2px(mContext, 12);
                params.setMargins(0, 20, margin, 0);
                keyView.setLayoutParams(params);
                keysLayout.addView(keyView, params);
            }
        }

        //设置供应商数据
        if (data.shop_id != null) {
            filterAdapter.clear();
            filterAdapter.setData(data.shop_id);
            filterAdapter.notifyDataSetChanged();
        }

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
//            clearView.setVisibility(View.VISIBLE);
        } else {
            clearView.performClick();
        }
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

}
