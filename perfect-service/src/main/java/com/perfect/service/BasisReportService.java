package com.perfect.service;

import com.perfect.dto.StructureReportDTO;
import com.perfect.dto.account.AccountReportDTO;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/8/8.
 */
public interface BasisReportService {
    /**
     * 获取报告
     *
     * @param devices    推广设备 0、全部  1、PC端 2、移动端
     * @param date       时间
     * @param dateType   分类时间  0、默认 1、分日 2、分周 3、分月
     * @param reportType 报告类型
     * @param limit      显示个数
     * @param start      开始数
     * @return
     */
    Map<String, List<StructureReportDTO>> getReportDate(String[] date, int devices, int dateType, int reportType, int start, int limit, String sort, Long dataId, String dateName);

    /**
     * 获取用户所有数据
     *
     * @param Sorted
     * @param fieldName
     * @return
     */
    Map<String, List<AccountReportDTO>> getAccountAll(int Sorted, String fieldName, int startJC, int limitJC);

/*******************************************AIP***************************************************/
    /**
     * 关键字查询
     *
     * @param id        需要查询的ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param devices   需要推广数据设备 0、整合数据  1、PC，移动 端数据独立
     *                  注：当devices为0时返回实体中直接 getPc 及是整合数据
     *                  当devices为1时返回实体中PC 和 Mobile 数据时独立分开
     *                  PC  及getPc  Mobile 及 getMobile
     * @return
     */
    Map<String, List<StructureReportDTO>> getKeywordReport(Long[] id, String startDate, String endDate, int devices);
}
