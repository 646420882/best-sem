package com.perfect.bidding.subpub;

import com.perfect.bidding.core.ApplicationConfig;
import com.perfect.bidding.redis.JRedisPools;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Executors;

/**
 * Created by baizz on 14-12-31.
 */
public class SubPubMain {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(ApplicationConfig.class);

        Executors.newSingleThreadExecutor().execute(() -> {
            Jedis jedis = null;
            try {
                jedis = JRedisPools.getConnection();
                jedis.psubscribe(new Subscriber(), "worker-*", "testqazxsw");
            } finally {
                if (jedis != null)
                    JRedisPools.returnJedis(jedis);
            }
        });

        Jedis jedis = null;
        try {
            jedis = JRedisPools.getConnection();
            jedis.publish("testqazxsw", "testqazxswMessage");
            jedis.publish("worker-1235", "Message000");
        } finally {
            if (jedis != null)
                JRedisPools.returnJedis(jedis);
        }
    }
}
