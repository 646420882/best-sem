package com.perfect.db.mongodb.impl;

import com.perfect.dao.report.GetAccountReportDAO;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.entity.account.AccountReportEntity;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.mongodb.DBNameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.perfect.commons.constants.MongoEntityConstants.ACCOUNT_ID;
import static com.perfect.commons.constants.MongoEntityConstants.TBL_ACCOUNT_REPORT;

/**
 * Created by john on 2014/8/8.
 * 2014-11-24 refactor
 */
@Component("getAccountReportDAO")
public class GetAccountReportDAOImpl implements GetAccountReportDAO {

    /**
     * 得到本地的数据报告（数据来自本地）
     *
     * @param startDate
     * @return
     */
    public AccountReportDTO getLocalAccountRealData(String userName, long accountId, Date startDate, Date endDate) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getReportDBName(userName));
        List<AccountReportEntity> list = mongoTemplate.find(Query.query(Criteria.where(ACCOUNT_ID).is(accountId).and("date").gte(startDate).lte(endDate)).with(new Sort(Sort.Direction.DESC, "date")), AccountReportEntity.class, TBL_ACCOUNT_REPORT);
        AccountReportEntity accountReportEntity= list.size() == 0 ? null : list.get(0);

        AccountReportDTO accountReportDTO = ObjectUtils.convert(accountReportEntity, AccountReportDTO.class);

        return accountReportDTO;
    }
}
