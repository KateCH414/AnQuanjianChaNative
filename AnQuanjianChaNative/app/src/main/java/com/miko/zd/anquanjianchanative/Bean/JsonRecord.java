package com.miko.zd.anquanjianchanative.Bean;
/*
 * Created by zd on 2016/11/4.
 */

import java.util.ArrayList;

public class JsonRecord {
    String inspector;
    String date;
    int order;
    String room,dept,principal;
    ArrayList<JsonItem>itemList = new ArrayList<>();

    public String getInspector() {
        return inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
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

    public ArrayList<JsonItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<JsonItem> itemList) {
        this.itemList = itemList;
    }

}
