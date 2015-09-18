package com.perfect.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by subdong on 15-9-18.
 */
public interface BasisReportUCService {
    /**
     * 账户报告数据对比
     *
     * @param startDate  数据1 开始时间
     * @param endDate    数据1 结束时间
     * @param startDate1 数据2 开始时间
     * @param endDate1   数据2 结束时间
     * @param dateType   时间类型  默认：0  分日:1  分周：2 分月：3
     * @param devices    推广设备 0、全部  1、PC端 2、移动端
     * @param compare    是否需要数据比较 1、比较 0、不比较
     * @return
     */
    Map<String, List<Object>> getAccountDateVS(Date startDate, Date endDate, Date startDate1, Date endDate1, int dateType, int devices, int compare, String sortVS, int startVS, int limitVS);
}
