package com.perfect.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by baizz on 2014-08-05.
 */
public class JSONUtils {
    private static ObjectMapper mapper;

    static {
        if (mapper == null)
            mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
    }

    public static Map<String, Object> getJsonMapData(Object o) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        try {
            JsonNode jsonNode = mapper.readTree(mapper.writeValueAsBytes(o));
            attributes.put("rows", jsonNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return attributes;
    }

    //获取JSON对象数组
    public static ArrayNode getJsonObjectArray(Object o) {
        ArrayNode arrayNode = mapper.createArrayNode();
        try {
            JsonNode jsonNodes = mapper.readTree(mapper.writeValueAsBytes(o));
            for (JsonNode jn : jsonNodes) {
                arrayNode.add(jn);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayNode;
    }

    //获取JSON对象
    public static JsonNode getJsonObject(Object o) {
        JsonNode jsonObj = null;
        try {
            jsonObj = mapper.readTree(mapper.writeValueAsBytes(o));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }

    //将JSON字符串转换为对应的Java Bean
    public static Object getObjectByJson(String jsonStr, Class _class) {
        Object obj = null;
        try {
            obj = mapper.readValue(jsonStr, _class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    //将JSON字符数组转换为对应的Java Bean集合
    public static Object[] getObjectsByJsonArrays(String jsonArrays, Class<? extends Object[]> _class) {
        Object[] objects = null;
        try {
            objects = mapper.readValue(jsonArrays, _class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }

}