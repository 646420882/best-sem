package com.perfect.db.mongodb.impl;

import com.perfect.dao.SourceDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.count.CensusDTO;
import com.perfect.entity.CensusEntity;
import com.perfect.utils.paging.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/12/1.
 */
@Repository("sourceDao")
public class SourceDAOImpl extends AbstractUserBaseDAOImpl<CensusDTO, String> implements SourceDAO {

    //table
    private final static String TABLE_NAME = "sys_census";

    //时间查询字段
    private final static String DATE_FIELD = "dat";

    //设备查询字段
    private final static String IPERATE = "ope";
    //新老客户表示
    private final static String USERTYPE = "up";

    @Override
    public List<CensusDTO> getSourceAnalysis(List<Date> dates, Integer accessType, Integer userType) {

        Query query = new Query();

        if(accessType.intValue() == 0 && userType.intValue() == 0){
            query.addCriteria(Criteria.where(DATE_FIELD).lte(dates.get(1)).gte(dates.get(0)));
        }else{
            if(accessType.intValue() > 0 && userType.intValue() > 0){
                query.addCriteria(Criteria.where(DATE_FIELD).lte(dates.get(1)).gte(dates.get(0)).and(IPERATE).is(accessType).and(USERTYPE).is(accessType));
            }else{
                if (accessType.intValue() > 0) {
                    query.addCriteria(Criteria.where(DATE_FIELD).lte(dates.get(1)).gte(dates.get(0)).and(IPERATE).is(accessType));
                }
                if (userType.intValue() > 0) {
                    query.addCriteria(Criteria.where(DATE_FIELD).lte(dates.get(1)).gte(dates.get(0)).and(USERTYPE).is(accessType));
                }
            }
        }

        List<CensusEntity> censusEntities = getSysMongoTemplate().find(query, CensusEntity.class, TABLE_NAME);

        List<CensusDTO> censusDTOs = new ArrayList<>();
        for (CensusEntity censusEntity : censusEntities) {
            CensusDTO censusDTO = new CensusDTO();
            BeanUtils.copyProperties(censusEntity, censusDTO);
            censusDTOs.add(censusDTO);
        }
        return censusDTOs;
    }


    @Override
    public Class<CensusDTO> getEntityClass() {
        return CensusDTO.class;
    }

    @Override
    public Class<CensusDTO> getDTOClass() {
        return CensusDTO.class;
    }
}
