package com.jd.yyc.search.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.HotSearch;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.search.db.HistoryDBHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangweifeng1 on 2017/6/6.
 */

public class HistoryAdapter extends RecyclerAdapter<String, YYCViewHolder> {
    private OnHistoryClickCallback onHistoryClickCallback;

    public HistoryAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new HistoryViewHloder(View.inflate(mContext, R.layout.item_history_search, null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    class HistoryViewHloder extends YYCViewHolder<String> {
        @InjectView(R.id.history_word)
        TextView historyView;

        public HistoryViewHloder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(String data) {
            super.onBind(data);
            historyView.setText(data);
        }

        @OnClick(R.id.clear)
        void clear() {
            removeItem(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
            if (!TextUtils.isEmpty(data)) {
                HistoryDBHelper.delete(mContext, data);
                if (onHistoryClickCallback != null) {
                    onHistoryClickCallback.onClearClick(getAdapterPosition());
                }
            }
        }

        @Override
        public void onNoDoubleCLick(View v) {
            if (onHistoryClickCallback != null) {
                onHistoryClickCallback.onHistoryClick(data);
            }
        }
    }


    public void setHistoryClickCallback(OnHistoryClickCallback onHistoryClickCallback) {
        this.onHistoryClickCallback = onHistoryClickCallback;
    }

    public interface OnHistoryClickCallback {

        void onHistoryClick(String key);

        void onClearClick(int position);
    }
}
