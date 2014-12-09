package com.perfect.service;

import java.util.Map;

/**
 * Created by vbzer_000 on 2014/9/2.
 */
public interface LogService {
    public Map<String, Long> getStatiscs(String userName , Long accountId);

    boolean upload(String userName , Long accountId);
}
