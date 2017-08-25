package com.jd.yyc.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jd.yyc.base.ActionBarActivity;

/**
 * Created by jiahongbin on 2017/5/25.
 */

public class StayPayActivity extends ActionBarActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, StayPayActivity.class);
        context.startActivity(intent);
    }

    @Override
    public Fragment getContentFragment() {
        return new StayPayFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("待付款");
    }

}
