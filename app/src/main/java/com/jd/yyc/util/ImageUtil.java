package com.jd.yyc.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.jd.yyc.R;
import com.jd.yyc.base.YYCApplication;
import com.jd.yyc.ui.util.glide.GlideRoundTransform;

import java.security.InvalidParameterException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created with IntelliJ IDEA. User: liu_yu Date: 13-9-3 Time: 下午2:50 To change
 * this template use File | Settings | File Templates.
 */
public class ImageUtil {

    public static final String TAG = "image loading";
    public static final int CHAT_IMG_SIZE = 300;

    private int default_img = R.drawable.default_pic;
    private int default_round_img = R.mipmap.ic_launcher;

    private static ImageUtil imageLoading;

    public synchronized static ImageUtil getInstance() {
        if (imageLoading == null) {
            imageLoading = new ImageUtil();
        }
        return imageLoading;
    }

    public static String getImageSize(String url, int width, int height) {
        if (CheckTool.isEmpty(url))
            return url;
        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("www"))
            return String.format("%s?imageView2/0/w/%s/h/%s", url, width, height);
        return url;
    }

    public void loadTinyImage(Object context, ImageView imageView, String url) {
        loadImage(context, imageView, url, default_img);
    }

    public void loadSmallImage(Object context, ImageView imageView, String url) {
        loadImage(context, imageView, url, default_img);
    }

    public void loadMediumImage(Object context, ImageView imageView, String url) {
        loadImage(context, imageView, url, default_img);
    }

    public void loadLargeCornerImage(Object context, ImageView imageView, String url, int cornerSize) {
        loadImage(context, imageView, getImageSize(url, 720, 720), default_img, cornerSize, false, null);
    }

    public void loadLargeImage(Object context, ImageView imageView, String url) {
        loadImage(context, imageView, url, default_img);
    }

    public void loadOriginalImage(Object context, ImageView imageView, String url) {
        loadImage(context, imageView, url, default_img);
    }

    public void loadSmallRoundImage(Object context, ImageView imageView, String url) {
        loadImage(context, imageView, url, default_round_img, true);
    }

    /**
     * 异步加载图片
     * 不做圆角和圆形裁剪
     *
     * @param imageView       图片View
     * @param url             图片地址
     * @param defaultResource 预加载默认图片
     */
    public void loadImage(Object context, ImageView imageView, String url, int defaultResource) {
        loadImage(context, imageView, url, defaultResource, -1, false, null);
    }

    /**
     * 异步加载图片
     * 做圆角裁剪
     *
     * @param imageView         图片View
     * @param url               图片地址
     * @param defaultResource   预加载默认图片
     * @param roundedCornerSize 图片外圆角尺寸
     */
    public void loadImage(Object context, ImageView imageView, String url, int defaultResource, float roundedCornerSize) {
        loadImage(context, imageView, url, defaultResource, roundedCornerSize, false, null);
    }

    /**
     * 异步加载图片
     * 做圆形裁剪
     *
     * @param imageView       图片View
     * @param url             图片地址
     * @param defaultResource 预加载默认图片
     * @param isRound         图片是否裁剪成圆形
     */
    public void loadImage(Object context, ImageView imageView, String url, int defaultResource, boolean isRound) {
        loadImage(context, imageView, url, defaultResource, -1, isRound, null);
    }

    /**
     * 异步加载图片
     * 不做圆角和圆形裁剪
     *
     * @param imageView       图片View
     * @param url             图片地址
     * @param defaultResource 预加载默认图片
     */
    public void loadImage(Object context, ImageView imageView, String url, int defaultResource, final ImageLoadCallBack imageLoadCallBack) {
        loadImage(context, imageView, url, defaultResource, -1, false, imageLoadCallBack);
    }

    /**
     * 异步加载图片
     * 做圆角裁剪
     *
     * @param imageView         图片View
     * @param url               图片地址
     * @param defaultResource   预加载默认图片图片外圆角尺寸
     * @param roundedCornerSize 图片外圆角尺寸
     */
    public void loadImage(Object context, ImageView imageView, String url, int defaultResource, float roundedCornerSize, final ImageLoadCallBack imageLoadCallBack) {
        loadImage(context, imageView, url, defaultResource, roundedCornerSize, false, imageLoadCallBack);
    }

    /**
     * 异步加载图片
     * 做圆形裁剪
     *
     * @param imageView       图片View
     * @param url             图片地址
     * @param defaultResource 预加载默认图片
     * @param isRound         图片是否裁剪成圆形
     */
    public void loadImage(Object context, ImageView imageView, String url, int defaultResource, boolean isRound, final ImageLoadCallBack imageLoadCallBack) {
        loadImage(context, imageView, url, defaultResource, -1, isRound, imageLoadCallBack);
    }

    /**
     * 获取毛玻璃图片
     */
    public void loadBlurImage(ImageView imageView, String url, int defaultResource, int maxSize, int radius) {
//        UrlImageViewHelper.setUrlDrawable(imageView, url, defaultResource, maxSize, radius);
    }

    /**
     * 异步加载图片
     *
     * @param imageView         图片View
     * @param url               图片地址
     * @param defaultResource   预加载默认图片
     * @param roundedCornerSize 图片外圆角尺寸
     * @param isRound           图片是否裁剪成圆形
     */
    private void loadImage(Object context, ImageView imageView, String url, int defaultResource, float roundedCornerSize, boolean isRound, final ImageLoadCallBack imageLoadCallBack) {
        if (isRound) {
            loadImageRound(context, imageView, url, default_round_img, imageLoadCallBack);
        } else if (roundedCornerSize > 0) {
            loadImageRounded(context, imageView, url, defaultResource, (int) roundedCornerSize, imageLoadCallBack);
        } else {
            loadImageMaxSize(context, imageView, url, defaultResource, imageLoadCallBack);
        }
    }

    /**
     * 获取普通图片
     */
    private void loadImageMaxSize(final Object context, ImageView imageView, String url, int defaultResource, final ImageLoadCallBack imageLoadCallBack) {
        if (CheckTool.isEmpty(url) && defaultResource != 0) {
            imageView.setImageResource(defaultResource);
            return;
        }

        RequestManager manager = getGlide(context);
        if (manager == null) {
            return;
        }
        manager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(defaultResource)
                .error(defaultResource)
                .crossFade()
                .thumbnail(0.8f)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(false);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(true);
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    /**
     * 获取圆角图片
     */
    private void loadImageRounded(Object context, ImageView imageView, String url, int defaultResource, final int roundedCornerSize, final ImageLoadCallBack imageLoadCallBack) {
        if (CheckTool.isEmpty(url) && defaultResource != 0) {
            imageView.setImageResource(defaultResource);
            return;
        }

//        Transformation transformation = new RoundedCornersTransformation(Nuts.context, roundedCornerSize, 0);
        GlideRoundTransform transformation = new GlideRoundTransform(YYCApplication.context, roundedCornerSize);

        RequestManager manager = getGlide(context);
        if (manager == null) {
            return;
        }
        manager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(defaultResource)
                .error(defaultResource)
                .crossFade(200)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(false);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(true);
                        }
                        return false;
                    }
                })
                .transform(new CenterCrop((Context) context), transformation)
                .into(imageView);
    }

    /**
     * 获取圆图片
     */
    private void loadImageRound(Object context, ImageView imageView, String url, int defaultResource, final ImageLoadCallBack imageLoadCallBack) {
        if (CheckTool.isEmpty(url) && defaultResource != 0) {
            imageView.setImageResource(defaultResource);
            return;
        }

        Transformation transformation = new CropCircleTransformation(YYCApplication.context);


        RequestManager manager = getGlide(context);
        if (manager == null) {
            return;
        }
        manager.load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(defaultResource)
                .error(defaultResource)
                .crossFade(200)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(false);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(true);
                        }
                        return false;
                    }
                })
                .bitmapTransform(transformation)
                .into(imageView);
    }


    //获取图片的bitmap
    public void getPicBitmap(Object object, String url, final BitmapCallBack callBack) {
        RequestManager manager = getGlide(object);
        if (manager == null) {
            return;
        }
        manager.load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(CHAT_IMG_SIZE, CHAT_IMG_SIZE)
                .placeholder(default_img).
                into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        callBack.callBack(resource);
                    }
                });
    }

    @Nullable
    private RequestManager getGlide(Object obj) {
        assertParam(obj);
        if (!assertNotDestroy(obj)) return null;

        if (obj instanceof Fragment) {
            return Glide.with((Fragment) obj);
        }
        if (obj instanceof android.app.Fragment) {
            return Glide.with((android.app.Fragment) obj);
        }
        return Glide.with((Context) obj);
    }

    private void assertParam(Object obj) {
        if (obj == null || (!(obj instanceof Fragment) && !(obj instanceof android.app.Fragment) && !(obj instanceof Context))) {
            throw new InvalidParameterException("you must start a load on a fragment or a context");
        }
    }

    private boolean assertNotDestroy(Object obj) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) return true;
        if (obj instanceof Activity)
            return !((Activity) obj).isDestroyed();
        if (obj instanceof Fragment)
            return (((Fragment) obj).getActivity() != null && !((Fragment) obj).getActivity().isDestroyed());
        if (obj instanceof android.app.Fragment)
            return (((android.app.Fragment) obj).getActivity() != null && !((android.app.Fragment) obj).getActivity().isDestroyed());
        return true;
    }

    public interface ImageLoadCallBack {
        void callBack(boolean isSuccess);
    }

    public interface BitmapCallBack {
        void callBack(Bitmap bitmap);
    }
}
