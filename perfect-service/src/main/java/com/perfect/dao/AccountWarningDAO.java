package com.perfect.dao;

import com.perfect.entity.WarningRuleEntity;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * Created by john on 2014/8/5.
 */
public interface AccountWarningDAO extends CrudRepository<WarningRuleEntity,Long>{
        List<WarningRuleEntity> find(Query query,Class entityClass);
}
