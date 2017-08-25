package com.jd.yyc.search.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.Category;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.search.db.HistoryDBHelper;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class FilterAdapter extends RecyclerAdapter<Category, YYCViewHolder> {
    private OnItemClickCallback onItemClickCallback;
    public ArrayList<Category> chooseList = new ArrayList<>();

    public FilterAdapter(Context mContext) {
        super(mContext);
    }

    public void reSet() {
        chooseList.clear();
        notifyDataSetChanged();
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new FilterViewHloder(View.inflate(mContext, R.layout.item_filter_company, null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    class FilterViewHloder extends YYCViewHolder<Category> {
        @InjectView(R.id.category_tv)
        TextView categoryTv;

        public FilterViewHloder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(Category data) {
            super.onBind(data);
            if (data != null) {
                categoryTv.setText(data.Name);
            }
            if (chooseList.contains(getAdapterPosition())) {
                itemView.setSelected(true);
            } else {
                itemView.setSelected(false);
            }
        }


        @Override
        public void onClick(View v) {
            if (v.isSelected()) {
                v.setSelected(false);
                chooseList.remove(data);
            } else {
                v.setSelected(true);
                chooseList.add(data);
            }

            if (onItemClickCallback != null) {
                onItemClickCallback.onItemClick(data);
            }
        }
    }


    public void setItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {

        void onItemClick(Category data);

    }
}
