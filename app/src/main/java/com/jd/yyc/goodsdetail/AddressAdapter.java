package com.jd.yyc.goodsdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.AddressBean;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by jiahongbin on 2017/6/8.
 */

public class AddressAdapter extends RecyclerAdapter<AddressBean,AddressAdapter.AddressHolder> {
    public Context cct;

    public AddressAdapter(Context mContext) {

        super(mContext);
        this.cct = mContext;
    }

    public String getSelectAddStr() {
        if (getList().size() == 0) {
            return "";
        }
        String addStr = getList().get(0).addressDetailForPage;
        for (AddressBean addressBean : getList()) {
            if (addressBean.isSelect) {
                addStr = addressBean.addressDetailForPage;
                break;
            }
        }
        return addStr;

    }

    public void setSelectStatus(int position){
        for(int i=0;i<getList().size();i++){
            getItem(i).isSelect = (position ==i?true:false);
            //   AddressHolder.setImagetype(getItem(i).isSelect);

        }



        notifyDataSetChanged();
    }



    @Override
    public AddressHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.address_item, null, false);

        return new AddressHolder(view);
    }

    public void onBindViewHolder2(AddressHolder holder, int position) {
        holder.onBind(position,getItem(position));
    }



    public class AddressHolder extends YYCViewHolder<AddressBean> {

        private void setImagetype(boolean isSelect) {
        }
        @InjectView(R.id.iv)
        ImageView iv;
        @InjectView(R.id.tv_address)
        TextView tv_address;


        public AddressHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }

        @Override
        public void onBind(final int position, final AddressBean data) {
            super.onBind(position, data);

            //     subPrice.setText(ResourceUtil.getString(R.string.sub_price, 200));
            tv_address.setText(data.addressDetailForPage);
            tv_address.setSelected(data.isSelect);
            iv.setSelected(data.isSelect);

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 //   ToastUtil.show("选择：------"+data.detailAddress);

                    setSelectStatus(position);

                }
            });
       /*     for(int i=0;i<getList().size();i++){

                //   AddressHolder.setImagetype(getItem(i).isSelect)
                if(getItem(i).isSelect = (position ==i?true:false)==true){
                    iv.setVisibility(View.VISIBLE);
                }else{
                    iv.setVisibility(View.INVISIBLE);
                }

            }*/


        }


    }



    public void setSelectS(int position){
        for(int i=0;i<getList().size();i++){
            getItem(i).isSelect = (position ==i?true:false);
           /* if(getItem(i).isSelect=true){
                iv.setVisibility(View.VISIBLE);
            }else{
                iv.setVisibility(View.INVISIBLE);
            }*/

        }



        notifyDataSetChanged();
    }



}
