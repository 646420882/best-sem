package com.perfect.service;

import com.perfect.dto.RealTimeResultDTO;
import com.perfect.dto.account.AccountReportDTO;

import java.util.Date;
import java.util.List;

/**
 * Created by XiaoWei on 2014/12/3.
 */
public interface GetAccountReportService {
    AccountReportDTO getLocalAccountRealData(String userName, long accountId, Date startDate, Date endDate);

    /**
     * 在百度上实时获取数据报告(数据来自百度)
     * @param startDate
     * @param endDate
     * @return
     */
    List<RealTimeResultDTO> getAccountRealTimeTypeByDate(String systemUserName, Long accountId, String startDate, String endDate);

}
