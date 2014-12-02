package com.perfect.service;

import com.perfect.dto.keyword.KeywordImportDTO;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/22.
 * 2014-11-26 refactor
 */
public interface KeywordImportService {
    KeywordImportDTO findByKwdId(Long kwdId);
    List<KeywordImportDTO> findByCgId(String cgId);
    List<KeywordImportDTO> getAll();
    List<Long> findByAdgroupIds(List<Long> adgroupIds);
    List<Long> findByAdgroupId(Long adgroupId);
    List<Long> findByKeywordName(String str);
    void deleteByObjId(String cgid);
    void update(KeywordImportDTO keywordImportDTO);
    void myInsertAll(List<KeywordImportDTO> keywordImportDTOs);
    void myInsert(KeywordImportDTO dto);
}
