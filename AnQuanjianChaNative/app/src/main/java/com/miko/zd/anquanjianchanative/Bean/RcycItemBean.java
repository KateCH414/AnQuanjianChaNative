package com.miko.zd.anquanjianchanative.Bean;
/*
 * Created by zd on 2016/11/1.
 */

public class RcycItemBean {
    String content;
    int type;
    boolean isOpen;
    public RcycItemBean(String content, int type,boolean isOpen) {
        this.content = content;
        this.isOpen = isOpen;
        this.type = type;
    }
    public RcycItemBean(String content, int type) {
        this.content = content;
        this.isOpen = false;
        this.type = type;
    }
    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

