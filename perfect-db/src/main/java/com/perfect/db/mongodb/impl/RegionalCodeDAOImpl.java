package com.perfect.db.mongodb.impl;

import com.perfect.dao.RegionalCodeDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.RegionalCodeDTO;
import com.perfect.dao.utils.Pager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.perfect.commons.constants.RegionalConstants.FIDE_REGIONNAME;
import static com.perfect.commons.constants.RegionalConstants.TBL_SYS_REGIONAL;

/**
 * Created by SubDong on 2014/9/29.
 */
@Repository("regionalCodeDAO")
public class RegionalCodeDAOImpl extends AbstractSysBaseDAOImpl<RegionalCodeDTO, Long> implements RegionalCodeDAO {
    @Override
    public void insertRegionalCode(List<RegionalCodeDTO> redisList) {
        getSysMongoTemplate().insert(redisList, TBL_SYS_REGIONAL);
    }

    @Override
    public List<RegionalCodeDTO> getRegional(String fieldName, String id) {
        List<RegionalCodeDTO> list = getSysMongoTemplate().find(Query.query(Criteria.where(fieldName).is(id)), RegionalCodeDTO.class, TBL_SYS_REGIONAL);
        return list;
    }

    @Override
    public RegionalCodeDTO getRegionalByRegionId(String feidName, String id) {
        List<RegionalCodeDTO> dtos = getSysMongoTemplate().find(new Query(Criteria.where(feidName).is(id).and(FIDE_REGIONNAME).is("")), getEntityClass(), TBL_SYS_REGIONAL);
        return dtos.size() == 0 ? null : dtos.get(0);
    }


    @Override
    public Class<RegionalCodeDTO> getEntityClass() {
        return RegionalCodeDTO.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
