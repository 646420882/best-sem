package com.perfect.mongodb.dao.impl;

import com.perfect.dao.AccountWarningDAO;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/8/5.
 */
@Repository("accountWarningDAO")
public class AccountWarningDAOImpl extends AbstractSysBaseDAOImpl<WarningRuleEntity, Long>  implements AccountWarningDAO {

    @Override
    public WarningRuleEntity findOne(Long aLong) {
        return null;
    }

    @Override
    public List<WarningRuleEntity> findAll() {
        return getSysMongoTemplate().findAll(WarningRuleEntity.class, "sys_warning");
    }

    @Override
    public List<WarningRuleEntity> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    public List<WarningRuleEntity> findEnableIsOne() {
        return getSysMongoTemplate().find(new Query(Criteria.where("isEnable").is(1)), getEntityClass(), "sys_warning");
    }

    @Override
    public Class<WarningRuleEntity> getEntityClass() {
        return WarningRuleEntity.class;
    }

    @Override
    public void insert(WarningRuleEntity warningRuleEntity) {
        getSysMongoTemplate().insert(warningRuleEntity, "sys_warning");
    }

    @Override
    public void insertAll(List<WarningRuleEntity> entities) {

    }

    @Override
    public void update(WarningRuleEntity warningRuleEntity) {
        if (warningRuleEntity == null) {
            return;
        }
           Query query = new Query();
            query.addCriteria(Criteria.where("id").is(warningRuleEntity.getId()));
            Class entityClass = warningRuleEntity.getClass();
            Field[] fields = entityClass.getDeclaredFields();

            Update update = new Update();

            for (Field field : fields) {
                String fiedName = field.getName();
                if(fiedName.equals("id")){
                    continue;
                }

                StringBuffer getterName = new StringBuffer("get");
                getterName.append(fiedName.substring(0, 1).toUpperCase()).append(fiedName.substring(1));
                try {
                    Method method = entityClass.getDeclaredMethod(getterName.toString());
                    Object obj = method.invoke(warningRuleEntity);
                    if (obj != null) {
                        update.set(fiedName, obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            getSysMongoTemplate().updateFirst(query, update, entityClass, "sys_warning");

        }



    @Override
    public  void updateMulti(Query query,Update update){
        getSysMongoTemplate().updateMulti(query,update,WarningRuleEntity.class,"sys_warning");
    }


    /**
     * 根据预警规则启用状态和当天预警状态查询
     * @param isEnable
     * @param isWarninged
     * @return
     */
    public List<WarningRuleEntity> findWarningRule(int isEnable, int isWarninged){
        return getSysMongoTemplate().find(new Query(Criteria.where("isEnable").is(isEnable).and("isWarninged").is(isWarninged)),getEntityClass(),"sys_warning");
    }

    public Iterable<WarningRuleEntity> findByUserName(String user){
        MongoTemplate mongoTemplate = getSysMongoTemplate();
        return mongoTemplate.find(Query.query(Criteria.where("sysUserName").is(user)),getEntityClass(),"sys_warning");
    }



    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }
}

