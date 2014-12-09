package com.perfect.dao.keyword;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.backup.KeywordBackUpDTO;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/9.
 */
public interface KeywordBackUpDAO extends HeyCrudRepository<KeywordBackUpDTO, Long> {


    KeywordBackUpDTO findByObjectId(String id);

    KeywordBackUpDTO findById(long id);

    void deleteByKwid(long kwid);

    boolean existsByObjectId(String id);

    void myInsertAll(List<KeywordBackUpDTO> list);

}
