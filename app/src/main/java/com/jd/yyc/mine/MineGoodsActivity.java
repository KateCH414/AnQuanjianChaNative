package com.jd.yyc.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jd.yyc.base.ActionBarActivity;
import com.jd.yyc.constants.JDMaContants;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.PvInterfaceParam;

/**
 * Created by jiahongbin on 2017/5/26.
 */

public class MineGoodsActivity extends ActionBarActivity implements JDMaContants {

    public final static int  WAITCHECK= 1;//待审核
    public final static int WAITCONFIRM = 2;//待确认
    public final static int WAITPAY = 5;//待付款
    public final static int WAIGOODS = 6;//待收货


    private int currentType=6;


    public static void launch(Context context ,int type) {

        Intent intent = new Intent(context, MineGoodsActivity.class);
        context.startActivity(intent);
    }


    @Override
    public Fragment getContentFragment() {

        //  Network.setCookie("wsKey=AAFZNPhuAEBp9iF8WZqvRHDG7akEcKNglIRfDGRvFMa68OiREFcVGK9aKO460nnk9HwZXoPAr3hAVoR-tR3PMUn5EghpkGgN;pin=%E6%B5%8B%E8%AF%95jijinye");

        return MineGoodsFragment.newInstance(currentType);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        currentType=getIntent().getIntExtra("type",0);
        super.onCreate(savedInstanceState);
        PvInterfaceParam param = new PvInterfaceParam();
        if (currentType==1){
            setTitle("待审核");
            param.page_name = PAGE_ORDER_CHECK_NAME;
            param.page_id = PAGE_ORDER_CHECK_ID;
        }
        if (currentType==2){
            setTitle("待确认");
            param.page_name = PAGE_ORDER_CONFIRM_NAME;
            param.page_id = PAGE_ORDER_CONFIRM_ID;
        }
        if (currentType==5){
            setTitle("待付款");
            param.page_name = PAGE_ORDER_PAY_NAME;
            param.page_id = PAGE_ORDER_PAY_ID;
        }
        if (currentType==6){
            setTitle("待收货");
            param.page_name = PAGE_ORDER_RECEIVE_NAME;
            param.page_id = PAGE_ORDER_RECEIVE_ID;
        }
        JDMaUtil.sendPVData(param);
    }

}
