package com.miko.zd.anquanjianchanative.Bean.RealmBean;
/*
 * Created by zd on 2016/11/13.
 */

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class HistoryCheckedBean extends RealmObject {
    String user;
    String date;
    @PrimaryKey
    int order;
    String dept;
    String principal;
    String room;
    boolean isSavad;


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public boolean isSavad() {
        return isSavad;
    }

    public void setSavad(boolean savad) {
        isSavad = savad;
    }
}
