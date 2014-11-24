package com.perfect.dao.mongodb.impl;

import com.perfect.dao.WarningInfoDAO;
import com.perfect.entity.WarningInfoEntity;
import com.perfect.dao.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.DBNameUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by john on 2014/8/8.
 */
@Repository("warningInfoDAO")
public class WarningInfoDAOImpl implements WarningInfoDAO {

    public void insert(String userName, WarningInfoEntity warningInfoEntity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getDatabaseName(userName));
        mongoTemplate.insert(warningInfoEntity, "warning_info");
    }
}
