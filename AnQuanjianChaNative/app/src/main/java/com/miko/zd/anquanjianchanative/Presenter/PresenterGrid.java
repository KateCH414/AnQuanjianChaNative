package com.miko.zd.anquanjianchanative.Presenter;

import android.content.Context;
import android.provider.MediaStore;

import com.miko.zd.anquanjianchanative.Bean.GridBean.ImageFolderBean;
import com.miko.zd.anquanjianchanative.Model.BizPicGrid.BizGetImagesPaths;
import com.miko.zd.anquanjianchanative.Model.BizPicGrid.GetResultListener;
import com.miko.zd.anquanjianchanative.Model.BizPicGrid.IBizGetImagesPaths;
import com.miko.zd.anquanjianchanative.Selector.IViewPicSelector;

import java.util.ArrayList;

/**
 * Created by zd on 2016/5/1.
 */
public class PresenterGrid {
    private ArrayList<ImageFolderBean> mAllFolders = null;
    private IBizGetImagesPaths mBizGetPath;
    private IViewPicSelector mViewPicSelector;
    private ArrayList<String> mMainFolderImagePaths = null;

    public PresenterGrid(IViewPicSelector mViewPicSelector) {
        this.mViewPicSelector = mViewPicSelector;
        this.mBizGetPath = new BizGetImagesPaths();
    }

    public void doInit(final Context mContext) {
        mBizGetPath.getAllIamgesOnSd(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                mContext, new GetResultListener() {
                    @Override
                    public void onSuccess(ArrayList<String> mAllImagePaths) {
                        mMainFolderImagePaths = mAllImagePaths;
                        mViewPicSelector.updateGrid(mAllImagePaths);
                        mAllFolders = mBizGetPath.getImageFolders(mContext, mAllImagePaths);

                        //将所有图片作为一个文件夹加入文件夹队列
                        ImageFolderBean mMainFolder = new ImageFolderBean();
                        mMainFolder.setFolderPath("main");
                        mMainFolder.setFirstImagePath(mAllImagePaths.get(0));
                        mMainFolder.setName("所有图片");
                        mMainFolder.setFileNumber(mAllImagePaths.size());

                        mAllFolders.add(0, mMainFolder);
                        mViewPicSelector.initBottomPop(mAllFolders.get(0).getName(), mAllFolders.get(0).getFileNumber());
                    }

                    @Override
                    public void onFailure() {

                    }
                });
    }

    public void openPopList() {
        mViewPicSelector.openListPop(mAllFolders);
    }

    public void closePopList(ImageFolderBean mOnShowFolder) {
        mViewPicSelector.updateBottomPop(mOnShowFolder.getName(), mOnShowFolder.getFileNumber());
        mViewPicSelector.closeListPop();
        if (mOnShowFolder.getFolderPath().equals("main")) {
            mViewPicSelector.updateGrid(mMainFolderImagePaths);
        } else
            mViewPicSelector.updateGrid(mBizGetPath.getAllImagePathsInFolder(mOnShowFolder.getFolderPath()));
    }

}
