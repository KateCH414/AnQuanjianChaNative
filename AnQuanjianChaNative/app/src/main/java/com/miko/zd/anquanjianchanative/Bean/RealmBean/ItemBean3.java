package com.miko.zd.anquanjianchanative.Bean.RealmBean;
/*
 * Created by zd on 2016/11/13.
 */

import io.realm.RealmObject;

public class ItemBean3 extends RealmObject {
    String itemName;
    String serialNum;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }
}
