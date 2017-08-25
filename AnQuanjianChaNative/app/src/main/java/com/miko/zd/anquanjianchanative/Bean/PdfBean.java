package com.miko.zd.anquanjianchanative.Bean;
/*
 * Created by zd on 2016/11/6.
 */

public class PdfBean {
    String path;
    String name;
    boolean isChecked;

    int type = 0;
    public PdfBean(String path, String name, boolean isChecked) {
        this.path = path;
        this.name = name;
        this.isChecked = isChecked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
