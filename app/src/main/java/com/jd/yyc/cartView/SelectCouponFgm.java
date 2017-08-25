package com.jd.yyc.cartView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.CouponModel;
import com.jd.yyc.cartView.adapter.CouponAdapter;
import com.jd.yyc.ui.widget.BottomSheetDialogFgm;


public class SelectCouponFgm extends BottomSheetDialogFgm implements View.OnClickListener {

    public static SelectCouponFgm newInstance() {
        Bundle args = new Bundle();
        SelectCouponFgm fragment = new SelectCouponFgm();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected View getContentView() {

        View content = LayoutInflater.from(getContext()).inflate(R.layout.fgm_select_coupon, null);
        ImageView closeImg = (ImageView) content.findViewById(R.id.close_img);
        RecyclerView category_list = (RecyclerView) content.findViewById(R.id.coupon_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        category_list.setLayoutManager(manager);
        CouponAdapter couponAdapter = new CouponAdapter(getActivity());
        couponAdapter.setCanLoadMore(false);
        category_list.setAdapter(couponAdapter);
        couponAdapter.setData(CouponModel.getDemoList());
        closeImg.setOnClickListener(this);
        return content;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_img:
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    public interface OnContinueSubListener {
        //        void cancel();
        void continueSub(String mobile);

        void onDismiss();
    }
}
