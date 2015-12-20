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

    /**
     * 通过token 得到用户信息
     * @param token
     * @return
     */
    public static SystemUserDTO getUserInfo(String token){
        Jedis jedis = null;

        try {
            jedis = JRedisUtils.get();
            String  JsonUserInfo = jedis.get(token);
            if (Objects.isNull(JsonUserInfo)) {
                return null;
            }

            SystemUserDTO systemUserDTO = getUserDTO(JsonUserInfo);

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

    /**
     * user JSON数据 转化为 user DTO
     * @param JsonUserInfo
     * @return
     */
    public static SystemUserDTO getUserDTO(String JsonUserInfo){
        return JSON.parseObject(JsonUserInfo, SystemUserDTO.class);
    }
}
