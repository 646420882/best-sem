package com.perfect.db.mongodb.impl;

import com.perfect.dao.WarningInfoDAO;
import com.perfect.dto.WarningInfoDTO;
import com.perfect.entity.WarningInfoEntity;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.utils.mongodb.DBNameUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by john on 2014/8/8.
 * 2014-11-28 refactor XiaoWei
 */
@Repository("warningInfoDAO")
public class WarningInfoDAOImpl implements WarningInfoDAO {

    public void insert(String userName, WarningInfoDTO warningInfoDTO) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getDatabaseName(userName));
        WarningInfoEntity warningInfoEntity=new WarningInfoEntity();
        BeanUtils.copyProperties(warningInfoDTO,warningInfoEntity);
        mongoTemplate.insert(warningInfoEntity, "warning_info");
    }
}
