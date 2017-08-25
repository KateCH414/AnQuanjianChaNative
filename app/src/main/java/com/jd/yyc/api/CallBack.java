package com.jd.yyc.api;

import java.util.List;

/**
 * Created by zhangweifeng1 on 2017/6/8.
 */

public abstract class CallBack<T> {

    public void onSuccess(List<T> list) {
    }

    public void onSuccess(T data) {
    }

    public void onFailure(int statusCode, String errorMessage) {

    }
}
