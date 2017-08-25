package com.jd.yyc.lbs;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.jd.project.lib.andlib.utils.Logger;
import com.jd.yyc.R;
import com.jd.yyc.api.model.LBS;
import com.jd.yyc.api.model.Result;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.base.YYCApplication;
import com.jd.yyc.event.EventOnLbsChange;
import com.jd.yyc.lbs.service.LocationService;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.L;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.SharePreferenceUtil;
import com.jd.yyc.util.Util;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * Created by zhangweifeng1 on 2017/5/17.
 */

public class LBSFragment extends BaseRefreshFragment {
    TextView currentCityTV;
    TextView lbsCity;
    private LBS lbsCityData;
    private String currentCityData;

    private MyLocationListener mMyLocationListener;
    private LocationClient locationClient;


    public static LBSFragment newInstance(String lbsName) {
        Bundle args = new Bundle();
        args.putString("lbsName", lbsName);
        LBSFragment fragment = new LBSFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public String getPath() {
        return "home/allArea";
    }

    @Override
    public void onSuccess(String data) {
        try {
            Gson gson = new Gson();
            Result<LBS> result = gson.fromJson(data, new TypeToken<Result<LBS>>() {
            }.getType());
            if (result != null && result.data != null) {
                setSuccessStatus(result.data);
                getEmptyView().setVisibility(View.GONE);
                setData(result.data);
                notifyDataSetChanged();
            } else {
                setSuccessStatus(null);
                getEmptyView().setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RecyclerView.LayoutManager createLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        return gridLayoutManager;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //当前城市
        currentCityTV = (TextView) view.findViewById(R.id.current_city);
        currentCityData = getActivity().getIntent().getStringExtra("city");
        if (!TextUtils.isEmpty(currentCityData)) {
            currentCityTV.setText("当前城市：" + currentCityData);
        }

        //定位城市
        lbsCity = (TextView) view.findViewById(R.id.lbs_city);
        lbsCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Util.isFastDoubleClick()) {
                    if (lbsCityData != null) {
                        SharePreferenceUtil.setLbsName(mContext, lbsCityData.name + "/" + lbsCityData.id);
                        EventBus.getDefault().post(new EventOnLbsChange(lbsCityData));
                        mContext.finish();
                    }
                }
            }
        });
        setRefreshEnabled(false);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext));
        setCanLoadMore(false);

        initLocation();
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_lbs;
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
    public void onStop() {
        if (locationClient != null) {
            locationClient.stop();
        }
        super.onStop();
    }

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
                                lbsCity.setText(result.data.name);
                                lbsCityData = result.data;
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
    public RecyclerAdapter createAdapter() {
        return new LBSAdapter(mContext);
    }

    class DividerItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public DividerItemDecoration(Context context) {
            itemSize = ScreenUtil.dip2px(context, 5);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
            if (holder.getAdapterPosition() == parent.getAdapter().getItemCount() - 1) {
                outRect.set(itemSize, 0, itemSize, itemSize);
            } else {
                outRect.set(itemSize, itemSize, itemSize, itemSize);
            }
        }
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            getLocationInfo(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
