package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jd.yyc.R;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ResourceUtil;

import java.util.ArrayList;

/**
 *SKU
 */

public class Sku extends Base implements Parcelable {
    public long sku;//skuid
    public long skuId;//skuid
    public long venderId; //商家id
    public long num;//数量
    public String name;//商品名称
    public String imgUrl;//图片地址
    public float price;//价格
    public long cat1; //一级分类id
    public long cat2;  //二级分类id
    public long cat3;  //三级分类id
    public long cid1; //一级分类id
    public long cid2; //一级分类id
    public long cid3; //一级分类id
    public long brandId;// 商品品牌ID
    public int checkType;//勾选状态，0未勾选，1勾选
    public int online; //1, 上架; 2,已下架
    public boolean hasStock;//是否有库存
    public String priceStatusEnum;//价格是否有效  YES，价格有效;NO,价格无效;
    public float promoPrice; //促销价
    public long promotionId; //促销Id
    public Integer promotionType; //促销类型
    public boolean canBuy;//是否可买
    public long spu;//spuId
    public String brandName;//商品品牌名称
    public int state;//商品上下架状态
    public int stock;//库存
    public String mainImg;//商品主图
    public String fullMainImg;//商品主图,全路径,需要配置https:
    public String factoryName;//生产厂家
    public long shopId;//店铺id
    public String shopName;//店铺名称
    public String advert;//广告词
    public String packNum;//包装数量
    public ArrayList<String> imgList;//url
    public ArrayList<String> fullImgList;//全路径
    public ArrayList<Relative> relative;//relative对象，包括attrName是否是标准单位，
    public Price myPrice;

    public String getRelativeName() {
        if (CheckTool.isEmpty(relative)) {
            return null;
        } else {
            return relative.get(0).attrName;
        }
    }


    public Long getSkuId() {
        return skuId;
    }

    public Long getVenderId() {
        return venderId;
    }

    public Long getCat1() {
        return cat1;
    }

    public Long getCat2() {
        return cat2;
    }

    public Long getCat3() {
        return cat3;
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

    public boolean isHasStock() {
        return hasStock;
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

    public long getSpu() {
        return spu;
    }

    public String getBrandName() {
        return brandName;
    }

    public int getState() {
        return state;
    }

    public int getStock() {
        return stock;
    }

    public String getMainImg() {
        return mainImg;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public long getShopId() {
        return shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public Sku() {
    }

    public String getImgUrl() {
        return imgUrl == null ? "" : imgUrl;
    }

    public String getName() {
        return name == null ? "" : name;
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

    public float getPrice() {
        return price;
    }

    public String getPriceStr() {
        return ResourceUtil.getString(R.string.sku_price, price);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.sku);
        dest.writeLong(this.skuId);
        dest.writeLong(this.venderId);
        dest.writeLong(this.num);
        dest.writeString(this.name);
        dest.writeString(this.imgUrl);
        dest.writeFloat(this.price);
        dest.writeLong(this.cat1);
        dest.writeLong(this.cat2);
        dest.writeLong(this.cat3);
        dest.writeLong(this.cid1);
        dest.writeLong(this.cid2);
        dest.writeLong(this.cid3);
        dest.writeLong(this.brandId);
        dest.writeInt(this.checkType);
        dest.writeInt(this.online);
        dest.writeByte(this.hasStock ? (byte) 1 : (byte) 0);
        dest.writeString(this.priceStatusEnum);
        dest.writeFloat(this.promoPrice);
        dest.writeLong(this.promotionId);
        dest.writeValue(this.promotionType);
        dest.writeByte(this.canBuy ? (byte) 1 : (byte) 0);
        dest.writeLong(this.spu);
        dest.writeString(this.brandName);
        dest.writeInt(this.state);
        dest.writeInt(this.stock);
        dest.writeString(this.mainImg);
        dest.writeString(this.fullMainImg);
        dest.writeString(this.factoryName);
        dest.writeLong(this.shopId);
        dest.writeString(this.shopName);
        dest.writeString(this.advert);
        dest.writeString(this.packNum);
        dest.writeStringList(this.imgList);
        dest.writeStringList(this.fullImgList);
        dest.writeTypedList(this.relative);
        dest.writeParcelable(this.myPrice, flags);
    }

    protected Sku(Parcel in) {
        this.sku = in.readLong();
        this.skuId = in.readLong();
        this.venderId = in.readLong();
        this.num = in.readLong();
        this.name = in.readString();
        this.imgUrl = in.readString();
        this.price = in.readFloat();
        this.cat1 = in.readLong();
        this.cat2 = in.readLong();
        this.cat3 = in.readLong();
        this.cid1 = in.readLong();
        this.cid2 = in.readLong();
        this.cid3 = in.readLong();
        this.brandId = in.readLong();
        this.checkType = in.readInt();
        this.online = in.readInt();
        this.hasStock = in.readByte() != 0;
        this.priceStatusEnum = in.readString();
        this.promoPrice = in.readFloat();
        this.promotionId = in.readLong();
        this.promotionType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.canBuy = in.readByte() != 0;
        this.spu = in.readLong();
        this.brandName = in.readString();
        this.state = in.readInt();
        this.stock = in.readInt();
        this.mainImg = in.readString();
        this.fullMainImg = in.readString();
        this.factoryName = in.readString();
        this.shopId = in.readLong();
        this.shopName = in.readString();
        this.advert = in.readString();
        this.packNum = in.readString();
        this.imgList = in.createStringArrayList();
        this.fullImgList = in.createStringArrayList();
        this.relative = in.createTypedArrayList(Relative.CREATOR);
        this.myPrice = in.readParcelable(Price.class.getClassLoader());
    }

    public static final Creator<Sku> CREATOR = new Creator<Sku>() {
        @Override
        public Sku createFromParcel(Parcel source) {
            return new Sku(source);
        }

        @Override
        public Sku[] newArray(int size) {
            return new Sku[size];
        }
    };
}
