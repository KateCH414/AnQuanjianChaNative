package com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.Bean.GridBean.ImageFolderBean;
import com.miko.zd.anquanjianchanative.Model.BizPicGrid.BizGetImagesPaths;
import com.miko.zd.anquanjianchanative.Model.BizPicGrid.BizLoadAdapterImage;
import com.miko.zd.anquanjianchanative.R;
import com.miko.zd.anquanjianchanative.Utils.GridUtils.CommonViewHolder;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by zd on 2016/5/4.
 */
public class PopListAdapter extends CommonAdapter<ImageFolderBean> {

    private BizLoadAdapterImage mBizLoadImages;
    //所有的已经被选中的文件夹路径
    private HashSet<String> mCheckedFolders = new HashSet<>();
    //所有的被选中的图片
    private ArrayList<String> mCheckedImages=new ArrayList<>();
    private BizGetImagesPaths mBizGetPaths = new BizGetImagesPaths();

    public PopListAdapter(Context mContext, int mItemLayoutId, ArrayList<ImageFolderBean> mData,
                          ListView mListView) {
        super(mContext, mItemLayoutId, mData);
        this.mBizLoadImages = new BizLoadAdapterImage(mContext, mListView);


    }

    @Override
    public void convert(CommonViewHolder helper, final ImageFolderBean item) {
        ImageView mImageView = helper.getView(R.id.id_iv_item_poplist);
        TextView mTvName = helper.getView(R.id.id_tv_name_item_poplist);
        TextView mTvNum = helper.getView(R.id.id_tv_num_item_poplist);
        mTvName.setText(item.getName());
        mTvNum.setText(String.valueOf(item.getFileNumber()));
        mImageView.setTag(item.getFirstImagePath());
        mBizLoadImages.setImageView(item.getFirstImagePath(), mImageView);
        helper.getConvertView().setOnClickListener(v -> mOnItemClickLitener.onItemClick(item));
    }

    OnItemClickListener mOnItemClickLitener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public interface OnItemClickListener {
        void onItemClick(ImageFolderBean item);
    }
}
