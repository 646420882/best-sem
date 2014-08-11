package com.perfect.mongodb.dao.impl;

import com.perfect.dao.CSVKeywordDAO;
import com.perfect.entity.CSVEntity;
import com.perfect.mongodb.utils.BaseMongoTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by XiaoWei on 2014/8/11.
 */
@Repository("cSVKeywordDAO")
public class CSVKeywordDAOImpl implements CSVKeywordDAO {
    MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate("user_perfect");
    @Override
    public void insertAll(List<CSVEntity> csvEntityList) {
        mongoTemplate.insertAll(csvEntityList);
    }
}
