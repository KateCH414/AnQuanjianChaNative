package com.jd.yyc.goodsdetail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.ResultObject;
import com.jd.yyc.api.model.VendorModel;
import com.jd.yyc.ui.widget.BottomSheetDialogFgm;
import com.jd.yyc.util.ToastUtil;
import com.jd.yyc.util.Util;

import java.util.HashMap;


public class DialogView extends BottomSheetDialogFgm implements View.OnClickListener {

    ImageView plus_icon;
    ImageView sub_icon;
    ImageView imageView;

    EditText sku_num;
    TextView tx_title;
    TextView tv_price;
    TextView tv_stock;
    Button confirm;
    public String url;
    public String id;
    public long sku_id;
    Context context;
    public  static DialogView newInstance(String str) {

        Bundle args = new Bundle();
        args.putString("goodsdetail",str);

        DialogView fragment = new DialogView();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    protected View getContentView() {
       String goodsDetail= getArguments().getString("goodsdetail");
        String[] detail=goodsDetail.split("@");
        View content = LayoutInflater.from(getContext()).inflate(R.layout.popup_slide_from_bottom, null);
//         plus_icon=(ImageView)(content.findViewById(R.id.plus_icon));
//        sub_icon=(ImageView)(content.findViewById(R.id.sub_icon));
//        sku_num=(TextView)(content.findViewById(R.id.sku_num));
//        tx_title=(TextView)(content.findViewById(R.id.tx_title));
//        tv_price=(TextView)(content.findViewById(R.id.tv_price));
//        tv_stock=(TextView)(content.findViewById(R.id.tv_stock));
//        imageView=(ImageView)(content.findViewById(R.id.imageView));
//        confirm=(Button)(content.findViewById(R.id.confirm));
//        confirm.setOnClickListener(this);
//        url=detail[1];
//        Glide.with(getContext()).load(HttpContants.BasePicUrl + "/" + url).placeholder(R.drawable.default_pic).into(imageView);
//        //    Toast.makeText(getContext(),detail[4]+"",Toast.LENGTH_SHORT).show();
//        tv_stock.setText(detail[3] + "件");
//        tx_title.setText(detail[0]);
//        url=detail[1];
//        id=detail[2];
//        sku_id = Long.parseLong(detail[5]);
//        tv_price.setText("¥" + detail[4]);
//
//        plus_icon.setOnClickListener(this);
//        sub_icon.setOnClickListener(this);

        /*RecyclerView category_list = (RecyclerView) content.findViewById(R.id.coupon_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        category_list.setLayoutManager(manager);
        CouponAdapter couponAdapter = new CouponAdapter(getActivity());
        couponAdapter.setCanLoadMore(false);
        category_list.setAdapter(couponAdapter);
        couponAdapter.setData(CouponModel.getDemoList());*/
        return content;
    }

    @Override
    public void onClick(View v) {
        int sum=0;
        switch (v.getId()) {

            case R.id.plus_icon:
               sum =Integer.valueOf(sku_num.getText().toString())+1;
                sku_num.setText(sum+"");

                break;

            case R.id.sub_icon:
                if(Integer.valueOf(sku_num.getText().toString())>2){
                    sum =Integer.valueOf(sku_num.getText().toString())-1;
                    sku_num.setText(sum+"");
                }else{
                    Toast.makeText(getContext(), "已经不能再少了", Toast.LENGTH_SHORT).show();

                }


                break;
            case R.id.confirm:
                long sku_sum = Long.parseLong(sku_num.getText().toString());
                addShopCar(sku_id, sku_sum);
                // submit();

                break;
            default:
                break;
        }



    }


    private void addShopCar(long skuId, long num) {
        HashMap<String, String> params = new HashMap<>();
        params.put("skuId", skuId + "");
        params.put("num", num + "");
        Network.getRequestBuilder(Util.createUrl("cart/add", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {
                            Gson gson = new Gson();
                            ResultObject<VendorModel> result = gson.fromJson(model, new TypeToken<ResultObject<VendorModel>>() {
                            }.getType());
                            if (result != null && result.data != null) {
                                Toast.makeText(getContext(), "商品已加入购物车", Toast.LENGTH_SHORT).show();
                                onDismiss(getDialog());
                            } else {
                                ToastUtil.show(getContext(), "添加失败");
                                onDismiss(getDialog());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).post();
    }


    private void submit() {

        HashMap<String, String> params = new HashMap<>();
        params.put("skuId",id);
        params.put("num",sku_num.getText().toString());

        Network.getRequestBuilder(Util.createUrl("cart/add", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {

                            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();
                            onDismiss(getDialog());
                          //  System.exit(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                })
                .error(new Error() {
                    @Override
                    public void Error(int statusCode, String errorMessage, Throwable t) {
                        System.out.println("errorMessage=" + errorMessage);
                    }
                })
                .post();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

    }

    public interface OnContinueSubListener {
        //        void cancel();
        void continueSub(String mobile);

        void onDismiss();
    }
}
