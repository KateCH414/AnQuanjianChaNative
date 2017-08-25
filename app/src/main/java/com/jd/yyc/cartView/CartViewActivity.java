package com.jd.yyc.cartView;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jd.yyc.base.NoActionBarActivity;

/**
 * Created by jiahongbin on 2017/5/27.
 */

public class CartViewActivity  extends NoActionBarActivity  {


    public static void launch(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), CartViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    public Fragment getContentFragment() {
        return CartViewFragment.newInstance(true);
    }


}
