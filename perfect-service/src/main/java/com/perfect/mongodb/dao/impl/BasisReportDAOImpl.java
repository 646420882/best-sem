package com.perfect.mongodb.dao.impl;

import com.perfect.dao.BasisReportDAO;
import com.perfect.dto.AccountReportDTO;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<StructureReportEntity> objectList = mongoTemplate.findAll(StructureReportEntity.class, userTable);
        return objectList;
    }

    @Override
    public List<AccountReportDTO> getAccountReport(int Sorted,String fieldName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        Sort sort = null;
        if (Sorted == 0) {
            sort = new Sort(Sort.Direction.ASC, fieldName);
        } else {
            sort = new Sort(Sort.Direction.DESC, fieldName);
        }
        List<AccountReportDTO> reportEntities = mongoTemplate.find(new Query().with(sort), AccountReportDTO.class, "account_report");
        return reportEntities;
    }

    @Override
    public long getAccountCount() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        long account_report = mongoTemplate.count(new Query(), "account_report");
        return account_report;
    }

    @Override
    public List<AccountReportDTO> getAccountReport(Date startDate, Date endDate) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<AccountReportDTO> reportResponses = mongoTemplate.find(Query.query(Criteria.where("date").gte(startDate).lte(endDate)),AccountReportDTO.class, "account_report");
        return reportResponses;
    }

    @Override
    public List<StructureReportEntity> getKeywordReport(Long[] id,String table) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserReportMongo();
        List<StructureReportEntity> entityList = mongoTemplate.find(new Query(Criteria.where("kwid").in(id)),StructureReportEntity.class,table);
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
