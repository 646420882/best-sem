package com.perfect.dao;

import com.perfect.vo.CensusVO;
import com.perfect.dto.count.CensusDTO;

/**
 * Created by XiaoWei on 2014/11/11.
 */
public interface CensusDAO {
    /**
     * 添加数据源
     * @param censusEntity
     * @return
     */
    CensusDTO saveParams(CensusDTO censusEntity);

    /**
     *根据某个url地址获取今日统计数据
     * @return
     */
    public CensusVO getTodayTotal(String url);

    /**
     * 根据某个url地址获取昨日统计数据
     * @param url
     * @return
     */
    public CensusVO getLastDayTotal(String url);

    /**
     * 根据某个url地址获取上周统计数据
     * @param url
     * @return
     */
    public CensusVO getLastWeekTotal(String url);

    /**
     * 根据某个url地址获取上个月统计数据
     * @param url
     * @return
     */
    public CensusVO getLastMonthTotal(String url);

}
