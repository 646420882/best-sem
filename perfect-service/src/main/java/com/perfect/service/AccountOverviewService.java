package com.perfect.service;

import java.util.Map;

/**
 * Created by john on 2014/8/19.
 */
public interface AccountOverviewService {
    Map<String,Object> getKeyWordSum(String startDate,String endDate);
}
