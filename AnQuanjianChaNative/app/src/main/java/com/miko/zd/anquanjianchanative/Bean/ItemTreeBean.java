package com.miko.zd.anquanjianchanative.Bean;
/*
 * Created by zd on 2016/11/1.
 */

import java.util.ArrayList;

public class ItemTreeBean {
    int index;
    int parentIndex;
    String itemName;
    ArrayList<ItemTreeBean>itemList;
    boolean isOpen;
    String note = "";
    int cb = -1;
    ArrayList<String>paths = new ArrayList<>();
    public ItemTreeBean(int index, int parentIndex, String itemName, ArrayList<ItemTreeBean> itemList) {
        this.index = index;
        this.parentIndex = parentIndex;
        this.itemName = itemName;
        this.itemList = itemList;
        isOpen = false;
        paths.add("miko");
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCb() {
        return cb;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<String> paths) {
        this.paths = paths;
    }

    public ItemTreeBean(int index, int parentIndex, String itemName) {
        this.index = index;
        this.parentIndex = parentIndex;
        this.itemName = itemName;
        itemList = null;
        paths.add("miko");
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ArrayList<ItemTreeBean> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<ItemTreeBean> itemList) {
        this.itemList = itemList;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
