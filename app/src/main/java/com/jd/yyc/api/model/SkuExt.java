package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/6.
 */

public class SkuExt extends Base implements Parcelable {


    public String medicalSpec; // 药品规格


   public String allowNum;//批准文号


    public  String manufacturer;//生产厂家






    public SkuExt() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.medicalSpec);
        dest.writeString(this.allowNum);
        dest.writeString(this.manufacturer);
    }

    protected SkuExt(Parcel in) {
        this.medicalSpec = in.readString();
        this.allowNum = in.readString();
        this.manufacturer = in.readString();
    }

    public static final Creator<SkuExt> CREATOR = new Creator<SkuExt>() {
        @Override
        public SkuExt createFromParcel(Parcel source) {
            return new SkuExt(source);
        }

        @Override
        public SkuExt[] newArray(int size) {
            return new SkuExt[size];
        }
    };
}
