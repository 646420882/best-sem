package com.perfect.utils.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.ResourceBundle;

/**
 * Created by yousheng on 2014/8/7.
 *
 * @author yousheng
 */
public class JRedisUtils {

    private static JedisPool pool;

    static {
        /*ResourceBundle bundle = ResourceBundle.getBundle("redis");
        if (bundle == null) {
            throw new IllegalArgumentException(
                    "[redis.properties] is not found!");
        }*/
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(10);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        pool = new JedisPool(config, "182.92.227.23",
                6379, Protocol.DEFAULT_TIMEOUT,
                "3edcvfr4");
    }

    public static Jedis get() {
        return pool.getResource();
    }

    public static JedisPool getPool() {
        return pool;
    }

    public static void returnJedis(Jedis jedis) {
        pool.returnResource(jedis);
    }

    public static void returnBrokenJedis(Jedis jedis) {
        pool.returnBrokenResource(jedis);
    }

}
