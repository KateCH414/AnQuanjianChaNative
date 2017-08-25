package com.miko.zd.anquanjianchanative.Bean.RealmBean;
/*
 * Created by zd on 2016/11/15.
 */

import io.realm.RealmObject;

public class OpenedItemBean extends RealmObject {
    String serialNum;
    int order;
    boolean isOpen;

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

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

}
