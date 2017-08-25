package com.jd.yyc.widget.bannerView;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jd.yyc.R;
import com.jd.yyc.base.YYCApplication;
import com.jd.yyc.widget.bannerView.loader.ImageLoader;


public class CustomImageListLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(YYCApplication.context)
                .load((String) path)
                .centerCrop()
                .crossFade()
                .thumbnail(0.1f)
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(imageView);

    }

    //提供createImageView 方法，方便fresco自定义ImageView
    @Override
    public ImageView createImageView(Context context) {
        return null;
    }
}
