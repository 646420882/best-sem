package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountAnalyzeDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.keyword.KeywordRealDTO;
import com.perfect.entity.account.AccountReportEntity;
import com.perfect.entity.keyword.KeywordRealEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-25.
 * 2014-12-2 refactor
 */
@Repository("accountAnalyzeDAO")
public class AccountAnalyzeDAOImpl extends AbstractUserBaseDAOImpl<AccountReportDTO, Long> implements AccountAnalyzeDAO {

    @Override
    @SuppressWarnings("unchecked")
    public <E> Class<E> getEntityClass() {
        return (Class<E>) AccountReportEntity.class;
    }

    @Override
    public Class<AccountReportDTO> getDTOClass() {
        return AccountReportDTO.class;
    }

    @Override
    public List<AccountReportDTO> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    private Class<KeywordRealEntity> getKeywordRealEntityClass() {
        return KeywordRealEntity.class;
    }

    @Override
    public List<KeywordRealDTO> performance(String userTable) {
        List<KeywordRealEntity> list = getMongoTemplate().findAll(getKeywordRealEntityClass(), userTable);
        return ObjectUtils.convert(list, getEntityClass());
    }

    @Override
    public List<AccountReportDTO> performaneUser(Date startDate, Date endDate) {
        List<AccountReportEntity> list = getMongoTemplate().find(Query.query(Criteria.where("date").gte(startDate).lte(endDate).and(ACCOUNT_ID).is(AppContext.getAccountId())), getEntityClass(), TBL_ACCOUNT_REPORT);
        return ObjectUtils.convert(list, getDTOClass());
    }

    @Override
    public List<AccountReportDTO> performaneCurve(Date startDate, Date endDate) {
        List<AccountReportEntity> list = getMongoTemplate().find(Query.query(Criteria.where("date").gte(startDate).lte(endDate).and(ACCOUNT_ID).is(AppContext.getAccountId())).with(new Sort(Sort.Direction.ASC, "date")), getEntityClass(), TBL_ACCOUNT_REPORT);
        return ObjectUtils.convert(list, getDTOClass());
    }

    @Override
    public List<AccountReportDTO> downAccountCSV() {
        List<AccountReportEntity> list = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())).with(new Sort("date")), getEntityClass(), TBL_ACCOUNT_REPORT);
        return ObjectUtils.convert(list, getDTOClass());
    }
}
