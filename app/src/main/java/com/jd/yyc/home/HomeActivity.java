package com.jd.yyc.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SkuSum;
import com.jd.yyc.base.NoActionBarActivity;
import com.jd.yyc.cartView.CartViewFragment;
import com.jd.yyc.category.CategoryFragment;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.event.EventCartNum;
import com.jd.yyc.event.EventHome;
import com.jd.yyc.event.EventLoginMessage;
import com.jd.yyc.event.EventLogout;
import com.jd.yyc.login.PortalActivity;
import com.jd.yyc.mine.CouponCenter;
import com.jd.yyc.mine.MineFragment;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jd.yyc.widget.NavigationBar;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/5/18.
 */

public class HomeActivity extends NoActionBarActivity implements NavigationBar.TabChangeListener, NavigationBar.TabClickListener, Contants {
    @InjectView(R.id.navigation_bar)
    NavigationBar navigationBar;

    @InjectView(R.id.tv_cartSum)
    TextView cartSum;

    private int currentTabItem = 0;
    private CartViewFragment cartViewFragment;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_home;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentTabItem == 2 && cartViewFragment != null) {
            cartViewFragment.getDate(false);
        }
        setCartSum();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationBar.setTabChangeListener(this);
        navigationBar.setTabClickListener(this);
        if (savedInstanceState != null) {
            currentTabItem = savedInstanceState.getInt("currentTabItem", 0);
            hideAllFragment();
        }

        navigationBar.findViewById(R.id.btn_home).setSelected(true);
        showFragment("home");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentTabItem", currentTabItem);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //默认选中第一个
        if (savedInstanceState != null) {
            currentTabItem = savedInstanceState.getInt("currentTabItem");
            navigationBar.onCheckPosition(currentTabItem);
        }
    }

    @Override
    public void onBackPressed() {
        if (!Util.isSlowFastDoubleClick()) {
            ToastUtil.show(mContext, "再按一次退出应用");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onTabChange(int position) {

        if (currentTabItem == 0) {
            hideFragment("home");
        } else if (currentTabItem == 1) {
            hideFragment("CategoryAdapter");
        } else if (currentTabItem == 2) {
            hideFragment("shopcar");
        } else {
            hideFragment("profile");
        }

        if (position == 0) {
            setCartEidtMode();
            showFragment("home");
            //首页点击埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0012";
            clickInterfaceParam.page_name = "首页";
            clickInterfaceParam.event_id = "yjc_android_201706262|30";
            JDMaUtil.sendClickData(clickInterfaceParam);
        } else if (position == 1) {
            setCartEidtMode();
            showFragment("CategoryAdapter");
            //分类点击埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0012";
            clickInterfaceParam.page_name = "首页";
            clickInterfaceParam.event_id = "yjc_android_201706262|31";
            JDMaUtil.sendClickData(clickInterfaceParam);
        } else if (position == 2) {
            showFragment("shopcar");
            //购物车点击埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0012";
            clickInterfaceParam.page_name = "首页";
            clickInterfaceParam.event_id = "yjc_android_201706262|32";
            JDMaUtil.sendClickData(clickInterfaceParam);
        } else {
            setCartEidtMode();
            showFragment("profile");
            //我的点击埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0012";
            clickInterfaceParam.page_name = "首页";
            clickInterfaceParam.event_id = "yjc_android_201706262|33";
            JDMaUtil.sendClickData(clickInterfaceParam);
        }

        currentTabItem = position;
    }

    private void showFragment(String tag) {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if (tag.equals("home")) {
                fragment = new HomeFragment();
            } else if (tag.equals("CategoryAdapter")) {
                fragment = new CategoryFragment();
            } else if (tag.equals("shopcar")) {
                cartViewFragment = CartViewFragment.newInstance(false);
                fragment = cartViewFragment;
            } else if (tag.equals("profile")) {
                fragment = new MineFragment();
            }
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.tab_content, fragment, tag)
                    .commitAllowingStateLoss();

        } else {
            getSupportFragmentManager().beginTransaction().show(fragment).commitAllowingStateLoss();
            if (tag.equals("shopcar") && cartViewFragment != null)
                cartViewFragment.getDate(false);

            if (tag.equals("home")) {
                //首页PV埋点
                PvInterfaceParam pvInterfaceParam = new PvInterfaceParam();
                pvInterfaceParam.page_id = "0012";
                pvInterfaceParam.page_name = "首页";
                JDMaUtil.sendPVData(pvInterfaceParam);
            } else if (tag.equals("CategoryAdapter")) {
                //分类页面埋点
                PvInterfaceParam pvInterfaceParam = new PvInterfaceParam();
                pvInterfaceParam.page_id = "0004";
                pvInterfaceParam.page_name = "分类列表页面";
                JDMaUtil.sendPVData(pvInterfaceParam);
            } else if (tag.equals("shopcar")) {

            } else if (tag.equals("profile")) {

            }
        }
    }

    private void hideAllFragment() {
        hideFragment("home");
        hideFragment("CategoryAdapter");
        hideFragment("shopcar");
        hideFragment("profile");
    }

    private void hideFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().hide(fragment).commitAllowingStateLoss();
        }
    }

    /**
     * @return 当前选中的tab索引
     */
    public int getCurrentItem() {
        return currentTabItem;
    }


    public void onEvent(EventHome event) {
        navigationBar.onCheckPosition(0);
    }

    public void onEvent(EventCartNum event) {
        setCartSum();
    }

    public void onEvent(EventLogout event) {
        cartSum.setVisibility(View.GONE);
    }


    public void onEvent(EventLoginMessage event) {
        if (event.type == 1) {
            CouponCenter.launch(mContext);
        }

        if (event.type == 2) {
            navigationBar.onCheckPosition(2);
        }

        if (event.type == 4) {
            navigationBar.onCheckPosition(3);
        }
        //  Toast.makeText(HomeActivity.this, event.type, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onTabClick(int position) {
        if ((position == 2) && !Util.isLogin()) {
            PortalActivity.launch(mContext, position == 2 ? LOGIN_FROM_HOME_CART : LOGIN_FROM_USER_CENTER);
            return false;
        } else {
            return true;
        }
    }

    private void setCartEidtMode() { //设置购物车的编辑模式
        if (cartViewFragment != null)
            cartViewFragment.setEdit();
    }

    private void setCartSum() {
        Network.getRequestBuilder(Util.createUrl("cart/cart", ""))
                .params(null)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<SkuSum> result = gson.fromJson(model, new TypeToken<ResultObject<SkuSum>>() {
                            }.getType());
                            if (result != null && result.success && result.data != null) {
                                cartSum.setVisibility(result.data.getTotal() > 0 ? View.VISIBLE : View.GONE);
                                if (result.data.getTotal() > 99) {
                                    cartSum.setText(99 + "+");
                                } else {
                                    cartSum.setText(result.data.getTotal() + "");
                                }
                            }
                        } catch (Exception e) {
                            cartSum.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                }).post();
    }
}
