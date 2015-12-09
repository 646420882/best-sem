package com.perfect.service.impl;

import com.perfect.core.AppContext;
import com.perfect.dao.keyword.KeywordBackUpDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.backup.KeywordBackUpDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.log.model.OperationRecordModel;
import com.perfect.service.KeywordBackUpService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by XiaoWei on 2014/9/9.
 * 2014-11-26 refactor
 */
@Service
public class KeywordBackUpServiceImpl implements KeywordBackUpService {

    @Resource
     private KeywordBackUpDAO keywordBackUpDAO;

    @Resource
    private KeywordDAO keywordDAO;




    public void insertAll(List<KeywordBackUpDTO> entities) {
        for(KeywordBackUpDTO tempKwdBack:entities){
            boolean exists = keywordBackUpDAO.existsByObjectId(tempKwdBack.getId());
            if (exists == false) {
                keywordBackUpDAO.save(tempKwdBack);
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
            KeywordBackUpDTO keywordBackUpDTOFind = keywordBackUpDAO.findById(Long.parseLong(id));
            BeanUtils.copyProperties(keywordBackUpDTOFind, keywordEntity);
            keywordEntity.setLocalStatus(null);
            keywordDAO.save(keywordEntity);
            keywordBackUpDAO.deleteByKwid(Long.parseLong(id));
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
            OperationRecordModel orm=new OperationRecordModel();
            orm.setUserId(AppContext.getAccountId());
//            orm.set
        }
    }

    @Override
    public void myInsertAll(List<KeywordBackUpDTO> list) {
        keywordBackUpDAO.myInsertAll(list);
    }


}
