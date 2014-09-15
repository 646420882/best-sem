package com.perfect.dao;



import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;

import java.util.Date;
import java.util.List;


/**
 * Created by baizz on 14-7-25.
 */
public interface AccountAnalyzeDAO extends MongoCrudRepository<KeywordRealTimeDataVOEntity, Long> {
    public List<KeywordRealTimeDataVOEntity> performance(String userTable);

     /**
     * 获取账户表现数据
     * @return
     */
    public List<AccountReportEntity> performaneUser(Date startDate, Date endDate);

    /**
     * 获取账户表现数据
     * @return
     */
    public List<AccountReportEntity> performaneCurve(Date startDate, Date endDate);

    /**
     * csv文件数据获取
     */
    public List<AccountReportEntity> downAccountCSV();
}
