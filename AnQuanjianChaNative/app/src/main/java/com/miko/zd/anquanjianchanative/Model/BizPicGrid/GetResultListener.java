package com.miko.zd.anquanjianchanative.Model.BizPicGrid;

import java.util.ArrayList;

/**
 * Created by zd on 2016/4/30.
 */
public interface GetResultListener {
    void onSuccess(ArrayList<String> allPaths);
    void onFailure();
}
