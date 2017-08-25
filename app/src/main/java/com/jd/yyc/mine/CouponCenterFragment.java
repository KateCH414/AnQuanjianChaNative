package com.jd.yyc.mine;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.CouponCenter;
import com.jd.yyc.api.model.CouponStatus;
import com.jd.yyc.api.model.CouponVO;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by jiahongbin on 2017/6/12.
 */

public class CouponCenterFragment extends BaseRefreshFragment {

    Map<String, String> coupon = new HashMap<String, String>();

    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("type", 1 + "");
        return hashMap;
    }

    @Override
    public String getPath() {
        return "coupon/glist";
    }


   /* private void getCenterData() {
        Network.getRequestBuilder(Util.createUrl("coupon/glist", ""))
                .params.put("type", "1");
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            Result<Brand> result = gson.fromJson(model, new TypeToken<Result<Brand>>() {
                            }.getType());


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
    }*/

    @Override
    public RecyclerAdapter createAdapter() {
        return new CouponCenterAdapter(mContext);
    }

    @Override
    public void onSuccess(String data) {

        setCouponCenterData(data);

    }

    private void setCouponCenterData(String data) {

        ArrayList<Long> couponType = new ArrayList<Long>();

        try {
            Gson gson = new Gson();
            ResultObject<List<CouponCenter>> result = gson.fromJson(data, new TypeToken<ResultObject<List<CouponCenter>>>() {
            }.getType());

            if (result != null && result.data != null) {
                if (PAGE == 1)
                    getAdapter().getList().clear();
                // setSuccessStatus(result.data, result.data.);

                for (int i = 0; i < result.data.size(); i++) {

                    ArrayList<CouponVO> coupons = result.data.get(i).coupons;
                    for (int j = 0; j < result.data.get(i).coupons.size(); j++) {
                        couponType.add(result.data.get(i).coupons.get(j).actRuleId);
                    }


                    setData(coupons);
                }
                getCouponStatus(couponType);
                setCanLoadMore(false);
                notifyDataSetChanged();
            } else {
                setSuccessStatus(null, 0);
                getEmptyView().setVisibility(View.VISIBLE);

                getEmptyView().setEmptyImage(R.drawable.noticecouponempty);


            }
        } catch (Exception e) {
            e.printStackTrace();


        }


    }

    private void getCouponStatus(List s) {
        ArrayList<CouponStatus> CouponStatus = new ArrayList<CouponStatus>();
        String statue = new String();
        for (int i = 0; i < s.size(); i++) {
            if (i != s.size() - 1) {
                statue = statue + s.get(i) + ",";
            } else {
                statue = statue + s.get(i) + "";
            }

        }


        HashMap<String, String> params = new HashMap<>();
        params.put("ids", statue);
        Network.getRequestBuilder(Util.createUrl("coupon/status", ""))
                .params(params)
                .success(new Success() {

                    public void success(String model) {

                        try {
                            Gson gson = new Gson();
                            ResultObject<List<CouponStatus>> result = gson.fromJson(model, new TypeToken<ResultObject<List<CouponStatus>>>() {
                            }.getType());
                            // FIXME: 2017/6/8
                            //      relationShip = result.data;
                            // relationShip = false;
                            if (result != null && result.data != null) {
                                // Toast.makeText(mContext, result.data.get(0).state, Toast.LENGTH_SHORT).show();
                                //      typecoupon = result.data.get(0).id;
                                coupon.clear();
                                for (int i = 0; i < result.data.size(); i++) {
                                    coupon.put(result.data.get(i).id, result.data.get(i).state);
                                }
                                ((CouponCenterAdapter) getAdapter()).setMapData(coupon);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        System.out.println("errorMessage=" + errorMessage);
                    }
                })
                .post();







    }


    @Override
    public int getContentView() {
        return R.layout.fragment_mine_couponcenter;
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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //  getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext));
        setCanLoadMore(true);
    }

}
