package com.miko.zd.anquanjianchanative.Selector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater.PicSelectorGridAdapter;
import com.miko.zd.anquanjianchanative.Adapter.PicSelectorAdater.PopListAdapter;
import com.miko.zd.anquanjianchanative.Bean.GridBean.ImageFolderBean;
import com.miko.zd.anquanjianchanative.Presenter.PresenterGrid;
import com.miko.zd.anquanjianchanative.R;

import java.util.ArrayList;

public class ViewPicSelector extends AppCompatActivity implements IViewPicSelector, View.OnTouchListener {
    private Button mBtComplete;
    private GridView mGridView;

    //底部用来触发触摸操作的部分
    private RelativeLayout mLnPopBottomClick;
    private TextView mTvPopFolderName;
    private TextView mTvPopImageNum;
    //底部以及弹出的list popup的布局
    private View mPopBtConvertview;
    private View mPopListConvertview;
    private PopupWindow mPopBottomWindow;
    private PopupWindow mPopListWindow;
    private ListView mPopListView;

    //用来显示在BottomPop中的文字
    private String mImageFolderName;
    private String mImageFolderSize;

    //PopList中的信息
    private ArrayList<ImageFolderBean> mAllFolders;
    private ArrayList<String> mAllOnShowImagePath = new ArrayList<>();

    private PicSelectorGridAdapter mGridAdapter;
    private PopListAdapter mPopListAdapter;
    private PresenterGrid mPresenter = new PresenterGrid(this);
    private Handler mHandler;

    //初始化helper,presenter
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_selector);
        LayoutInflater.from(this).inflate(R.layout.activity_pic_selector, null, false).setOnTouchListener(this);
        initView();
        initEvent();
        mPresenter.doInit(this);
        updateView();

    }


    private void updateView() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.arg1) {
                    //打开ListPop
                    case 3:
                        mPopListAdapter = new PopListAdapter(ViewPicSelector.this, R.layout.item_list_pop_pic, mAllFolders,
                                mPopListView);
                        mPopListAdapter.setOnItemClickListener(item -> mPresenter.closePopList(item));
                        mPopListView.setAdapter(mPopListAdapter);
                        mPopBottomWindow.dismiss();
                        int listHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, ViewPicSelector.this
                                .getResources().getDisplayMetrics());
                        mPopListWindow = new PopupWindow(mPopListConvertview, ViewGroup.LayoutParams.MATCH_PARENT, listHeight, true);
                        mPopListWindow.showAtLocation(LayoutInflater.from(ViewPicSelector.this).inflate(R.layout.activity_pic_selector, null),
                                Gravity.BOTTOM, 0, 0);
                        WindowManager.LayoutParams lp = ViewPicSelector.this.getWindow().getAttributes();
                        lp.alpha = 0.3f;
                        ViewPicSelector.this.getWindow().setAttributes(lp);
                        mPopListWindow.setOnDismissListener(() -> {
                            WindowManager.LayoutParams lp1 = ViewPicSelector.this.getWindow().getAttributes();
                            lp1.alpha = 1f;
                            ViewPicSelector.this.getWindow().setAttributes(lp1);
                            mTvPopFolderName.setText(mImageFolderName);
                            mTvPopImageNum.setText(mImageFolderSize);
                            //更新Bottom
                            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, ViewPicSelector.this
                                    .getResources().getDisplayMetrics());
                            mPopBottomWindow = new PopupWindow(mPopBtConvertview, ViewGroup.LayoutParams.MATCH_PARENT, height, false);
                            mPopBottomWindow.showAtLocation(LayoutInflater.from(ViewPicSelector.this).inflate(R.layout.activity_pic_selector, null),
                                    Gravity.BOTTOM, 0, 0);
                        });
                        break;
                    case 2:
                        //初始化bottom
                        WindowManager.LayoutParams lp1 = ViewPicSelector.this.getWindow().getAttributes();
                        lp1.alpha = 1f;
                        ViewPicSelector.this.getWindow().setAttributes(lp1);

                        mTvPopFolderName.setText(mImageFolderName);
                        mTvPopImageNum.setText(mImageFolderSize);
                        int height1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, ViewPicSelector.this
                                .getResources().getDisplayMetrics());
                        mPopBottomWindow = new PopupWindow(mPopBtConvertview, ViewGroup.LayoutParams.MATCH_PARENT, height1, false);
                        mPopBottomWindow.showAtLocation(LayoutInflater.from(ViewPicSelector.this).inflate(R.layout.activity_pic_selector, null),
                                Gravity.BOTTOM, 0, 0);
                        break;
                    //更新GridView
                    case 1:
                        //如果Adapter未被初始化，也就是说第一次打开那么初始化Adpter,否则notification
                        if (mGridAdapter == null) {
                            mGridAdapter = new PicSelectorGridAdapter(ViewPicSelector.this, R.layout.item_grid_pic,
                                    mAllOnShowImagePath, mGridView);
                            mGridAdapter.setOnCheckBoxClickListener(checkedPicNum -> {
                                if (checkedPicNum != 0) {
                                    mBtComplete.setEnabled(true);
                                    mBtComplete.setBackgroundResource(R.drawable.bg_bt_complete_toolbar);
                                    mBtComplete.setText("(" + checkedPicNum + "个) 完成");
                                } else {
                                    mBtComplete.setBackgroundResource(R.drawable.bg_bt_complete_toolbar_gray);
                                    mBtComplete.setEnabled(false);
                                    mBtComplete.setText("完成");
                                }
                            }, path -> {
                                Intent intent = new Intent(ViewPicSelector.this, ViewPicPreview.class);
                                intent.putExtra("TYPE", ViewPicPreview.CLICK_PREVIEW);
                                ArrayList<String> paths = new ArrayList<>();
                                paths.add(path);
                                intent.putStringArrayListExtra("path", paths);
                                startActivity(intent);
                            });
                            mGridView.setAdapter(mGridAdapter);
                            mBtComplete.setBackgroundResource(R.drawable.bg_bt_complete_toolbar_gray);
                            mBtComplete.setEnabled(false);
                            mBtComplete.setText("完成");
                        } else
                            mGridAdapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    private void initEvent() {
        mLnPopBottomClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.openPopList();
            }
        });
        mBtComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPicSelector.this, ViewPicPreview.class);
                ArrayList<String> paths = new ArrayList<>();
                paths.addAll(PicSelectorGridAdapter.mAllChecked);
                intent.putStringArrayListExtra("path", paths);
                if (mPopBottomWindow == null)
                    mPopBottomWindow.dismiss();
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initView() {
        //选择器主界面布局

        findViewById(R.id.id_selector_back).setOnClickListener(v -> finish());
        findViewById(R.id.id_tv_back_selector).setOnClickListener(v -> finish());

        mBtComplete = (Button) findViewById(R.id.id_bt_complete_pic_fragment);

        mBtComplete.setBackgroundResource(R.drawable.bg_bt_complete_toolbar_gray);
        mBtComplete.setEnabled(false);

        mGridView = (GridView) findViewById(R.id.id_gv_pic_fragment);
        //底部Pop布局
        mPopBtConvertview = LayoutInflater.from(ViewPicSelector.this).inflate(R.layout.popup_bottom_pic, null);
        mTvPopFolderName = (TextView) mPopBtConvertview.findViewById(R.id.id_popup_tv_foldername);
        mTvPopImageNum = (TextView) mPopBtConvertview.findViewById(R.id.id_popup_tv_num);
        mLnPopBottomClick = (RelativeLayout) mPopBtConvertview.findViewById(R.id.id_popup_linear);
        //ListPop布局
        mPopListConvertview = LayoutInflater.from(ViewPicSelector.this).inflate(R.layout.popup_list_pic, null);
        mPopListView = (ListView) mPopListConvertview.findViewById(R.id.id_pop_list);
    }


    //防止触摸泄露
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    @Override
    public void updateBottomPop(String mName, int mSize) {
        //更新数据
        this.mImageFolderName = mName;
        this.mImageFolderSize = String.valueOf(mSize);

    }

    @Override
    public void openListPop(ArrayList<ImageFolderBean> mAllFolders) {
        this.mAllFolders = mAllFolders;

        Message msg = new Message();
        msg.arg1 = 3;
        mHandler.sendMessage(msg);
    }

    @Override
    public void closeListPop() {
        mPopListWindow.dismiss();
    }

    @Override
    public void updateGrid(ArrayList<String> allPaths) {
        mAllOnShowImagePath.clear();
        mAllOnShowImagePath.addAll(allPaths);
        Message msg = new Message();
        msg.arg1 = 1;
        mHandler.sendMessage(msg);
    }

    @Override
    public void initBottomPop(String name, int fileNumber) {
        this.mImageFolderName = name;
        this.mImageFolderSize = String.valueOf(fileNumber);
        Message msg = new Message();
        msg.arg1 = 2;
        mHandler.sendMessage(msg);
    }


}
