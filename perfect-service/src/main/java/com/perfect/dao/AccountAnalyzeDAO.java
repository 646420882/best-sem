package com.perfect.dao;



import com.perfect.entity.AccountRealTimeDataVOEntity;
import com.perfect.entity.KeywordRealTimeDataVOEntity;

import java.util.Date;
import java.util.List;


/**
 * Created by baizz on 14-7-25.
 */
public interface AccountAnalyzeDAO extends CrudRepository<KeywordRealTimeDataVOEntity, Long> {
    List<KeywordRealTimeDataVOEntity> performance(String userTable);

     /**
     * 获取账户表现数据
     * @return
     */
    List<AccountRealTimeDataVOEntity> performaneUser(Date startDate, Date endDate,String fieldName,int Sorted,int limit);

    /**
     * 获取账户表现数据
     * @return
     */
    List<AccountRealTimeDataVOEntity> performaneCurve(Date startDate, Date endDate);
}
