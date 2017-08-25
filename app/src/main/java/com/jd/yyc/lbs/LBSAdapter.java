package com.jd.yyc.lbs;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jd.yyc.R;
import com.jd.yyc.api.model.LBS;
import com.jd.yyc.event.EventOnLbsChange;
import com.jd.yyc.refreshfragment.RecyclerAdapter;
import com.jd.yyc.refreshfragment.YYCViewHolder;
import com.jd.yyc.util.SharePreferenceUtil;
import com.jd.yyc.util.jdma.JDMaUtil;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.PvInterfaceParam;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * Created by zhangweifeng1 on 2017/5/17.
 */

public class LBSAdapter extends RecyclerAdapter<LBS, YYCViewHolder> {

    public LBSAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public YYCViewHolder onCreateViewHolder2(ViewGroup parent, int viewType) {
        return new LBSViewHloder(View.inflate(mContext, R.layout.item_lbs, null));
    }

    @Override
    public void onBindViewHolder2(YYCViewHolder holder, int position) {
        holder.onBind(getItem(position));
    }

    class LBSViewHloder extends YYCViewHolder<LBS> {
        @InjectView(R.id.lbs_tv)
        TextView lbsTv;

        public LBSViewHloder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        @Override
        public void onBind(LBS data) {
            super.onBind(data);
            lbsTv.setText(data.name);
        }

        @Override
        public void onNoDoubleCLick(View v) {
            v.setSelected(true);
            SharePreferenceUtil.setLbsName(mContext, data.name + "/" + data.id);
            EventBus.getDefault().post(new EventOnLbsChange(data));
            ((Activity) mContext).finish();

            //埋点
            ClickInterfaceParam clickInterfaceParam = new ClickInterfaceParam();
            clickInterfaceParam.page_id = "0013";
            clickInterfaceParam.page_name = "地区选择";
            clickInterfaceParam.event_id = "yjc_android_201706262|46";
            clickInterfaceParam.map = new HashMap<>();
            clickInterfaceParam.map.put("选中地址", data.name);
            JDMaUtil.sendClickData(clickInterfaceParam);
        }
    }
}
