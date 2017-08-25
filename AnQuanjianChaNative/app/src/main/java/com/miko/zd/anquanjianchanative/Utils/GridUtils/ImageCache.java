package com.miko.zd.anquanjianchanative.Utils.GridUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

/*
 * Created by zd on 2016/4/23.
 */
public class ImageCache {
    private LruCache<String, Bitmap> mMemoryCache;
    private Context mContext;

    public ImageCache(Context mContext) {
        this.mContext = mContext;
        int mMaxMemory = (int) Runtime.getRuntime().maxMemory();
        int mCacheMemorySize = mMaxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(mCacheMemorySize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
        };
    }


    public void addBitmap2Cache(String key, Bitmap bitmap) {
        if (getBitmapFromCache(key) == null && bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }

    }

    public Bitmap getBitmapFromCache(String key) {
        return mMemoryCache.get(key);
    }
}
