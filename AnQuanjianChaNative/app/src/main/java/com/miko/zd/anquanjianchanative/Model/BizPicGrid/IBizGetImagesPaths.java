package com.miko.zd.anquanjianchanative.Model.BizPicGrid;

import android.content.Context;
import android.net.Uri;

import com.miko.zd.anquanjianchanative.Bean.GridBean.ImageFolderBean;

import java.util.ArrayList;

/**
 * Created by zd on 2016/4/30.
 */
public interface IBizGetImagesPaths {
    void getAllIamgesOnSd(Uri mType, Context mContext, GetResultListener mGetResultListener);
    ArrayList<ImageFolderBean> getImageFolders(Context mContext, ArrayList<String> allPath);
    ArrayList<String> getAllImagePathsInFolder(String mFolderPath);
}
