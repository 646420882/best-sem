package com.perfect.dao;

import com.perfect.dto.CustomGroupDTO;
import com.perfect.dto.keyword.KeywordImportDTO;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/22.
 */
public interface KeywordImportDAO {
    public KeywordImportDTO findByKwdId(Long kwdId);

    public List<KeywordImportDTO> findByCgId(String cgId);

    public List<KeywordImportDTO> getAll();

    public List<Long> findByAdgroupIds(List<Long> adgroupIds);

    List<Long> findByAdgroupId(Long adgroupId);

    List<Long> findByKeywordName(String str);

    public void deleteByObjId(String cgid);

    void update(KeywordImportDTO keywordImportDTO);

    void myInsertAll(List<KeywordImportDTO> keywordImportDTOs);

    void myInsert(KeywordImportDTO dto);
}
