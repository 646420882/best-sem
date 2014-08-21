package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
        List<KeywordRealTimeDataVOEntity> list = getMongoTemplate().findAll(KeywordRealTimeDataVOEntity.class, userTable);
        return list;
    }

    @Override
    public List<AccountRealTimeDataVOEntity> performaneUser(Date startDate, Date endDate, String fieldName, int Sorted, int limit) {
        Sort sort = null;
        if (Sorted == 0) {
            sort = new Sort(Sort.Direction.ASC, fieldName);
        } else {
            sort = new Sort(Sort.Direction.DESC, fieldName);
        }
        List<AccountRealTimeDataVOEntity> list = getMongoTemplate().find(Query.query(Criteria.where("date").gte(startDate).lte(endDate)).with(sort).skip(0).limit(limit), AccountRealTimeDataVOEntity.class, "account_report");
        return list;
    }

    @Override
    public List<AccountRealTimeDataVOEntity> performaneCurve(Date startDate, Date endDate) {

        List<AccountRealTimeDataVOEntity> list = getMongoTemplate().find(Query.query(Criteria.where("date").gte(startDate).lte(endDate)).with(new Sort(Sort.Direction.ASC, "date")), AccountRealTimeDataVOEntity.class, "account_report");
        return list;
    }
}
