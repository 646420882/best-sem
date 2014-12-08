package com.perfect.service;



import com.perfect.dto.count.CensusDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/12/1.
 */
public interface SourceService {
    /**
     * 得到 来源分析 基础数据
     * @param dates 查询时间范围
     * @param accessType 访问类型  0：全部     1：计算机    2：移动设备
     * @param userType 访问用户类型  0：全部   1：新访客   2：老访客
     * @return
     */
    public List<CensusDTO> getSourceAnalysis(List<Date> dates, Integer accessType, Integer userType);

}
