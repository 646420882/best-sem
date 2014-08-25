package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.BasisReportDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.AccountReportResponse;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/8/6.
 */
@Repository("basisReportDAO")

public class BasisReportDAOImpl extends AbstractUserBaseDAOImpl<StructureReportEntity,Long> implements BasisReportDAO {

    @Override
    public List<StructureReportEntity> getUnitReportDate(String userTable) {
        List<StructureReportEntity> objectList = getMongoTemplate().findAll(StructureReportEntity.class, userTable);
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
        List<AccountReportResponse> reportEntities = getMongoTemplate().find(new Query().with(sort), AccountReportResponse.class, "account_report");
        return reportEntities;
    }

    @Override
    public long getAccountCount() {
        long account_report = getMongoTemplate().count(new Query(), "account_report");
        return account_report;
    }

    @Override
    public List<AccountReportResponse> getAccountReport(Date startDate, Date endDate) {
        List<AccountReportResponse> reportResponses = getMongoTemplate().find(Query.query(Criteria.where("date").gte(startDate).lte(endDate)),AccountReportResponse.class, "account_report");
        return reportResponses;
    }

    @Override
    public List<StructureReportEntity> getKeywordReport(Long[] id,String table) {
        List<StructureReportEntity> entityList = getMongoTemplate().find(new Query(Criteria.where("kwid").in(id)),StructureReportEntity.class,table);
        return  entityList;
    }

    @Override
    public Class<StructureReportEntity> getEntityClass() {
        return StructureReportEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
