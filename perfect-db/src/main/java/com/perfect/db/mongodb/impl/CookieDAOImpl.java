package com.perfect.db.mongodb.impl;

import com.perfect.dao.CookieDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dao.utils.Pager;
import com.perfect.entity.CookieEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-10.
 * 2014-11-24 refactor
 */
@Repository("cookieDAO")
public class CookieDAOImpl extends AbstractSysBaseDAOImpl<CookieEntity, String> implements CookieDAO {

//    public static final Set<String> set = new HashSet<String>() {{
//        addAll(Arrays.asList("CASSSID", "GBIZSSID", "GIMGSSID", "LOGINAID", "LOGINUID", "__cas__id__", "__cas__st__", "bdsfuid"));
//    }};

    @Override
    public Class<CookieEntity> getEntityClass() {
        return CookieEntity.class;
    }

    @Override
    public CookieEntity takeOne() {
        return getSysMongoTemplate().findAndModify(
                Query.query(
                        Criteria.where("i").is(true)
                                .and("f").lte(System.currentTimeMillis())).limit(1).with(new Sort(Sort.Direction.ASC, "f")),
                Update.update("i", false),
                FindAndModifyOptions.options().returnNew(true),
                getEntityClass());
    }

    @Override
    public void returnOne(CookieEntity cookieEntity) {
        cookieEntity.setIdle(true);
        getMongoTemplate().save(cookieEntity);
    }

    @Override
    /**
     * 查询最后执行时间在5分钟之前的账号
     */
    public List<CookieEntity> allUnused() {
        return getSysMongoTemplate()
                .find(Query.query(Criteria.where("f").lte(System.currentTimeMillis() - 5 * 60 * 1000))
                        .with(new Sort(Sort.Direction.ASC, "f")), getEntityClass());
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}
