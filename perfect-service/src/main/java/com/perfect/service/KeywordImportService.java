package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.keyword.KeywordImportDTO;
import com.perfect.entity.KeywordImportEntity;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/22.
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
