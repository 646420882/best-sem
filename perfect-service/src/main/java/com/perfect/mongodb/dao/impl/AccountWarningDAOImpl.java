package com.perfect.mongodb.dao.impl;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/5.
 */
@Repository("accountWarningDAO")
public class AccountWarningDAOImpl implements AccountWarningDAO{

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());

    @Override
    public WarningRuleEntity findOne(Long aLong) {
        return null;
    }

    @Override
    public List<WarningRuleEntity> findAll() {
        return mongoTemplate.findAll(WarningRuleEntity.class,"sys_warning");
    }

    @Override
    public List<WarningRuleEntity> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    public List<WarningRuleEntity> find(Query query,Class entityClass){
         return mongoTemplate.find(query,entityClass,"sys_warning");
    }

    @Override
    public void insert(WarningRuleEntity warningRuleEntity) {
        mongoTemplate.insert(warningRuleEntity,"sys_warning");
    }

    @Override
    public void insertAll(List<WarningRuleEntity> entities) {

    }

    @Override
    public void update(WarningRuleEntity warningRuleEntity) {
        if(warningRuleEntity==null){
           return;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(warningRuleEntity.getId()));

        Update update = new Update();

        if(warningRuleEntity.getId()==null){
            return;
        }

        if(warningRuleEntity.getAccountId()!=null){
            update.set("accountId",warningRuleEntity.getAccountId());
        }
        if(warningRuleEntity.getBudget()!=null){
            update.set("budget",warningRuleEntity.getBudget());
        }
        if(warningRuleEntity.getBudgetType()!=null){
            update.set("budgetType",warningRuleEntity.getBudgetType());
        }
        if(warningRuleEntity.getWarningPercent()!=null){
            update.set("warningPercent",warningRuleEntity.getWarningPercent());
        }
        if(warningRuleEntity.getTels()!=null){
            update.set("tels",warningRuleEntity.getTels());
        }
        if(warningRuleEntity.getMails()!=null){
            update.set("mails",warningRuleEntity.getMails());
        }
        if(warningRuleEntity.getIsEnable()!=null){
            update.set("isEnable",warningRuleEntity.getIsEnable());
        }
        if(warningRuleEntity.getIsWarninged()!=null){
            update.set("isWarninged",warningRuleEntity.getIsWarninged());
        }
        if(warningRuleEntity.getDayCountDate()!=null){
            update.set("dayCountDate",warningRuleEntity.getDayCountDate());
        }
        mongoTemplate.updateFirst(query,update,WarningRuleEntity.class,"sys_warning");
    }

    @Override
    public void update(List<WarningRuleEntity> entities) {

    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void deleteByIds(List<Long> longs) {

    }

    @Override
    public void delete(WarningRuleEntity warningRuleEntity) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
