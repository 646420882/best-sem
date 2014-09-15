package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.perfect.mongodb.utils.EntityConstants.ACCOUNT_ID;
import static com.perfect.mongodb.utils.EntityConstants.TBL_ACCOUNT_REPORT;

/**
 * Created by baizz on 14-7-25.
 */
@Repository("accountAnalyzeDAO")
public class AccountAnalyzeDAOImpl extends AbstractUserBaseDAOImpl<KeywordRealTimeDataVOEntity, Long> implements AccountAnalyzeDAO {

    @Override
    public Class<KeywordRealTimeDataVOEntity> getEntityClass() {
        return KeywordRealTimeDataVOEntity.class;
    }

    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public List<KeywordRealTimeDataVOEntity> performance(String userTable) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<KeywordRealTimeDataVOEntity> list = mongoTemplate.findAll(KeywordRealTimeDataVOEntity.class, userTable);
        return list;
    }

    @Override
    public List<AccountReportEntity> performaneUser(Date startDate, Date endDate) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();

        List<AccountReportEntity> list = mongoTemplate.find(Query.query(Criteria.where("date").gte(startDate).lte(endDate).and(ACCOUNT_ID).is(AppContext.getAccountId())), AccountReportEntity.class, TBL_ACCOUNT_REPORT);
        return list;
    }

    @Override
    public List<AccountReportEntity> performaneCurve(Date startDate, Date endDate) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<AccountReportEntity> list = mongoTemplate.find(Query.query(Criteria.where("date").gte(startDate).lte(endDate).and(ACCOUNT_ID).is(AppContext.getAccountId())).with(new Sort(Sort.Direction.ASC, "date")), AccountReportEntity.class, TBL_ACCOUNT_REPORT);
        return list;
    }

    @Override
    public List<AccountReportEntity> downAccountCSV() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<AccountReportEntity> list = mongoTemplate.find(new Query().with(new Sort("date")),AccountReportEntity.class,TBL_ACCOUNT_REPORT);
        return list;
    }
}
