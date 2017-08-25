package com.jd.yyc.mine;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.utils.Logger;
import com.jd.yyc.R;
import com.jd.yyc.api.model.MyCoupon;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.Util;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.jd.yyc.mine.MineGoodsFragment.PADDING;

/**
 * Created by jiahongbin on 2017/6/4.
 */

public class MineCouponFragment extends BaseRefreshFragment implements Contants, View.OnClickListener {

    @InjectView(R.id.rl_center)
    RelativeLayout rl_center;

    public static String TYPE = "type";
    public int type = 8;//0未使用，1已使用，2已过期
    public static CouponNumCallback callback;
    public static MineCouponFragment newInstance(int typ, Context context) {
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, typ);

        MineCouponFragment fragment = new MineCouponFragment();
        CouponActivity coupon = (CouponActivity) context;
        callback = coupon;
        fragment.setArguments(bundle);
        return fragment;
    }
    public static MineCouponFragment newInstance(int typ, boolean topPadding, Context context) {
        MineCouponFragment fragment = new MineCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, typ);

        bundle.putBoolean(PADDING, topPadding);
        fragment.setArguments(bundle);
        CouponActivity coupon = (CouponActivity) context;
        callback = coupon;
        return fragment;
    }
    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("status", type + "");
        hashMap.put("page", PAGE + "");
        hashMap.put("pageSize", 20 + "");
        return hashMap;
    }
    @Override
    public String getPath() {
        return "coupon/pageList";
    }

    @Override
    public RecyclerAdapter createAdapter() {
        return new MineCouponAdapter(mContext, type);
    }
    @Override
    public void onSuccess(String data) {

        setCoupondata(data);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setCoupondata(String data) {
        try {
            Gson gson = new Gson();
            ResultObject<MyCoupon> result = gson.fromJson(data, new TypeToken<ResultObject<MyCoupon>>() {
            }.getType());
            if (result.data.list != null) {
//                if (PAGE == 1)
//                    getAdapter().getList().clear();
                int count = result.data.totalCount;
                if (callback != null)
                    callback.setCouponNum(type, count);
                setSuccessStatus(result.data.list, result.data.totalCount);
                setData(result.data.list);
//            getEmptyView().setEmptyText("暂无订单");
                notifyDataSetChanged();
            } else {
                setSuccessStatus(null, 0);
                getEmptyView().setEmptyImage(R.drawable.noticecouponempty);
              /*  getEmptyView().setVisibility(View.VISIBLE);
                getEmptyView().getEmptyImage().setVisibility(View.GONE);
                getEmptyView().getEmptyText().setVisibility(View.VISIBLE);
                getEmptyView().setEmptyText("暂无优惠券");*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_mine_coupon;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_center:
                if (Util.isFastDoubleClick()) {
                    return;
                }
                CouponCenter.launch(mContext);
                break;
            default:
                break;
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
                outRect.set(0, 10, 0, 10);
            } else {
                outRect.set(0, 10, 0, 10);
            }
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (getArguments() != null) {

            type = getArguments().getInt(TYPE);
        }
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        rl_center.setOnClickListener(this);
        Logger.e("type", type + "");
        //   ((MineCouponAdapter) adapter).setDataType(type);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext));
    }
    public interface CouponNumCallback {
        void setCouponNum(int type, int num);
    }

}
