package com.jd.yyc.api.model;

import java.util.List;

/**
 * Created by zhenguo on 6/5/17.
 */
public class ResultEntity {

    /**
     * success : true
     * code : 1
     * msg : null
     * data : {"expired":{"totalCount":119,"list":[{"id":"10527843730","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527843731","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527843732","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527830012","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527830013","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527830011","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527825994","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194558,"areaId":0,"areaName":null},{"id":"10527825992","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194558,"areaId":0,"areaName":null}]},"unused":{"totalCount":97,"list":[{"id":"12310456569","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"5","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055737,"areaId":0,"areaName":null},{"id":"12309265041","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"10000","discount":"150","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055757,"areaId":0,"areaName":null},{"id":"12309183113","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"5000","discount":"70","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055741,"areaId":0,"areaName":null},{"id":"12307776101","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12307776105","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12307776097","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12291009821","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"3000","discount":"80","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127713,"areaId":0,"areaName":null},{"id":"12291009825","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"3000","discount":"80","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127713,"areaId":0,"areaName":null}]},"used":{"totalCount":6,"list":[{"id":"10117745531","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1492358400000,"endTime":1494863999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35822829,"areaId":0,"areaName":null},{"id":"9813607673","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1489680000000,"endTime":1492271999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35319869,"areaId":0,"areaName":null},{"id":"9813607674","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1489680000000,"endTime":1492271999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35319869,"areaId":0,"areaName":null},{"id":"7865660913","key":null,"actKey":null,"actRuleId":null,"type":0,"quota":"0","discount":"1","beginTime":1480348800000,"endTime":1480780740000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":34035649,"areaId":0,"areaName":null},{"id":"7809589313","key":null,"actKey":null,"actRuleId":null,"type":0,"quota":"0","discount":"1","beginTime":1479398400000,"endTime":1480089540000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":33921161,"areaId":0,"areaName":null},{"id":"6920292386","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2","discount":"1","beginTime":1477505700000,"endTime":1480175940000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉB2BÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":33518570,"areaId":0,"areaName":null}]}}
     */

    private boolean success;
    private int code;
    private Object msg;
    private DataEntity data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * expired : {"totalCount":119,"list":[{"id":"10527843730","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527843731","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527843732","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527830012","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527830013","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527830011","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527825994","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194558,"areaId":0,"areaName":null},{"id":"10527825992","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194558,"areaId":0,"areaName":null}]}
         * unused : {"totalCount":97,"list":[{"id":"12310456569","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"5","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055737,"areaId":0,"areaName":null},{"id":"12309265041","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"10000","discount":"150","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055757,"areaId":0,"areaName":null},{"id":"12309183113","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"5000","discount":"70","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055741,"areaId":0,"areaName":null},{"id":"12307776101","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12307776105","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12307776097","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12291009821","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"3000","discount":"80","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127713,"areaId":0,"areaName":null},{"id":"12291009825","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"3000","discount":"80","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127713,"areaId":0,"areaName":null}]}
         * used : {"totalCount":6,"list":[{"id":"10117745531","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1492358400000,"endTime":1494863999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35822829,"areaId":0,"areaName":null},{"id":"9813607673","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1489680000000,"endTime":1492271999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35319869,"areaId":0,"areaName":null},{"id":"9813607674","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1489680000000,"endTime":1492271999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35319869,"areaId":0,"areaName":null},{"id":"7865660913","key":null,"actKey":null,"actRuleId":null,"type":0,"quota":"0","discount":"1","beginTime":1480348800000,"endTime":1480780740000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":34035649,"areaId":0,"areaName":null},{"id":"7809589313","key":null,"actKey":null,"actRuleId":null,"type":0,"quota":"0","discount":"1","beginTime":1479398400000,"endTime":1480089540000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":33921161,"areaId":0,"areaName":null},{"id":"6920292386","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2","discount":"1","beginTime":1477505700000,"endTime":1480175940000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉB2BÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":33518570,"areaId":0,"areaName":null}]}
         */

        private ExpiredEntity expired;
        private UnusedEntity unused;
        private UsedEntity used;

        public ExpiredEntity getExpired() {
            return expired;
        }

        public void setExpired(ExpiredEntity expired) {
            this.expired = expired;
        }

        public UnusedEntity getUnused() {
            return unused;
        }

        public void setUnused(UnusedEntity unused) {
            this.unused = unused;
        }

        public UsedEntity getUsed() {
            return used;
        }

        public void setUsed(UsedEntity used) {
            this.used = used;
        }

        public static class ExpiredEntity {
            /**
             * totalCount : 119
             * list : [{"id":"10527843730","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527843731","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527843732","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2000","discount":"50","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194557,"areaId":0,"areaName":null},{"id":"10527830012","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527830013","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527830011","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194928,"areaId":0,"areaName":null},{"id":"10527825994","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194558,"areaId":0,"areaName":null},{"id":"10527825992","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1493913600000,"endTime":1496505599000,"limitCat":"½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":36194558,"areaId":0,"areaName":null}]
             */

            private int totalCount;
            private List<ListEntity> list;

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public static class ListEntity {
                /**
                 * id : 10527843730
                 * key : null
                 * actKey : null
                 * actRuleId : null
                 * type : 1
                 * quota : 2000
                 * discount : 50
                 * beginTime : 1493913600000
                 * endTime : 1496505599000
                 * limitCat : ½ö¿É¹ºÂòÌì½ò²¿·ÖÉÌÆ·ÉÌÆ·
                 * limitPlatform : null
                 * tag : 0
                 * useUrl : null
                 * batchId : 36194557
                 * areaId : 0
                 * areaName : null
                 */

                private String id;
                private Object key;
                private Object actKey;
                private Object actRuleId;
                private int type;
                private String quota;
                private String discount;
                private long beginTime;
                private long endTime;
                private String limitCat;
                private Object limitPlatform;
                private int tag;
                private Object useUrl;
                private int batchId;
                private int areaId;
                private Object areaName;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public Object getKey() {
                    return key;
                }

                public void setKey(Object key) {
                    this.key = key;
                }

                public Object getActKey() {
                    return actKey;
                }

                public void setActKey(Object actKey) {
                    this.actKey = actKey;
                }

                public Object getActRuleId() {
                    return actRuleId;
                }

                public void setActRuleId(Object actRuleId) {
                    this.actRuleId = actRuleId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getQuota() {
                    return quota;
                }

                public void setQuota(String quota) {
                    this.quota = quota;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }

                public long getBeginTime() {
                    return beginTime;
                }

                public void setBeginTime(long beginTime) {
                    this.beginTime = beginTime;
                }

                public long getEndTime() {
                    return endTime;
                }

                public void setEndTime(long endTime) {
                    this.endTime = endTime;
                }

                public String getLimitCat() {
                    return limitCat;
                }

                public void setLimitCat(String limitCat) {
                    this.limitCat = limitCat;
                }

                public Object getLimitPlatform() {
                    return limitPlatform;
                }

                public void setLimitPlatform(Object limitPlatform) {
                    this.limitPlatform = limitPlatform;
                }

                public int getTag() {
                    return tag;
                }

                public void setTag(int tag) {
                    this.tag = tag;
                }

                public Object getUseUrl() {
                    return useUrl;
                }

                public void setUseUrl(Object useUrl) {
                    this.useUrl = useUrl;
                }

                public int getBatchId() {
                    return batchId;
                }

                public void setBatchId(int batchId) {
                    this.batchId = batchId;
                }

                public int getAreaId() {
                    return areaId;
                }

                public void setAreaId(int areaId) {
                    this.areaId = areaId;
                }

                public Object getAreaName() {
                    return areaName;
                }

                public void setAreaName(Object areaName) {
                    this.areaName = areaName;
                }
            }
        }

        public static class UnusedEntity {
            /**
             * totalCount : 97
             * list : [{"id":"12310456569","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"5","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055737,"areaId":0,"areaName":null},{"id":"12309265041","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"10000","discount":"150","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055757,"areaId":0,"areaName":null},{"id":"12309183113","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"5000","discount":"70","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43055741,"areaId":0,"areaName":null},{"id":"12307776101","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12307776105","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12307776097","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"1000","discount":"20","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127717,"areaId":0,"areaName":null},{"id":"12291009821","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"3000","discount":"80","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127713,"areaId":0,"areaName":null},{"id":"12291009825","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"3000","discount":"80","beginTime":1494777600000,"endTime":1497369599000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":"","batchId":43127713,"areaId":0,"areaName":null}]
             */

            private int totalCount;
            private List<ListEntityX> list;

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public List<ListEntityX> getList() {
                return list;
            }

            public void setList(List<ListEntityX> list) {
                this.list = list;
            }

            public static class ListEntityX {
                /**
                 * id : 12310456569
                 * key : null
                 * actKey : null
                 * actRuleId : null
                 * type : 1
                 * quota : 500
                 * discount : 5
                 * beginTime : 1494777600000
                 * endTime : 1497369599000
                 * limitCat : ½ö¿É¹ºÂò¹ã¶«µØÇø²¿·ÖÉÌÆ·
                 * limitPlatform : null
                 * tag : 0
                 * useUrl :
                 * batchId : 43055737
                 * areaId : 0
                 * areaName : null
                 */

                private String id;
                private Object key;
                private Object actKey;
                private Object actRuleId;
                private int type;
                private String quota;
                private String discount;
                private long beginTime;
                private long endTime;
                private String limitCat;
                private Object limitPlatform;
                private int tag;
                private String useUrl;
                private int batchId;
                private int areaId;
                private Object areaName;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public Object getKey() {
                    return key;
                }

                public void setKey(Object key) {
                    this.key = key;
                }

                public Object getActKey() {
                    return actKey;
                }

                public void setActKey(Object actKey) {
                    this.actKey = actKey;
                }

                public Object getActRuleId() {
                    return actRuleId;
                }

                public void setActRuleId(Object actRuleId) {
                    this.actRuleId = actRuleId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getQuota() {
                    return quota;
                }

                public void setQuota(String quota) {
                    this.quota = quota;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }

                public long getBeginTime() {
                    return beginTime;
                }

                public void setBeginTime(long beginTime) {
                    this.beginTime = beginTime;
                }

                public long getEndTime() {
                    return endTime;
                }

                public void setEndTime(long endTime) {
                    this.endTime = endTime;
                }

                public String getLimitCat() {
                    return limitCat;
                }

                public void setLimitCat(String limitCat) {
                    this.limitCat = limitCat;
                }

                public Object getLimitPlatform() {
                    return limitPlatform;
                }

                public void setLimitPlatform(Object limitPlatform) {
                    this.limitPlatform = limitPlatform;
                }

                public int getTag() {
                    return tag;
                }

                public void setTag(int tag) {
                    this.tag = tag;
                }

                public String getUseUrl() {
                    return useUrl;
                }

                public void setUseUrl(String useUrl) {
                    this.useUrl = useUrl;
                }

                public int getBatchId() {
                    return batchId;
                }

                public void setBatchId(int batchId) {
                    this.batchId = batchId;
                }

                public int getAreaId() {
                    return areaId;
                }

                public void setAreaId(int areaId) {
                    this.areaId = areaId;
                }

                public Object getAreaName() {
                    return areaName;
                }

                public void setAreaName(Object areaName) {
                    this.areaName = areaName;
                }
            }
        }

        public static class UsedEntity {
            /**
             * totalCount : 6
             * list : [{"id":"10117745531","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1492358400000,"endTime":1494863999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35822829,"areaId":0,"areaName":null},{"id":"9813607673","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1489680000000,"endTime":1492271999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35319869,"areaId":0,"areaName":null},{"id":"9813607674","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"500","discount":"10","beginTime":1489680000000,"endTime":1492271999000,"limitCat":"½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":35319869,"areaId":0,"areaName":null},{"id":"7865660913","key":null,"actKey":null,"actRuleId":null,"type":0,"quota":"0","discount":"1","beginTime":1480348800000,"endTime":1480780740000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":34035649,"areaId":0,"areaName":null},{"id":"7809589313","key":null,"actKey":null,"actRuleId":null,"type":0,"quota":"0","discount":"1","beginTime":1479398400000,"endTime":1480089540000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":33921161,"areaId":0,"areaName":null},{"id":"6920292386","key":null,"actKey":null,"actRuleId":null,"type":1,"quota":"2","discount":"1","beginTime":1477505700000,"endTime":1480175940000,"limitCat":"½ö¿É¹ºÂòÒ©¾©²ÉB2BÆ½Ì¨²¿·ÖÉÌÆ·","limitPlatform":null,"tag":0,"useUrl":null,"batchId":33518570,"areaId":0,"areaName":null}]
             */

            private int totalCount;
            private List<ListEntityXX> list;

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }

            public List<ListEntityXX> getList() {
                return list;
            }

            public void setList(List<ListEntityXX> list) {
                this.list = list;
            }

            public static class ListEntityXX {
                /**
                 * id : 10117745531
                 * key : null
                 * actKey : null
                 * actRuleId : null
                 * type : 1
                 * quota : 500
                 * discount : 10
                 * beginTime : 1492358400000
                 * endTime : 1494863999000
                 * limitCat : ½ö¿É¹ºÂò±±¾©µØÇø²¿·ÖÉÌÆ·
                 * limitPlatform : null
                 * tag : 0
                 * useUrl : null
                 * batchId : 35822829
                 * areaId : 0
                 * areaName : null
                 */

                private String id;
                private Object key;
                private Object actKey;
                private Object actRuleId;
                private int type;
                private String quota;
                private String discount;
                private long beginTime;
                private long endTime;
                private String limitCat;
                private Object limitPlatform;
                private int tag;
                private Object useUrl;
                private int batchId;
                private int areaId;
                private Object areaName;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public Object getKey() {
                    return key;
                }

                public void setKey(Object key) {
                    this.key = key;
                }

                public Object getActKey() {
                    return actKey;
                }

                public void setActKey(Object actKey) {
                    this.actKey = actKey;
                }

                public Object getActRuleId() {
                    return actRuleId;
                }

                public void setActRuleId(Object actRuleId) {
                    this.actRuleId = actRuleId;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getQuota() {
                    return quota;
                }

                public void setQuota(String quota) {
                    this.quota = quota;
                }

                public String getDiscount() {
                    return discount;
                }

                public void setDiscount(String discount) {
                    this.discount = discount;
                }

                public long getBeginTime() {
                    return beginTime;
                }

                public void setBeginTime(long beginTime) {
                    this.beginTime = beginTime;
                }

                public long getEndTime() {
                    return endTime;
                }

                public void setEndTime(long endTime) {
                    this.endTime = endTime;
                }

                public String getLimitCat() {
                    return limitCat;
                }

                public void setLimitCat(String limitCat) {
                    this.limitCat = limitCat;
                }

                public Object getLimitPlatform() {
                    return limitPlatform;
                }

                public void setLimitPlatform(Object limitPlatform) {
                    this.limitPlatform = limitPlatform;
                }

                public int getTag() {
                    return tag;
                }

                public void setTag(int tag) {
                    this.tag = tag;
                }

                public Object getUseUrl() {
                    return useUrl;
                }

                public void setUseUrl(Object useUrl) {
                    this.useUrl = useUrl;
                }

                public int getBatchId() {
                    return batchId;
                }

                public void setBatchId(int batchId) {
                    this.batchId = batchId;
                }

                public int getAreaId() {
                    return areaId;
                }

                public void setAreaId(int areaId) {
                    this.areaId = areaId;
                }

                public Object getAreaName() {
                    return areaName;
                }

                public void setAreaName(Object areaName) {
                    this.areaName = areaName;
                }
            }
        }
    }
}
