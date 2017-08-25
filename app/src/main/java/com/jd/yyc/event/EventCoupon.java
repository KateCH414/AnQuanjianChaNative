package com.jd.yyc.event;

/**
 *
 */
public class EventCoupon {
    private Long vId;   //店铺id

    public EventCoupon(Long vId) {
        this.vId = vId;
    }

    public Long getvId() {
        return vId == null ? 0 : vId;
    }
}
