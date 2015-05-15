package com.perfect.utils;

/**
 * Created by XiaoWei on 2015/5/15.
 */
public class IdConvertUtils {
    public static Long convert(Long l) {
        String _tmp = l.toString();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < _tmp.length(); i++) {
            Integer _number = Integer.parseInt(_tmp.charAt(i) + "");
            sb.append(9 - +_number);
        }
        return Long.valueOf(sb.toString());
    }
}
