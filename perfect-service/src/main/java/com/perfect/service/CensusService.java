package com.perfect.service;

import com.perfect.dto.count.CensusCfgDTO;
import com.perfect.dto.count.ConstantsDTO;
import com.perfect.entity.CensusEntity;
import com.perfect.service.impl.CensusServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/11/11.
 */
public interface CensusService  {
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
    public Map<String,ConstantsDTO> getTodayTotal(String url);

    public List<ConstantsDTO> getVisitCustom(Map<String, Object> q);

    public int saveConfig(CensusCfgDTO censusCfgDTO);

    List<CensusCfgDTO> getCfgList(String ip);

    void delete(String id);

    CountDTO getVisitPage(String ip, int datStatus);

}
