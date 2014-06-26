package com.perfect.mongodb.dao.impl;

import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.mongodb.dao.AccountDAO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/6/18.
 */
@Repository("accountDAO")
public class AccountDAOImpl extends AbstractBaseDAO<AccountInfoType> implements AccountDAO {

    @Override
    public void insert(AccountInfoType accountInfoType) {
        getMongoTemplate().insert(accountInfoType);
    }

    @Override
    public void delete(AccountInfoType criteria) {
        getMongoTemplate().remove(criteria);
    }

    @Override
    public void deleteAll() {
        getMongoTemplate().dropCollection(AccountInfoType.class);
    }

    @Override
    public void updateById(AccountInfoType accountInfoType) {
        Criteria criteria = Criteria.where("userid").is(accountInfoType.getUserid());
        Query query = new Query(criteria);
        getMongoTemplate().upsert(query, getUpdate(accountInfoType), AccountInfoType.class);
    }

    @Override
    public void update(AccountInfoType src, AccountInfoType dest) {
        BeanUtils.copyProperties(src, dest);
        getMongoTemplate().save(dest);
    }

    @Override
    public AccountInfoType findById(String id) {
        return getMongoTemplate().findById(id, AccountInfoType.class);
    }

    @Override
    public List<AccountInfoType> findAll() {
        return getMongoTemplate().findAll(AccountInfoType.class);
    }

    @Override
    public long count() {
        return -1;
    }

    @Override
    public List<AccountInfoType> find(AccountInfoType criteria, int skip, int limit) {
        Query query = getQuery(criteria);
        query.skip(skip).limit(limit);

        return getMongoTemplate().find(query, AccountInfoType.class);
    }

    @Override
    public AccountInfoType findAndModify(AccountInfoType criteria, AccountInfoType update) {
        AccountInfoType target = getMongoTemplate().findAndModify(new Query(Criteria.where("userId").is(criteria.getUserid())), getUpdate(update), AccountInfoType.class);
        return target;
    }

    private Update getUpdate(AccountInfoType update) {
        return Update.update("balance", update.getBalance()).set("cost", update.getCost()).set("payment", update.getPayment()).set("budgetType", update.getBudgetType()).set("budget", update.getBudget()).set("regionTarget", update.getRegionTarget()).set("excludeIp", update.getExcludeIp()).set("openDomains", update.getOpenDomains()).set("regDomain", update.getRegDomain()).set("budgetOfflineTime", update.getBudgetOfflineTime()).set("weeklyBudget", update.getWeeklyBudget()).set("userStat", update.getUserStat());
    }

    @Override
    public AccountInfoType findAndRemove(AccountInfoType criteria) {
        AccountInfoType target = getMongoTemplate().findAndRemove(new Query(Criteria.where("userId").is(criteria.getUserid())), AccountInfoType.class);
        return target;
    }


    public Query getQuery(AccountInfoType accountInfoType) {
        Query query = new Query();

        if (accountInfoType == null)
            return query;

        if (accountInfoType.getUserid() != null) {
            query.addCriteria(Criteria.where("userId").is(accountInfoType.getUserid()));
        }

        return query;
    }

}
