/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 * work 2014-2-14
 */
package com.perfect.autosdk_v4.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @title ConfigUtils
 * @description read configs from baidu-api.properties 
 * @author wangbin14
 * @date 2014-2-14
 * @version 1.0
 */
public class ApiConfig {

    private static Properties props = new Properties();

    static {
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("baidu-api.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ApiConfig() {
    }

    /**
     * @description simply read the value of property key
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return props.getProperty(key);
    }

    /**
     * @description update the value of property key
     * @param key
     * @param value
     */
    public static void updateProperties(String key, String value) {
        props.setProperty(key, value);
    }
}
