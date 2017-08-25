package com.jd.yyc.search.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.Category;
import com.jd.yyc.api.model.CategoryTree;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/6/8.
 */

public class FilterShowAdapter extends RecyclerAdapter<String, YYCViewHolder> {

    public FilterShowAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(View.inflate(mContext, R.layout.item_search_filter_show, null));
    }


    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public class CategoryViewHolder extends YYCViewHolder<String> {
        @InjectView(R.id.category_tv)
        TextView categoryTv;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(final String data) {
            if (data != null) {
                categoryTv.setText(data);
            }
        }
    }

}
