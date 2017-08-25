package com.miko.zd.anquanjianchanative.Bean;

import java.util.ArrayList;

public class JsonItem {
    int g,p,i,cb;
    String note,title;
    ArrayList<String> base64Pic;

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getCb() {
        return cb;
    }

    public void setCb(int cb) {
        this.cb = cb;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getBase64Pic() {
        return base64Pic;
    }

    public void setBase64Pic(ArrayList<String> base64Pic) {
        this.base64Pic = base64Pic;
    }
}