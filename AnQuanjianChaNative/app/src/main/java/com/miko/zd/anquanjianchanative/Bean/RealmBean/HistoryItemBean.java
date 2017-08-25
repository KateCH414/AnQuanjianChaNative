package com.miko.zd.anquanjianchanative.Bean.RealmBean;
/*
 * Created by zd on 2016/11/16.
 */

import io.realm.RealmList;
import io.realm.RealmObject;

public class HistoryItemBean extends RealmObject {
    int order;
    String serialNum,note;
    int checkBox;
    RealmList<PathRealm>pathList;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(int checkBox) {
        this.checkBox = checkBox;
    }

    public RealmList<PathRealm> getPathList() {
        return pathList;
    }

    public void setPathList(RealmList<PathRealm> pathList) {
        this.pathList = pathList;
    }
}
