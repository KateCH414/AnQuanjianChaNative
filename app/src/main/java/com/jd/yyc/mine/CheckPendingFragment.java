package com.jd.yyc.mine;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Floor;
import com.jd.yyc.api.model.Result;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.ScreenUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiahongbin on 2017/5/25.
 */

public class CheckPendingFragment extends BaseRefreshFragment {


    @Override
    public Map<String, String> getParams() {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("areaId", "1");
        return hashMap;
    }

    @Override
    public String getPath() {
        return "home/floors";
    }



    @Override
    public int getContentView() {
        return R.layout.fragment_mine_confirm;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(mContext));
        setCanLoadMore(false);
    }


    public RecyclerAdapter createAdapter() {
        return new MineGoodsAdapter(mContext);
    }

    @Override
    public void onSuccess(String data) {
        Gson gson = new Gson();
        Result<Floor> result = gson.fromJson(data, new TypeToken<Result<Floor>>() {
        }.getType());
        System.out.println("result=" + result);
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


}
