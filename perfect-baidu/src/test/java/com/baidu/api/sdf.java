package com.baidu.api;

import org.apache.cxf.aegis.type.xml.SourceType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by subdong on 15-10-8.
 */
public class sdf {
    public static void main(String[] args) {
        Map<String,Integer> ss= new HashMap<>();
        ss.put("sss", 111);


        System.out.println(ss.containsKey("sss"));
        System.out.println(ss.get("bbb"));
        ss.put("sss", ss.get("bbb") + 111435);
        System.out.println(ss.get("bbb"));
        System.out.println(ss.containsKey("bbb"));
    }
}
