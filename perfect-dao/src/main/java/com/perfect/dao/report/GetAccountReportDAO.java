package com.perfect.dao.report;

import com.perfect.dto.RealTimeResultDTO;
import com.perfect.dto.account.AccountReportDTO;

import java.util.Date;
import java.util.List;

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

    /**
     * 在百度上实时获取数据报告(数据来自百度)
     * @param startDate
     * @param endDate
     * @return
     */
    List<RealTimeResultDTO> getAccountRealTimeTypeByDate(String systemUserName,Long accountId, String startDate, String endDate);

}
