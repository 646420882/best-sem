package com.perfect.mongodb.dao.impl;

import com.perfect.dao.RegionalCodeDAO;
import com.perfect.dto.RegionalCodeDTO;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/9/29.
 */
@Repository("regionalCodeDAO")
public class RegionalCodeDAOImpl extends AbstractSysBaseDAOImpl<RegionalCodeDTO,Long> implements RegionalCodeDAO {
    @Override
    public void insertRegionalCode(List<RegionalCodeDTO> redisList) {
        getSysMongoTemplate().insert(redisList,"sys_regional");
    }

    @Override
    public List<RegionalCodeDTO> getRegional(String FieldName,String id) {
        List<RegionalCodeDTO> list = getSysMongoTemplate().find(Query.query(Criteria.where(FieldName).is(id)),RegionalCodeDTO.class,"sys_regional");
        return list;
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
