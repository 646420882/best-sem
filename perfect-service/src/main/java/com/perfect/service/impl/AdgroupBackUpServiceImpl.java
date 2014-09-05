package com.perfect.service.impl;

import com.perfect.dao.AdgroupBackUpDAO;
import com.perfect.dao.AdgroupDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import com.perfect.service.AdgroupBackUpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Service("adgroupBackUpService")
public class AdgroupBackUpServiceImpl extends AbstractUserBaseDAOImpl<AdgroupBackUpEntity,Long> implements AdgroupBackUpService {
    @Resource
    private AdgroupBackUpDAO adgroupBackUpDAO;
    @Resource
    private AdgroupDAO adgroupDAO;
    @Override
    public Class<AdgroupBackUpEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public AdgroupBackUpEntity agReBack(Long id) {
        AdgroupBackUpEntity adgroupBakcUpEntityFind=adgroupBackUpDAO.findByLongId(id);
        if (adgroupBakcUpEntityFind!=null){
          adgroupBakcUpEntityFind.setLocalStatus(null);
            adgroupDAO.insertReBack(adgroupBakcUpEntityFind);
            adgroupBackUpDAO.deleteByLongId(id);
        }
        return adgroupBakcUpEntityFind;
    }
}
