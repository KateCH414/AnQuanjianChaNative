package com.jd.yyc.category;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.huajianjiang.expandablerecyclerview.util.Logger;
import com.github.huajianjiang.expandablerecyclerview.widget.ExpandableAdapter;
import com.github.huajianjiang.expandablerecyclerview.widget.ParentViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Brand;
import com.jd.yyc.api.model.Category;
import com.jd.yyc.api.model.CategoryTree;
import com.jd.yyc.api.model.Result;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.base.CommonFragment;
import com.jd.yyc.category.adapter.CategoryThirdAdapter;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.event.EventOnHomeRefresh;
import com.jd.yyc.event.EventOnLbsChange;
import com.jd.yyc.event.EventOnScroll;
import com.jd.yyc.home.HomeAdapter;
import com.jd.yyc.search.SearchActivity;
import com.jd.yyc.util.L;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by zhangweifeng1 on 2017/5/27.
 */

public class CategoryFragment extends CommonFragment implements CategoryAdapter.OnCategoryClickListener {
    @InjectView(R.id.category_recyclerview)
    RecyclerView categoryRecyclerView;
    @InjectView(R.id.brand_recyclerview)
    RecyclerView categortThirdRecyclerview;
    @InjectView(R.id.up_view)
    LinearLayout upView;


    private CategoryAdapter mAdapter;
    private RecyclerView.ItemAnimator mItemAnimator;
    private List<CategoryTree> mData = new ArrayList<>();
    private SkuFragment skuFragment;

    private CategoryThirdAdapter categoryThirdAdapter;

    @Override
    public int getContentView() {
        return R.layout.fragment_category;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        getCategoryData();
    }


    @Override
    public void onResume() {
        super.onResume();
        //分类页面埋点
        PvInterfaceParam pvInterfaceParam = new PvInterfaceParam();
        pvInterfaceParam.page_id = "0004";
        pvInterfaceParam.page_name = "分类列表页面";
        JDMaUtil.sendPVData(pvInterfaceParam);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 保存 ExpandableRecyclerView 状态
        if (mAdapter != null) {
            mAdapter.onRestoreInstanceState(savedInstanceState);
        }
    }

    private void init() {
        //一二级分类
        mAdapter = new CategoryAdapter(mContext, mData);
        mAdapter.setExpandCollapseMode(ExpandableAdapter.ExpandCollapseMode.MODE_DEFAULT);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        categoryRecyclerView.setAdapter(mAdapter);
        categoryRecyclerView.addItemDecoration(mAdapter.getItemDecoration());
        mItemAnimator = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? new CircularRevealItemAnimator() : new DefaultItemAnimator();
        categoryRecyclerView.setItemAnimator(mItemAnimator);
        mAdapter.addParentExpandableStateChangeListener(new ParentExpandableStateChangeListener());
        mAdapter.addParentExpandCollapseListener(new ParentExpandCollapseListener());
        mAdapter.setOnCategoryClickListener(this);
        mAdapter.expandParent(0);

        //三级分类
        categoryThirdAdapter = new CategoryThirdAdapter(mContext);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        categortThirdRecyclerview.setLayoutManager(gridLayoutManager);
        categoryThirdAdapter.setCanLoadMore(false);
        categortThirdRecyclerview.setAdapter(categoryThirdAdapter);
        categoryThirdAdapter.setOnItemClickListener(new CategoryThirdAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, long cat2Id) {
                if (categoryThirdAdapter.getItem(postion) != null) {
                    long cat3Id = categoryThirdAdapter.getItem(postion).id;
                    refreshSkuData(0, 0, cat3Id);
                    //埋点
                    ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
                    clickInterfaceParam.page_id = "0004";
                    clickInterfaceParam.page_name = "分类列表页面";
                    clickInterfaceParam.event_id = "yjc_android_201706262|18";
                    clickInterfaceParam.map = new HashMap<>();
                    clickInterfaceParam.map.put("item_third_cate_id", cat3Id + "");
                    JDMaUtil.sendClickData(clickInterfaceParam);
                } else {//展示全部三级，就是二级
                    refreshSkuData(0, cat2Id, 0);
                }
            }

            @Override
            public void onMoreClick(View view) {
                upView.setVisibility(View.VISIBLE);
            }
        });


        //列表Fragment
        skuFragment = new SkuFragment();
        if (!mData.isEmpty()) {
            skuFragment.setId(mData.get(0).id, 0, 0);
        }
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_sku, skuFragment)
                .commitAllowingStateLoss();
    }

    public void refreshSkuData(long cat1Id, long cat2Id, long cat3Id) {
        skuFragment.clear();
        skuFragment.setId(cat1Id, cat2Id, cat3Id);
        skuFragment.setRefreshing(true);
        skuFragment.onRefresh();
    }

    public void onEvent(EventOnScroll event) {
        if (upView.getVisibility() == View.VISIBLE) {
            onClikUp();
        }
    }

    @OnClick(R.id.up_view)
    public void onClikUp() {
        if (categoryThirdAdapter != null) {
            categoryThirdAdapter.setFoldStatus();
        }
        upView.setVisibility(View.GONE);
    }

    @OnClick(R.id.search)
    void toSearch() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        SearchActivity.launch(mContext);

        //埋点
        ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
        clickInterfaceParam.page_id = "0004";
        clickInterfaceParam.page_name = "分类列表页面";
        clickInterfaceParam.event_id = "yjc_android_201706262|15";
        JDMaUtil.sendClickData(clickInterfaceParam);
    }

    /**
     * 获取分类数据
     */
    private void getCategoryData() {

        Network.getRequestBuilder(Util.createUrl("list/catTree", ""))
                .success(new Success() {

                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            Result<CategoryTree> result = gson.fromJson(model, new TypeToken<Result<CategoryTree>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                mData.addAll(result.data);
                                init();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                    }
                })
                .post();
    }

    @Override
    public void onChildClick(List<CategoryTree> categoryTreeList, long id) {
        //埋点
        ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
        clickInterfaceParam.page_id = "0004";
        clickInterfaceParam.page_name = "分类列表页面";
        clickInterfaceParam.event_id = "yjc_android_201706262|17";
        clickInterfaceParam.map = new HashMap<>();
        clickInterfaceParam.map.put("item_second_cate_id", id + "");
        JDMaUtil.sendClickData(clickInterfaceParam);

        if (categoryTreeList != null && categoryTreeList.size() > 0) {
            categortThirdRecyclerview.setVisibility(View.VISIBLE);
            categoryThirdAdapter.clear();
            categoryThirdAdapter.reSet();
            upView.setVisibility(View.GONE);
            categoryThirdAdapter.setCategoryData(categoryTreeList, id);
            categoryThirdAdapter.notifyDataSetChanged();

            refreshSkuData(0, id, 0);//展示二级分类的所有数据
        } else {
            categortThirdRecyclerview.setVisibility(View.GONE);
        }
    }

    @Override
    public void onParentClick(long id) {
        refreshSkuData(id, 0, 0);//展示一级分类的所有数据
        //埋点
        ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
        clickInterfaceParam.page_id = "0004";
        clickInterfaceParam.page_name = "分类列表页面";
        clickInterfaceParam.event_id = "yjc_android_201706262|16";
        clickInterfaceParam.map = new HashMap<>();
        clickInterfaceParam.map.put("item_first_cate_id", id + "");
        JDMaUtil.sendClickData(clickInterfaceParam);
    }

    private class ParentExpandableStateChangeListener
            implements ExpandableAdapter.OnParentExpandableStateChangeListener {
        @Override
        public void onParentExpandableStateChanged(RecyclerView rv, ParentViewHolder pvh,
                                                   int position, boolean expandable) {
            Logger.e(TAG, "onParentExpandableStateChanged=" + position + "," + rv.getTag());
            if (pvh == null) return;
            final ImageView arrow = pvh.getView(R.id.arrow);
            if (expandable && arrow.getVisibility() != View.VISIBLE) {
                arrow.setVisibility(View.VISIBLE);
                arrow.setRotation(pvh.isExpanded() ? 180 : 0);
            } else if (!expandable && arrow.getVisibility() == View.VISIBLE) {
                arrow.setVisibility(View.GONE);
            }
        }
    }

    private class ParentExpandCollapseListener
            implements ExpandableAdapter.OnParentExpandCollapseListener {
        @Override
        public void onParentExpanded(RecyclerView rv, ParentViewHolder pvh, int position,
                                     boolean pendingCause, boolean byUser) {
            Logger.e(TAG, "onParentExpanded=" + position + "," + rv.getTag() + ",byUser=" + byUser);
            if (pvh == null) return;
            ImageView arrow = pvh.getView(R.id.arrow);
            if (arrow.getVisibility() != View.VISIBLE) return;
            float currRotate = arrow.getRotation();
            //重置为从0开始旋转
            if (currRotate == 360) {
                arrow.setRotation(0);
            }
            if (pendingCause) {
                arrow.setRotation(180);
            } else {
                arrow.animate().rotation(180).setDuration(mItemAnimator.getAddDuration() + 180)
                        .start();
            }
        }

        @Override
        public void onParentCollapsed(RecyclerView rv, ParentViewHolder pvh, int position,
                                      boolean pendingCause, boolean byUser) {
            Logger.e(TAG,
                    "onParentCollapsed=" + position + ",tag=" + rv.getTag() + ",byUser=" + byUser);

            if (pvh == null) return;
            ImageView arrow = pvh.getView(R.id.arrow);
            if (arrow.getVisibility() != View.VISIBLE) return;
            float currRotate = arrow.getRotation();
            float rotate = 360;
            //未展开完全并且当前旋转角度小于180，逆转回去
            if (currRotate < 180) {
                rotate = 0;
            }
            if (pendingCause) {
                arrow.setRotation(rotate);
            } else {
                arrow.animate().rotation(rotate)
                        .setDuration(mItemAnimator.getRemoveDuration() + 180).start();
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAdapter != null) {
            mAdapter.onSaveInstanceState(outState);
        }
    }

    public void onEvent(EventOnHomeRefresh event) {
        if (event != null && event.type == EventOnHomeRefresh.CATEGORY) {
            if (mData.isEmpty()) {
                getCategoryData();
            }
        }
    }
}
