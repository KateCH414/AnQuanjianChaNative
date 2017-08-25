package com.miko.zd.anquanjianchanative.Biz;
/*
 * Created by zd on 2016/11/12.
 */

public interface IBizDBInitItem {
    boolean hadInited();
    void insertItem(String serialNum,String content);
}
