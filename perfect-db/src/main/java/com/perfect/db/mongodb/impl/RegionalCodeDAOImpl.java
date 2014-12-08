package com.perfect.db.mongodb.impl;

import com.perfect.dao.RegionalCodeDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.regional.RegionalCodeDTO;
import com.perfect.entity.RegionalCodeEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.perfect.commons.constants.RegionalConstants.*;

/**
 * Created by SubDong on 2014/9/29.
 */
@Repository("regionalCodeDAO")
public class RegionalCodeDAOImpl extends AbstractSysBaseDAOImpl<RegionalCodeDTO, Long> implements RegionalCodeDAO {
    @Override
    public void insertRegionalCode(List<RegionalCodeDTO> redisList) {
        List<RegionalCodeEntity> regionalCodeEntities = ObjectUtils.convert(redisList, getEntityClass());
        getSysMongoTemplate().insert(regionalCodeEntities, TBL_SYS_REGIONAL);
    }

    @Override
    public List<RegionalCodeDTO> getRegional(String fieldName, String id) {
        List<RegionalCodeEntity> list = getSysMongoTemplate().find(Query.query(Criteria.where(fieldName).is(id)), getEntityClass(), TBL_SYS_REGIONAL);
        List<RegionalCodeDTO> regionalCodeDTOs = ObjectUtils.convert(list, getDTOClass());
        return regionalCodeDTOs;
    }

    @Override
    public RegionalCodeDTO getRegionalByRegionId(String feidName, String id) {
        List<RegionalCodeEntity> dtos = getSysMongoTemplate().find(new Query(Criteria.where(feidName).is(id).and(FIDE_REGIONNAME).is("")), getEntityClass(), TBL_SYS_REGIONAL);

        List<RegionalCodeDTO> codeDTOs = ObjectUtils.convert(dtos, getDTOClass());

        return codeDTOs.size() == 0 ? null : codeDTOs.get(0);
    }

    @Override
    public String getProvinceNameById(Integer provinceId) {
        String fideName = FIDE_PROVINCEID;
        RegionalCodeEntity entity = getSysMongoTemplate().findOne(new Query(Criteria.where(fideName).is(provinceId.toString()).and(FIDE_REGIONNAME).is("")), getEntityClass(), TBL_SYS_REGIONAL);
        String name;
        if(entity != null){
            name = entity.getProvinceName();
        }else{
            name = "北京";
        }
        return name;
    }

    @Override
    public String getRegionNameById(Integer regionId) {
        String fideName = FIDE_REGIONID;
        RegionalCodeEntity entity = getSysMongoTemplate().findOne(new Query(Criteria.where(fideName).is(regionId.toString()).and(FIDE_REGIONNAME).is("")), getEntityClass(), TBL_SYS_REGIONAL);
        String name;
        if(entity != null){
            name = entity.getRegionName();
        }else{
            name = "北京";
        }
        return name;
    }


    @Override
    public Class<RegionalCodeEntity> getEntityClass() {
        return RegionalCodeEntity.class;
    }


    @Override
    public Class<RegionalCodeDTO> getDTOClass() {
        return RegionalCodeDTO.class;
    }
}
