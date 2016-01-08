package com.perfect.bidding.subpub;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by baizz on 14-12-31.
 */
public class Subscriber extends JedisPubSub {
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(pattern + "\n" + channel + "\n" + message);
        System.out.println();
    }
}
