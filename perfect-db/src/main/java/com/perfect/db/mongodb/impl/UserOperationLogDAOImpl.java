package com.perfect.db.mongodb.impl;

import com.google.common.collect.Lists;
import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.core.AppContext;
import com.perfect.dao.log.UserOperationLogDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.log.UserOperationLogDTO;
import com.perfect.entity.log.UserOperationLogEntity;
import com.perfect.param.SystemLogParams;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yousheng on 2015/12/13.
 */
@Repository
public class UserOperationLogDAOImpl extends AbstractUserBaseDAOImpl<UserOperationLogDTO, String> implements UserOperationLogDAO {


    public Class<UserOperationLogEntity> getEntityClass() {
        return UserOperationLogEntity.class;
    }

    @Override
    public Class<UserOperationLogDTO> getDTOClass() {
        return UserOperationLogDTO.class;
    }

    @Override
    public PagerInfo queryLog(Long start, Long end, Integer pageNo, Integer pageSize, List<Long> oid, Integer level) {
        Query q = new Query();
        q.addCriteria(Criteria.where(MongoEntityConstants.ACCOUNT_ID).is(AppContext.getAccountId()));
        switch (level) {
            case 3:
                q.addCriteria(Criteria.where(MongoEntityConstants.KEYWORD_ID).in(oid));
                break;
            case 4:
                q.addCriteria(Criteria.where(MongoEntityConstants.CREATIVE_ID).in(oid));
                break;
        }
        int totalCount = getListTotalCount(q);
        PagerInfo p = new PagerInfo(pageNo, pageSize, totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(Lists.newArrayList());
            return p;
        }
        List<UserOperationLogEntity> list = getMongoTemplate().find(q, getEntityClass());
        List<UserOperationLogDTO> dtos = ObjectUtils.convert(list, UserOperationLogDTO.class);
        p.setList(dtos);
        return p;
    }

    @Override
    public PagerInfo queryLog(SystemLogParams slp) {
        Query q = new Query();
        q.addCriteria(Criteria.where("userId").is(AppContext.getAccountId()));
        if (slp != null) {
            if (slp.getStart() != null && slp.getEnd() != null) {
                q.addCriteria(Criteria.where("time").gte(slp.getStart()).lte(slp.getEnd()));
            }
            if (slp.getLevel() != null) {
                if (slp.getLevel() != 0) {
                    switch (slp.getLevel()) {
                        case 1:
                            q.addCriteria(Criteria.where("campgainId").in(slp.getOids()));
                            break;
                        case 2:
                            q.addCriteria(Criteria.where("adgroupdId").in(slp.getOids()));
                            break;
                        default:
                            q.addCriteria(Criteria.where("oid").in(slp.getOids()));
                            break;
                    }
                }
            }
            List<String> pros = slp.getProperty();
            if (pros != null) {
                if (pros.size() > 0) {
                    q.addCriteria(Criteria.where("property").in(slp.getProperty()));
                }
            }
        }
        int totalCount = getListTotalCount(q);
        PagerInfo p = new PagerInfo(slp.getPageNo(), slp.getPageSize(), totalCount);
        q.skip(p.getFirstStation());
        q.limit(p.getPageSize());
        if (totalCount < 1) {
            p.setList(Lists.newArrayList());
            return p;
        }
        List<UserOperationLogEntity> list = getMongoTemplate().find(q, getEntityClass());
        List<UserOperationLogDTO> dtos = ObjectUtils.convert(list, UserOperationLogDTO.class);
        p.setList(dtos);
        return p;
    }

    private int getListTotalCount(Query query) {
        return (int) getMongoTemplate().count(query, MongoEntityConstants.SYS_LOG);
    }

}
