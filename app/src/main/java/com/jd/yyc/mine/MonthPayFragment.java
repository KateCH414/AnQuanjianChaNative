package com.jd.yyc.mine;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.MonthPay;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.refreshfragment.BaseRefreshFragment;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.ScreenUtil;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.Util;
import com.jd.yyc.util.recyclerview.SpacesItemDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by jiahongbin on 2017/5/30.
 */

public class MonthPayFragment extends BaseRefreshFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    @InjectView(R.id.back_view)
    ImageView backView;
    @InjectView(R.id.bar_title)
    TextView barTitle;
    @InjectView(R.id.btn_right)
    TextView btnRight;
    @InjectView(R.id.st_tv)
    TextView stTv;
    @InjectView(R.id.et_tv)
    TextView etTv;
    @InjectView(R.id.confirm_btn)
    TextView confirmBtn;
    @InjectView(R.id.select_layout)
    LinearLayout selectLayout;

    @InjectView(R.id.rl_time)
    RelativeLayout rl_time;
    @InjectView(R.id.tv_time)
    TextView tv_time;
    @InjectView(R.id.close_time)
    ImageView close_time;
    @InjectView(R.id.button_view)
    View button_view;
    public int timetype;
    public String st_time;
    public String et_time;
    //  @InjectView(R.id.tv_mine_monthPay_saixuan)
    private Calendar showDate;
    public boolean state = true;


    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

    public MonthPayFragment() {
    }


    @Override
    public RecyclerAdapter createAdapter() {
        return new MonthPayAdapter(mContext);
    }

    @Override
    public void onSuccess(String data) {

        setMonthPayData(data);

    }

    private void setMonthPayData(String data) {

        try {
            Gson gson = new Gson();
            ResultObject<MonthPay> result = gson.fromJson(data, new TypeToken<ResultObject<MonthPay>>() {
            }.getType());

            if (result != null && result.data != null) {
                /*if (PAGE == 1)
                    getAdapter().getList().clear();*/
                setSuccessStatus(result.data.getList(), result.data.totalPage);
                if (result.data.getList().size() < 20) {
                    //      Toast.makeText(mContext,"无法加载更多",Toast.LENGTH_SHORT).show();
                    setCanLoadMore(false);
                } else {
                    setCanLoadMore(true);
                }
                if (result.data.list.size() != 0)
                    setData(result.data.list);
                notifyDataSetChanged();
            } else {
                setSuccessStatus(null, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }


    }

    public Map<String, String> getParams() {

        HashMap<String, String> hashMap = new HashMap();


        hashMap.put("status", 6 + "");
        hashMap.put("payType", 4 + "");
        hashMap.put("page", PAGE + "");
        hashMap.put("pageSize", 20 + "");
        return hashMap;
    }


    @Override
    public String getPath() {
        return "order/pageList";
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_mine_monthpay;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_right:
                if (selectLayout.getVisibility() == View.VISIBLE) {
                    btnRight.setText("筛选");
                    selectLayout.setVisibility(View.GONE);
                } else {
                    btnRight.setText("取消");
                    selectLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.st_tv:
                timetype = 1;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                //   new DatePickerDialog(getTopActivity(), MonthPayFragment.this, mYear, mMonth, mDay).show();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getTopActivity(), MonthPayFragment.this, mYear, mMonth, mDay);
                DatePicker picker = datePickerDialog.getDatePicker();
                Date date = new Date();//当前时间
                long time = date.getTime();
                picker.setMaxDate(time);//设置最大日期
                datePickerDialog.show();
                break;
            case R.id.et_tv:
                timetype = 2;
                mYear = Calendar.getInstance().get(Calendar.YEAR);
                mMonth = Calendar.getInstance().get(Calendar.MONTH);
                mDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                // new DatePickerDialog(getTopActivity(), MonthPayFragment.this, mYear, mMonth, mDay).show();
                DatePickerDialog datePicker = new DatePickerDialog(getTopActivity(), MonthPayFragment.this, mYear, mMonth, mDay);
                DatePicker pickerr = datePicker.getDatePicker();
                Date dat = new Date();//当前时间
                long tim = dat.getTime();
                pickerr.setMaxDate(tim);//设置最大日期
                datePicker.show();

                break;
            case R.id.confirm_btn:
                orderSelect();
                break;
            case R.id.back_view:
                mContext.finish();
                break;
            case R.id.close_time:
                rl_time.setVisibility(View.GONE);
                setRefreshing(true);
                break;
            case R.id.button_view:
                btnRight.setText("筛选");
                selectLayout.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }
    private void orderSelect() {
        if (CheckTool.isEmpty(st_time)) {
            ToastUtil.show("开始时间不能为空");
            return;
        }
        if (CheckTool.isEmpty(et_time)) {
            ToastUtil.show("结束时间不能为空");
            return;
        }
        Boolean bigger = isDateOneBigger(st_time, et_time);
        //   Toast.makeText(mContext,bigger+"",Toast.LENGTH_SHORT).show();
        if (bigger == true) {
            Toast.makeText(mContext, "时间选择错误,请重新选择", Toast.LENGTH_SHORT).show();
        }
        PAGE = 1;
        getselectData();
        rl_time.setVisibility(View.VISIBLE);
        tv_time.setText("以下为" + st_time + "至" + et_time + "的全部订单");
        selectLayout.setVisibility(View.GONE);
        if (selectLayout.getVisibility() == View.GONE) {
            btnRight.setText("筛选");
        } else {
            btnRight.setText("取消");
        }
    }


    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }


    private void getselectData() {
        HashMap<String, String> params = new HashMap<>();


        params.put("pageSize", 20 + "");
        params.put("page", PAGE + "");
        params.put("status", 6 + "");
        params.put("payType", 4 + "");
        params.put("startTime", st_time);
        params.put("endTime", et_time);
        Network.getRequestBuilder(Util.createUrl("order/pageList", ""))
                .params(params)
                .success(new Success() {

                    @Override
                    public void success(String model) {
                        try {


                            //    yaoser.jd.com/order/list?relationType=3&status=5&page=1&pageSize=20&ver=1.0&pf=android
                            Gson gson = new Gson();
                            ResultObject<MonthPay> result = gson.fromJson(model, new TypeToken<ResultObject<MonthPay>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                if (PAGE == 1)
                                    getAdapter().getList().clear();
                                setSuccessStatus(result.data.getList(), result.data.totalPage);
                                if (result.data.getList().size() < 20) {
                                    //      Toast.makeText(mContext,"无法加载更多",Toast.LENGTH_SHORT).show();
                                    setCanLoadMore(false);
                                } else {
                                    setCanLoadMore(true);
                                }

                                setData(result.data.list);
                                notifyDataSetChanged();
                            } else {
                                if (PAGE == 1)
                                    getAdapter().getList().clear();
                                setSuccessStatus(null, 0);
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }


                    }
                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {

                    }
                })
                .post();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.inject(this, rootView);
        btnRight.setOnClickListener(this);
        close_time.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        if (timetype == 1) {
            st_time = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(dayOfMonth);
            stTv.setText(year + "-" + month + "-" + dayOfMonth);
        } else if (timetype == 2) {
            et_time = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(dayOfMonth);
            etTv.setText(year + "-" + month + "-" + dayOfMonth);
        }
    }


    class DividerItemDecoration extends RecyclerView.ItemDecoration {

        int itemSize;

        public DividerItemDecoration(Context context) {
            itemSize = ScreenUtil.dip2px(context, 10);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);

            if (holder.getAdapterPosition() == 0 || holder.getAdapterPosition() == 1) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, itemSize, 0, itemSize);
            }
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getRecyclerView().addItemDecoration(new SpacesItemDecoration(ScreenUtil.dip2px(10)));
        //   getRecyclerView().setClipToPadding(false);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        backView.setOnClickListener(this);
        barTitle.setText("月结待还款");
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText("筛选");
        btnRight.setOnClickListener(this);
        stTv.setOnClickListener(this);
        etTv.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        button_view.setOnClickListener(this);
        etTv.addTextChangedListener(textWatcher);
        //  setData(YaoOrder.getDemolist());
        getEmptyView().setDefaultEmptyId(R.drawable.noticeordersempty);

    }

    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (!CheckTool.isEmpty(st_time) && !CheckTool.isEmpty(et_time)) {

                confirmBtn.setBackgroundColor(Color.parseColor("#C81623"));
            }


        }
    };






}
