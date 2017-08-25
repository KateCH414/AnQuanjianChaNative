package com.miko.zd.anquanjianchanative.Model.BizPicGrid;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by zd on 2016/4/30.
 */
public class GetAllFilePathsTask extends AsyncTask {
    private Uri mType;
    private GetResultListener mResultListener;
    private Context mContext;
    private ArrayList<String> allPaths=new ArrayList<>();
    public GetAllFilePathsTask(Context mContext, Uri mType, GetResultListener mResultListener) {
        this.mType = mType;
        this.mResultListener = mResultListener;
        this.mContext=mContext;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        String[] projection = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME
        };
        Cursor mCursor = mContext.getContentResolver().query(mType, projection, null, null, null);
        while (mCursor.moveToNext()) {
            allPaths.add(mCursor.getString(1));
        }
        if (allPaths.size() > 0)
            mResultListener.onSuccess(allPaths);
        else
            mResultListener.onFailure();
        return null;
    }
}
