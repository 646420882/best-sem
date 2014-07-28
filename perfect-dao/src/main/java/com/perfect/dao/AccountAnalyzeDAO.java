package com.perfect.dao;



import com.perfect.entity.KeywordRealTimeDataVOEntity;

import java.util.List;


/**
 * Created by baizz on 14-7-25.
 */
public interface AccountAnalyzeDAO extends CrudRepository<KeywordRealTimeDataVOEntity, Long> {
    List<KeywordRealTimeDataVOEntity> performance(String userTable);
}
