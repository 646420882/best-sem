package com.perfect.mongodb.dao.impl;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
        MongoTemplate mongoTemplate = getSysMongoTemplate();

        return mongoTemplate.find(query, entityClass, "sys_warning");
    }

    @Override
    public Class<WarningRuleEntity> getEntityClass() {
        return null;
    }

    @Override
    public void insert(WarningRuleEntity warningRuleEntity) {
        getSysMongoTemplate().insert(warningRuleEntity, "sys_warning");
    }

    @Override
    public void insertAll(List<WarningRuleEntity> entities) {

    }

    @Override
    public void update(WarningRuleEntity warningRuleEntity) {
        if (warningRuleEntity == null) {
            return;
        }
           Query query = new Query();
            query.addCriteria(Criteria.where("id").is(warningRuleEntity.getId()));
            Class entityClass = warningRuleEntity.getClass();
            Field[] fields = entityClass.getDeclaredFields();

            Update update = new Update();

            for (Field field : fields) {
                String fiedName = field.getName();
                if(fiedName.equals("id")){
                    continue;
                }

                StringBuffer getterName = new StringBuffer("get");
                getterName.append(fiedName.substring(0, 1).toUpperCase()).append(fiedName.substring(1));
                try {
                    Method method = entityClass.getDeclaredMethod(getterName.toString());
                    Object obj = method.invoke(warningRuleEntity);
                    if (obj != null) {
                        update.set(fiedName, obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            getSysMongoTemplate().updateFirst(query, update, entityClass, "sys_warning");

        }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}





/*  if(warningRuleEntity==null){
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
        getSysMongoTemplate().updateFirst(query, update, WarningRuleEntity.class, "sys_warning");*/
