package com.jd.yyc.net;

/**
 *
 */
public abstract class RequestCallback<T> {

    public abstract void requestCallBack(boolean success, T result, String err);
}