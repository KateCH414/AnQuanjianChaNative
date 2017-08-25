package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by zhangweifeng1 on 2017/6/7.
 */

public class Search extends Base implements Parcelable {
    public SearchHead Head;
    public SearchObjCollection ObjCollection;
    public ArrayList<SearchSku> Paragraph;
    public String imgDomain;

    public Search() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.Head, flags);
        dest.writeParcelable(this.ObjCollection, flags);
        dest.writeTypedList(this.Paragraph);
        dest.writeString(this.imgDomain);
    }

    protected Search(Parcel in) {
        this.Head = in.readParcelable(SearchHead.class.getClassLoader());
        this.ObjCollection = in.readParcelable(SearchObjCollection.class.getClassLoader());
        this.Paragraph = in.createTypedArrayList(SearchSku.CREATOR);
        this.imgDomain = in.readString();
    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel source) {
            return new Search(source);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}
