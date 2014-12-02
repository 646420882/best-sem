package com.perfect.dao.creative;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.backup.CreativeBackUpDTO;

/**
 * Created by XiaoWei on 2014/9/4.
 */

public interface CreativeBackUpDAO extends HeyCrudRepository<CreativeBackUpDTO,Long> {
    CreativeBackUpDTO findByStringId(String id);

    CreativeBackUpDTO findByLongId(Long crid);

    void deleteByLongId(Long crid);
}
