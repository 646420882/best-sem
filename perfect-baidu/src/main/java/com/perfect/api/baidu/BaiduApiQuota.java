package com.perfect.api.baidu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by subdong on 15-10-8.
 */
public class BaiduApiQuota {
    private static final Logger quotaLogger = LoggerFactory.getLogger("baidu_quota");

    private static Map<String, Integer> qutoa = new HashMap<>();

    public static void setQuota(String userName, Integer qutoa) {
        if (Objects.nonNull(qutoa)) {
            if (BaiduApiQuota.qutoa.containsKey(userName))
                BaiduApiQuota.qutoa.put(userName, BaiduApiQuota.qutoa.get(userName) + qutoa);
            else BaiduApiQuota.qutoa.put(userName, qutoa);
        }
    }

    public static void clearQuota() {
        if (!qutoa.isEmpty()) qutoa.clear();
    }

    public static void printlnQuota() {
        qutoa.forEach((k, v) -> quotaLogger.info("账号：" + k + " 共消耗 " + v + " 个配额"));
    }


}
