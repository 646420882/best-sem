package com.perfect.dao;

import com.perfect.entity.AccountRealTimeDataVOEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/8/6.
 */
public interface BasisReportDAO{
    /**
     * 单元报告
     * @param terminal 终端  终端 0、全部  1、PC   2、移动
     * @param userTable 数据表名
     * @return
     */
    public List<Object> getUnitReportDate(String terminal,String userTable);

    /**
     * 关键词报告
     * @param terminal 终端 0、全部  1、PC   2、移动
     * @param userTable 数据表名
     * @return
     */
    public List<Object> getKeywordsReportDate(String terminal,String userTable);

    /**
     * 创意报告
     * @param terminal 终端 0、全部  1、PC   2、移动
     * @param userTable 数据表名
     * @return
     */
    public List<Object> getCreativeReportDate(String terminal,String userTable);

    /**
     * 地域报告
     * @param terminal 终端 0、全部  1、PC   2、移动
     * @param userTable 数据表名
     * @return
     */
    public List<Object> getRegionalReportDate(String terminal,String userTable);
}
