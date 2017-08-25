package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jd.yyc.R;
import com.jd.yyc.util.ResourceUtil;

/**
 * 购物车医药实体
 */

public class ProductModel extends Base implements Parcelable {
    private Long skuId;//skuid
    private Long venderId; //商家id
    private Long num;//数量
    private String name;//商品名称
    private String imgUrl;//图片地址
    private Float price;//价格
    private float linePrice; //划线价格
    private Long cat1; //一级分类id
    private Long cat2;  //二级分类id
    private Long cat3;  //三级分类id
    private Long brandId;// 商品品牌ID
    private int checkType;//勾选状态，0未勾选，1勾选
    private int editCheckType; //编辑状态下的勾选状态 0未勾选，1勾选
    private int online; //1, 上架; 2,已下架
    private int stockNum;//库存
    private boolean hasStock;//是否有库存
    private String priceStatusEnum;//价格是否有效  YES，价格有效;NO,价格无效;
    private float promoPrice; //促销价
    private Long promotionId; //促销Id
    private Integer promotionType; //促销类型
    private boolean canBuy;//是否可买


    private boolean secKill; //是否是秒杀商品

    public void setSecKill(boolean secKill) {
        this.secKill = secKill;
    }

    public boolean isSecKill() {
        return secKill;
    }

    public float getPrice() {
        return price == null ? 0 : price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setLinePrice(float linePrice) {
        this.linePrice = linePrice;
    }

    public float getLinePrice() {
        return linePrice;
    }

    public Long getSkuId() {
        return skuId == null?0:skuId;
    }

    public Long getVenderId() {
        return venderId;
    }

    public long getNum() {
        return num == null ? 0 : num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getCat1() {
        return cat1 == null ? 0 : cat1;
    }

    public Long getCat2() {
        return cat2 == null ? 0 : cat2;
    }

    public Long getCat3() {
        return cat3 == null ? 0 : cat3;
    }

    public Long getBrandId() {
        return brandId;
    }

    public int getCheckType() {
        return checkType;
    }

    public int getOnline() {
        return online;
    }

    public int getStockNum() {
        return stockNum;
    }

    public boolean isNoSkuOrOnline() { //是否是下架或者无货
        if (online == 2)
            return true;
        if (stockNum <= 0)
            return true;
        return false;
    }

    public String getNoSkuOrOnlineStr() {
        if (online == 2)
            return "已下架";
        if (stockNum <= 0)
            return "无货";
        return "";
    }

    public boolean isHasStock() {
        return stockNum > 0;
    }

    public String getPriceStatusEnum() {
        return priceStatusEnum;
    }

    public float getPromoPrice() {
        return promoPrice;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public boolean isCanBuy() {
        return canBuy;
    }

    public ProductModel() {
    }

    public String getImgUrl() {
        return imgUrl == null ? "" : imgUrl;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public boolean hasEditCheck() {
        return editCheckType == 1;
    }

    public void setEditCheck(boolean check) {
        if (check) {
            editCheckType = 1;
        } else {
            editCheckType = 0;
        }
    }

    public boolean hasCheck() {
        return checkType == 1;
    }

    public void setCheck(boolean check) {
        if (check) {
            checkType = 1;
        } else {
            checkType = 0;
        }
    }

    public String getPriceStr() {
        return getPrice() > 0 ? ResourceUtil.getString(R.string.sku_price, getPrice()) : "- - -";
    }

    public String getLinePriceStr() {
        return ResourceUtil.getString(R.string.sku_price, linePrice);
    }


    public static ProductModel getDemo() {
        ProductModel sku = new ProductModel();
        sku.name = "白加黑感冒颗粒";
        sku.price = 34.2f;
        sku.imgUrl = "http://wx2.sinaimg.cn/mw690/a8d43f7egy1fg146wqercj20dw08njvv.jpg";
        return sku;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.skuId);
        dest.writeValue(this.venderId);
        dest.writeValue(this.num);
        dest.writeString(this.name);
        dest.writeString(this.imgUrl);
        dest.writeFloat(this.price);
        dest.writeValue(this.cat1);
        dest.writeValue(this.cat2);
        dest.writeValue(this.cat3);
        dest.writeValue(this.brandId);
        dest.writeInt(this.checkType);
        dest.writeInt(this.online);
        dest.writeInt(this.stockNum);
        dest.writeByte(this.hasStock ? (byte) 1 : (byte) 0);
        dest.writeString(this.priceStatusEnum);
        dest.writeFloat(this.promoPrice);
        dest.writeValue(this.promotionId);
        dest.writeValue(this.promotionType);
        dest.writeByte(this.canBuy ? (byte) 1 : (byte) 0);
    }

    protected ProductModel(Parcel in) {
        this.skuId = (Long) in.readValue(Long.class.getClassLoader());
        this.venderId = (Long) in.readValue(Long.class.getClassLoader());
        this.num = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.imgUrl = in.readString();
        this.price = in.readFloat();
        this.cat1 = (Long) in.readValue(Long.class.getClassLoader());
        this.cat2 = (Long) in.readValue(Long.class.getClassLoader());
        this.cat3 = (Long) in.readValue(Long.class.getClassLoader());
        this.brandId = (Long) in.readValue(Long.class.getClassLoader());
        this.checkType = in.readInt();
        this.online = in.readInt();
        this.stockNum = in.readInt();
        this.hasStock = in.readByte() != 0;
        this.priceStatusEnum = in.readString();
        this.promoPrice = in.readFloat();
        this.promotionId = (Long) in.readValue(Long.class.getClassLoader());
        this.promotionType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.canBuy = in.readByte() != 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel source) {
            return new ProductModel(source);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };
}
