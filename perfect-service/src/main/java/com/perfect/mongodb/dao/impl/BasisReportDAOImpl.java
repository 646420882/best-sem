package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.BasisReportDAO;
import com.perfect.entity.StructureReportEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

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
    public List<StructureReportEntity> getKeywordsReportDate(String userTable) {
        List<StructureReportEntity> objectList = mongoTemplate.findAll(StructureReportEntity.class, userTable);
        return objectList;
    }

    @Override
    public List<StructureReportEntity> getCreativeReportDate(String userTable) {
        List<StructureReportEntity> objectList = mongoTemplate.findAll(StructureReportEntity.class,userTable);
        return objectList;
    }

    @Override
    public List<StructureReportEntity> getRegionalReportDate(String userTable) {
        List<StructureReportEntity> objectList = mongoTemplate.findAll(StructureReportEntity.class, userTable);
        return objectList;
    }
}
