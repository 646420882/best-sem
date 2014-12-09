package com.perfect.dao;



import com.perfect.dto.count.CensusDTO;
import com.perfect.dto.count.SourcesAllDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/12/1.
 */
public interface SourceDAO {

    /**
     * 得到 来源分析 基础数据
     * @param dates 查询时间范围
     * @param accessType 访问类型  0：全部     1：计算机    2：移动设备
     * @param userType 访问用户类型  0：全部   1：新访客   2：老访客
     * @return
     */
    public List<CensusDTO> getSourceAnalysis(List<Date> dates, Integer accessType, Integer userType);

    /**
     * 获取分组数据
     * @param dates 查询时间范围
     * @param accessType 访问类型  0：全部     1：计算机    2：移动设备
     * @param userType 访问用户类型  0：全部   1：新访客   2：老访客
     * @return
     */
    public SourcesAllDTO getGroupFind(List<Date> dates, Integer accessType, Integer userType);

    /**
     * 获取指定条件的全部数据
     * @param dates
     * @param accessType
     * @param userType
     * @param conditionName 条件字段
     * @param data  条件数据
     * @return
     */
    public List<CensusDTO> getDesignationData(List<Date> dates, Integer accessType, Integer userType,String conditionName,String data);

}
