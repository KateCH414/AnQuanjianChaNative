package com.jd.yyc.goodsdetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.cartView.CartViewActivity;
import com.jd.yyc.event.EventLoginMessage;

/**
 * Created by jiahongbin on 2017/5/26.
 */



public class GoodsDetailActivity  extends NoActionBarActivity{
    public Long skuId;

    public static void launch(Context context, long skuId) {

        Intent intent = new Intent(context, GoodsDetailActivity.class);
        intent.putExtra("skuId",skuId);
// TODO: 2017/7/6  
        context.startActivity(intent);
    }


    public void onCreate(Bundle savedInstanceState) {
        skuId = getIntent().getLongExtra("skuId", 0);
        // skuId=12581060203l;
        super.onCreate(savedInstanceState);

    }


    @Override
    public Fragment getContentFragment() {
        return new GoodsDetailFragment().newInstance(skuId);
    }

    public void onEvent(EventLoginMessage event) {
        if (event.type == 3) {
            CartViewActivity.launch(this);
        }
    }




}
