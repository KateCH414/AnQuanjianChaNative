package com.jd.yyc.api.model;

import com.google.gson.annotations.SerializedName;
import com.jd.yyc.R;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺实体
 */

public class VendorModel extends Base {

    private Float totalPrice; //总价格
    private int checkedItemCnt; //选中商品项的数量
    @SerializedName("productView")
    private List<ProductModel> skuList;//商品对象
    private Long venderId;//店铺id
    private String venderName;//店铺名称

    private boolean scanAllSku; //购物车是否展开全部商品


    public Long getVenderId() {
        return venderId==null?0:venderId;
    }

    public List<Long> getSkuIdList(){
        List<Long> ids = new ArrayList<>();
        for(ProductModel productModel : getSku()){
            ids.add(productModel.getSkuId());
        }
        return ids;
    }

    public String getScanOtherStr() {
        if (!scanAllSku) {
            return ResourceUtil.getString(R.string.scan_all_sku, getSkuSize() - 5);
        } else {
            return ResourceUtil.getString(R.string.retract_all_sku, getSkuSize() - 5);
        }
    }

    public int getSkuSize() {
        if (skuList == null)
            return 0;
        return skuList.size();
    }

    public float getTotalPrice() {
        return totalPrice == null ? 0 : totalPrice;
    }

    public List<ProductModel> getSku() {
        if (skuList == null)
            return new ArrayList<>();
        return skuList;
    }

    public String getVenderName() {
        return venderName == null ? "" : venderName;
    }

    public boolean showScanOtherLayout() {
        if (getSkuSize() <= 5)
            return false;
        return true;
    }

    public void setScanAllSku(boolean scanAllSku) {
        this.scanAllSku = scanAllSku;
    }

    public boolean isScanAllSku() {
        return scanAllSku;
    }

    public void setSkuPrice(List<SkuPrice> skuPriceList){

        if(CheckTool.isEmpty(skuPriceList))
            return;
        if(skuPriceList.size() != getSku().size())
            return;
        for(int i=0;i<skuPriceList.size();i++){

            for(int j =0;j<skuList.size();j++){
                if(skuList.get(j).getSkuId().equals(skuPriceList.get(i).getSkuId())){

                    if (skuPriceList.get(i).secKill()) {
                        skuList.get(j).setSecKill(true);
                        skuList.get(j).setPrice(skuPriceList.get(i).getPromotionPrice());
                        skuList.get(j).setLinePrice(skuPriceList.get(i).getPrice());

                    } else if (skuPriceList.get(i).priceDown()) {
                        skuList.get(j).setSecKill(false);
                        skuList.get(j).setPrice(skuPriceList.get(i).getPromotionPrice());
                    } else {
                        skuList.get(j).setSecKill(false);
                        skuList.get(j).setPrice(skuPriceList.get(i).getPrice());
                    }
                    break;
                }
            }
        }
    }

    public boolean hasCheck(boolean isEdit) {
        if (getSku().size() == 0) {
            return false;
        }

        boolean check = true;
        if (isEdit) {
            for (ProductModel sku : getSku()) {
                if (!sku.hasEditCheck())
                    check = false;
            }
        } else {
            for (ProductModel sku : getSku()) {
                if (!sku.hasCheck())
                    check = false;
            }
        }
        return check;
    }
}
