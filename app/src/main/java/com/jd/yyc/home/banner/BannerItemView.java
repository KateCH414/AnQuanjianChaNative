package com.jd.yyc.home.banner;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jd.yyc.R;
import com.jd.yyc.api.model.Banner;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.goodsdetail.GoodsDetailActivity;
import com.jd.yyc.search.SearchActivity;
import com.jd.yyc.ui.activity.web.YYCWebActivity;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;


/**
 * Created by wf on 15/3/20.
 */
public class BannerItemView extends FrameLayout {

    ImageView imageView;


    public BannerItemView(Context context) {
        super(context);
        init();

    }

    public BannerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BannerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_banner_view, this);
        imageView = (ImageView) findViewById(R.id.banner_view);
    }

    public void setData(final Banner bean) {
        Glide.with(getContext())
                .load(HttpContants.BasePicUrl + bean.img)
                .thumbnail(0.1f)
                .placeholder(R.drawable.default_pic)
//                .error(R.drawable.photo_picker_ic_broken_image_black_48dp)
                .into(imageView);

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!Util.isFastDoubleClick()) {
                    //轮播图点击埋点
                    ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
                    clickInterfaceParam.page_id = "0012";
                    clickInterfaceParam.page_name = "首页";
                    clickInterfaceParam.event_id = "yjc_android_201706262|37";
                    JDMaUtil.sendClickData(clickInterfaceParam);
                    if (bean.source == 1) {//原生页面
                        if (bean.type == 1) {// 商详页
                            GoodsDetailActivity.launch(getContext(), bean.skuId);
                        } else if (bean.type == 2) {//搜索页
                            SearchActivity.launch(getContext(), bean.searchKey);
                        }
                    } else {
                        YYCWebActivity.launch(getContext(), bean.url, "", true);
                    }
                }
            }
        });
    }
}
