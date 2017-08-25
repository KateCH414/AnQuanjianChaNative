package com.jd.yyc.util.jdma;

import com.jd.yyc.base.YYCApplication;
import com.jd.yyc.constants.JDMaContants;
import com.jd.yyc.util.ChannelUtil;
import com.jd.yyc.util.CheckTool;
import com.jd.yyc.util.DeviceUtil;
import com.jd.yyc.util.Util;
import com.jingdong.jdma.JDMaInterface;
import com.jingdong.jdma.minterface.ClickInterfaceParam;
import com.jingdong.jdma.minterface.MaInitCommonInfo;
import com.jingdong.jdma.minterface.PvInterfaceParam;

/**
 * 埋点工具类
 */

public class JDMaUtil implements JDMaContants {
    static MaInitCommonInfo maInitCommonInfo = new MaInitCommonInfo();

    public static MaInitCommonInfo initMaCommonInfo() {
        maInitCommonInfo.site_id = TEST_SITE;
        maInitCommonInfo.appv = DeviceUtil.getVersionName();
        maInitCommonInfo.channel = ChannelUtil.getChannel(YYCApplication.context);
        maInitCommonInfo.guid = DeviceUtil.getDeviceId();
        return maInitCommonInfo;
    }


    //发送pv埋点数据
    public static void sendPVData(PvInterfaceParam param) {
        if (param != null && !CheckTool.isEmpty(Util.getUserAccount(YYCApplication.context))) {
            param.pin = Util.getUserAccount(YYCApplication.context);
        }
        JDMaInterface.sendPvData(YYCApplication.context, maInitCommonInfo, param);
    }

    //发送点击埋点数据
    public static void sendClickData(ClickInterfaceParam param) {
        if (param != null && !CheckTool.isEmpty(Util.getUserAccount(YYCApplication.context))) {
            param.pin = Util.getUserAccount(YYCApplication.context);
        }
        JDMaInterface.sendClickData(YYCApplication.context, maInitCommonInfo, param);
    }
}
