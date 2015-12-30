package com.perfect.service.impl;

import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.keyword.KeywordBackUpDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeywordBackUpDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.dto.keyword.KeywordInfoDTO;
import com.perfect.service.KeywordBackUpService;
import com.perfect.service.UserOperationLogService;
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

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private UserOperationLogService userOperationLogService;


    public void insertAll(List<KeywordBackUpDTO> entities) {
        for (KeywordBackUpDTO tempKwdBack : entities) {
            boolean exists = keywordBackUpDAO.existsByObjectId(tempKwdBack.getId());
            if (exists == false) {
                keywordBackUpDAO.save(tempKwdBack);
            }
        }
    }

    /**
     * 还原修改的关键词
     *
     * @param id
     */
    public KeywordInfoDTO reducUpdate(String id) {
        KeywordInfoDTO keywordInfoDTO = new KeywordInfoDTO();

        if (id.matches("^\\d+$") == true) {
            KeywordDTO keywordEntity = new KeywordDTO();
            KeywordBackUpDTO keywordBackUpDTOFind = keywordBackUpDAO.findById(Long.parseLong(id));
            BeanUtils.copyProperties(keywordBackUpDTOFind, keywordEntity);
            keywordEntity.setLocalStatus(null);
            keywordDAO.save(keywordEntity);
            keywordBackUpDAO.deleteByKwid(Long.parseLong(id));
            KeywordDTO keywordDTO = new KeywordDTO();
            BeanUtils.copyProperties(keywordEntity, keywordDTO);
            keywordInfoDTO.setObject(keywordDTO);
            AdgroupDTO adgroupDTO = adgroupDAO.findOne(keywordDTO.getAdgroupId());
            if (adgroupDTO != null) {
                keywordInfoDTO.setAdgroupName(adgroupDTO.getAdgroupName());
            }
            return keywordInfoDTO;
        } else {
            KeywordDTO keywordDTO = keywordDAO.findByObjectId(id);
            keywordDAO.deleteById(id);
            AdgroupDTO adgroupDTO = adgroupDAO.findByObjId(keywordDTO.getId());
            if (adgroupDTO != null) {
                keywordInfoDTO.setAdgroupName(adgroupDTO.getAdgroupName());
            }
            keywordInfoDTO.setObject(keywordDTO);
            return keywordInfoDTO;
        }
    }


    /**
     * 还原软删除
     *
     * @param id
     * @return
     */
    public void reducDel(String id) {
        if (id.matches("^\\d+$")) {
            keywordDAO.updateLocalstatu(Long.parseLong(id));
        }
    }


    @Override
    public void myInsertAll(List<KeywordBackUpDTO> list) {
        keywordBackUpDAO.myInsertAll(list);
    }


}
