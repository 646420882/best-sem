package com.perfect.db.mongodb.impl;

import com.perfect.dao.FarmDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.UrlDTO;
import com.perfect.entity.sys.UrlEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
@Component("farmDAO")
public class FarmDAOImpl extends AbstractSysBaseDAOImpl<UrlDTO, String> implements FarmDAO {

    @Override
    @SuppressWarnings("unchecked")
    public Class<UrlEntity> getEntityClass() {
        return UrlEntity.class;
    }

    @Override
    public Class<UrlDTO> getDTOClass() {
        return UrlDTO.class;
    }

    @Override
    public List<UrlDTO> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    @Override
    public UrlDTO takeOne() {
        UrlDTO urlDTO = new UrlDTO();
        BeanUtils.copyProperties(getSysMongoTemplate().findAndModify(
                Query.query(
                        Criteria.where("i").is(true)
                                .and("f").lte(System.currentTimeMillis())).limit(1).with(new Sort(Sort.Direction.ASC, "f")),
                Update.update("i", false),
                FindAndModifyOptions.options().returnNew(true),
                getEntityClass()), getDTOClass());
        return urlDTO;
    }

//    @Override
//    public void returnOne(UrlDTO urlDTO) {
//        UrlEntity urlEntity = new UrlEntity();
//        BeanUtils.copyProperties(urlDTO, urlEntity);
//        urlEntity.setIdle(true);
//        getSysMongoTemplate().save(urlEntity);
//    }

    /*
     * 查询最后执行时间在5分钟之前的账号
     */
    @Override
    public List<UrlDTO> allUnused() {
        return ObjectUtils.convert(getSysMongoTemplate()
                .find(Query.query(Criteria.where("f").lte(System.currentTimeMillis() - 5 * 60 * 1000))
                        .with(new Sort(Sort.Direction.ASC, "f")), getEntityClass()), getDTOClass());
    }

}
