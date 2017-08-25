package com.miko.zd.anquanjianchanative.Model.BizPicGrid;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.miko.zd.anquanjianchanative.Bean.GridBean.ImageFolderBean;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by zd on 2016/4/30.
 */
public class BizGetImagesPaths implements IBizGetImagesPaths {

    @Override
    public void getAllIamgesOnSd(Uri mType, Context mContext,
                                 GetResultListener mGetResultListener) {
        GetAllFilePathsTask getPicPaths = new GetAllFilePathsTask(mContext, mType, mGetResultListener);
        getPicPaths.execute();
    }

    @Override
    public ArrayList<ImageFolderBean> getImageFolders(Context mContext, ArrayList<String> allPath) {
        ArrayList<ImageFolderBean> mAllImageFolders = new ArrayList<>();
        //设置辅助HashSet防止多次查找同一文件夹
        HashSet<String> hashIsContain = new HashSet<>();

        for (String filePath : allPath) {
            // 获取该图片的父路径名
            File folderFile = new File(filePath).getParentFile();
            if (folderFile == null)
                continue;

            String folderpath = folderFile.getAbsolutePath();
            ImageFolderBean imageFolder = null;
            //防止多次寻找一个文件夹
            if (hashIsContain.contains(folderpath)) {
                continue;
            } else {
                imageFolder = new ImageFolderBean();
                imageFolder.setFolderPath(folderpath);
                imageFolder.setFirstImagePath(filePath);
                imageFolder.setName(folderFile.getName());
                //获取文件夹中的图片的个数
                File[] fileArray = folderFile.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        if (pathname.getName().endsWith(".jpg") || pathname.getName().endsWith(".png")
                                || pathname.getName().endsWith(".gif"))
                            return true;
                        else
                            return false;
                    }
                });
                int fileNum = 0;
                if(fileArray == null){
                    Log.i("wtf","fff");
                }
                else {
                    fileNum = fileArray.length;
                }
                imageFolder.setFileNumber(fileNum);
                //将文件夹添加如队列
                mAllImageFolders.add(imageFolder);
                hashIsContain.add(folderpath);
            }
        }
        return mAllImageFolders;
    }

    @Override
    public ArrayList<String> getAllImagePathsInFolder(String mFolderPath) {
        ArrayList<String> mMainFolderImagePathss = new ArrayList<>();
        File mFolder = new File(mFolderPath);
        File[] files = mFolder.listFiles(pathname -> {
            if (pathname.getName().endsWith(".jpg") || pathname.getName().endsWith(".png")
                    || pathname.getName().endsWith(".gif"))
                return true;
            else
                return false;
        });
        for (File f : files) {
            mMainFolderImagePathss.add(f.getAbsolutePath());
        }
        return mMainFolderImagePathss;
    }
}

