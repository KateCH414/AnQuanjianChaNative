package com.jd.yyc.goodsdetail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.CallBack;
import com.jd.yyc.api.model.AddressBean;
import com.jd.yyc.api.model.GoodsInfo;
import com.jd.yyc.api.model.Price;
import com.jd.yyc.api.model.PromotionBaseVo;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.SkuSum;
import com.jd.yyc.api.model.VendorModel;
import com.jd.yyc.base.CommonFragment;
import com.jd.yyc.cartView.CartViewActivity;
import com.jd.yyc.constants.Contants;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.login.PortalActivity;
import com.jd.yyc.refreshfragment.LoadingDialogUtil;
import com.jd.yyc.search.SearchResultActivity;
import com.jd.yyc.ui.activity.pic.ImagePreviewAct;
import com.jd.yyc.ui.util.DialogUtil;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.HttpUtil;
import com.jd.yyc.util.L;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.SharePreferenceUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.UIUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jd.yyc.widget.CountDownTimerView;
import com.jd.yyc.widget.SlideDetailsLayout;
import com.jd.yyc.widget.bannerView.Banner;
import com.jd.yyc.widget.bannerView.BannerConfig;
import com.jd.yyc.widget.bannerView.CustomImageListLoader;
import com.jd.yyc.widget.bannerView.Transformer;
import com.jd.yyc.widget.bannerView.listener.OnBannerClickListener;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.jd.yyc.refreshfragment.LoadingDialogUtil.dialog;


/**
 * Created by jiahongbin on 2017/5/26.
 */

public class GoodsDetailFragment extends CommonFragment implements View.OnClickListener,
        SlideDetailsLayout.OnSlideDetailsListener, DialogAddress.AddSelectListener, Contants {

    @InjectView(R.id.tv_goodsDetail_name)
    TextView tv_goodsDetail_name;
    @InjectView(R.id.tv_goods_detail_ad)
    TextView tv_goods_detail_ad;
    @InjectView(R.id.tv_goods_detail_pifa)
    TextView wholesalePrice;
    @InjectView(R.id.tv_text_ls)
    TextView singlePriceView;
    @InjectView(R.id.tv_goods_detail_ls)
    TextView skuPrice;
    @InjectView(R.id.tv_goods_detail_cuxiao)
    TextView tv_goods_detail_cuxiao;
    @InjectView(R.id.sale_goods)
    RelativeLayout sale_goods;
    @InjectView(R.id.tv_goods_detail_yaoguige)
    TextView tv_goods_detail_yaoguige;
    @InjectView(R.id.tv_goods_detail_guoyao)
    TextView tv_goods_detail_guoyao;
    @InjectView(R.id.tv_goods_detail_fuwudizhi)
    TextView tv_goods_detail_fuwudizhi;
    @InjectView(R.id.tv_goods_detail_dizhi)
    TextView tv_goods_detail_dizhi;
    @InjectView(R.id.tv_state)
    TextView tv_state;
    @InjectView(R.id.seckill_time)
    CountDownTimerView countDownTimerView;
    @InjectView(R.id.tv_join_cart)
    TextView tv_join_cart;
    @InjectView(R.id.iv_address)
    ImageView iv_address;
    @InjectView(R.id.cart_layout)
    LinearLayout cart_layout;
    @InjectView(R.id.ll_shop)
    LinearLayout ll_shop;
    @InjectView(R.id.rl_Goodsaddress)
    RelativeLayout rl_Goodsaddress;
    @InjectView(R.id.fl_goods)
    FrameLayout fl_goods;
    @InjectView(R.id.iv_back)
    ImageView iv_back;
    @InjectView(R.id.tv_sk)
    TextView tv_sk;
    @InjectView(R.id.lv_skill)
    LinearLayout lv_skill;
    @InjectView(R.id.tv_goods_detail_yuanjia)
    TextView tv_goods_detail_yuanjia;
    @InjectView(R.id.tv_pifa)
    TextView wholesaleType;
    @InjectView(R.id.view_fragment_banner)
    Banner view_banner;
    @InjectView(R.id.tv_cartSum)
    TextView tv_cartSum;
    EditText sku_num;

    @InjectView(R.id.sv_switch)
    SlideDetailsLayout sv_switch;
    public Long skuId;
    public static List<AddressBean> addressList;
    public String buttonTitle;
    public String imgurl;
    public Long id;
    public int stock;
    public int stockNum;
    public float buttonPrice;
    public long vendorId; //商家id
    private String shopName;//商家名称
    public ArrayList<String> banerImgList = new ArrayList<>();
    public int onLine;
    public int SKU_Sum;
    Button confirm;
    TextView tx_title;
    TextView tv_stock;
    TextView pifa;
    String cat1;
    String cat2;
    String cat3;
    String shopId;
    String data_SkuId;
    public Boolean PriceTpye = false;
    boolean isLeft;
    private int lastValue = -1;
    private int currentPosition = 1;

    public GoodsDetailFragment newInstance(long str) {
        Bundle args = new Bundle();
        args.putLong("GoodsId", str);
        GoodsDetailFragment fragment = new GoodsDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_goodsdetail;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        skuId = getArguments().getLong("GoodsId");
        initBanner();
        initListener();
        getGoodsData();
        getCartSum();
        addFragment();
        SetSlideDetail();
    }

    @Override
    public void onDestroy() {
        view_banner.stopAutoPlay();
        if (lv_skill.getVisibility() == View.VISIBLE)
            countDownTimerView.cancelDownTimer();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int sum = 0;
        switch (v.getId()) {
            case R.id.tv_join_cart:
                HashMap<String, String> join_hashmap = new HashMap<>();
                ClickInterfaceParam paramJoin = new ClickInterfaceParam();
                paramJoin.event_id = "yjc_android_201706262|14";
                paramJoin.page_name = "单品页";
                join_hashmap.put("item_first_cate_id", cat1);
                join_hashmap.put("item_second_cate_id", cat2);
                join_hashmap.put("item_third_cate_id", cat3);
                join_hashmap.put("shop_id", shopId);
                join_hashmap.put("sku_id", data_SkuId);
                paramJoin.map = join_hashmap;
                JDMaUtil.sendClickData(paramJoin);
                if (Util.isLogin()) {
                    HttpUtil.checkRelation(vendorId, new CallBack<Boolean>() {
                        @Override
                        public void onSuccess(Boolean isRelation) {
                            if (isRelation) {
                                showCouponList();
                                //   DialogView hintDialog = DialogView.newInstance(buttonTitle + "@" + imgurl + "@" + id + "@" + stockNum + "@" + buttonPrice + "@" + skuId);
//                                hintDialog.show(getFragmentManager(), "hindialog");
                            } else {
                                ToastUtil.show(mContext, "尚未建立采购关系");
                            }
                        }
                    });
                } else {
                    PortalActivity.launch(mContext, Activity.RESULT_OK);
                }
                break;
            case R.id.rl_Goodsaddress:
                ClickInterfaceParam param = new ClickInterfaceParam();
                param.event_id = "yjc_android_201706262|6";
                param.page_name = "单品页";
                JDMaUtil.sendClickData(param);
                if (Util.isLogin() == true && tv_goods_detail_dizhi.getText().toString().trim() != null) {
                    showAddressDialog();
                } else {
                    Toast.makeText(mContext, "尚未登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.view_banner:
                break;
            case R.id.ll_shop:
                HashMap<String, String> Shop_hasmap = new HashMap<>();
                ClickInterfaceParam paramshop = new ClickInterfaceParam();
                paramshop.event_id = "yjc_android_201706262|8";
                paramshop.page_name = "单品页";
                Shop_hasmap.put("shop_id", shopId);
                paramshop.map = Shop_hasmap;
                JDMaUtil.sendClickData(paramshop);
                if (!Util.isFastDoubleClick() && !TextUtils.isEmpty(shopName)) {
                    SearchResultActivity.launch(mContext, shopName);
                }
                break;
            case R.id.cart_layout:
                ClickInterfaceParam paramcar = new ClickInterfaceParam();
                paramcar.event_id = "yjc_android_201706262|7";
                paramcar.page_name = "单品页";
                JDMaUtil.sendClickData(paramcar);
                if (Util.isLogin()) {
                    CartViewActivity.launch(mContext);
                } else {
                    PortalActivity.launch(mContext, LOGIN_FROM_ALONE_CART);
                }
                break;
            case R.id.plus_icon:
                HashMap<String, String> plus_hashmap = new HashMap<>();
                ClickInterfaceParam paramPlus = new ClickInterfaceParam();
                paramPlus.event_id = "yjc_android_201706262|10";
                paramPlus.page_name = "单品页";
                plus_hashmap.put("item_first_cate_id", cat1);
                plus_hashmap.put("item_second_cate_id", cat2);
                plus_hashmap.put("item_third_cate_id", cat3);
                plus_hashmap.put("shop_id", shopId);
                plus_hashmap.put("sku_id", data_SkuId);
                paramPlus.map = plus_hashmap;
                JDMaUtil.sendClickData(paramPlus);

                sum = Integer.valueOf(sku_num.getText().toString()) + 1;
                sku_num.setText(sum + "");
                break;
            case R.id.sub_icon:
                HashMap<String, String> sub_hashmap = new HashMap<>();
                ClickInterfaceParam paramSub = new ClickInterfaceParam();
                paramSub.event_id = "yjc_android_201706262|11";
                paramSub.page_name = "单品页";
                sub_hashmap.put("item_first_cate_id", cat1);
                sub_hashmap.put("item_second_cate_id", cat2);
                sub_hashmap.put("item_third_cate_id", cat3);
                sub_hashmap.put("shop_id", shopId);
                sub_hashmap.put("sku_id", data_SkuId);
                paramSub.map = sub_hashmap;
                JDMaUtil.sendClickData(paramSub);
                if (Integer.valueOf(sku_num.getText().toString()) > 1) {
                    sum = Integer.valueOf(sku_num.getText().toString()) - 1;
                    sku_num.setText(sum + "");
                } else {
                    Toast.makeText(getContext(), "已经不能再少了", Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.confirm:

                long sku_sum = Long.parseLong(sku_num.getText().toString());
                if (stockNum < sku_sum) {
                    Toast.makeText(mContext, "库存不足，请适量购买", Toast.LENGTH_SHORT).show();
                } else {
                    addShopCar(skuId, sku_sum);
                }
                break;
            default:
                break;
        }
    }

    private void addShopCar(long skuId, long num) {
        HashMap<String, String> params = new HashMap<>();
        params.put("skuId", skuId + "");
        params.put("num", num + "");
        Network.getRequestBuilder(Util.createUrl("cart/add", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<VendorModel> result = gson.fromJson(model, new TypeToken<ResultObject<VendorModel>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                                getCartSum();
                                dialog.dismiss();
                            } else {
                                ToastUtil.show(getContext(), "添加失败");
                                dialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).post();
    }
    private void showCouponList() {
        View content = LayoutInflater.from(getTopActivity()).inflate(R.layout.popup_slide_from_bottom, null);
        final ImageView plus_icon = (ImageView) (content.findViewById(R.id.plus_icon));
        final ImageView sub_icon = (ImageView) (content.findViewById(R.id.sub_icon));
        sku_num = (EditText) (content.findViewById(R.id.sku_num));
        tx_title = (TextView) (content.findViewById(R.id.tx_title));
        TextView tv_price = (TextView) (content.findViewById(R.id.tv_price));
        pifa = (TextView) (content.findViewById(R.id.pifa));
        tv_stock = (TextView) (content.findViewById(R.id.tv_stock));
        ImageView imageView = (ImageView) (content.findViewById(R.id.imageView));
        confirm = (Button) (content.findViewById(R.id.confirm));
        dialog = DialogUtil.buildDialog(mContext, content, Gravity.BOTTOM, R.anim.appear_from_bottom, true);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        // dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        sku_num.setInputType(InputType.TYPE_CLASS_NUMBER);
        sku_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String numStr = sku_num.getText().toString().trim();
                if (CheckTool.isEmpty(numStr)) {
                    sub_icon.setEnabled(false);
                    return;
                }
                int num = Integer.valueOf(numStr);
                if (num < 1) {
                    num = 1;
                    sku_num.setText(num + "");
                    sku_num.setSelection(sku_num.getText().toString().trim().length());
                    return;
                }

                if (num > 999) {
                    num = 999;
                    sku_num.setText(num + "");
                    sku_num.setSelection(sku_num.getText().toString().trim().length());
                    ToastUtil.show("每种商品最多购买999件");
                    return;
                }
                if (num > stock) {
                    num = stock;
                    sku_num.setText(num + "");
                    sku_num.setSelection(sku_num.getText().toString().trim().length());
                    ToastUtil.show("不能超过库存量");
                    return;
                }
                sub_icon.setEnabled(num > 1);
                plus_icon.setEnabled(num < stock && num < 999);

            }
        });
        //  getskuSum(skuId);
        getStockNum();
        if (PriceTpye) {
            pifa.setText("秒杀价");
        }
        tx_title.setText(buttonTitle);
        tv_price.setText("¥" + buttonPrice);
        Glide.with(getContext()).load(Contants.BasePicUrl + imgurl).placeholder(R.drawable.default_pic).into(imageView);
        confirm.setOnClickListener(this);
        plus_icon.setOnClickListener(this);
        sub_icon.setOnClickListener(this);
        dialog.show();
    }

    private void getStockNum() {
        HashMap<String, String> params = new HashMap<>();
        params.put("skuId", skuId + "");
        Network.getRequestBuilder(Util.createUrl("sku/detail", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        Gson gson = new Gson();
                        ResultObject<GoodsInfo> result = gson.fromJson(model, new TypeToken<ResultObject<GoodsInfo>>() {
                        }.getType());
                        if (result != null && result.data != null) {
                            stock = result.data.sku.stock;
                            tv_stock.setText(result.data.sku.stock + "件");

                        }
                    }
                })
                .post();

    }

    private void getskuSum(Long skuId) {

        HashMap<String, String> params = new HashMap<>();
        params.put("skuId", skuId + "");
        Network.getRequestBuilder(Util.createUrl("cart/cart", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<SkuSum> result = gson.fromJson(model, new TypeToken<ResultObject<SkuSum>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                SKU_Sum = result.data.total;
                                sku_num.setText(result.data.total + "");
                            } else {

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).post();


    }

    private void initListener() {
        iv_address.setOnClickListener(this);
        rl_Goodsaddress.setOnClickListener(this);
        tv_join_cart.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        cart_layout.setOnClickListener(this);
        ll_shop.setOnClickListener(this);
    }

    private void initBanner() {
        //设置banner样式
        view_banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        //设置指示器位置（当banner模式中有指示器时）
        view_banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置自动轮播，默认为true
        view_banner.isAutoPlay(false);
        //设置轮播时间
        view_banner.setDelayTime(3000);
        //设置切换动画
        view_banner.setBannerAnimation(Transformer.Default);
        view_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset != 0) {
                    if (lastValue >= positionOffsetPixels) {
                        //右滑
                        isLeft = false;
                    } else if (lastValue < positionOffsetPixels) {
                        //左滑
                        isLeft = true;
                    }
                }
                lastValue = positionOffsetPixels;
            }

            @Override
            public void onPageSelected(int position) {
                // ToastUtil.show("currentPosition"+currentPosition+"position"+position);
//                if (currentPosition != position) {
//                    if (isLeft) {
//                        //左滑
//                        //      ToastUtil.show("左滑");
//                        ClickInterfaceParam param = new ClickInterfaceParam();
//                        param.event_id = "yjc_android_201706262|2";
//                        param.page_name = "单品页";
//                        JDMaUtil.sendClickData(param);
//
//                    } else {
//                        //右滑
//                        //       ToastUtil.show("右滑");
//                        ClickInterfaceParam param = new ClickInterfaceParam();
//                        param.event_id = "yjc_android_201706262|3";
//                        param.page_name = "单品页";
//                        JDMaUtil.sendClickData(param);
//
//                    }
//                    currentPosition = position;
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        view_banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                ClickInterfaceParam param = new ClickInterfaceParam();
                param.event_id = "yjc_android_201706262|4";
                param.page_name = getString(R.string.SIgnle);
                JDMaUtil.sendClickData(param);
                Intent intent = new Intent(mContext.getApplicationContext(), ImagePreviewAct.class);
                intent.putStringArrayListExtra(ImagePreviewAct.EXTRA_URL_IMAGE_LIST, banerImgList);
                intent.putExtra(ImagePreviewAct.EXTRA_IMAGE_INDEX, position - 1);
                startActivity(intent);
            }
        });

        int width = ScreenUtil.getScreenWidth(mContext);
        UIUtil.setLinearLayoutParams(view_banner, width, width);
    }

    public void setBannerData(List<String> data) {
        final ArrayList<String> newImageList = new ArrayList<>();
        for (String s : data) {
            s = HttpContants.BasePicUrl + s;
            newImageList.add(s);
        }
        //开始轮播
        view_banner.setImages(newImageList).setImageLoader(new CustomImageListLoader()).start();
    }

    private void addFragment() {
        String url = "http://yao.m.jd.com/goodsDetail.html?skuId=" + skuId;
        GoodsInfoWebFragment goodsInfo = GoodsInfoWebFragment.newInstance(url);
        //默认显示商品详情tab
        getChildFragmentManager().beginTransaction().replace(R.id.fl_goods, goodsInfo).commitAllowingStateLoss();
    }

    /**
     * 获取价格数据
     */
    private void getPriceData() {
        HttpUtil.getSkuPrice(skuId + "", new CallBack<Price>() {
            @Override
            public void onSuccess(List<Price> list) {
                if (list != null && !list.isEmpty()) {
                    setPrice(list.get(0));
                }
            }
        });

    }

    /**
     * 设置价格数据
     *
     * @param
     */

    //零售文案 tv_text_ls，零售价格tv_goods_detail_ls，批发价格文案tv_pifa，批发价格tv_goods_detail_pifa，
    public void setPrice(Price myPrice) {
        singlePriceView.setText("零售价");
        skuPrice.setText("- - -");
        wholesalePrice.setText("- - -");
        if (myPrice != null) {
            float price = 0;
            float retailPrice = 0;
            float promotionPrice = 0;
            try {
                if (!TextUtils.isEmpty(myPrice.price)) {
                    price = Float.parseFloat(myPrice.price);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(myPrice.retailPrice)) {
                    retailPrice = Float.parseFloat(myPrice.retailPrice);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(myPrice.promotionPrice)) {
                    promotionPrice = Float.parseFloat(myPrice.promotionPrice);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            skuPrice.setText("¥" + (retailPrice <= 0 ? "- - -" : myPrice.retailPrice));
            if (myPrice.status == 0) {           //0 正常显示
                if (stock > 0 && onLine == 2) {
                    tv_join_cart.setBackgroundColor(Color.parseColor("#C81623"));
                    tv_join_cart.setClickable(true);
                } else {
                    tv_join_cart.setClickable(false);
                    tv_join_cart.setBackgroundColor(Color.parseColor("#666666"));
                }
                if (myPrice.promotionList != null && !myPrice.promotionList.isEmpty()) {
                    for (int i = 0; i < myPrice.promotionList.size(); i++) {
                        if (myPrice.promotionList.get(i).adWords != null)
                            tv_goods_detail_ad.setText(myPrice.promotionList.get(i).adWords);
                        if (myPrice.promotionList.get(i).description != null) {
                            sale_goods.setVisibility(View.VISIBLE);
                            tv_goods_detail_cuxiao.setText(myPrice.promotionList.get(i).description);
                        } else {
                            sale_goods.setVisibility(View.GONE);
                        }
                        PromotionBaseVo bean = myPrice.promotionList.get(i);
                        if (bean.promotionId > 0 && bean.promotionType == 7) {//优先展示秒杀

                            lv_skill.setVisibility(View.VISIBLE);
                            PriceTpye = true;
                            wholesaleType.setText("秒杀价");
                            singlePriceView.setText("零售指导价");
                            if (myPrice.currDate > bean.beginTime && myPrice.currDate < bean.endTime) {//判断是否在活动有效期内
                                countDownTimerView.setDownTime((bean.endTime - myPrice.currDate));
                                countDownTimerView.startDownTimer();
                                wholesalePrice.setText("¥" + (promotionPrice <= 0 ? "- - -" : myPrice.promotionPrice));
                                buttonPrice = Float.valueOf(myPrice.promotionPrice);

                            } else {
                                buttonPrice = Float.valueOf(myPrice.price);
                                wholesalePrice.setText("¥" + (price <= 0 ? "- - -" : myPrice.price));
                                lv_skill.setVisibility(View.GONE);
                            }
                            break;
                        } else if (bean.promotionId > 0 && bean.promotionType == 3) {//直降
                            lv_skill.setVisibility(View.GONE);
                            wholesaleType.setText("批发价");
                            wholesalePrice.setText("¥" + (promotionPrice <= 0 ? "- - -" : myPrice.promotionPrice));
                            buttonPrice = Float.valueOf(myPrice.price);
                        } else {
                            lv_skill.setVisibility(View.GONE);
                            wholesaleType.setText("批发价");               //批发价
                            wholesalePrice.setText("¥" + (price <= 0 ? "- - -" : myPrice.price));
                            buttonPrice = Float.valueOf(myPrice.price);
                        }
                    }
                } else {
                    lv_skill.setVisibility(View.GONE);
                    wholesaleType.setText("批发价");
                    wholesalePrice.setText("¥" + (price <= 0 ? "- - -" : myPrice.price));
                    buttonPrice = Float.valueOf(myPrice.price);
                }
            } else if (myPrice.status == 2) {//建立采购关系可见
                wholesalePrice.setText("建立采购关系可见");
                tv_join_cart.setBackgroundColor(Color.parseColor("#666666"));
                tv_join_cart.setClickable(false);
            } else {      //批发价认证后可见
                // wholesaleType.setVisibility(View.GONE);
                wholesalePrice.setText("批发价认证后可见");
                tv_join_cart.setBackgroundColor(Color.parseColor("#666666"));
                tv_join_cart.setClickable(false);
            }
        }
    }

    /**
     * 获取商品数据
     */
    private void getGoodsData() {
        LoadingDialogUtil.show(mContext);
        HashMap<String, String> params = new HashMap<>();
        params.put("skuId", skuId + "");
        Network.getRequestBuilder(Util.createUrl("sku/detail", ""))
                .params(params)
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        ToastUtil.show("网络异常，请稍后重试");
                        LoadingDialogUtil.close();
                    }
                })
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        LoadingDialogUtil.close();
                        Gson gson = new Gson();
                        ResultObject<GoodsInfo> result = gson.fromJson(model, new TypeToken<ResultObject<GoodsInfo>>() {
                        }.getType());
                        if (result != null && result.data != null) {
                            setGoodsData(result.data);
                            if(result.data.sku!=null){
                                cat1 = result.data.sku.cid1 + "";
                                cat2 = result.data.sku.cid2 + "";
                                cat3 = result.data.sku.cid3 + "";
                                data_SkuId = result.data.sku.sku + "";
                            }
                            shopId = result.data.shop.id + "";
                        }

                        HashMap<String, String> hasmap = new HashMap<>();
                        PvInterfaceParam param = new PvInterfaceParam();
                        param.page_id = "0002";
                        param.page_name = "单品页";
                        hasmap.put("item_first_cate_id", cat1);
                        hasmap.put("item_second_cate_id", cat2);
                        hasmap.put("item_third_cate_id", cat3);
                        hasmap.put("shop_id", shopId);
                        hasmap.put("sku_id", data_SkuId);
                        param.map = hasmap;
                        JDMaUtil.sendPVData(param);
                    }
                })
                .post();
    }

    private void getCartSum() {

        HashMap<String, String> params = new HashMap<>();
        params.put("skuId", skuId + "");
        Network.getRequestBuilder(Util.createUrl("cart/cart", ""))
                .params(null)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<SkuSum> result = gson.fromJson(model, new TypeToken<ResultObject<SkuSum>>() {
                            }.getType());
                            if (result != null &&result.success&& result.data != null) {
                                if (result.data.getTotal() > 0) {
                                    tv_cartSum.setVisibility(View.VISIBLE);
                                    if (result.data.getTotal() > 99) {
                                        tv_cartSum.setText(99 + "+");
                                    } else {
                                        tv_cartSum.setText(result.data.getTotal() + "");
                                    }
                                }else{
                                    tv_cartSum.setVisibility(View.INVISIBLE);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).post();
    }

    /**
     * 设置商品数据
     */
    private void setGoodsData(GoodsInfo data) {
        // 数据校验
        if (data.getSku().name != null && CheckTool.isEmpty(data.getSku().getRelativeName())) {
            if (data.sku.relative.get(0).attrName != null) {
                tv_goodsDetail_name.setText(data.sku.name + data.sku.relative.get(0).attrName);
            } else {
                tv_goodsDetail_name.setText(data.sku.name);
            }
        } else {
            tv_goodsDetail_name.setText(data.getSku().name);
        }
        if (tv_goodsDetail_name.getText().toString() != null) {
            buttonTitle = tv_goodsDetail_name.getText().toString();
        }
        onLine = data.sku.state;
        if (data.skuExt.medicalSpec != null) {
            tv_goods_detail_yaoguige.setText(data.skuExt.medicalSpec);
        }
        if (data.skuExt.allowNum != null) {
            tv_goods_detail_guoyao.setText(data.skuExt.allowNum);
        }
        stock = data.sku.stock;
        getPriceData();
        /*if (data.sku.stock <= 0) {
           // tv_state.setText("现货");
            tv_join_cart.setClickable(false);
            tv_join_cart.setBackgroundColor(Color.parseColor("#666666"));
        }*/
        if (data.sku.mainImg != null) {
            imgurl = data.sku.fullMainImg;
        }
        if (data.sku.stock >= 0) {
            stockNum = data.sku.stock;
        }
        if (data.shop.title != null) {
            tv_goods_detail_fuwudizhi.setText("由" + " " + data.shop.title + " " + "发货，并提供售后服务");
            shopName = data.shop.title;
        }
        if (data.sku != null && data.sku.fullImgList != null && data.sku.fullImgList.size() > 0) {
            banerImgList.clear();
            banerImgList.addAll(data.sku.fullImgList);
            setBannerData(data.sku.fullImgList);
        }
        if (data.shop.venderId != 0) {
            vendorId = data.shop.venderId;
            if (Util.isLogin()) {
                getDetailAddress();
            } else {
                rl_Goodsaddress.setVisibility(View.GONE);
            }
        }

        id = data.sku.sku;
    }

    private void showAddressDialog() {
        if (vendorId == 0)
            return;
        AddressBean.getAddressList(getTopActivity(), vendorId, new AddressBean.AddressCallback() {

            @Override
            public void requestCallback(AddressBean addressBean, List<AddressBean> addressList) {
                DialogAddress Rl_addressDialog = DialogAddress.newInstance();
                Rl_addressDialog.setAddSelectListener(GoodsDetailFragment.this);
                Rl_addressDialog.setDate(addressList);
                Rl_addressDialog.show(getFragmentManager(), "hint_dialog");
            }

            @Override
            public void onFailed(String msg) {
                Toast.makeText(mContext, "未登录", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailAddress() {
        HashMap<String, String> params = new HashMap<>();
        params.put("venderId", vendorId + "");
        Network.getRequestBuilder(Util.createUrl("confirm/address", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<List<AddressBean>> result = gson.fromJson(model, new TypeToken<ResultObject<List<AddressBean>>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                addressList = result.data;
                                String detail = result.data.get(0).addressDetailForPage;
                                if (detail != null)
                                    tv_goods_detail_dizhi.setText(detail);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        L.d("errorMessage=" + errorMessage);
                    }
                })
                .post();
    }

    @Override
    public void onStatucChanged(SlideDetailsLayout.Status status) {

    }

    @Override
    public void addressSelect(String addStr) {
        tv_goods_detail_dizhi.setText(addStr);
        SharePreferenceUtil.setSelectAddress(mContext, addStr);
    }

    private void SetSlideDetail() {
        sv_switch.setOnSlideDetailsListener(new SlideDetailsLayout.OnSlideDetailsListener() {
            @Override
            public void onStatucChanged(SlideDetailsLayout.Status status) {
                if (status.equals(SlideDetailsLayout.Status.OPEN)) {
                    HashMap<String, String> hasmap = new HashMap<>();
                    PvInterfaceParam param = new PvInterfaceParam();
                    param.page_id = "0003";
                    param.page_name = "单品页";
                    hasmap.put("item_first_cate_id", cat1);
                    hasmap.put("item_second_cate_id", cat2);
                    hasmap.put("item_third_cate_id", cat3);
                    hasmap.put("shop_id", shopId);
                    hasmap.put("sku_id", data_SkuId);
                    param.map = hasmap;
                    JDMaUtil.sendPVData(param);
                }else{
                    if (status.equals(SlideDetailsLayout.Status.CLOSE)) {
                        HashMap<String, String> hasmap = new HashMap<>();
                        ClickInterfaceParam param = new ClickInterfaceParam();
                        param.event_id = "yjc_android_201706262|13";
                        param.page_name = getString(R.string.MoreDetail);
                        hasmap.put("shop_id", shopId);
                        param.map = hasmap;
                        JDMaUtil.sendClickData(param);

                    }
                }
            }
        });
    }




}
