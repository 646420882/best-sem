package com.perfect.db.mongodb.impl;

import com.perfect.dao.WarningInfoDAO;
import com.perfect.entity.WarningInfoEntity;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.DBNameUtils;
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
