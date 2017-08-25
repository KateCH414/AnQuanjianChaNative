package com.miko.zd.anquanjianchanative.Bean;
/*
 * Created by zd on 2016/11/2.
 */

import java.util.ArrayList;

public class RecordItemBean {
    String content;
    int checkBox;
    String note;
    ArrayList<String>paths;
    int grad,parent,index;

    public int getGrad() {
        return grad;
    }

    public void setGrad(int grad) {
        this.grad = grad;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public RecordItemBean(int grad, int parent, int index) {
        this.grad = grad;
        this.parent = parent;
        this.index = index;
    }

    public RecordItemBean(String content, int checkBox, String note, ArrayList<String> paths, int grad, int parent, int index) {

        this.content = content;
        this.checkBox = checkBox;
        this.note = note;
        this.paths = paths;
        this.grad = grad;
        this.parent = parent;
        this.index = index;
    }

    public RecordItemBean(String content, int checkBox, String note, ArrayList<String> paths) {
        this.content = content;
        this.checkBox = checkBox;
        this.note = note;
        this.paths = paths;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(int checkBox) {
        this.checkBox = checkBox;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public ArrayList<String> getPaths() {
        return paths;
    }

    public void setPaths(ArrayList<String> paths) {
        this.paths = paths;
    }
}
