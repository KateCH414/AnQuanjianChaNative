package com.jd.yyc.api.model;


/**
 * Created by wf on 16/6/17.
 */
public class ResultObject<T> extends YYCBase {
    public T data;

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
