package com.perfect.dao;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.perfect.entity.AccountAnalyzeEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;

import java.util.List;


/**
 * Created by baizz on 14-7-25.
 */
public interface AccountAnalyzeDAO extends CrudRepository<KeywordRealTimeDataVOEntity, Long> {
    List<AccountAnalyzeEntity> performance(String userTable);
}
