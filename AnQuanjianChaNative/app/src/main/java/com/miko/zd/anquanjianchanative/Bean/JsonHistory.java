package com.miko.zd.anquanjianchanative.Bean;
/*
 * Created by zd on 2016/11/4.
 */

import java.util.ArrayList;

public class JsonHistory {
    public static int TYPE_SAVED = 1;
    public static int TYPE_UNSAVED = 0;

    String inspector;
    String date;
    int order;
    String room,dept,principal;
    ArrayList<HistoryItem>itemList;
    int type = TYPE_SAVED;
    String rePath;
    public String getInspector() {
        return inspector;
    }

    public int getType() {
        return type;
    }

    public static int getTypeSaved() {
        return TYPE_SAVED;
    }

    public static void setTypeSaved(int typeSaved) {
        TYPE_SAVED = typeSaved;
    }

    public static int getTypeUnsaved() {
        return TYPE_UNSAVED;
    }

    public static void setTypeUnsaved(int typeUnsaved) {
        TYPE_UNSAVED = typeUnsaved;
    }

    public String getRePath() {
        return rePath;
    }

    public void setRePath(String rePath) {
        this.rePath = rePath;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ArrayList<HistoryItem> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<HistoryItem> itemList) {
        this.itemList = itemList;
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

}
