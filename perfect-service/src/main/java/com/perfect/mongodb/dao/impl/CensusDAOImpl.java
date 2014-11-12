package com.perfect.mongodb.dao.impl;

import com.perfect.dao.CensusDAO;
import com.perfect.dto.ConstantsDTO;
import com.perfect.entity.CensusEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.utils.EntityConstants;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

/**
 * Created by XiaoWei on 2014/11/11.
 */
@Component
public class CensusDAOImpl extends AbstractUserBaseDAOImpl<CensusEntity, Long> implements CensusDAO {
    @Override
    public Class<CensusEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public CensusEntity saveParams(CensusEntity censusEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        mongoTemplate.save(censusEntity, EntityConstants.SYS_CENSUS);
        return censusEntity;
    }

    @Override
    public ConstantsDTO getTodayTotal() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        System.out.println(getTodayStartDate().toString() + ":" + getTodayEndDate().toString());
        Aggregation agg = Aggregation.newAggregation(
                match(Criteria.where("dat").gte(getTodayStartDate()).lte(getTodayEndDate())),
                project("ip"),
                group("ip").count().as("ipCount")
        );
        AggregationResults<ConstantsDTO> aggregationResults = mongoTemplate.aggregate(agg, EntityConstants.SYS_CENSUS, ConstantsDTO.class);
        List<ConstantsDTO> list = new ArrayList<>(aggregationResults.getMappedResults());
        for (ConstantsDTO li : list) {
            System.out.println(li.getIpCount());
        }
        ConstantsDTO constantsDTO = new ConstantsDTO();
//        constantsDTO.setTotalPv((int) mongoTemplate.count(null, CensusEntity.class));
//        constantsDTO.setIpCount();
        return constantsDTO;
    }

    public static Date getTodayStartDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date getTodayEndDate() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    public static Date getLastDayDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DATE,-1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    public static Date getLastWeekDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.DAY_OF_WEEK_IN_MONTH,-1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    public static Date getLastMonthDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }
    public static void main(String[] agrs){
        System.out.println(getLastWeekDate().toLocaleString() + ":" + getTodayEndDate().toLocaleString());
    }

}
