package com.perfect.mongodb.impl;

import com.perfect.dao.AccountDAO;
import com.perfect.entity.AccountInfoEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
@Repository(value = "accountDAO")
public class AccountDAOImpl extends AbstractBaseDAOImpl<AccountInfoEntity> implements AccountDAO {

    @Override
    public void insert(AccountInfoEntity accountInfoEntity) {
        getMongoTemplate().insert(accountInfoEntity);
    }

    @Override
    public void delete(AccountInfoEntity criteria) {
        getMongoTemplate().remove(criteria);
    }

    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(AccountInfoEntity.class);
    }

    @Override
    public void updateById(AccountInfoEntity accountInfoEntity) {
        Criteria criteria = Criteria.where("userid").is(accountInfoEntity.getUserid());
        Query query = new Query(criteria);
        getMongoTemplate().upsert(query, getUpdate(accountInfoEntity), AccountInfoEntity.class);
    }

    @Override
    public void update(AccountInfoEntity src, AccountInfoEntity dest) {
        BeanUtils.copyProperties(src, dest);
        getMongoTemplate().save(dest);
    }

    @Override
    public AccountInfoEntity findById(String id) {
        return getMongoTemplate().findById(id, AccountInfoEntity.class);
    }

    @Override
    public List<AccountInfoEntity> findAll() {
        return getMongoTemplate().findAll(AccountInfoEntity.class);
    }

    @Override
    public long count() {
        return -1;
    }

    @Override
    public List<AccountInfoEntity> find(AccountInfoEntity criteria, int skip, int limit) {
        Query query = getQuery(criteria);
        query.skip(skip).limit(limit);

        return getMongoTemplate().find(query, AccountInfoEntity.class);
    }

    @Override
    public AccountInfoEntity findAndModify(AccountInfoEntity criteria, AccountInfoEntity update) {
        AccountInfoEntity target = getMongoTemplate().findAndModify(new Query(Criteria.where("userId").is(criteria.getUserid())), getUpdate(update), AccountInfoEntity.class);
        return target;
    }

    private Update getUpdate(AccountInfoEntity update) {
        return Update.update("balance", update.getBalance()).set("cost", update.getCost()).set("payment", update.getPayment()).set("budgetType", update.getBudgetType()).set("budget", update.getBudget()).set("regionTarget", update.getRegionTarget()).set("excludeIp", update.getExcludeIp()).set("openDomains", update.getOpenDomains()).set("regDomain", update.getRegDomain()).set("budgetOfflineTime", update.getBudgetOfflineTime()).set("weeklyBudget", update.getWeeklyBudget()).set("userStat", update.getUserStat());
    }

    @Override
    public AccountInfoEntity findAndRemove(AccountInfoEntity criteria) {
        AccountInfoEntity target = getMongoTemplate().findAndRemove(new Query(Criteria.where("userId").is(criteria.getUserid())), AccountInfoEntity.class);
        return target;
    }


    public Query getQuery(AccountInfoEntity accountInfoEntity) {
        Query query = new Query();

        if (accountInfoEntity == null)
            return query;

        if (accountInfoEntity.getUserid() != null) {
            query.addCriteria(Criteria.where("userId").is(accountInfoEntity.getUserid()));
        }

        return query;
    }

    @Override
    public boolean isExists(String id) {
        long count = getMongoTemplate().count(new Query(Criteria.where("userid").is(id)), AccountInfoEntity.class);
        return count > 0 ? true : false;
    }
}
