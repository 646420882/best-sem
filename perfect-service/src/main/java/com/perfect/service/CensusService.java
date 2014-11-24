package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.ConstantsDTO;
import com.perfect.entity.CensusEntity;

/**
 * Created by XiaoWei on 2014/11/11.
 */
public interface CensusService extends MongoCrudRepository<CensusEntity,Long> {
    /**
     * 参数组，添加方法
     * @param osAnBrowser
     * @return
     */
    String saveParams(String[] osAnBrowser);

    /**
     * 根据某个url地址获取今日统计数据
     * @return
     */
    public ConstantsDTO getTodayTotal(String url);

    /**
     * 根据某个url地址获取昨日统计数据
     * @param url
     * @return
     */
    public ConstantsDTO getLastDayTotal(String url);

    /**
     * 根据某个url地址获取上周统计数据
     * @param url
     * @return
     */
    public ConstantsDTO getLastWeekTotal(String url);

    /**
     * 根据某个url地址获取上个月统计数据
     * @param url
     * @return
     */
    public ConstantsDTO getLastMonthTotal(String url);

}
