package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.BasisReportDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.AccountReportResponse;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/8/6.
 */
@Repository("basisReportDAO")
public class BasisReportDAOImpl implements BasisReportDAO {
    String currUserName = AppContext.getUser().toString();

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(
            DBNameUtils.getUserDBName(AppContext.getUser().toString(), "report"));

    @Override
    public List<StructureReportEntity> getUnitReportDate(String userTable) {
        List<StructureReportEntity> objectList = mongoTemplate.findAll(StructureReportEntity.class, userTable);
        return objectList;
    }

    @Override
    public List<AccountReportResponse> getAccountReport(int Sorted,String fieldName) {
        Sort sort = null;
        if (Sorted == 0) {
            sort = new Sort(Sort.Direction.ASC, fieldName);
        } else {
            sort = new Sort(Sort.Direction.DESC, fieldName);
        }
        List<AccountReportResponse> reportEntities = mongoTemplate.find(new Query().with(sort), AccountReportResponse.class, "account_report");
        return reportEntities;
    }

    @Override
    public long getAccountCount() {
        long account_report = mongoTemplate.count(new Query(), "account_report");
        return account_report;
    }

    @Override
    public List<AccountReportResponse> getAccountReport(Date startDate, Date endDate) {
        return null;
    }

}
