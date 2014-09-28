package com.perfect.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/17.
 */
public class RegionalCodeUtils {

    public static  Map<Integer,String> regionalCode(List<Integer> id){
        Jedis jc = JRedisUtils.get();
        String data = jc.get("RegionalCodeRedis");
        Gson gson = new Gson();
        Map<String,String> d = gson.fromJson(data, new TypeToken<Map<Integer,String>>() {}.getType());
        Map<Integer,String> map = new HashMap<>();
        for (Integer aLong : id){
            map.put(aLong, d.get(aLong));
        }
        JRedisUtils.returnJedis(jc);
        return map;
    }
    public static  Map<Integer,String> regionalCodeName(List<String> name){
        Jedis jc = JRedisUtils.get();
        String data = jc.get("RegionalCodeRedis");
        Gson gson = new Gson();
        Map<Integer,String> d = gson.fromJson(data, new TypeToken<Map<Integer,String>>() {}.getType());
        Map<Integer,String> map = new HashMap<>();
        for (String aString : name){
            for (Map.Entry<Integer, String> voEntity : d.entrySet()){
                if(aString.equals(voEntity.getValue())){
                    map.put(voEntity.getKey(),aString);
                }
            }
        }
        JRedisUtils.returnJedis(jc);
        return map;
    }
}
