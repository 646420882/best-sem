package com.perfect.commons.web;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by yousheng on 15/12/15.
 */
public class JsonResultMaps {

    public static final String KEY_CODE = "code";
    public static final String KEY_MSG = "msg";
    public static final String KEY_DATA = "data";

    public static Map<String, Object> successMap() {
        return map(0);
    }

    public static Map<String, Object> successMap(Object data) {
        return map(0, "", data);
    }

    private static Map<String, Object> map(int code, String msg, Object data) {
        Map<String, Object> returnMap = jsonMap();

        returnMap.put(KEY_CODE, code);
        returnMap.put(KEY_MSG, msg);
        returnMap.put(KEY_DATA, data);
        return returnMap;
    }

    public static Map<String, Object> jsonMap() {
        Map<String, Object> returnMap = Maps.newHashMap();
        return returnMap;
    }

    public static Map<String, Object> map(int code) {
        Map<String, Object> returnMap = jsonMap();

        returnMap.put(KEY_CODE, code);
        return returnMap;
    }


    public static Map<String, Object> map(int code, String msg) {
        Map<String, Object> returnMap = jsonMap();

        returnMap.put(KEY_CODE, code);
        returnMap.put(KEY_MSG, msg);
        return returnMap;
    }


    public static Map<String, Object> failedMap() {
        return map(-1);
    }
}
