package com.perfect.service.impl;

import com.perfect.dao.KeyWordBackUpDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.entity.KeywordEntity;
import com.perfect.entity.backup.KeyWordBackUpEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import com.perfect.service.KeyWordBackUpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/9.
 */
@Service
public class KeyWordBackUpServiceImpl extends AbstractUserBaseDAOImpl<KeyWordBackUpEntity,Long> implements KeyWordBackUpService {

    @Resource
     private KeyWordBackUpDAO keyWordBackUpDAO;

    @Resource
    private KeywordDAO keywordDAO;


    @Override
    public Class<KeyWordBackUpEntity> getEntityClass() {
        return KeyWordBackUpEntity.class;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }


    @Override
    public void insertAll(List<KeyWordBackUpEntity> entities) {
        for(KeyWordBackUpEntity tempKwdBack:entities){
            boolean exists = keyWordBackUpDAO.existsByObjectId(tempKwdBack.getId());
            if (exists == false) {
                keyWordBackUpDAO.insert(tempKwdBack);
            }
        }
    }

    /**
     * 还原修改的关键词
     * @param id
     */
    public KeywordEntity reducUpdate(String id){

        if(id.matches("^\\d+$")==true){
            KeywordEntity keywordEntity = new KeywordEntity();
            KeyWordBackUpEntity keywordBackEntity = keyWordBackUpDAO.findById(Long.parseLong(id));
            BeanUtils.copyProperties(keywordBackEntity, keywordEntity);
            keywordEntity.setLocalStatus(null);
            keywordDAO.save(keywordEntity);
            keyWordBackUpDAO.deleteByKwid(Long.parseLong(id));
            return keywordEntity;
        }else{
            KeywordEntity keywordEntity = keywordDAO.findByObjectId(id);
            keywordDAO.deleteById(id);
            return keywordEntity;
        }
    }


    /**
     * 还原软删除
     * @param id
     * @return
     */
    public void reducDel(String id){
        if(id.matches("^\\d+$")){
            keywordDAO.updateLocalstatu(Long.parseLong(id));
        }
    }

}
