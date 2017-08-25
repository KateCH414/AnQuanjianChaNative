package com.jd.yyc.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.CallBack;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.api.model.Brand;
import com.jd.yyc.api.model.Floor;
import com.jd.yyc.api.model.FloorInfo;
import com.jd.yyc.api.model.LBS;
import com.jd.yyc.api.model.Price;
import com.jd.yyc.api.model.Result;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.event.EventLoginSuccess;
import com.jd.yyc.event.EventLogout;
import com.jd.yyc.event.EventOnLbsChange;
import com.jd.yyc.lbs.LbsActivity;
import com.jd.yyc.login.PortalActivity;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.search.SearchActivity;
import com.jd.yyc.util.HttpUtil;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.SharePreferenceUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jd.yyc.widget.DragFloatActionButton;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangweifeng1 on 2017/5/18.
 */

public class HomeFragment extends BaseRefreshFragment implements View.OnClickListener {
    TextView lbsCityView;
    TextView searchView;
    DragFloatActionButton floatingActionButton;
    LBS currentLbs = new LBS();//当前的城市
    private MyLocationListener mMyLocationListener;
    private LocationClient locationClient;


    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //获取历史用户定位信息
        String historyCity = SharePreferenceUtil.getLbsName(mContext);
        String historyName = "";
        String historyId = "";
        if (!TextUtils.isEmpty(historyCity)) {
            if (historyCity.indexOf("/") > 0 && historyCity.indexOf("/") < historyCity.length() - 1) {
                historyName = historyCity.substring(0, historyCity.indexOf("/"));
                historyId = historyCity.substring(historyCity.indexOf("/") + 1);
            }
        }
        if (!TextUtils.isEmpty(historyName) && !TextUtils.isEmpty(historyId)) {
            currentLbs.name = historyName;
            currentLbs.id = Long.parseLong(historyId);
        }

        super.onViewCreated(view, savedInstanceState);
        lbsCityView = ((TextView) view.findViewById(R.id.lbs_city));
        searchView = ((TextView) view.findViewById(R.id.search));
        floatingActionButton = ((DragFloatActionButton) view.findViewById(R.id.float_button));
        lbsCityView.setOnClickListener(this);
        searchView.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext));
        setCanLoadMore(false);

        if (!Util.isLogin()) {
            floatingActionButton.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(historyName)) {
            lbsCityView.setText(historyName);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //首页PV埋点
        PvInterfaceParam pvInterfaceParam = new PvInterfaceParam();
        pvInterfaceParam.page_id = "0012";
        pvInterfaceParam.page_name = "首页";
        JDMaUtil.sendPVData(pvInterfaceParam);
    }

    private void initLocation() {
        mMyLocationListener = new MyLocationListener();
        locationClient = new LocationClient(mContext.getApplication());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setIsNeedAddress(true);
        locationClient.registerLocationListener(mMyLocationListener);
        locationClient.setLocOption(option);
        locationClient.start();
    }


    @Override
    public RecyclerAdapter createAdapter() {
        return new HomeAdapter(mContext);
    }

    @Override
    public void doNet() {
        getEmptyView().setVisibility(View.GONE);
        ((HomeAdapter) adapter).setData();
        getBannerData();
        getBrandData();
        getAreaData();
    }


    private synchronized void showNodata() {
        if (((HomeAdapter) adapter).isEmptyData()) {
            ((HomeAdapter) adapter).dataTypeList.clear();
            notifyDataSetChanged();
            getEmptyView().setVisibility(View.VISIBLE);
            getEmptyView().getEmptyImage().setVisibility(View.GONE);
            getEmptyView().getEmptyText().setVisibility(View.VISIBLE);
            getEmptyView().setEmptyText("出错啦，请稍后再试");
        }
    }


    /**
     * 城市更新
     */
    public void onEvent(EventOnLbsChange event) {
        if (event != null && event.lbs != null && !TextUtils.isEmpty(event.lbs.name)) {
            lbsCityView.setText(event.lbs.name);
            currentLbs = event.lbs;
            setRefreshing(true);
        }
    }

    /**
     * 退出登录
     */
    public void onEvent(EventLogout event) {
        if (event != null && floatingActionButton != null) {
            floatingActionButton.setVisibility(View.VISIBLE);
            SharePreferenceUtil.setLbsName(mContext, "");//退出登录清空用户城市信息
            lbsCityView.setText("北京");
            currentLbs.name = "";
            currentLbs.id = 0;
            setRefreshing(true);
        }
    }

    /**
     * 登录成功
     */
    public void onEvent(EventLoginSuccess event) {
        if (event != null && floatingActionButton != null) {
            floatingActionButton.setVisibility(View.GONE);
            setRefreshing(true);
        }
    }


    /**
     * 推荐和中西药展示模块全部删除
     */
    private void removeRecoomendAndShop() {
        ((HomeAdapter) adapter).removeData(HomeAdapter.RECOMMEND);
        ((HomeAdapter) adapter).removeData(HomeAdapter.SKU_TYPE);
    }

    /**
     * 设置楼层数据
     */
    private void setFloorData(String data) {
        try {
            Gson gson = new Gson();
            Result<Floor> result = gson.fromJson(data, new TypeToken<Result<Floor>>() {
            }.getType());

            if (result != null && result.data != null && result.data.size() > 0) {
                //推荐
                Floor floor = result.data.get(0);
                ArrayList<Sku> skuList = joinData(floor);
                ((HomeAdapter) adapter).setRecommendList(skuList);
                getPrice(skuList);

                //药分类
                if (result.data.size() > 1) {
                    List<Floor> floors = result.data.subList(1, result.data.size());
                    ((HomeAdapter) adapter).setSkuTypeList(floors);
                }

                notifyDataSetChanged();
                if (result.data.size() < 2) {//没有中西药模块
                    ((HomeAdapter) adapter).removeData(HomeAdapter.SKU_TYPE);
                }
            } else {
                removeRecoomendAndShop();
                showNodata();
            }
        } catch (Exception e) {
            e.printStackTrace();
            removeRecoomendAndShop();
            showNodata();
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

    /**
     * 获取定位城市
     */
    private void getLocationInfo(String lat, String lng) {
        HashMap<String, String> paramas = new HashMap<>();
        paramas.put("lng", lng);
        paramas.put("lat", lat);
        Network.getRequestBuilder(Util.createUrl("home/curArea", ""))
                .params(paramas)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<LBS> result = gson.fromJson(model, new TypeToken<ResultObject<LBS>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                lbsCityView.setText(result.data.name);
                                currentLbs = result.data;
                                SharePreferenceUtil.setLbsName(mContext, result.data.name + "/" + result.data.id);
                                getFloorData();
                            } else {
                                setRefreshing(false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            setRefreshing(false);
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        setRefreshing(false);
                    }
                })
                .post();
    }

    /**
     * 获取楼层数据
     */
    private void getFloorData() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("areaId", currentLbs.id == 0 ? "1" : currentLbs.id + "");//默认取北京

        Network.getRequestBuilder(Util.createUrl("home/floors", ""))
                .params(hashMap)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        setRefreshing(false);
                        setFloorData(model);
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        onFailure(statusCode, errorMessage);
                        setRefreshing(false);
                        //接口失败，推荐和中西药展示模块全部删除
                        removeRecoomendAndShop();
                        showNodata();
                    }
                })
                .post();
    }


    /**
     * 获取区域数据
     */
    private void getAreaData() {
        if (!Util.isLogin()) {
            if (currentLbs.id != 0) {
                getFloorData();
            } else {
                initLocation();
            }
        } else {
            Network.getRequestBuilder(Util.createUrl("home/userArea", ""))
                    .success(new Success() {
                        @Override
                        public void success(String model) {
                            setRefreshing(false);
                            try {
                                Gson gson = new Gson();
                                ResultObject<LBS> result = gson.fromJson(model, new TypeToken<ResultObject<LBS>>() {
                                }.getType());
                                if (result != null && result.data != null && !TextUtils.isEmpty(result.data.name)) {
                                    lbsCityView.setText(result.data.name);
                                    currentLbs = result.data;
                                    SharePreferenceUtil.setLbsName(mContext, result.data.name + "/" + result.data.id);
                                    getFloorData();
                                } else {
                                    lbsCityView.setText("北京");//默认城市
                                    getFloorData();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .error(new Error() {
                        @Override
                        public void Error(int statusCode, String errorMessage, Throwable t) {
                            setRefreshing(false);
                        }
                    })
                    .post();
        }
    }

    /**
     * 获取轮播图数据
     */
    private void getBannerData() {
        HashMap<String, String> params = new HashMap<>();
        Network.getRequestBuilder(Util.createUrl("home/banners", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            Result<Banner> result = gson.fromJson(model, new TypeToken<Result<Banner>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                ((HomeAdapter) adapter).setBannerList(result.data);
                                notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            ((HomeAdapter) adapter).removeData(HomeAdapter.BANNER);
                            showNodata();
                            e.printStackTrace();
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        ((HomeAdapter) adapter).removeData(HomeAdapter.BANNER);
                        showNodata();
                    }
                })
                .post();
    }

    /**
     * 获取合作商家数据
     */
    private void getBrandData() {
        Network.getRequestBuilder(Util.createUrl("home/brandWalls", ""))
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            Result<Brand> result = gson.fromJson(model, new TypeToken<Result<Brand>>() {
                            }.getType());

                            if (result != null && result.data != null) {
                                if (result.data.size() < 16) {
                                    ((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                                    showNodata();
                                } else if (result.data.size() > 16) {
                                    ((HomeAdapter) adapter).setBrandList(result.data.subList(0, 16));
                                    notifyDataSetChanged();
                                } else {
                                    ((HomeAdapter) adapter).setBrandList(result.data);
                                    notifyDataSetChanged();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            ((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                            showNodata();
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        ((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                        showNodata();
                    }
                })
                .post();
    }

    /**
     * 获取价格
     */
    private void getPrice(final List<Sku> skus) {
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

    @Override
    public void onClick(View v) {
        if (Util.isFastDoubleClick()) {
            return;
        }
        if (v.getId() == R.id.search) {
            SearchActivity.launch(mContext);
        } else if (v.getId() == R.id.lbs_city) {
            if (!Util.isLogin()) {
                if (!TextUtils.isEmpty(currentLbs.name)) {
                    LbsActivity.launch(mContext, currentLbs.name);
                } else {
                    LbsActivity.launch(mContext, "北京");//默认城市
                }
            }
        } else if (v.getId() == R.id.float_button) {
            //跳转登录页面
            PortalActivity.launch(mContext, Activity.RESULT_OK);

            //埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0012";
            clickInterfaceParam.page_name = "首页";
            clickInterfaceParam.event_id="yjc_android_201706262|34";
            JDMaUtil.sendClickData(clickInterfaceParam);
        }
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

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                getLocationInfo(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    @Override
    public void onStop() {
        if (locationClient != null) {
            locationClient.stop();
        }
        super.onStop();
    }
}
