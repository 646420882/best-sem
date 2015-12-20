package com.perfect.utils;

import com.alibaba.fastjson.JSON;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.utils.redis.JRedisUtils;
import redis.clients.jedis.Jedis;

import java.util.Objects;

import static javax.servlet.http.HttpServletResponse.SC_FOUND;

/**
 * Created by subdong on 15-12-20.
 */
public class RedisObtainedByToken {

    public static SystemUserDTO getUserInfo(String token){
        Jedis jedis = null;

        try {
            jedis = JRedisUtils.get();
            String  JsonUserInfo = jedis.get(token);
            if (Objects.isNull(JsonUserInfo)) {
                return null;
            }

            SystemUserDTO systemUserDTO = JSON.parseObject(JsonUserInfo, SystemUserDTO.class);
            if (Objects.nonNull(systemUserDTO)) {
                return systemUserDTO;
            }
        } finally {
            if (Objects.nonNull(jedis)) {
                jedis.close();
            }
        }
        return null;
    }
}
