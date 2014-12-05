package com.perfect.dao;

import com.perfect.dto.count.CensusCfgDTO;
import com.perfect.dto.count.CensusDTO;
import com.perfect.dto.count.ConstantsDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/11/11.
 */
public interface CensusDAO  {
    /**
     * 添加数据源
     * @param censusEntity
     * @return
     */
    CensusDTO saveParams(CensusDTO censusEntity);

    /**
     *根据某个url地址获取几个时间段的统计数据
     * @return
     */
    Map<String, ConstantsDTO> getTodayTotal(String url);

    /**
     * 根据多条件查询数据
     * @param q
     * @return
     */
    List<ConstantsDTO> getVisitCustom(Map<String, Object> q);

    /**
     * 设置监控Url
     * @param censusCfgDTO
     */
    public int saveConfig(CensusCfgDTO censusCfgDTO);

    /**
     * 根据Ip获取Url配置列表
     * @param ip
     * @return
     */
    List<CensusCfgDTO> getCfgList(String ip);

    /**
     * 删除配置的Url地址
     * @param id
     */
    void delete(String id);

    /**
     * 根据用户ip获取受访页面数据
     * @param ip
     * @return
     */
    CountDTO getVisitPage(String ip, ConstantsDTO.CensusStatus censusStatus);

}
