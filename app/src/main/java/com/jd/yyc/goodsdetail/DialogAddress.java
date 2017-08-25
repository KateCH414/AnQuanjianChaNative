package com.jd.yyc.goodsdetail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jd.project.lib.andlib.net.Network;
import com.jd.project.lib.andlib.net.interfaces.Error;
import com.jd.project.lib.andlib.net.interfaces.Success;
import com.jd.yyc.R;
import com.jd.yyc.api.model.AddressBean;
import com.jd.yyc.ui.widget.BottomSheetDialogFgm;
import com.jd.yyc.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DialogAddress extends BottomSheetDialogFgm implements View.OnClickListener {
    public static final String ADDRESS_LIST = "address_list";

    public static int position;
    ImageView iv_second;
    ImageView iv_first;
    ImageView close;
    RelativeLayout rl_second;
    RelativeLayout third;
    TextView address;
    TextView tv_address;
    AddressAdapter addressAdapter;
    public List<AddressBean> addressList = new ArrayList<>();

    public Context context;
    Button confirm;
    public String url;
    public String id;

    private AddSelectListener addSelectListener;

    public  static DialogAddress newInstance() {

        Bundle args = new Bundle();
        DialogAddress fragment = new DialogAddress();
        fragment.setArguments(args);
        return fragment;
    }

    public void setAddSelectListener(AddSelectListener addSelectListener) {
        this.addSelectListener = addSelectListener;
    }

    public void setDate(List<AddressBean> list){
        addressList.clear();
        addressList.addAll(list);
    }



    @NonNull
    @Override
    protected View getContentView() {
//        getDetailAddress();
        View content = LayoutInflater.from(getContext()).inflate(R.layout.dialog_address,null);
        RecyclerView rv_adress=(RecyclerView)content.findViewById(R.id.rv_adress);
       close = (ImageView) content.findViewById(R.id.close);
        confirm = (Button) content.findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
        close.setOnClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
       // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,  LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
       // rv_adress.setLayoutParams(params);
        rv_adress.setLayoutManager(manager);
        addressAdapter = new AddressAdapter(getActivity());
        addressAdapter.setCanLoadMore(false);
        rv_adress.setAdapter(addressAdapter);
        addressAdapter.setData(addressList);
        return content;
    }



    @Override
    public void onClick(View v) {
        int sum=0;
        switch (v.getId()) {

            case R.id.confirm:
                String addstr = addressAdapter.getSelectAddStr();
                dismiss();
//                onDismiss(getDialog());
                if (addSelectListener != null)
                    addSelectListener.addressSelect(addstr);

                break;

            case R.id.close:
                onDismiss(getDialog());

                break;


            default:
                break;
        }



    }

    private void submit() {
        HashMap<String, String> params = new HashMap<>();
        params.put("skuId",id);


        Network.getRequestBuilder(Util.createUrl("cart/add", ""))
                .params(params)
                .success(new Success() {
                    @Override
                    public void success(String model) {
                        try {

                            Toast.makeText(getContext(), "添加成功", Toast.LENGTH_SHORT).show();

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

    public static void setPositon(int i) {
        //  position=i;
        //   String detail= addressList(i);


    }

    public interface OnContinueSubListener {
        //        void cancel();
        void continueSub(String mobile);

        void onDismiss();
    }

    public interface AddSelectListener {
        void addressSelect(String addStr);
    }
}
