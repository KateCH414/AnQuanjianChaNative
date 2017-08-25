package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jiahongbin on 2017/6/13.
 */

public class UserData extends Base implements Parcelable {

    public String userName;//用户名
    public String syUserName;//系统用户名
    public String pin;//用户pin
    public Integer department;//部门
    public Integer gender;

    public UserData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.syUserName);
        dest.writeString(this.pin);
        dest.writeValue(this.department);
        dest.writeValue(this.gender);
    }

    protected UserData(Parcel in) {
        this.userName = in.readString();
        this.syUserName = in.readString();
        this.pin = in.readString();
        this.department = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gender = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
}
