package com.perfect.service.impl;

import com.perfect.dao.keyword.KeywordImportDAO;
import com.perfect.dto.keyword.KeywordImportDTO;
import com.perfect.service.KeywordImportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by XiaoWei on 2014/9/22.
 * 2014-11-26 refactor
 */
@Service
public class KeywordImportServiceImpl  implements KeywordImportService {
    @Resource
    private KeywordImportDAO keywordImportDAO;


    @Override
    public KeywordImportDTO findByKwdId(Long kwdId) {
        return keywordImportDAO.findByKwdId(kwdId);
    }

    @Override
    public List<KeywordImportDTO> findByCgId(String cgId) {
        return keywordImportDAO.findByCgId(cgId);
    }

    @Override
    public List<KeywordImportDTO> getAll() {
        return keywordImportDAO.getAll();
    }

    @Override
    public List<Long> findByAdgroupIds(List<Long> adgroupIds) {
        return keywordImportDAO.findByAdgroupIds(adgroupIds);
    }

    @Override
    public List<Long> findByAdgroupId(Long adgroupId) {
        return keywordImportDAO.findByAdgroupId(adgroupId);
    }

    @Override
    public List<Long> findByKeywordName(String str) {
        return keywordImportDAO.findByKeywordName(str);
    }

    @Override
    public void deleteByObjId(String cgid) {
        keywordImportDAO.deleteByObjId(cgid);
    }

    @Override
    public void update(KeywordImportDTO keywordImportDTO) {
        keywordImportDAO.update(keywordImportDTO);
    }

    @Override
    public void myInsertAll(List<KeywordImportDTO> keywordImportDTOs) {
        keywordImportDAO.myInsertAll(keywordImportDTOs);
    }

    @Override
    public void myInsert(KeywordImportDTO dto) {
        keywordImportDAO.myInsert(dto);
    }

    @Override
    public void deleteBySelectLong(String cgid, Long kwd) {
        keywordImportDAO.deleteBySelectLong(cgid,kwd);
    }

    @Override
    public void deleteBySelectObj(String cgid, String kwd) {
        keywordImportDAO.deleteBySelectObj(cgid,kwd);
    }
}
