package com.perfect.mongodb.dao.impl;

import com.perfect.api.baidu.AccountRealTimeData;
import com.perfect.autosdk.sms.v3.RealTimeResultType;
import com.perfect.dao.GetRealTimeDataDAO;
import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/8/8.
 */
@Component("getRealTimeDataDAO")
public class GetRealTimeDataDAOImpl implements GetRealTimeDataDAO {
    /**
     * 得到本地的数据报告（数据来自本地）
     * @param startDate
     * @param endDate
     * @return
     */
    public List<AccountRealTimeDataVOEntity> getLocalAccountRealData(String userName,Date startDate,Date endDate){
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(userName));
        List<AccountRealTimeDataVOEntity> list = mongoTemplate.find(Query.query(Criteria.where("date").gte(startDate).lte(endDate)).with(new Sort(Sort.Direction.ASC, "date")), AccountRealTimeDataVOEntity.class, "accountRealTimeData");
        return list;
    }



    /**
     * 得到当天的实时数据报告(来自百度)
     * @return
     */
    public List<RealTimeResultType> getAccountRealTimeTypeByDate(String startDate,String endDate){
        List<RealTimeResultType> realTimeDataList = AccountRealTimeData.getAccountRealTimeData(startDate, endDate);
        return realTimeDataList;
    }
}
