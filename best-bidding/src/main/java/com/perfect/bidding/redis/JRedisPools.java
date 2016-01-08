package com.perfect.bidding.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.util.ResourceBundle;

/**
 * Created by baizz on 2014-12-24.
 */
public class JRedisPools {

    private static JedisPool jedisPool;


    public void init() {
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        if (bundle == null) {
            throw new IllegalArgumentException(
                    "[redis.properties] is not found!");
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.valueOf(bundle
                .getString("redis.pool.maxActive")));
        config.setMaxIdle(Integer.valueOf(bundle
                .getString("redis.pool.maxIdle")));
        config.setMaxWaitMillis(Integer.valueOf(bundle
                .getString("redis.pool.maxWait")));
        config.setTestOnBorrow(Boolean.valueOf(bundle
                .getString("redis.pool.testOnBorrow")));
        config.setTestOnReturn(Boolean.valueOf(bundle
                .getString("redis.pool.testOnReturn")));
        jedisPool = new JedisPool(config, bundle.getString("redis.hostName"),
                Integer.valueOf(bundle.getString("redis.port")), Protocol.DEFAULT_TIMEOUT,
                bundle.getString("redis.password"));
    }

    public static JedisPool getPool() {
        return jedisPool;
    }

    public static Jedis getConnection() {
        Jedis jedis = jedisPool.getResource();
//        jedis.getClient().select(1);    //db1
        return jedis;
    }

    public static void returnJedis(Jedis jedis) {
        jedisPool.returnResource(jedis);
    }

    public static void returnBrokenJedis(Jedis jedis) {
        jedisPool.returnBrokenResource(jedis);
    }

}
