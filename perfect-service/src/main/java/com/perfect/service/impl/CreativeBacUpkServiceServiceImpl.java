package com.perfect.service.impl;

import com.perfect.dao.CreativeBackUpDAO;
import com.perfect.dao.CreativeDAO;
import com.perfect.entity.CreativeEntity;
import com.perfect.entity.backup.CreativeBackUpEntity;
import com.perfect.dao.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dao.mongodb.utils.Pager;
import com.perfect.service.CreativeBackUpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Service("creativeBackUpService")
public class CreativeBacUpkServiceServiceImpl extends AbstractUserBaseDAOImpl<CreativeBackUpEntity, Long> implements CreativeBackUpService {
    @Resource
    private CreativeBackUpDAO creativeBackUpDAO;
    @Resource
    private CreativeDAO creativeDAO;

    @Override
    public Class<CreativeBackUpEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public CreativeBackUpEntity findByStringId(String id) {
        return creativeBackUpDAO.findByStringId(id);
    }

    @Override
    public CreativeBackUpEntity findByLongId(Long crid) {
        return creativeBackUpDAO.findByLongId(crid);
    }

    @Override
    public void deleteByLongId(Long crid) {
        creativeBackUpDAO.deleteByLongId(crid);
    }

    @Override
    public CreativeBackUpEntity reBack(Long crid) {
        CreativeBackUpEntity creativeBackUpEntityFind = creativeBackUpDAO.findByLongId(crid);
        if (creativeBackUpEntityFind != null) {
            creativeBackUpEntityFind.setLocalStatus(null);
            CreativeEntity creativeEntity = new CreativeEntity();
            BeanUtils.copyProperties(creativeBackUpEntityFind, creativeEntity);
            creativeDAO.insertByReBack(creativeEntity);
            creativeBackUpDAO.deleteByLongId(crid);
        }
        return creativeBackUpEntityFind;
    }
}
