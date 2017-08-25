package com.jd.yyc.util.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 *
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int left;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public SpacesItemDecoration(int space, int left) {
        this.space = space;
        this.left = left;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.left = (left == 0 ? space : left);
//        outRect.right = (left == 0 ? space : left);
        outRect.bottom = space;
//        outRect.top = space;
    }
}
