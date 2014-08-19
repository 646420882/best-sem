package com.perfect.mongodb.dao.impl;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
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
public class AccountWarningDAOImpl extends AbstractSysBaseDAOImpl<WarningRuleEntity, Long> implements AccountWarningDAO {

    @Override
    public WarningRuleEntity findOne(Long aLong) {
        return null;
    }

    @Override
    public List<WarningRuleEntity> findAll() {
        return getSysMongoTemplate().findAll(WarningRuleEntity.class, "sys_warning");
    }

    @Override
    public List<WarningRuleEntity> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    public List<WarningRuleEntity> find(Query query, Class entityClass) {
        return getSysMongoTemplate().find(query, entityClass, "sys_warning");
    }

    @Override
    public void insert(WarningRuleEntity warningRuleEntity) {
        getSysMongoTemplate().insert(warningRuleEntity, "sys_warning");
    }

    @Override
    public void insertAll(List<WarningRuleEntity> entities) {

    }

    public void update(WarningRuleEntity warningRuleEntity) {
        if (warningRuleEntity == null) {
            return;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(warningRuleEntity.getId()));

        Update update = new Update();

        if (warningRuleEntity.getId() == null) {
            return;
        }

        if (warningRuleEntity.getAccountId() != null) {
            update.set("accountId", warningRuleEntity.getAccountId());
        }
        if (warningRuleEntity.getBudget() != null) {
            update.set("budget", warningRuleEntity.getBudget());
        }
        if (warningRuleEntity.getBudgetType() != null) {
            update.set("budgetType", warningRuleEntity.getBudgetType());
        }
        if (warningRuleEntity.getWarningPercent() != null) {
            update.set("warningPercent", warningRuleEntity.getWarningPercent());
        }
        if (warningRuleEntity.getTels() != null) {
            update.set("tels", warningRuleEntity.getTels());
        }
        if (warningRuleEntity.getMails() != null) {
            update.set("mails", warningRuleEntity.getMails());
        }
        if (warningRuleEntity.getIsEnable() != null) {
            update.set("isEnable", warningRuleEntity.getIsEnable());
        }
        if (warningRuleEntity.getIsWarninged() != null) {
            update.set("isWarninged", warningRuleEntity.getIsWarninged());
        }
        if (warningRuleEntity.getDayCountDate() != null) {
            update.set("dayCountDate", warningRuleEntity.getDayCountDate());
        }
        getSysMongoTemplate().updateFirst(query, update, WarningRuleEntity.class, "sys_warning");
    }


    @Override
    public Class<WarningRuleEntity> getEntityClass() {
        return WarningRuleEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
