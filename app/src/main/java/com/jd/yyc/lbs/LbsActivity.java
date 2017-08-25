package com.jd.yyc.lbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jd.yyc.R;
import com.jd.yyc.base.ActionBarActivity;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.PvInterfaceParam;

/**
 * Created by zhangweifeng1 on 2017/5/16.
 */

public class LbsActivity extends ActionBarActivity {

    public static void launch(Context context, String city) {
        Intent intent = new Intent(context, LbsActivity.class);
        intent.putExtra("city", city);
        context.startActivity(intent);
    }

    @Override
    public Fragment getContentFragment() {
        return new LBSFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("地区选择");

        PvInterfaceParam pvInterfaceParam = new PvInterfaceParam();
        pvInterfaceParam.page_id = "0013";
        pvInterfaceParam.page_name = "地区选择";
        JDMaUtil.sendPVData(pvInterfaceParam);
    }
}
