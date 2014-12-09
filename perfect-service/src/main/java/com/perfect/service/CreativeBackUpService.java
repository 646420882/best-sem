package com.perfect.service;

import com.perfect.dto.backup.CreativeBackUpDTO;

/**
 * Created by XiaoWei on 2014/9/4.
 * 2014-11-26 refactor
 */
public interface CreativeBackUpService {
    CreativeBackUpDTO findByStringId(String id);
    CreativeBackUpDTO findByLongId(Long crid);
    void deleteByLongId(Long crid);
    CreativeBackUpDTO reBack(Long crid);
}
