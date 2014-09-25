package com.perfect.service.impl;

import com.perfect.dao.KeywordImDAO;
import com.perfect.entity.KeywordImEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import com.perfect.service.KeywordImService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/22.
 */
@Service
public class KeywordImServiceImpl extends AbstractUserBaseDAOImpl<KeywordImEntity,Long> implements KeywordImService{
    @Resource
    private KeywordImDAO keywordImDAO;
    @Override
    public Class<KeywordImEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public KeywordImEntity findByKwdId(Long kwdId) {
        return keywordImDAO.findByKwdId(kwdId);
    }

    @Override
    public List<KeywordImEntity> findByCgId(String cgId) {
        return keywordImDAO.findByCgId(cgId);
    }

    @Override
    public List<KeywordImEntity> getAll() {
        return keywordImDAO.getAll();
    }

    @Override
    public List<Long> findByAdgroupIds(List<Long> adgroupIds) {
        return keywordImDAO.findByAdgroupIds(adgroupIds);
    }

    @Override
    public List<Long> findByAdgroupId(Long adgroupId) {
        return keywordImDAO.findByAdgroupId(adgroupId);
    }
}
