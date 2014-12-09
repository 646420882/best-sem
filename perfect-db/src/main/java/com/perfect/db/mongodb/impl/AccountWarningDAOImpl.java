package com.perfect.db.mongodb.impl;

import com.perfect.utils.ObjectUtils;
import com.perfect.dao.account.AccountWarningDAO;
import com.perfect.db.mongodb.base.AbstractSysBaseDAOImpl;
import com.perfect.dto.WarningRuleDTO;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.utils.paging.Pager;
import org.springframework.beans.BeanUtils;
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
 * 2014-11-28 refactor XiaoWei
 */
@Repository("accountWarningDAO")
public class AccountWarningDAOImpl extends AbstractSysBaseDAOImpl<WarningRuleDTO, Long> implements AccountWarningDAO {

    @Override
    public Class<WarningRuleDTO> getDTOClass() {
        return WarningRuleDTO.class;
    }

    @Override
    public WarningRuleDTO findOne(Long aLong) {
        return null;
    }

    @Override
    public List<WarningRuleDTO> findAll() {
        List<WarningRuleEntity> warningRuleEntities = getSysMongoTemplate().findAll(WarningRuleEntity.class, "sys_warning");
        return ObjectUtils.convert(warningRuleEntities, WarningRuleDTO.class);
    }

    @Override
    public List<WarningRuleDTO> find(Map<String, Object> params, int skip, int limit) {
        return null;
    }

    public List<WarningRuleDTO> findEnableIsOne() {
        List<WarningRuleEntity> warningRuleEntityList = getSysMongoTemplate().find(new Query(Criteria.where("isEnable").is(1)), WarningRuleEntity.class, "sys_warning");
        return ObjectUtils.convert(warningRuleEntityList, WarningRuleDTO.class);
    }

    @Override
    public Class<WarningRuleEntity> getEntityClass() {
        return WarningRuleEntity.class;
    }


    @Override
    public void update(WarningRuleDTO warningRuleEntity) {
        if (warningRuleEntity == null) {
            return;
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(warningRuleEntity.getId()));
        Class entityClass = WarningRuleEntity.class;
        Field[] fields = entityClass.getDeclaredFields();

        Update update = new Update();

        for (Field field : fields) {
            String fiedName = field.getName();
            if (fiedName.equals("id")) {
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


    public void updateMulti(Query query, Update update) {
        getSysMongoTemplate().updateMulti(query, update, WarningRuleEntity.class, "sys_warning");
    }


    /**
     * 根据预警规则启用状态和当天预警状态查询
     *
     * @param isEnable
     * @param isWarninged
     * @return
     */
    public List<WarningRuleDTO> findWarningRule(int isEnable, int isWarninged) {
        List<WarningRuleEntity> warningRuleEntityList = getSysMongoTemplate().find(new Query(Criteria.where("isEnable").is(isEnable).and("isWarninged").is(isWarninged)), WarningRuleEntity.class, "sys_warning");
        return ObjectUtils.convert(warningRuleEntityList, WarningRuleDTO.class);
    }

    public Iterable<WarningRuleDTO> findByUserName(String user) {
        MongoTemplate mongoTemplate = getSysMongoTemplate();
        List<WarningRuleEntity> warningRuleEntityList = mongoTemplate.find(Query.query(Criteria.where("sysUserName").is(user)), WarningRuleEntity.class, "sys_warning");
        return ObjectUtils.convert(warningRuleEntityList, WarningRuleDTO.class);
    }

    @Override
    public void mySave(WarningRuleDTO warningRuleDTO) {
        WarningRuleEntity warningRuleEntity = new WarningRuleEntity();
        BeanUtils.copyProperties(warningRuleDTO, warningRuleEntity);
        getMongoTemplate().save(warningRuleEntity, "sys_warning");
    }

}

