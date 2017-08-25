package com.jd.yyc.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.huajianjiang.expandablerecyclerview.widget.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangweifeng1 on 2017/6/2.
 */

public class CategoryTree extends Base implements Parent<CategoryTree>, Parcelable {
    public long id;//类目ID
    public long parentId;//类目父ID
    public String name;//类目名称
    public String level;//类目级别
    public ArrayList<CategoryTree> subs;//子类目树

    private boolean isExpandable = true;
    private boolean isInitiallyExpanded = false;



    @Override
    public List<CategoryTree> getChildren() {
        return subs;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public void setInitiallyExpanded(boolean initiallyExpanded) {
        isInitiallyExpanded = initiallyExpanded;
    }


    public boolean hasChildren() {
        return subs != null && !subs.isEmpty();
    }

    @Override
    public boolean isInitiallyExpandable() {
        return isExpandable;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return isInitiallyExpanded;
    }


    public CategoryTree() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.parentId);
        dest.writeString(this.name);
        dest.writeString(this.level);
        dest.writeTypedList(this.subs);
        dest.writeByte(this.isExpandable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isInitiallyExpanded ? (byte) 1 : (byte) 0);
    }

    protected CategoryTree(Parcel in) {
        this.id = in.readLong();
        this.parentId = in.readLong();
        this.name = in.readString();
        this.level = in.readString();
        this.subs = in.createTypedArrayList(CategoryTree.CREATOR);
        this.isExpandable = in.readByte() != 0;
        this.isInitiallyExpanded = in.readByte() != 0;
    }

    public static final Creator<CategoryTree> CREATOR = new Creator<CategoryTree>() {
        @Override
        public CategoryTree createFromParcel(Parcel source) {
            return new CategoryTree(source);
        }

        @Override
        public CategoryTree[] newArray(int size) {
            return new CategoryTree[size];
        }
    };
}
