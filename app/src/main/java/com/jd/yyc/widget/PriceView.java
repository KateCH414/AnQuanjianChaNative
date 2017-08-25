package com.jd.yyc.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.Price;
import com.jd.yyc.api.model.PromotionBaseVo;
import com.jd.yyc.util.Util;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by zhangweifeng1 on 2017/6/9.
 */

public class PriceView extends LinearLayout {
    @InjectView(R.id.single_price)
    TextView singlePriceView;
    @InjectView(R.id.sku_price)
    TextView skuPrice;
    @InjectView(R.id.wholesale_price)
    TextView wholesalePrice;
    @InjectView(R.id.wholesale_type)
    TextView wholesaleType;
    @InjectView(R.id.sku_authentication)
    TextView authentication;
    @InjectView(R.id.wholesale_container)
    LinearLayout wholesaleContainer;

    public PriceView(Context context) {
        super(context);
        init();
    }

    public PriceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PriceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.layout_price, this);
        ButterKnife.inject(this, this);
    }


    public void setPrice(Price myPrice) {
        singlePriceView.setText("零售价");
        skuPrice.setText("- - -");
        wholesalePrice.setText("- - -");

        if (myPrice != null) {
            wholesaleContainer.setVisibility(VISIBLE);
            authentication.setVisibility(GONE);
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
            skuPrice.setText(retailPrice <= 0 ? "- - -" : myPrice.retailPrice);
            if (myPrice.status == 0) {           //0 正常显示
                if (myPrice.promotionList != null && !myPrice.promotionList.isEmpty()) {
                    for (int i = 0; i < myPrice.promotionList.size(); i++) {
                        PromotionBaseVo bean = myPrice.promotionList.get(i);
                        if (bean.promotionId > 0 && bean.promotionType == 7) {//优先展示秒杀
                            wholesaleType.setText("批发价");
                            if (myPrice.currDate > bean.beginTime && myPrice.currDate < bean.endTime) {//判断是否在活动有效期内
                                wholesalePrice.setText(promotionPrice <= 0 ? "- - -" : myPrice.promotionPrice);
                            } else {
                                wholesalePrice.setText(price <= 0 ? "- - -" : myPrice.price);
                            }
                            break;
                        } else if (bean.promotionId > 0 && bean.promotionType == 3) {//直降
                            wholesaleType.setText("批发价");
                            wholesalePrice.setText(promotionPrice <= 0 ? "- - -" : myPrice.promotionPrice);
                        } else {
                            wholesaleType.setText("批发价");               //批发价
                            wholesalePrice.setText(price <= 0 ? "- - -" : myPrice.price);
                        }
                    }
                } else {
                    wholesaleType.setText("批发价");
                    wholesalePrice.setText(price <= 0 ? "- - -" : myPrice.price);
                }
            } else if (myPrice.status == 2) {//建立采购关系可见
                wholesaleContainer.setVisibility(GONE);
                authentication.setVisibility(VISIBLE);
                authentication.setText("建立采购关系可见");
            } else {      //批发价认证后可见
                wholesaleContainer.setVisibility(GONE);
                authentication.setVisibility(VISIBLE);
                authentication.setText("批发价认证后可见");
            }
        } else {//没有数据
            authentication.setVisibility(GONE);
        }
    }

}
