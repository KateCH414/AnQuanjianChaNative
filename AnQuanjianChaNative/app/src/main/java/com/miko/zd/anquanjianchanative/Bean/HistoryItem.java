package com.miko.zd.anquanjianchanative.Bean;
/*
 * Created by zd on 2016/11/4.
 */

import android.graphics.Bitmap;

import java.util.ArrayList;

public class HistoryItem {
    int g,p,i,cb;
    String note,title;
    ArrayList<Bitmap> btPic;

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

    public ArrayList<Bitmap> getBtPic() {
        return btPic;
    }

    public void setBtPic(ArrayList<Bitmap> btPic) {
        this.btPic = btPic;
    }
}
