package com.perfect.dao;

import com.perfect.entity.AccountRealTimeDataVOEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/8/6.
 */
public interface BasisReportDAO{
    /**
     *
     * @param date 时间
     * @param terminal 终端
     * @param reportType 报告类型
     * @return
     */
    public List<Object> getReportGenerationDate(Date[] date,String terminal, int reportType);
}
