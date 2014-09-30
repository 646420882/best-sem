package com.perfect.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.perfect.dto.RegionalCodeDTO;
import com.perfect.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

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
        Jedis jc = JRedisUtils.get();
        String data = jc.get("RegionalCodeRedis");
        Gson gson = new Gson();
        Map<String, String> d = gson.fromJson(data, new TypeToken<Map<Integer, String>>() {
        }.getType());
        Map<Integer, String> map = new HashMap<>();
        for (Integer aLong : id) {
            map.put(aLong, d.get(aLong));
        }
        JRedisUtils.returnJedis(jc);
        return map;
    }

    /**
     * 通过地域名称查询地域ID
     *
     * @param name
     * @return
     */
    public static Map<Integer, String> regionalCodeName(List<String> name) {
        Jedis jc = JRedisUtils.get();
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
        JRedisUtils.returnJedis(jc);
        return map;
    }

    /**
     * 通过省域ID查询对应此省下的说有 地域以及id
     * @param id
     * @return
     */
    /**
     * 通过地域id查询地域名称
     *
     * @param id
     * @return
     */
    public static Map<Integer, List<RegionalCodeDTO>> provinceCode(List<Integer> id) {
        Jedis jc = JRedisUtils.get();
        String data = jc.get("RegionalCodeRedis");
        Gson gson = new Gson();
        Map<Integer, String> mapKey = gson.fromJson(data, new TypeToken<Map<Integer, String>>() {
        }.getType());

        Map<Integer, List<RegionalCodeDTO>> map = new HashMap<>();

        for (Integer integer : id) {

            List<RegionalCodeDTO> redisRegionalList = new ArrayList<>();

            for (Map.Entry<Integer, String> voEntity : mapKey.entrySet()) {

                RegionalCodeDTO redisRegional = new RegionalCodeDTO();
                int regional = voEntity.getKey() / 1000;
                int proCode = integer / 1000;
                if (regional == proCode) {
                    if (voEntity.getKey().intValue() != integer.intValue()) {
                      /*  redisRegional.setRegionalId(voEntity.getKey());
                        redisRegional.setRegionalName(voEntity.getValue());*/
                        redisRegionalList.add(redisRegional);
                    }
                }
            }
            map.put(integer, redisRegionalList);
        }
        JRedisUtils.returnJedis(jc);
        return map;
    }
}
