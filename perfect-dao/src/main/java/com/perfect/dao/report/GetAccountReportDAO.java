package com.perfect.dao.report;

import com.perfect.dto.account.AccountReportDTO;

import java.util.Date;

/**
 * Created by john on 2014/8/8.
 */
public interface GetAccountReportDAO {

    /**
     * 得到本地的数据报告（数据来自本地）
     * @param startDate
     * @return
     */
   AccountReportDTO getLocalAccountRealData(String userName,long accountId, Date startDate,Date endDate);

}
