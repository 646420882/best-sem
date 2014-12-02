package com.perfect.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.utils.redis.JRedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/17.
 */
public class RegionalCodeUtils {

    /**
     * 通过地域id查询地域名称
     *
     * @param id
     * @return
     */
    public static Map<Integer, String> regionalCode(List<Integer> id) {
        Jedis jc = null;
        try {
            jc = JRedisUtils.get();
            String data = jc.get("RegionalCodeRedis");
            Gson gson = new Gson();
            Map<String, String> d = gson.fromJson(data, new TypeToken<Map<Integer, String>>() {
            }.getType());
            Map<Integer, String> map = new HashMap<>();
            for (Integer aLong : id) {
                map.put(aLong, d.get(aLong));
            }
            return map;
        } catch (JedisException e) {
            if (jc != null) {
                JRedisUtils.returnBrokenJedis(jc);
                jc = null;
            }
        } finally {
            if (jc != null) {
                JRedisUtils.returnJedis(jc);
            }

        }
        return null;
    }

    /**
     * 通过地域名称查询地域ID
     *
     * @param name
     * @return
     */
    public static Map<Integer, String> regionalCodeName(List<String> name) {
        Jedis jc = null;
        try {
            jc = JRedisUtils.get();
            String data = jc.get("RegionalCodeRedis");
            Gson gson = new Gson();
            Map<Integer, String> d = gson.fromJson(data, new TypeToken<Map<Integer, String>>() {
            }.getType());
            Map<Integer, String> map = new HashMap<>();
            for (String aString : name) {
                for (Map.Entry<Integer, String> voEntity : d.entrySet()) {
                    if (aString.equals(voEntity.getValue())) {
                        map.put(voEntity.getKey(), aString);
                    }
                }
            }
            return map;
        } catch (JedisException e) {
            if (jc != null) {
                JRedisUtils.returnBrokenJedis(jc);
            }
        } finally {
            if (jc != null) {
                JRedisUtils.returnJedis(jc);
            }
        }
        return null;
    }

}
