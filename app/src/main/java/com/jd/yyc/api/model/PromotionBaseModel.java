package com.jd.yyc.api.model;

/**
 * 促销信息
 */

public class PromotionBaseModel extends Base {
    private Long promotionId; //促销id
    private String description; //促销描述
    private String adWords; //促销广告词
    private String adLinkName; //广告词链接名称
    private String adLinkSrc; //    广告词链接地址
    private Integer limitTime; //限制购买次数
    private long beginTime; //促销活动开始时间
    private long endTime; //促销活动结束时间
    private Integer promotionType; //促销类型 见PRomotionTypeEnum  3, "直降    7, "秒杀
    //    private CetusPrice mustManPrice; //满赠促销的满多少元
//    private CetusPrice addPrice;  //满赠促销加价购元
    private Integer maxChooseNum; //最多可选赠品数量
    private Double soldRate; //已售比例
    private Integer totalNum; //总库存
    private Integer saleNum; //已售库存


    public int getPromotionType() {
        return promotionType == null ? 0 : promotionType;
    }

}
