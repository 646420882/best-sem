package com.perfect.dao.keyword;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.backup.KeyWordBackUpDTO;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/9.
 */
public interface KeyWordBackUpDAO extends HeyCrudRepository<KeyWordBackUpDTO,Long> {


    KeyWordBackUpDTO findByObjectId(String id);

    void deleteByObjectId(String id);

    KeyWordBackUpDTO findById(long id);

    void deleteByKwid(long kwid);

    boolean existsByObjectId(String id);

    void myInsertAll(List<KeyWordBackUpDTO> list);

}
