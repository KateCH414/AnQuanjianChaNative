package com.miko.zd.anquanjianchanative.Bean;
/*
 * Created by zd on 2016/11/6.
 */

public class TextBean {
    String c;
    String content;

    public TextBean(String c) {
        this.c = c;
    }

    public TextBean(String c, String content) {
        this.c = c;
        this.content = content;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
