package com.perfect.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by baizz on 2014-6-5.
 */
public class JSONUtils {
    private static ObjectMapper mapper;

    static {
        if (mapper == null)
            mapper = new ObjectMapper();
    }

    public static Map<String, Object> getJsonMapData(Object[] objects, String... dateFormat) {
        Map<String, Object> attributes = Collections.synchronizedMap(new LinkedHashMap<String, Object>());
        if (objects != null) {
            JsonConfig jsonConfig = new JsonConfig();
            if (dateFormat != null && dateFormat.length == 1)
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateProcessor(dateFormat[0]));
            else
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateProcessor());
            attributes.put("rows", JSONArray.fromObject(objects, jsonConfig));
        }
        return attributes;
    }

    //用于返回JSON对象数组
    public static JSONArray getJSONArrayData(Object[] objects, String... dateFormat) {
        JSONArray jsonArray = new JSONArray();
        if (objects != null) {
            JsonConfig jsonConfig = new JsonConfig();
            if (dateFormat != null && dateFormat.length == 1)
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateProcessor(dateFormat[0]));
            else
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateProcessor());
            jsonArray = JSONArray.fromObject(objects, jsonConfig);
        }
        return jsonArray;
    }
}