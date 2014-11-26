package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.backup.CreativeBackUpDTO;
import com.perfect.entity.backup.CreativeBackUpEntity;

/**
 * Created by XiaoWei on 2014/9/4.
 */
public interface CreativeBackUpService {
    CreativeBackUpDTO findByStringId(String id);
    CreativeBackUpDTO findByLongId(Long crid);
    void deleteByLongId(Long crid);
    CreativeBackUpDTO reBack(Long crid);
}
