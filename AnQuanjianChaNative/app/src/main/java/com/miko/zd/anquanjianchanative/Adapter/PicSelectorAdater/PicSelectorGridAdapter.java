package com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;

import com.miko.zd.anquanjianchanative.Model.BizPicGrid.BizLoadAdapterImage;
import com.miko.zd.anquanjianchanative.R;
import com.miko.zd.anquanjianchanative.Utils.GridUtils.CommonViewHolder;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by zd on 2016/4/30.
 */

/**
 * GridView的Adapter,在这其中我们使用一个暂时的HashSet来从数据库中获取已经选择的图片，用这个暂时的set来进行渲染，
 * set的增删是与数据库的增删同步的，这样忌改变了数据库，又提高了滑动的效率
 *
 * @author Create by zd
 * @time 17:12
 */

public class PicSelectorGridAdapter extends CommonAdapter<String> {

    public static HashSet<String> mAllChecked ;
    private BizLoadAdapterImage mLoadAdapterImage;

    public PicSelectorGridAdapter(Context mContext, int mItemLayoutId, ArrayList<String> mData
            , GridView mGridview) {
        super(mContext, mItemLayoutId, mData);
        this.mLoadAdapterImage = new BizLoadAdapterImage(mContext, mGridview);
        mAllChecked = new HashSet<>();
    }

    @Override
    public void convert(CommonViewHolder helper, final String item) {

        final ImageView imageView = helper.getView(R.id.id_iv_pic_item_grid);
        CheckBox checkBox = helper.getView(R.id.id_cb_item_grid);
        imageView.setTag(item);
        mLoadAdapterImage.setImageView(item, imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImageClickListener.onImageClick(item);
            }
        });

        if (mAllChecked.contains(item)) {
            checkBox.setChecked(true);
            imageView.setColorFilter(Color.parseColor("#77000000"));
        } else {
            checkBox.setChecked(false);
            imageView.setColorFilter(null);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("xxx", String.valueOf(mAllChecked.size()));
                if (mAllChecked.contains(item)) {
                    mAllChecked.remove(item);
                    imageView.setColorFilter(null);
                    mOnCheckBoxClickListener.onBoxClick(mAllChecked.size());
                } else {
                    mAllChecked.add(item);
                    imageView.setColorFilter(Color.parseColor("#77000000"));
                    mOnCheckBoxClickListener.onBoxClick(mAllChecked.size());
                }
            }
        });
    }

    private OnCheckBoxClickListener mOnCheckBoxClickListener;
    private OnImageClickListener mOnImageClickListener;
    public void setOnCheckBoxClickListener(OnCheckBoxClickListener mOnCheckBoxClickListener,
                                           OnImageClickListener mOnImageClickListener) {
        this.mOnCheckBoxClickListener = mOnCheckBoxClickListener;
        this.mOnImageClickListener=mOnImageClickListener;
    }

    public interface OnImageClickListener{
        void onImageClick(String path);
    }
    public interface OnCheckBoxClickListener {
        void onBoxClick(int size);
    }
}

