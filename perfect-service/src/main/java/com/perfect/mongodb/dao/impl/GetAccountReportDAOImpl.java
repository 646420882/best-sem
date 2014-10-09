package com.perfect.mongodb.dao.impl;

import com.perfect.api.baidu.AccountRealTimeReport;
import com.perfect.autosdk.sms.v3.RealTimeResultType;
import com.perfect.dao.GetAccountReportDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/8/8.
 */
@Component("getAccountReportDAO")
public class GetAccountReportDAOImpl implements GetAccountReportDAO {

    @Resource
    private AccountRealTimeReport accountRealTimeReport;

    /**
     * 得到本地的数据报告（数据来自本地）
     * @param startDate
     * @param endDate
     * @return
     */
    public List<AccountReportEntity> getLocalAccountRealData(String userName,Date startDate,Date endDate){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(userName));
        List<AccountReportEntity> list = mongoTemplate.find(new Query(Criteria.where("date").gte(startDate).lte(endDate)).with(new Sort(Sort.Direction.ASC, "date")), AccountReportEntity.class, EntityConstants.TBL_ACCOUNT_REPORT);
        return list;
    }



    /**
     * 得到当天的实时数据报告(来自百度)
     * @return
     */
    public List<RealTimeResultType> getAccountRealTimeTypeByDate(Long accountId,String startDate,String endDate){
        List<RealTimeResultType> realTimeDataList = accountRealTimeReport.getAccountRealTimeData(accountId,startDate, endDate);
        return realTimeDataList;
    }
}
