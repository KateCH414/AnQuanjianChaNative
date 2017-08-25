package com.jd.yyc.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jd.yyc.base.NoActionBarActivity;

/**
 * Created by jiahongbin on 2017/5/25.
 */

public class MineActivity extends NoActionBarActivity {



    private int currentType;


    public static void launch(Context context) {
        Intent intent = new Intent(context, MineActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        currentType=getIntent().getIntExtra("type",0);
        if (currentType==1){
            setTitle("");
        }

    }

    public Fragment getContentFragment() {
        return new MineFragment();
    }


}
