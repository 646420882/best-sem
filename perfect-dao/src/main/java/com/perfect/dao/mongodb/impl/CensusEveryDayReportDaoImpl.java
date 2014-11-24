package com.perfect.dao.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.CensusEveryDayReportDao;
import com.perfect.dto.ViewsDTO;
import com.perfect.entity.CensusEntity;
import com.perfect.entity.CensusEveryDayReportEntity;
import com.perfect.dao.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dao.mongodb.utils.EntityConstants;
import com.perfect.dao.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by john on 2014/11/17.
 */
@Repository("censusEveryDayReportDao")
public class CensusEveryDayReportDaoImpl extends AbstractSysBaseDAOImpl implements CensusEveryDayReportDao {



    public List<ViewsDTO> getGroupLastPageByDate(Date date){
        MongoTemplate mongoTemplate = getSysMongoTemplate();
        Query q = new Query();
        Criteria c = new Criteria("dat").gte(getStartByDate(date)).lte(getEndByDate(date));
        q.addCriteria(c);

        Aggregation agg = Aggregation.newAggregation(
                match(c),
                project("lp"),
                group("lp")
        );

        AggregationResults<ViewsDTO> results = mongoTemplate.aggregate(agg, MongoEntityConstants.SYS_CENSUS, ViewsDTO.class);
        List<ViewsDTO> list = results.getMappedResults();

        return list;
    }

    public long getCensusCount(Date date, String lastPage,String database_Field) {
        MongoTemplate mongoTemplate = getSysMongoTemplate();
        Query q = new Query();
        Criteria c = new Criteria("dat").gte(getStartByDate(date)).lte(getEndByDate(date)).and("lp").is(lastPage);
        q.addCriteria(c);

        Aggregation aggre = null;
        if("ip".equals(database_Field)){
            aggre = Aggregation.newAggregation(
                    match(c),
                    project("ip"),
                    group("ip").count().as("count")
            );
        }else if("uid".equals(database_Field)){
            aggre = Aggregation.newAggregation(
                    match(c),
                    project("uid"),
                    group("uid").count().as("count")
            );
        }


        long count;
        if(aggre!=null){
            AggregationResults<ViewsDTO> results = mongoTemplate.aggregate(aggre, MongoEntityConstants.SYS_CENSUS, ViewsDTO.class);
            count = results.getMappedResults().size();
        }else{
            count = mongoTemplate.count(q,MongoEntityConstants.SYS_CENSUS);
        }

        return count;
    }



    public void insertList(List<CensusEveryDayReportEntity> list){
        MongoTemplate mongoTemplate = getSysMongoTemplate();
        mongoTemplate.insertAll(list);
    }


    public List<CensusEntity> getCensus(){
        MongoTemplate mongoTemplate = getSysMongoTemplate();
        return mongoTemplate.find(new Query(Criteria.where("sys").is("Windows 7")),CensusEntity.class,MongoEntityConstants.SYS_CENSUS);
    }



    /**
     * 得到某天（date）的开始时间
     *
     * @param date
     * @return
     */
    private Date getStartByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        return calendar.getTime();
    }


    /**
     * 得到某天(date)的最末的时间
     *
     * @param date
     * @return
     */
    private Date getEndByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
        return calendar.getTime();
    }


    @Override
    public Class getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map q, int orderBy) {
        return null;
    }


}
