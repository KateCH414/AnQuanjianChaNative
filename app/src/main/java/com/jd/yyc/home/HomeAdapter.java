package com.jd.yyc.home;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jd.yyc.R;
import com.jd.yyc.api.CallBack;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.api.model.Brand;
import com.jd.yyc.api.model.Floor;
import com.jd.yyc.api.model.FloorInfo;
import com.jd.yyc.api.model.Price;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.home.adapter.BrandAdapter;
import com.jd.yyc.home.adapter.ShopAdapter;
import com.jd.yyc.home.banner.HomeBannerAdapter;
import com.jd.yyc.login.PortalActivity;
import com.jd.yyc.mine.CouponCenter;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.HttpUtil;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jd.yyc.widget.CountDownTimerView;
import com.jd.yyc.widget.banner.BannerView;
import com.jd.yyc.widget.banner.HorizontalRecyclerView;
import com.jingdong.jdma.minterface.ClickInterfaceParam;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/5/18.
 */

public class HomeAdapter extends RecyclerAdapter<Object, YYCViewHolder> implements Contants {
    public static final int BANNER = 10;//banner图
    public static final int COUPON = 11;//优惠券
    public static final int SECKILL = 12;//秒杀
    public static final int BRAND = 13;//品牌推荐
    public static final int RECOMMEND = 14;//推荐品种
    public static final int SKU_TYPE = 15;//药品分类

    private ArrayList<Banner> bannerList = new ArrayList<>();//轮播图
    private ArrayList<Brand> brandList = new ArrayList<>();//合作商家
    private ArrayList<Sku> recommendList = new ArrayList<>();//推荐数据
    private ArrayList<Floor> skuTypeList = new ArrayList<>();//药品分类

    public ArrayList<Integer> dataTypeList = new ArrayList<>();//adapter数据条目


    public HomeAdapter(Context mContext) {
        super(mContext);
        setData();
    }

    //判断是否有数据,默认优惠券已经添加
    public boolean isEmptyData() {
        return dataTypeList.size() == 1 ? true : false;
    }

    public void setData() {
        this.dataTypeList.clear();
        dataTypeList.add(BANNER);
        dataTypeList.add(COUPON);
        dataTypeList.add(BRAND);
        dataTypeList.add(RECOMMEND);
        dataTypeList.add(SKU_TYPE);
    }

    public synchronized void removeData(Integer dataType) {
        try {
            int position = dataTypeList.indexOf(dataType);
            dataTypeList.remove(dataType);
            notifyItemRemoved(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dataTypeList.get(position);
    }


    @Override
    public int getItemCount() {
        return super.getItemCount() + dataTypeList.size();
    }


    public void setBannerList(List<Banner> data) {
        bannerList.clear();
        if (data != null) {
            bannerList.addAll(data);
        }
    }


    public void setBrandList(List<Brand> brandList) {
        this.brandList.clear();
        if (brandList != null) {
            this.brandList.addAll(brandList);
        }
    }


    public void setRecommendList(ArrayList<Sku> recommendList) {
        this.recommendList.clear();
        if (recommendList != null) {
            this.recommendList.addAll(recommendList);
        }
    }

    public void setSkuTypeList(List<Floor> skuTypeList) {
        this.skuTypeList.clear();
        if (skuTypeList != null) {
            this.skuTypeList.addAll(skuTypeList);
            for (int i = 0; i < skuTypeList.size(); i++) {
                Floor floor = skuTypeList.get(i);
                ArrayList<Sku> skuList = joinData(floor);
                getPrice(skuList);
            }
        }
    }


    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            return new BannerViewHolder(View.inflate(mContext, R.layout.layout_home_banner, null));
        } else if (viewType == COUPON) {
            return new CouponViewHolder(View.inflate(mContext, R.layout.layout_coupon, null));
        } else if (viewType == SECKILL) {
            return new SeckillViewHolder(View.inflate(mContext, R.layout.layout_seckill, null));
        } else if (viewType == BRAND) {
            return new BrandViewHolder(View.inflate(mContext, R.layout.layout_brand, null));
        } else if (viewType == RECOMMEND) {
            return new RecommendViewHolder(View.inflate(mContext, R.layout.layout_recommend, null));
        } else if (viewType == SKU_TYPE) {
            return new ShopTypeViewHolder(View.inflate(mContext, R.layout.layout_shop_type, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
            if (bannerList != null && !bannerList.isEmpty()) {
                ((BannerViewHolder) holder).onBind(bannerList);
            }
        } else if (holder instanceof SeckillViewHolder) {
            ((SeckillViewHolder) holder).onBind(null);
            ((SeckillViewHolder) holder).shopAdapter.clear();
            ((SeckillViewHolder) holder).shopAdapter.setData(recommendList);
            ((SeckillViewHolder) holder).shopAdapter.notifyDataSetChanged();
        } else if (holder instanceof BrandViewHolder) {
            ((BrandViewHolder) holder).brandAdapter.clear();
            ((BrandViewHolder) holder).brandAdapter.setData(brandList);
            ((BrandViewHolder) holder).brandAdapter.notifyDataSetChanged();
        } else if (holder instanceof RecommendViewHolder) {
            ((RecommendViewHolder) holder).shopAdapter.clear();
            ((RecommendViewHolder) holder).shopAdapter.setData(recommendList);
            ((RecommendViewHolder) holder).shopAdapter.notifyDataSetChanged();
            ((RecommendViewHolder) holder).onBind(null);
        } else if (holder instanceof ShopTypeViewHolder) {
            ((ShopTypeViewHolder) holder).onBind(skuTypeList);

        }
    }

    public class BannerViewHolder extends YYCViewHolder<ArrayList<Banner>> {

        @InjectView(R.id.banner_view)
        BannerView bannerView;

        private HomeBannerAdapter bannerAdapter;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(final ArrayList<Banner> data) {
            super.onBind(data);
            int width = ScreenUtil.getScreenWidth(mContext);
            int height = width * 125 / 375;
            bannerView.setLayoutParams(width, height);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) bannerView.getPageIndicator().getLayoutParams();
            bannerView.getPageIndicator().setLayoutParams(params);
            bannerAdapter = new HomeBannerAdapter(mContext);
            bannerAdapter.setData(data);
            bannerView.setAdapter(bannerAdapter);
            bannerAdapter.notifyDataSetChanged();
        }
    }

    public class CouponViewHolder extends YYCViewHolder<ArrayList<Banner>> {

        public CouponViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(final ArrayList<Banner> data) {
            super.onBind(data);
        }

        @Override
        public void onNoDoubleCLick(View v) {
            if (!Util.isLogin()) {
                PortalActivity.launch(mContext, LOGIN_FROM_COUPON);
            } else {
                CouponCenter.launch(mContext);
                //埋点
                ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
                clickInterfaceParam.page_id = "0012";
                clickInterfaceParam.page_name = "首页";
                clickInterfaceParam.event_id="yjc_android_201706262|40";
                JDMaUtil.sendClickData(clickInterfaceParam);
            }
        }
    }


    public class SeckillViewHolder extends YYCViewHolder<ArrayList<Sku>> {
        @InjectView(R.id.horizontal_recycleview)
        HorizontalRecyclerView horizontalRecyclerView;
        @InjectView(R.id.seckill_time)
        CountDownTimerView countDownTimerView;

        ShopAdapter shopAdapter;

        public SeckillViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            shopAdapter = new ShopAdapter(mContext);
            shopAdapter.setCanLoadMore(false);
            horizontalRecyclerView.setAdapter(shopAdapter);
            horizontalRecyclerView.getRecyclerView().addItemDecoration(new ShopItemDecoration(mContext));
        }

        @Override
        public void onBind(final ArrayList<Sku> data) {
            countDownTimerView.setDownTime(16000000);
//            countDownTimerView.startDownTimer();
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }


    public class BrandViewHolder extends YYCViewHolder<ArrayList<Brand>> {
        @InjectView(R.id.recyclerView_brand)
        RecyclerView brandRecyclerView;

        BrandAdapter brandAdapter;

        public BrandViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            brandAdapter = new BrandAdapter(mContext);
            brandAdapter.setCanLoadMore(false);

            brandRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
            brandRecyclerView.setAdapter(brandAdapter);
            brandRecyclerView.addItemDecoration(new ShopItemDecoration(mContext));
        }

    }

    public class RecommendViewHolder extends YYCViewHolder<ArrayList<Sku>> {
        @InjectView(R.id.horizontal_recycleview)
        HorizontalRecyclerView horizontalRecyclerView;

        ShopAdapter shopAdapter;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            shopAdapter = new ShopAdapter(mContext);
            shopAdapter.setCanLoadMore(false);
            horizontalRecyclerView.setAdapter(shopAdapter);
            horizontalRecyclerView.getRecyclerView().addItemDecoration(new ShopItemDecoration(mContext));
        }

        @Override
        public void onBind(final ArrayList<Sku> data) {
            super.onBind(data);
            horizontalRecyclerView.getRecyclerView().smoothScrollToPosition(0);
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }

    public class ShopTypeViewHolder extends YYCViewHolder<ArrayList<Floor>> {
        @InjectView(R.id.tabs)
        TabLayout tabs;
        @InjectView(R.id.horizontal_recycleview)
        HorizontalRecyclerView horizontalRecyclerView;

        ShopAdapter shopAdapter;

        public ShopTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            shopAdapter = new ShopAdapter(mContext);
            shopAdapter.setCanLoadMore(false);
            horizontalRecyclerView.setAdapter(shopAdapter);
            horizontalRecyclerView.getRecyclerView().addItemDecoration(new ShopItemDecoration(mContext));
        }

        @Override
        public void onBind(final ArrayList<Floor> data) {
            if (data == null) {
                return;
            }
            tabs.removeAllTabs();
            for (int i = 0; i < data.size(); i++) {
                Floor floor = data.get(i);
                if (floor != null && i == 0) {
                    tabs.addTab(tabs.newTab().setText(floor.name), true);
                } else if (floor != null) {
                    tabs.addTab(tabs.newTab().setText(floor.name), false);
                }
            }

            setUpIndicatorWidth(tabs, 10, 10);

            tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    //刷新数据
                    horizontalRecyclerView.getRecyclerView().smoothScrollToPosition(0);
                    Floor floor = skuTypeList.get(tab.getPosition());
                    ArrayList<Sku> skuList = joinData(floor);
                    shopAdapter.clear();
                    shopAdapter.setData(skuList);
                    shopAdapter.notifyDataSetChanged();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        }

        @Override
        public void onNoDoubleCLick(View v) {

        }
    }

    class ShopItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public ShopItemDecoration(Context context) {
            itemSize = ScreenUtil.dip2px(context, 1);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);

            if (holder.getAdapterPosition() == 0) {
                outRect.set(0, itemSize, 0, 0);
            } else if (parent.getAdapter().getItemCount() - 1 == holder.getAdapterPosition()) {
                outRect.set(itemSize, itemSize, 0, 0);
            } else {
                outRect.set(itemSize, itemSize, 0, 0);
            }
        }
    }


    private void setUpIndicatorWidth(TabLayout tabLayout, int marginLeft, int marginRight) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = layout.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.setMarginStart(ScreenUtil.dip2px(mContext, marginLeft));
                    params.setMarginEnd(ScreenUtil.dip2px(mContext, marginRight));
                }
                child.setLayoutParams(params);
                child.invalidate();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private ArrayList<Sku> joinData(Floor floor) {
        ArrayList<Sku> skuList = new ArrayList<>();
        if (floor != null && floor.floorInfos != null) {
            for (int i = 0; i < floor.floorInfos.size(); i++) {
                FloorInfo floorInfo = floor.floorInfos.get(i);
                if (floorInfo != null && floorInfo.skuList != null) {
                    skuList.addAll(floorInfo.skuList);
                }
            }
        }

        return skuList;
    }

    public void getPrice(final List<Sku> skus) {
        StringBuilder skuIds = new StringBuilder();
        if (skus != null) {
            for (int i = 0; i < skus.size(); i++) {
                Sku bean = skus.get(i);
                if (bean != null) {
                    long id = bean.sku;
                    skuIds.append(id);
                    if (i != skus.size() - 1) {
                        skuIds.append(",");
                    }
                }
            }
        }
        HttpUtil.getSkuPrice(skuIds.toString(), new CallBack<Price>() {
            @Override
            public void onSuccess(List<Price> list) {
                if (list == null) {
                    return;
                }
                for (int i = 0; i < skus.size(); i++) {
                    Sku sku = skus.get(i);
                    for (int j = 0; j < list.size(); j++) {
                        Price price = list.get(j);
                        if (price != null && sku != null && price.skuId == sku.sku) {
                            sku.myPrice = price;
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

}
