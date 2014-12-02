package com.perfect.service.impl;

import com.perfect.commons.constants.MongoEntityConstants;
import com.perfect.dao.keyword.KeyWordBackUpDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.backup.KeyWordBackUpDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.KeyWordBackUpService;
import com.perfect.utils.paging.Pager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/9.
 * 2014-11-26 refactor
 */
@Service
public class KeyWordBackUpServiceImpl implements KeyWordBackUpService {

    @Resource
     private KeyWordBackUpDAO keyWordBackUpDAO;

    @Resource
    private KeywordDAO keywordDAO;




    public void insertAll(List<KeyWordBackUpDTO> entities) {
        for(KeyWordBackUpDTO tempKwdBack:entities){
            boolean exists = keyWordBackUpDAO.existsByObjectId(tempKwdBack.getId());
            if (exists == false) {
                keyWordBackUpDAO.save(tempKwdBack);
            }
        }
    }

    /**
     * 还原修改的关键词
     * @param id
     */
    public KeywordDTO reducUpdate(String id){

        if(id.matches("^\\d+$")==true){
            KeywordDTO keywordEntity = new KeywordDTO();
            KeyWordBackUpDTO keyWordBackUpDTOFind = keyWordBackUpDAO.findById(Long.parseLong(id));
            BeanUtils.copyProperties(keyWordBackUpDTOFind, keywordEntity);
            keywordEntity.setLocalStatus(null);
            keywordDAO.save(keywordEntity);
            keyWordBackUpDAO.deleteByKwid(Long.parseLong(id));
            KeywordDTO keywordDTO=new KeywordDTO();
            BeanUtils.copyProperties(keywordEntity,keywordDTO);
            return keywordDTO;
        }else{
            KeywordDTO keywordEntity = keywordDAO.findByObjectId(id);
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

    @Override
    public void myInsertAll(List<KeyWordBackUpDTO> list) {
        keyWordBackUpDAO.myInsertAll(list);
    }

}
