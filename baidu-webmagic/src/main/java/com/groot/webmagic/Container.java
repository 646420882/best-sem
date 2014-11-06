package com.groot.webmagic;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yousheng on 14/11/6.
 */
public class Container {

    private static final ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Object> locker = new ConcurrentHashMap<>();

    public static void put(String key, Object value) {

        synchronized (key) {
            resultMap.put(key, value);
            key.notify();
        }
    }

    public static Object get(String key) throws InterruptedException {
        if(!resultMap.containsKey(key)){
            synchronized (key) {
                if(!resultMap.containsKey(key)) {
                    key.wait();
                }
            }
        }
        return resultMap.remove(key);
    }

}
