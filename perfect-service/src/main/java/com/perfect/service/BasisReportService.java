package com.perfect.service;

import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.AccountReportResponse;
import com.perfect.entity.StructureReportEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Map<String,List<StructureReportEntity>> getReportDate(String[] date,int terminal,int categoryTime,int reportType,int reportNumber);

    /**
     * 获取用户所有数据
     * @param Sorted
     * @param fieldName
     * @return
     */
    public Map<String,List<AccountReportResponse>> getAccountAll(int Sorted,String fieldName);

    /**
     *
     * 账户报告数据对比
     * @param startDate 数据1 开始时间
     * @param endDate   数据1 结束时间
     *
     * @param startDate1 数据2 开始时间
     * @param endDate1   数据2 结束时间
     * @param dateType  时间类型  默认：0  分日:1  分周：2 分月：3
     * @param devices   推广设备 0、全部  1、PC端 2、移动端
     * @return
     */
    public Map<String,List<Object>> getAccountDateVS(Date startDate, Date endDate,Date startDate1, Date endDate1,int dateType,int devices);


/*******************************************AIP***************************************************/
    /**
     *
     * @param id 需要查询的ID
     * @param startDate  开始时间
     * @param endDate    结束时间
     * @param devices    需要推广数据设备 0、整合数据  1、PC，移动 端数据独立
     * @return
     */
    public Map<String,List<StructureReportEntity>> getKeywordReport(Long[] id,String startDate,String endDate, int devices);
}
