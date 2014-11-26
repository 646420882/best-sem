package com.perfect.dao;

import com.perfect.dto.backup.KeyWordBackUpDTO;

/**
 * Created by XiaoWei on 2014/9/9.
 */
public interface KeyWordBackUpDAO {


    KeyWordBackUpDTO findByObjectId(String id);

    void deleteByObjectId(String id);

    KeyWordBackUpDTO findById(long id);

    void deleteByKwid(long kwid);

    boolean existsByObjectId(String id);
}
