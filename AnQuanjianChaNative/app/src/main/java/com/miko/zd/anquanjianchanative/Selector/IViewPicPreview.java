package com.miko.zd.anquanjianchanative.Selector;/*
 * Created by zd on 2016/7/20.
 */

public interface IViewPicPreview {
    void showProgress(String title);
    void showProgress(String title, int max);
    void updateProgress(int progress);
    void closeProgress();
    void changeTitle(String title);
    void closePreview();
}
