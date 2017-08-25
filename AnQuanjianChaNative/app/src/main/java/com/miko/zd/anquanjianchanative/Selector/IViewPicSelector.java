package com.miko.zd.anquanjianchanative.Selector;

import com.miko.zd.anquanjianchanative.Bean.GridBean.ImageFolderBean;

import java.util.ArrayList;


public interface IViewPicSelector {

    void updateBottomPop(String mName, int mSize);

    void openListPop(ArrayList<ImageFolderBean> mAllFolders);

    void closeListPop();

    void updateGrid(ArrayList<String> allPaths);

    void initBottomPop(String name, int fileNumber);
}
