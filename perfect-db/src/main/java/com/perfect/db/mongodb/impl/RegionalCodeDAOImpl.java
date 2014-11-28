package com.perfect.db.mongodb.impl;

import com.perfect.dao.RegionalCodeDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.regional.RegionalCodeDTO;
import com.perfect.entity.RegionalCodeEntity;
import com.perfect.ObjectUtils;
import com.perfect.paging.Pager;
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
        List<RegionalCodeEntity> regionalCodeEntities = ObjectUtils.convert(redisList, RegionalCodeEntity.class);
        getSysMongoTemplate().insert(regionalCodeEntities, TBL_SYS_REGIONAL);
    }

    @Override
    public List<RegionalCodeDTO> getRegional(String fieldName, String id) {
        List<RegionalCodeEntity> list = getSysMongoTemplate().find(Query.query(Criteria.where(fieldName).is(id)), RegionalCodeEntity.class, TBL_SYS_REGIONAL);
        List<RegionalCodeDTO> regionalCodeDTOs = ObjectUtils.convert(list, getEntityClass());
        return regionalCodeDTOs;
    }

    @Override
    public RegionalCodeDTO getRegionalByRegionId(String feidName, String id) {
        List<RegionalCodeEntity> dtos = getSysMongoTemplate().find(new Query(Criteria.where(feidName).is(id).and(FIDE_REGIONNAME).is("")), RegionalCodeEntity.class, TBL_SYS_REGIONAL);

        List<RegionalCodeDTO> codeDTOs = ObjectUtils.convert(dtos, getEntityClass());

        return codeDTOs.size() == 0 ? null : codeDTOs.get(0);
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
