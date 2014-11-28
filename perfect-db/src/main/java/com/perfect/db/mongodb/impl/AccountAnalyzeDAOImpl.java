package com.perfect.db.mongodb.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountAnalyzeDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.keyword.KeywordRealDTO;
import com.perfect.entity.account.AccountReportEntity;
import com.perfect.entity.keyword.KeywordRealEntity;
import com.perfect.ObjectUtils;
import com.perfect.paging.Pager;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.perfect.commons.constants.MongoEntityConstants.ACCOUNT_ID;
import static com.perfect.commons.constants.MongoEntityConstants.TBL_ACCOUNT_REPORT;

/**
 * Created by baizz on 2014-7-25.
 * 2014-11-24 refactor
 */
@Repository("accountAnalyzeDAO")
public class AccountAnalyzeDAOImpl extends AbstractUserBaseDAOImpl<KeywordRealDTO, Long> implements AccountAnalyzeDAO {

    @Override
    public Class<KeywordRealDTO> getEntityClass() {
        return KeywordRealDTO.class;
    }

    public Class<KeywordRealEntity> getKeywordRealEntityClass() {
        return KeywordRealEntity.class;
    }

    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public List<KeywordRealDTO> performance(String userTable) {
        List<KeywordRealEntity> list = getMongoTemplate().findAll(getKeywordRealEntityClass(), userTable);

        List<KeywordRealDTO> keywordRealDTOs = ObjectUtils.convert(list,getEntityClass());
        return keywordRealDTOs;
    }

    @Override
    public List<AccountReportDTO> performaneUser(Date startDate, Date endDate) {
        List<AccountReportEntity> list = getMongoTemplate().find(Query.query(Criteria.where("date").gte(startDate).lte(endDate).and(ACCOUNT_ID).is(AppContext.getAccountId())), AccountReportEntity.class, TBL_ACCOUNT_REPORT);
        List<AccountReportDTO> accountReportDTOs = ObjectUtils.convert(list,AccountReportDTO.class);
        return accountReportDTOs;
    }

    @Override
    public List<AccountReportDTO> performaneCurve(Date startDate, Date endDate) {
        List<AccountReportEntity> list = getMongoTemplate().find(Query.query(Criteria.where("date").gte(startDate).lte(endDate).and(ACCOUNT_ID).is(AppContext.getAccountId())).with(new Sort(Sort.Direction.ASC, "date")), AccountReportEntity.class, TBL_ACCOUNT_REPORT);

        List<AccountReportDTO> accountReportDTOs = ObjectUtils.convert(list,AccountReportDTO.class);
        return accountReportDTOs;
    }

    @Override
    public List<AccountReportDTO> downAccountCSV() {
        List<AccountReportEntity> list = getMongoTemplate().find(Query.query(Criteria.where(ACCOUNT_ID).is(AppContext.getAccountId())).with(new Sort("date")), AccountReportEntity.class, TBL_ACCOUNT_REPORT);

        List<AccountReportDTO> accountReportDTOs = ObjectUtils.convert(list,AccountReportDTO.class);
        return accountReportDTOs;
    }
}
