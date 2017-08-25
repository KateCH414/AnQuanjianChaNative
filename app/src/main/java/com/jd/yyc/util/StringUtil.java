package com.jd.yyc.util;

import java.util.List;

/**
 * 文件操作工具类
 */
public class StringUtil {

    public static String getListStr(List<String> list) {
        if (CheckTool.isEmpty(list))
            return "";
        StringBuffer sb = new StringBuffer();

        for (String str : list) {
            sb.append(str);
            sb.append(" ");
        }

        return sb.toString();
    }

}