package com.perfect.dao;

import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.entity.StructureReportEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/8/6.
 */
public interface BasisReportDAO{
    /**
     * 单元报告
     * @param userTable 数据表名
     * @return
     */
    public List<StructureReportEntity> getUnitReportDate(String userTable);

    /**
     * 关键词报告
     * @param userTable 数据表名
     * @return
     */
    public List<StructureReportEntity> getKeywordsReportDate(String userTable);

    /**
     * 创意报告
     * @param userTable 数据表名
     * @return
     */
    public List<StructureReportEntity> getCreativeReportDate(String userTable);

    /**
     * 地域报告
     * @param userTable 数据表名
     * @return
     */
    public List<StructureReportEntity> getRegionalReportDate(String userTable);
}
