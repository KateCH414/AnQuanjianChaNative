package com.jd.yyc.api.model;

import java.util.List;

/**
 * Created by wf on 16/6/20.
 */
public class Data<E> extends Base {
    public List<E> list;
    public int page;
    public long ts;
}
