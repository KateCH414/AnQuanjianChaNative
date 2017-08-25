package com.jd.yyc.api.model;


import java.util.List;

/**
 * Created by wf on 16/6/17.
 */
public class Result<T> extends YYCBase {
    public List<T> data;

    public Result() {
    }

    public boolean isNoData() {
        return code == STATUS_NO_DATA;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
