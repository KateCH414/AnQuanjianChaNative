package com.jd.yyc.ui.activity.pic;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jd.yyc.R;
import com.jd.yyc.base.CommonActivity;
import com.jd.yyc.constants.HttpContants;
import com.jd.yyc.ui.widget.photoview.HackyViewPager;
import com.jd.yyc.ui.widget.photoview.PhotoView;
import com.jd.yyc.ui.widget.photoview.PhotoViewAttacher;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ImageUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class ImagePreviewAct extends CommonActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    public static String EXTRA_URL_IMAGE_LIST = "url_image_list";
    public static String EXTRA_LOCAL_IMAGE_LIST = "local_image_list";
    public static String EXTRA_IMAGE_INDEX = "image_index";

    HackyViewPager mActImageGallery;
    RelativeLayout titleLayout;
    ImageView backBtn;

    private int toShowIndex = 0;
    private List<String> urlPicList = new ArrayList<>();
    private List<String> localPicPath = new ArrayList<>();

    private SamplePagerAdapter samplePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localPicPath = getIntent().getStringArrayListExtra(EXTRA_LOCAL_IMAGE_LIST);
        urlPicList = getIntent().getStringArrayListExtra(EXTRA_URL_IMAGE_LIST);
        toShowIndex = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);

        if (CheckTool.isEmpty(localPicPath) && CheckTool.isEmpty(urlPicList)) {
            finish();
            return;
        }
        setContentView(R.layout.act_pic_preview);
        mActImageGallery = (HackyViewPager) findViewById(R.id.act_image_gallery);
        titleLayout = (RelativeLayout) findViewById(R.id.constom_action_bar);
        backBtn = (ImageView) findViewById(R.id.back_view);
        titleLayout.setBackgroundColor(getResources().getColor(R.color.black));
        backBtn.setOnClickListener(this);
        setTranslucentStatus();
        setStatusBarColor();
        setWidgetState();
    }

    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setStatusBarColor() {
        SystemBarTintManager tintManager = new SystemBarTintManager(mContext);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(getResources().getColor(R.color.black));
    }


    protected void initWidget() {

    }

    protected void setWidgetState() {
        samplePagerAdapter = new SamplePagerAdapter();
        mActImageGallery.setAdapter(samplePagerAdapter);
        mActImageGallery.setCurrentItem(toShowIndex);

        mActImageGallery.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_view:
                finish();
                break;
        }
    }

    private class SamplePagerAdapter extends PagerAdapter implements View.OnLongClickListener, PhotoViewAttacher.OnViewTapListener {
        @Override
        public int getCount() {
            if (!CheckTool.isEmpty(urlPicList)) {
                return urlPicList.size();
            } else {
                return localPicPath.size();
            }
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            photoView.setMaxScale(8);

            if (!CheckTool.isEmpty(urlPicList)) {
                final String imgUrl = urlPicList.get(position);
                ImageUtil.getInstance().loadLargeImage(ImagePreviewAct.this, photoView, HttpContants.BasePicUrl + imgUrl);
                photoView.setTag(imgUrl);
            } else {
                ImageUtil.getInstance().loadLargeImage(ImagePreviewAct.this, photoView, localPicPath.get(position));
            }
            photoView.setOnViewTapListener(this);

            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public boolean onLongClick(final View v) {
            return true;
        }

        @Override
        public void onViewTap(View view, float x, float y) {
            finish();
        }
    }
}
