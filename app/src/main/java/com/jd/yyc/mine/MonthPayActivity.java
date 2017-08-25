package com.jd.yyc.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jd.yyc.base.NoActionBarActivity;

/**
 * Created by jiahongbin on 2017/5/30.
 */

public class MonthPayActivity  extends NoActionBarActivity{

    public static void launch(Context context) {
        Intent intent = new Intent(context, MonthPayActivity.class);
        context.startActivity(intent);
    }

    @Override
    public Fragment getContentFragment() {
        return new MonthPayFragment();
    }


}
