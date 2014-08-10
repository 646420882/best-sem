package com.perfect.mongodb.dao.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.BasisReportDAO;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by SubDong on 2014/8/6.
 */
@Repository("basisReportDAO")
public class BasisReportDAOImpl implements BasisReportDAO {
    String currUserName = AppContext.getUser().toString();

    private MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(currUserName);
    @Override
    public List<Object> getReportGenerationDate(Date[] date, String terminal, int reportType) {
        List<Object> objectList = mongoTemplate.find(Query.query(Criteria.where("date").in(date).and("terminal").is(terminal).and("type").is(reportType)),Object.class);
        return objectList;
    }
}
