package com.perfect.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.codehaus.jackson.map.DeserializationConfig;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    //返回JSON对象数组
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

    //返回JSON对象
    public static JSONObject getJSONObject(Object object, String... dateFormat) {
        JSONObject jsonObject = new JSONObject();
        if (object != null) {
            JsonConfig jsonConfig = new JsonConfig();
            if (dateFormat != null && dateFormat.length == 1)
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateProcessor(dateFormat[0]));
            else
                jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateProcessor());
            jsonObject = JSONObject.fromObject(object, jsonConfig);
        }
        return jsonObject;
    }

    //将JSON字符串转换为对应的Java Bean
    public static Object getObjectByJSON(String objStr, Class _class, String... dateFormat) {
        DateFormat df;
        if (dateFormat != null && dateFormat.length == 1)
            df = new SimpleDateFormat(dateFormat[0]);
        else
            df = new SimpleDateFormat("yyyy-M-d");
        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        mapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(df);
        Object obj = null;
        try {
            obj = mapper.readValue(objStr, _class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}