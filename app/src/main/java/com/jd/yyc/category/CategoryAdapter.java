package com.jd.yyc.category;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.github.huajianjiang.expandablerecyclerview.widget.ChildViewHolder;
import com.github.huajianjiang.expandablerecyclerview.widget.ExpandableAdapter;
import com.github.huajianjiang.expandablerecyclerview.widget.ParentViewHolder;
import com.jd.yyc.R;
import com.jd.yyc.api.model.CategoryTree;
import com.jd.yyc.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhj_Plus on 2016/9/2.
 */
public class CategoryAdapter
        extends ExpandableAdapter<CategoryAdapter.MyParentViewHolder, CategoryAdapter.MyChildViewHolder, CategoryTree, CategoryTree> {
    private static final String TAG = "CategoryAdapter";

    public int childChoosePosition = -1;
    public int parentChoosePosition = 0;

    public static final String LEVEL1 = "1";
    public static final String LEVEL2 = "2";
    private OnCategoryClickListener onCategoryClickListener;

    private List<CategoryTree> mData = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;

    public CategoryAdapter(Context context, List<CategoryTree> data) {
        super(data);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        if (data != null) {
            mData = data;
        }
    }

    public List<CategoryTree> getData() {
        return mData;
    }

    public void setCategoryList(List<CategoryTree> categoryList) {
        if (categoryList != null) {
            this.mData.clear();
            this.mData.addAll(categoryList);
        }
    }

    @Override
    public MyParentViewHolder onCreateParentViewHolder(ViewGroup parent, int parentType) {
        View itemView = mInflater.inflate(R.layout.item_parent, parent, false);
        return new MyParentViewHolder(itemView);
    }

    @Override
    public MyChildViewHolder onCreateChildViewHolder(ViewGroup child, int childType) {
        View itemView = mInflater.inflate(R.layout.item_child, child, false);
        return new MyChildViewHolder(itemView);
    }

    @Override
    public void onBindParentViewHolder(MyParentViewHolder pvh, int parentPosition, CategoryTree parent) {
        pvh.bind(parent);
    }

    @Override
    public void onBindChildViewHolder(MyChildViewHolder cvh, int parentPosition, int childPosition, CategoryTree child) {
        cvh.bind(child);
    }

    @Override
    public int getParentType(int parentPosition) {
        CategoryTree myParent = mData.get(parentPosition);
        if (myParent != null && LEVEL1.equals(myParent.level)) {
            return 1;
        }
        return super.getParentType(parentPosition);
    }

    @Override
    public int getChildType(int parentPosition, int childPosition) {
        CategoryTree myParent = mData.get(parentPosition);
        if (myParent != null && myParent.getChildren() != null) {
            CategoryTree myChild = myParent.getChildren().get(childPosition);
            if (myChild != null && LEVEL2.equals(myChild.level)) {
                return 2;
            }
        }

        return super.getParentType(parentPosition);

    }

    public ItemDecoration getItemDecoration() {
        return new ItemDecoration();
    }

    private class ItemDecoration extends RecyclerView.ItemDecoration {

        int itemOffset = ScreenUtil.dip2px(mContext, 1);

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            final int childAdapterPos = parent.getChildAdapterPosition(view);
            outRect.set(0, 0, itemOffset, itemOffset);
        }
    }

    public class MyChildViewHolder extends ChildViewHolder<CategoryTree> {
        private static final String TAG = "MyChildViewHolder";

        public MyChildViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(final CategoryTree data) {
            TextView tv_info = getView(R.id.info);
            if (data != null) {
                tv_info.setText(data.name);
            }
            tv_info.setSelected(false);
            tv_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (childChoosePosition == getAdapterPosition()) {
                        return;
                    }
                    v.setSelected(true);
                    notifyItemChanged(childChoosePosition);
                    if (onCategoryClickListener != null) {
                        onCategoryClickListener.onChildClick(data.subs, data.id);
                    }
                    childChoosePosition = getAdapterPosition();
                }
            });
        }

    }

    public class MyParentViewHolder extends ParentViewHolder<CategoryTree> {
        private static final String TAG = "MyParentViewHolder";

        public MyParentViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(CategoryTree data) {
            TextView tv_info = getView(R.id.info);
            if (data != null) {
                tv_info.setText(data.name);
            }
            View arrow = getView(R.id.arrow);
            arrow.setVisibility(isExpandable() ? View.VISIBLE : View.INVISIBLE);
            if (isExpandable()) {
                arrow.setRotation(isExpanded() ? 180 : 0);
            }
        }


        @Override
        public void onItemClick(RecyclerView rv, View v) {
            super.onItemClick(rv, v);

            if (onCategoryClickListener != null && getParent() != null && isExpanded()) {
                onCategoryClickListener.onParentClick(getParent().id);
            }

            try {
                RecyclerView recyclerView = getAttachedRecyclerViews()[0];
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(getAdapterPosition(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }

            int parentPosotion = CategoryAdapter.this.getParentPosition(getAdapterPosition());
            if (parentPosotion == parentChoosePosition) {
                return;
            }
            collapseParent(parentChoosePosition);
            parentChoosePosition = parentPosotion;
            childChoosePosition = -1;
        }

        @Override
        public boolean onItemLongClick(RecyclerView rv, View v) {
//            AppUtil.showToast(v.getContext(), "Parent LongClick==>" + getAssociateAdapter()
//                    .getParentPosition(getAdapterPosition()));
//            MyParent myParent = (MyParent) getAssociateAdapter()
//                    .getParentForAdapterPosition(getAdapterPosition());
//            MyParent myParent = getParent();
            return true;
        }
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    public interface OnCategoryClickListener {

        void onChildClick(List<CategoryTree> categoryTreeList, long id);

        void onParentClick(long id);

    }


}
