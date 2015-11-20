package com.perfect.service;

import com.perfect.vo.CountAssistantVO;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/19.
 */
public interface AccountOverviewService {
    Map<String,Object> getKeyWordSum(String startDate,String endDate);

    /**
     * 对推广助手中的关键字,创意,计划,单元进行统计
     * @return
     */
    List<CountAssistantVO> countAssistant();

}
