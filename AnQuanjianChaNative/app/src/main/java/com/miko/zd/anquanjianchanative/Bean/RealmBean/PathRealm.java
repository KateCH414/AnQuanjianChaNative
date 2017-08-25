package com.miko.zd.anquanjianchanative.Bean.RealmBean;
/*
 * Created by zd on 2016/11/16.
 */

import io.realm.RealmObject;

public class PathRealm extends RealmObject{
    String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
