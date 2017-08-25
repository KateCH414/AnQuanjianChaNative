package com.jd.yyc.category.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.CategoryTree;
import com.jd.yyc.api.model.Sku;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/5/27.
 */

public class CategoryThirdAdapter extends RecyclerAdapter<CategoryTree, YYCViewHolder> {
    public static final int MORE = 10;//更多

    private ArrayList<CategoryTree> categoryArrayList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private int choosePosition = 0;//默认选中全部
    private boolean isExpand = false;//是否展开，默认不展开
    private long cat2Id;


    public CategoryThirdAdapter(Context mContext) {
        super(mContext);
    }

    public void reSet() {
        this.choosePosition = 0;
        this.isExpand = false;
    }

    public void setCategoryData(List<CategoryTree> categoryArrayList, long cat2Id) {
        this.cat2Id = cat2Id;
        if (categoryArrayList != null) {
            this.categoryArrayList.clear();
            this.categoryArrayList.addAll(categoryArrayList);
            if (categoryArrayList.size() > 5) {
                setData(categoryArrayList.subList(0, 4));
            } else {
                setData(categoryArrayList);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (categoryArrayList.size() > 5) {
            if (isExpand) {
                return super.getItemCount() + 1;//只有全部按钮
            }
            return super.getItemCount() + 2;//带有全部和更多按钮
        } else if (super.getItemCount() == 0) {
            return super.getItemCount();//无数据都不展示
        }
        return super.getItemCount() + 1;//只有全部按钮
    }

    @Override
    public CategoryTree getItem(int position) {
        if (position == 0) {
            return null;
        }

        if (categoryArrayList.size() > 5 && position == getItemCount() - 1 && !isExpand) {
            return null;
        }
        return super.getItem(position - 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (categoryArrayList.size() > 5 && position == getItemCount() - 1 && !isExpand) {
            return MORE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        if (viewType == MORE) {
            return new MoreViewHolder(View.inflate(mContext, R.layout.item_category_third_more, null));
        }
        return new CategoryViewHolder(View.inflate(mContext, R.layout.item_category_third, null));
    }


    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    public class CategoryViewHolder extends YYCViewHolder<CategoryTree> {
        @InjectView(R.id.category_tv)
        TextView categoryTv;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(final CategoryTree data) {
            if (getAdapterPosition() == 0) {
                categoryTv.setText("全部");
            } else {
                if (data != null) {
                    categoryTv.setText(data.name);
                }
            }

            if (choosePosition == getAdapterPosition()) {
                categoryTv.setSelected(true);
            } else {
                categoryTv.setSelected(false);
            }
        }

        @Override
        public void onNoDoubleCLick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAdapterPosition(), cat2Id);
            }
            int lastChoosePosition = choosePosition;
            categoryTv.setSelected(false);
            choosePosition = getAdapterPosition();
            notifyItemChanged(choosePosition);
            notifyItemChanged(lastChoosePosition);
        }
    }

    public class MoreViewHolder extends YYCViewHolder<Sku> {
        @InjectView(R.id.more_tv)
        TextView moreTv;
        @InjectView(R.id.arrow_down)
        ImageView arrowDown;

        public MoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(final Sku data) {

        }

        @Override
        public void onNoDoubleCLick(View v) {
            clear();
            isExpand = true;
            if (onItemClickListener != null) {
                onItemClickListener.onMoreClick(moreTv);
            }
            setData(categoryArrayList);
            notifyDataSetChanged();
        }
    }

    public void setFoldStatus() {
        clear();
        isExpand = false;
        try {
            setData(categoryArrayList.subList(0, 4));
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int postion, long cat2Id);

        void onMoreClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

}
