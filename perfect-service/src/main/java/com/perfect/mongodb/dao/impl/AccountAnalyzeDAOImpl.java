package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.AccountAnalyzeDAO;
import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 14-7-25.
 */
@Repository("accountAnalyzeDAO")
public class AccountAnalyzeDAOImpl implements AccountAnalyzeDAO {

    String currUserName = AppContext.getUser().toString();

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(currUserName);

    public KeywordRealTimeDataVOEntity findOne(Long aLong) {
        return null;
    }

    public List<KeywordRealTimeDataVOEntity> findAll() {
        return null;
    }

    public List<KeywordRealTimeDataVOEntity> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    public void insert(KeywordRealTimeDataVOEntity keywordRealTimeDataVOEntity) {

    }

    public void insertAll(List<KeywordRealTimeDataVOEntity> entities) {

    }

    public void update(KeywordRealTimeDataVOEntity keywordRealTimeDataVOEntity) {

    }

    public void update(List<KeywordRealTimeDataVOEntity> entities) {

    }

    public void deleteById(Long aLong) {

    }

    public void deleteByIds(List<Long> longs) {

    }

    public void delete(KeywordRealTimeDataVOEntity keywordRealTimeDataVOEntity) {

    }

    public void deleteAll() {

    }

    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public List<KeywordRealTimeDataVOEntity> performance(String userTable) {
        List<KeywordRealTimeDataVOEntity> list = mongoTemplate.findAll(KeywordRealTimeDataVOEntity.class, userTable);
        return list;
    }

    @Override
    public List<AccountRealTimeDataVOEntity> performaneUser(Date startDate, Date endDate,String fieldName,int Sorted, int limit) {
        Sort sort = null;
        if(Sorted == 0){
            sort =  new Sort(Sort.Direction.ASC,fieldName);
        }else{
            sort = new Sort(Sort.Direction.DESC,fieldName);
        }
        List<AccountRealTimeDataVOEntity> list = mongoTemplate.find(Query.query(Criteria.where("date").gte(startDate).lte(endDate)).with(sort).skip(0).limit(limit),AccountRealTimeDataVOEntity.class, "AccountRealTimeData");
        return list;
    }

    @Override
    public List<AccountRealTimeDataVOEntity> performaneCurve(Date startDate, Date endDate) {

        List<AccountRealTimeDataVOEntity> list = mongoTemplate.find(Query.query(Criteria.where("date").gte(startDate).lte(endDate)).with(new Sort(Sort.Direction.ASC,"date")),AccountRealTimeDataVOEntity.class, "AccountRealTimeData");
        return list;
    }
}
