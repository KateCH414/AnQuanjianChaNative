package com.jd.yyc.search.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.HotSearch;
import com.jd.yyc.api.model.LBS;
import com.jd.yyc.event.EventOnLbsChange;
import com.jd.yyc.lbs.LBSAdapter;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.search.db.HistoryDBHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by zhangweifeng1 on 2017/6/6.
 */

public class HotAdapter extends RecyclerAdapter<HotSearch, YYCViewHolder> {
    private OnHotClickCallback onHotClickCallback;

    public HotAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new HotViewHloder(View.inflate(mContext, R.layout.item_hot_search, null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    class HotViewHloder extends YYCViewHolder<HotSearch> {
        @InjectView(R.id.hot_word)
        TextView hotView;

        public HotViewHloder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(HotSearch data) {
            super.onBind(data);
            hotView.setText(data.name);
        }

        @Override
        public void onNoDoubleCLick(View v) {
            if (data != null && !TextUtils.isEmpty(data.name)) {
                if (onHotClickCallback != null) {
                    onHotClickCallback.onHotClick(data.name);
                }
            }
        }
    }

    public void setHotClickCallback(OnHotClickCallback onHotClickCallback) {
        this.onHotClickCallback = onHotClickCallback;
    }

    public interface OnHotClickCallback {

        void onHotClick(String key);
    }
}
