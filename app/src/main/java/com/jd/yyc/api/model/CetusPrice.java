package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class CetusPrice extends Base implements Parcelable {
    private Float cent;
    private String currency;
    private Float centFactor;
    private Float amount;
    private String currencyCode;


    public float getCent() {
        return amount == null?0:amount;
    }

    public String getCurrency() {
        return currency;
    }

    public Float getCentFactor() {
        return centFactor;
    }

    public Float getAmount() {
        return amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.cent);
        dest.writeString(this.currency);
        dest.writeValue(this.centFactor);
        dest.writeFloat(this.amount);
        dest.writeString(this.currencyCode);
    }

    public CetusPrice() {
    }

    protected CetusPrice(Parcel in) {
        this.cent = (Float) in.readValue(Float.class.getClassLoader());
        this.currency = in.readString();
        this.centFactor = (Float) in.readValue(Float.class.getClassLoader());
        this.amount = in.readFloat();
        this.currencyCode = in.readString();
    }

    public static final Parcelable.Creator<CetusPrice> CREATOR = new Parcelable.Creator<CetusPrice>() {
        @Override
        public CetusPrice createFromParcel(Parcel source) {
            return new CetusPrice(source);
        }

        @Override
        public CetusPrice[] newArray(int size) {
            return new CetusPrice[size];
        }
    };
}
