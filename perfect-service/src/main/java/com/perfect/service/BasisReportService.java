package com.perfect.service;

import com.perfect.entity.StructureReportEntity;

import java.util.List;

/**
 * Created by SubDong on 2014/8/8.
 */
public interface BasisReportService {
    /**
     * 获取单元报告
     * @param terminal 推广设备 0、全部  1、PC端 2、移动端
     * @param date 时间
     * @param categoryTime 分类时间  0、默认 1、分日 2、分周 3、分月
     * @return
     */
    public List<StructureReportEntity> getUnitReportDate(String[] date,int terminal,int categoryTime);
}
