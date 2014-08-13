package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.BasisReportDAO;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    public List<Object> getUnitReportDate(String terminal, String userTable) {
        List<Object> objectList = mongoTemplate.find(Query.query(Criteria.where("terminal").is(terminal)),Object.class,userTable);
        return objectList;
    }

    @Override
    public List<Object> getKeywordsReportDate(String terminal, String userTable) {
        List<Object> objectList = mongoTemplate.find(Query.query(Criteria.where("terminal").is(terminal)),Object.class,userTable);
        return objectList;
    }

    @Override
    public List<Object> getCreativeReportDate(String terminal, String userTable) {
        List<Object> objectList = mongoTemplate.find(Query.query(Criteria.where("terminal").is(terminal)),Object.class,userTable);
        return objectList;
    }

    @Override
    public List<Object> getRegionalReportDate(String terminal, String userTable) {
        List<Object> objectList = mongoTemplate.find(Query.query(Criteria.where("terminal").is(terminal)),Object.class,userTable);
        return objectList;
    }
}
