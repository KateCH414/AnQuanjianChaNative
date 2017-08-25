package com.jd.yyc.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Coupon;
import com.jd.yyc.api.model.MineGoods;
import com.jd.yyc.api.model.Monthsum;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.UserData;
import com.jd.yyc.base.CommonFragment;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.event.EventLoginMessage;
import com.jd.yyc.event.EventLoginSuccess;
import com.jd.yyc.event.EventLogout;
import com.jd.yyc.login.PortalActivity;
import com.jd.yyc.login.UserUtil;
import com.jd.yyc.net.NetLoading;
import com.jd.yyc.net.RequestCallback;
import com.jd.yyc.ui.util.DialogUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import jd.wjlogin_sdk.common.listener.OnCommonCallback;
import jd.wjlogin_sdk.model.FailResult;


/**
 * Created by jiahongbin on 2017/5/25.
 */

public class MineFragment extends CommonFragment implements View.OnClickListener, Contants {

    public MineFragment() {
    }

    @InjectView(R.id.check_num)
    TextView checkNum;

    @InjectView(R.id.confirm_num)
    TextView confirmNum;

    @InjectView(R.id.pay_num)
    TextView payNum;

    @InjectView(R.id.receive_num)
    TextView receiveNum;

    @InjectView(R.id.tv_couponSum)
    TextView tv_couponSum;

    @InjectView(R.id.tv_monthPaySum)
    TextView tv_monthPaySum;

    @InjectView(R.id.civ_bw_header)
    ImageView headerIcon;


    @InjectView(R.id.tv_mine_total)
    TextView tv_mine_total;

    @InjectView(R.id.bt_Mine_back)
    TextView bt_Mine_back;

    @InjectView(R.id.tv_Mine_name)
    TextView tv_Mine_name;

    //  user/userInfo
    @Override
    public int getContentView() {
        return R.layout.fragment_mine;
    }


    @Override
    public void onResume() {
        super.onResume();
        getCouponNumberData();
        refeshDate();
        exitBack();
        PvInterfaceParam param = new PvInterfaceParam();
        param.page_id = "0016";
        param.page_name = "个人中心";
        JDMaUtil.sendPVData(param);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        exitBack();
        bt_Mine_back.setOnClickListener(this);
        headerIcon.setOnClickListener(this);
        tv_Mine_name.setOnClickListener(this);
        getUserData();
        getCouponNumberData();
        getMonthPaySum();
        getOrderCount(ORDER_WILL_CHECK);
        getOrderCount(ORDER_WILL_CONFIRM);
        getOrderCount(ORDER_WILL_PAY);
        getOrderCount(ORDER_WILL_RECEIVE);
    }


    private void getOrderCount(final int type) {
        NetLoading.getInstance().getOrderList(getTopActivity(), 1, 1, type, new RequestCallback<ResultObject<MineGoods>>() {
            @Override
            public void requestCallBack(boolean success, ResultObject<MineGoods> result, String err) {
                if (success && result.success && result.data != null) {
                    if (type == ORDER_WILL_CHECK) {
                        checkNum.setVisibility(result.data.totalCount > 0 ? View.VISIBLE : View.GONE);
                        checkNum.setText(result.data.totalCount + "");
                    } else if (type == ORDER_WILL_CONFIRM) {
                        confirmNum.setVisibility(result.data.totalCount > 0 ? View.VISIBLE : View.GONE);
                        confirmNum.setText(result.data.totalCount + "");
                    } else if (type == ORDER_WILL_PAY) {
                        payNum.setVisibility(result.data.totalCount > 0 ? View.VISIBLE : View.GONE);
                        payNum.setText(result.data.totalCount + "");
                    } else if (type == ORDER_WILL_RECEIVE) {
                        receiveNum.setVisibility(result.data.totalCount > 0 ? View.VISIBLE : View.GONE);
                        receiveNum.setText(result.data.totalCount + "");
                    }
                }
            }
        });
    }

    private void getMonthPaySum() {

        Network.getRequestBuilder(Util.createUrl("order/listAll", ""))
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<Monthsum> result = gson.fromJson(model, new TypeToken<ResultObject<Monthsum>>() {
                            }.getType());

                            if (result != null && result.data != null) {

                                tv_monthPaySum.setText(result.data.monthPay.totalCount + "");
                                int s = result.data.monthPay.totalCount;
                                Toast.makeText(mContext, result.data.monthPay.totalCount, Toast.LENGTH_SHORT).show();
                                // ((HomeAdapter) adapter).setBrandList(result.data);
                                //      notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //   ((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        //((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                    }
                })
                .post();


    }

    private void getCouponNumberData() {

        Network.getRequestBuilder(Util.createUrl("", "coupon/mylist"))
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<Coupon> result = gson.fromJson(model, new TypeToken<ResultObject<Coupon>>() {
                            }.getType());

                            if (result != null && result.data != null) {


                                tv_couponSum.setText(result.data.unused.totalCount + "");
                                //    int s=result.data.monthPay.totalCount;
                                //    Toast.makeText(mContext,result.data.monthPay.totalCount,Toast.LENGTH_SHORT).show();
                                // ((HomeAdapter) adapter).setBrandList(result.data);
                                //      notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //   ((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        //((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                    }
                })
                .post();


    }

    private void getUserData() {

        Network.getRequestBuilder(Util.createUrl("user/userInfo", ""))
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<UserData> result = gson.fromJson(model, new TypeToken<ResultObject<UserData>>() {
                            }.getType());

                            if (result != null && result.data != null) {

                                tv_Mine_name.setText(result.data.userName);
                                // ((HomeAdapter) adapter).setBrandList(result.data);
                                //      notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //   ((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        //((HomeAdapter) adapter).removeData(HomeAdapter.BRAND);
                    }
                })
                .post();

        headerIcon.setImageResource(R.drawable.commondefaultavatar);
        //  ImageUtil.getInstance().loadSmallRoundImage(getTopActivity(), headerIcon, "http://img1.imgtn.bdimg.com/it/u=2236805734,2794297711&fm=26&gp=0.jpg");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.check_layout)
    void toWaiCheck() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        if (Util.isLogin()) {
            //埋点
            ClickInterfaceParam param = new ClickInterfaceParam();
            param.event_id = ME_ORDER_CHECK_LIST_ID;
            param.page_name = PAGE_MINE_NAME;
            JDMaUtil.sendClickData(param);

            Intent in = new Intent();
            in.putExtra("type", 1);
            in.setClass(mContext, MineGoodsActivity.class);
            startActivity(in);
        } else {
            PortalActivity.launch(mContext, LOGIN_FROM_MINE_CHECK);
        }
        //MineGoodsActivity.launch(mContext,1);
    }

    @OnClick(R.id.confirm_layout)
    void WaiConfirm() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        if (Util.isLogin()) {
            //埋点
            ClickInterfaceParam param = new ClickInterfaceParam();
            param.event_id = ME_ORDER_CONFIRM_LIST_ID;
            param.page_name = PAGE_MINE_NAME;
            JDMaUtil.sendClickData(param);

            Intent in = new Intent();
            in.putExtra("type", 2);
            in.setClass(mContext, MineGoodsActivity.class);
            startActivity(in);
        } else {
            PortalActivity.launch(mContext, LOGIN_FROM_MINE_CONFIRM);
        }

        //MineGoodsActivity.launch(mContext,1);
    }


    @OnClick(R.id.pay_layout)
    void toWaiPay() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        if (Util.isLogin()) {
            //埋点
            ClickInterfaceParam param = new ClickInterfaceParam();
            param.event_id = ME_ORDER_PAY_LIST_ID;
            param.page_name = PAGE_MINE_NAME;
            JDMaUtil.sendClickData(param);

            Intent in = new Intent();
            in.putExtra("type", 5);
            in.setClass(mContext, MineGoodsActivity.class);
            startActivity(in);
        } else {
            PortalActivity.launch(mContext, LOGIN_FROM_MINE_WAIPAY);
        }
      //  MineGoodsActivity.launch(mContext,3);
    }

    @OnClick(R.id.receive_layout)
    void toWaiGooodsReceive() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        if (Util.isLogin()) {
            //埋点
            ClickInterfaceParam param = new ClickInterfaceParam();
            param.event_id = ME_ORDER_RECEIVE_LIST_ID;
            param.page_name = PAGE_MINE_NAME;
            JDMaUtil.sendClickData(param);


            Intent in = new Intent();
            in.putExtra("type", 6);
            in.setClass(mContext, MineGoodsActivity.class);
            startActivity(in);
        } else {
            PortalActivity.launch(mContext, LOGIN_FROM_MINE_RECEIVE);
        }
    //   MineGoodsActivity.launch(mContext,4);
    }

    @OnClick(R.id.tv_mine_total)
    void toTotal() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        if (Util.isLogin()) {
            ClickInterfaceParam param = new ClickInterfaceParam();
            param.event_id = ME_ORDER_LIST_ID;
            param.page_name = PAGE_MINE_NAME;
            JDMaUtil.sendClickData(param);
            TotalActivity.launch(mContext);
        } else {
            PortalActivity.launch(mContext, LOGIN_FROM_MINE_TOTAL);
        }
    }

    @OnClick(R.id.moth_pay_layout)
    void toMonthPay() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        if (Util.isLogin()) {
            MonthPayActivity.launch(mContext);
        } else {
            PortalActivity.launch(mContext, LOGIN_FROM_MINE_MONTH);
        }
    }

    @OnClick(R.id.coupon_layout)
    void toCoupon() {
        if (Util.isFastDoubleClick()) {
            return;
        }
        if (Util.isLogin()) {
            CouponActivity.launch(mContext);
        } else {
            PortalActivity.launch(mContext, LOGIN_FROM_MINE_COUPON);
        }
    }

    public void onEvent(EventLoginMessage event) {
        if (event.type == 5) {
            CouponActivity.launch(mContext);
        }

        if (event.type == 6) {
            MonthPayActivity.launch(mContext);
        }

        if (event.type == 7) {
            Intent in = new Intent();
            in.putExtra("type", 1);
            in.setClass(mContext, MineGoodsActivity.class);
            startActivity(in);
        }
        if (event.type == 8) {
            Intent in = new Intent();
            in.putExtra("type", 2);
            in.setClass(mContext, MineGoodsActivity.class);
            startActivity(in);
        }
        if (event.type == 9) {
            Intent in = new Intent();
            in.putExtra("type", 5);
            in.setClass(mContext, MineGoodsActivity.class);
            startActivity(in);
        }
        if (event.type == 10) {
            Intent in = new Intent();
            in.putExtra("type", 6);
            in.setClass(mContext, MineGoodsActivity.class);
            startActivity(in);
        }
        if (event.type == 11) {
            TotalActivity.launch(mContext);
        }

        //  Toast.makeText(HomeActivity.this, event.type, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_Mine_back:
                DialogUtil.showDialog(mContext, "退出登录", "您真要退出吗", "确定", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserUtil.getWJLoginHelper().exitLogin(new OnCommonCallback() {
                            @Override
                            public void onSuccess() {
                                UserUtil.getWJLoginHelper().clearLocalOnlineState();
                                tv_Mine_name.setText("请登录");
                                tv_couponSum.setText("");
                                tv_monthPaySum.setText("");
                                confirmNum.setVisibility(View.GONE);
                                confirmNum.setText("");
                                payNum.setVisibility(View.GONE);
                                payNum.setText("");
                                receiveNum.setVisibility(View.GONE);
                                receiveNum.setText("");
                                checkNum.setVisibility(View.GONE);
                                checkNum.setText("");
                                bt_Mine_back.setVisibility(View.GONE);
                                Network.setCookie("");
                                EventBus.getDefault().post(new EventLogout());
                                // EventBus.getDefault().post(new EventHome());
                                Toast.makeText(mContext, "退出成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String s) {
                                ToastUtil.show("退出失败");
                            }

                            @Override
                            public void onFail(FailResult failResult) {
                                ToastUtil.show("退出失败");
                            }
                        });
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case R.id.civ_bw_header:
                if (Util.isLogin()) {
                    break;
                } else {
                    PortalActivity.launch(mContext, 3);
                }

                break;
            case R.id.tv_Mine_name:
                if (Util.isLogin()) {
                    break;
                } else {
                    PortalActivity.launch(mContext, 3);
                }

                break;
            default:
                break;
        }


    }

    private void exitBack() {
        if (Util.isLogin()) {
            bt_Mine_back.setVisibility(View.VISIBLE);
        } else {
            bt_Mine_back.setVisibility(View.GONE);
        }
    }

    public void refeshDate() {
        getUserData();
        getCouponNumberData();
        getMonthPaySum();
        getOrderCount(ORDER_WILL_CHECK);
        getOrderCount(ORDER_WILL_CONFIRM);
        getOrderCount(ORDER_WILL_PAY);
        getOrderCount(ORDER_WILL_RECEIVE);
    }

    public void onEvent(EventLoginSuccess event) {
        refeshDate();
    }
}
