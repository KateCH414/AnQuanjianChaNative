package com.miko.zd.anquanjianchanative.Model.BizPicGrid;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.miko.zd.anquanjianchanative.R;
import com.miko.zd.anquanjianchanative.Utils.GridUtils.CreateBitmapUtils;
import com.miko.zd.anquanjianchanative.Utils.GridUtils.ImageCache;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zd on 2016/4/25.
 */
public class BizLoadAdapterImage {
    private ImageCache mImageCache;
    private View mAdapterview;
    public static Set<LoadImageTask> tasks = new HashSet<>();
    public static ExecutorService FULL_TASK_EXECUTOR = (ExecutorService) Executors.newFixedThreadPool(20);

    public BizLoadAdapterImage(Context mContext, View mAdapterview) {
        this.mImageCache = new ImageCache(mContext);
        this.mAdapterview = mAdapterview;
    }

    public void setImageView(String uri, ImageView imageView) {

        Bitmap bitmap = mImageCache.getBitmapFromCache(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.ic_default_picture);
            if (tasks.size() < 20) {
                LoadImageTask task = new LoadImageTask();
                tasks.add(task);
                task.executeOnExecutor(FULL_TASK_EXECUTOR, uri);
            }
        }
    }


    public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            // 在后台开始下载图片
            Bitmap bitmap = CreateBitmapUtils.decodeSampledBitmapFromResource(imageUrl, 200, 200);
            if (bitmap != null) {
                // 图片下载完成后缓存到LrcCache中
                mImageCache.addBitmap2Cache(imageUrl, bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // 根据Tag找到相应的ImageView控件，将下载好的图片显示出来。
            ImageView imageView = (ImageView) mAdapterview.findViewWithTag(imageUrl);
            if (imageView != null && bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
            tasks.remove(this);
        }
    }

    public void cancelAllTasks() {
        if (tasks != null) {
            for (LoadImageTask task : tasks) {
                task.cancel(false);
            }
        }
    }
}
