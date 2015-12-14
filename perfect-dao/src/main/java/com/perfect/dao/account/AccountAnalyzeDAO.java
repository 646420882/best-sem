package com.perfect.dao.account;


import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.keyword.KeywordRealDTO;

import java.util.Date;
import java.util.List;


/**
 * Created by baizz on 2014-7-25.
 * 2014-12-2 refactor
 */
public interface AccountAnalyzeDAO extends HeyCrudRepository<AccountReportDTO, Long> {
    List<KeywordRealDTO> performance(String userTable);

    /**
     * 获取账户表现数据
     *
     * @return
     */
    List<AccountReportDTO> performaneUser(Date startDate, Date endDate);

    /**
     * 获取账户表现数据
     *
     * @return
     */
    List<AccountReportDTO> performaneCurve(Date startDate, Date endDate);

    /**
     * csv文件数据获取
     */
    List<AccountReportDTO> downAccountCSV(Date start, Date end);

    /**
     * 统计推广助手关键字
     *
     * @param number 0 为查询所有.不为0为查询修改后的数据
     * @return
     */
    Long countKeyword(int number);

    /**
     * 统计推广助手计划
     *
     * @param number 0 为查询所有.不为0为查询修改后的数据
     * @return
     */
    Long countCampaign(int number);

    /**
     * 统计推广助手创意
     *
     * @param number 0 为查询所有.不为0为查询修改后的数据
     * @return
     */
    Long countCreative(int number);

    /**
     * 统计推广助手单元
     *
     * @param number 0 为查询所有.不为0为查询修改后的数据
     * @return
     */
    Long countAdgroup(int number);
}