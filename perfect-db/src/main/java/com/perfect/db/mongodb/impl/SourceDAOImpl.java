package com.perfect.db.mongodb.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.SourceDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.count.CensusDTO;
import com.perfect.dto.count.SourcesAllDTO;
import com.perfect.entity.CensusEntity;
import com.perfect.vo.BasedDataVO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by SubDong on 2014/12/1.
 */
@Repository("sourceDao")
public class SourceDAOImpl extends AbstractUserBaseDAOImpl<CensusDTO, String> implements SourceDAO {



    @Override
    public List<CensusDTO> getSourceAnalysis(List<Date> dates, Integer accessType, Integer userType) {

        Query query = new Query();
        Criteria criteria = getCriteria(dates, accessType, userType);

        query.addCriteria(criteria);

        List<CensusEntity> censusEntities = getSysMongoTemplate().find(query, CensusEntity.class, MongoEntityConstants.SYS_CENSUS);

        List<CensusDTO> censusDTOs = new ArrayList<>();
        for (CensusEntity censusEntity : censusEntities) {
            CensusDTO censusDTO = new CensusDTO();
            BeanUtils.copyProperties(censusEntity, censusDTO);
            censusDTOs.add(censusDTO);
        }
        return censusDTOs;
    }

    @Override
    public SourcesAllDTO getGroupFind(List<Date> dates, Integer accessType, Integer userType) {

        Criteria criteria = getCriteria(dates, accessType, userType);
        SourcesAllDTO sourcesAllDTO = new SourcesAllDTO();
        //获得浏览量
        sourcesAllDTO.setPageviews(getCountNumber(criteria));
        //得到外部连接分组数据
        sourcesAllDTO.setIntoPageData(getIntoPgeCount(criteria));
        //得到搜索引擎分组数据
        sourcesAllDTO.setSearchEngine(getSearchEngine(criteria));
        //得到访客数  数据
        sourcesAllDTO.setVisitors(getVisits(criteria));
        //得到总条数
        sourcesAllDTO.setFindCount(getCountNumber(criteria));
        return sourcesAllDTO;
    }

    @Override
    public List<CensusDTO> getDesignationData(List<Date> dates, Integer accessType, Integer userType,String conditionName, String condition) {
        Query query = new Query();
        Criteria criteria = getCriteria(dates, accessType, userType);
        criteria.and(conditionName).is(condition);
        query.addCriteria(criteria);

        List<CensusEntity> censusEntities = getSysMongoTemplate().find(query, CensusEntity.class, MongoEntityConstants.SYS_CENSUS);

        List<CensusDTO> censusDTOs = new ArrayList<>();
        for (CensusEntity censusEntity : censusEntities) {
            CensusDTO censusDTO = new CensusDTO();
            BeanUtils.copyProperties(censusEntity, censusDTO);
            censusDTOs.add(censusDTO);
        }
        return censusDTOs;
    }


    /**
     * 外部连接分组统计
     * @param criteria
     * @return
     */
    private List<BasedDataVO> getIntoPgeCount(Criteria criteria){
        Aggregation aggregation = Aggregation.newAggregation(
                match(criteria),
                project(MongoEntityConstants.INTOPAGE),
                group(MongoEntityConstants.INTOPAGE).count().as("count")
        );
        //外部连接分组统计
        AggregationResults<BasedDataVO> aggregates = getSysMongoTemplate().aggregate(aggregation, MongoEntityConstants.SYS_CENSUS, BasedDataVO.class);
        List<BasedDataVO> intoPageVOs = new ArrayList<>(aggregates.getMappedResults());
        return intoPageVOs;
    }

    /**
     * 搜索引擎分组统计
     * @param criteria
     * @return
     */
    private List<BasedDataVO> getSearchEngine(Criteria criteria){
        Aggregation aggregation1 = Aggregation.newAggregation(
                match(criteria),
                project(MongoEntityConstants.SEARCHENGINE),
                group(MongoEntityConstants.SEARCHENGINE).count().as("count")
        );
        //搜索引擎分组统计
        AggregationResults<BasedDataVO> engineVOs = getSysMongoTemplate().aggregate(aggregation1, MongoEntityConstants.SYS_CENSUS, BasedDataVO.class);
        List<BasedDataVO> searchEngineVOs = new ArrayList<>(engineVOs.getMappedResults());
        return searchEngineVOs;
    }


    /**
     * 得到UV访问数
     * @param criteria
     * @return
     */
    private int getVisits(Criteria criteria){
        Aggregation aggregation2 = Aggregation.newAggregation(
                match(criteria),
                project(MongoEntityConstants.COOKIE_UUID),
                group(MongoEntityConstants.COOKIE_UUID).count().as("count")
        );
        //访问数
        AggregationResults<BasedDataVO> uidCountResult = getSysMongoTemplate().aggregate(aggregation2, MongoEntityConstants.SYS_CENSUS, BasedDataVO.class);
        List<BasedDataVO> uidCountList = new ArrayList<>(uidCountResult.getMappedResults());
        return uidCountList.size();
    }

    /**
     * 得到查询条件中的 总条数
     * @param criteria
     * @return
     */
    private int getCountNumber(Criteria criteria){
        Query q = new Query();
        q.addCriteria(criteria);
        return (int) getSysMongoTemplate().count(q, MongoEntityConstants.SYS_CENSUS);
    }

    /**
     * 获取查询条件
     * @param dates
     * @param accessType
     * @param userType
     * @return
     */
    private Criteria getCriteria(List<Date> dates,Integer accessType, Integer userType){
        Criteria query = new Criteria();

        if(accessType.intValue() == 0 && userType.intValue() == 0){
            query = new Criteria(MongoEntityConstants.DATE_FIELD).lte(dates.get(1)).gte(dates.get(0));
        }else{
            if(accessType.intValue() > 0 && userType.intValue() > 0){
                query = new Criteria(MongoEntityConstants.DATE_FIELD).lte(dates.get(1)).gte(dates.get(0)).and(MongoEntityConstants.IPERATE).is(accessType).and(MongoEntityConstants.USERTYPE).is(accessType);
            }else{
                if (accessType.intValue() > 0) {
                    query = new Criteria(MongoEntityConstants.DATE_FIELD).lte(dates.get(1)).gte(dates.get(0)).and(MongoEntityConstants.IPERATE).is(accessType);
                }
                if (userType.intValue() > 0) {
                    query = new Criteria(MongoEntityConstants.DATE_FIELD).lte(dates.get(1)).gte(dates.get(0)).and(MongoEntityConstants.USERTYPE).is(accessType);
                }
            }
        }
        return query;
    }


    @Override
    public Class<CensusEntity> getEntityClass() {
        return CensusEntity.class;
    }

    @Override
    public Class<CensusDTO> getDTOClass() {
        return CensusDTO.class;
    }
}