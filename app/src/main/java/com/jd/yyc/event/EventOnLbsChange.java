package com.jd.yyc.event;

import com.jd.yyc.api.model.LBS;

/**
 * Created by zhangweifeng on 16-2-23.
 */
public class EventOnLbsChange {
    public LBS lbs;

    public EventOnLbsChange(LBS lbs) {
        this.lbs = lbs;
    }
}
