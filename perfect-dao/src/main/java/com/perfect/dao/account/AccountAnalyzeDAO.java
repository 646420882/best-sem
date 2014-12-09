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
    public List<KeywordRealDTO> performance(String userTable);

    /**
     * 获取账户表现数据
     *
     * @return
     */
    public List<AccountReportDTO> performaneUser(Date startDate, Date endDate);

    /**
     * 获取账户表现数据
     *
     * @return
     */
    public List<AccountReportDTO> performaneCurve(Date startDate, Date endDate);

    /**
     * csv文件数据获取
     */
    public List<AccountReportDTO> downAccountCSV();
}
