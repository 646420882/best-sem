package com.perfect.dao.creative;

import com.perfect.dto.backup.CreativeBackUpDTO;

/**
 * Created by XiaoWei on 2014/9/4.
 */

public interface CreativeBackUpDAO {
    CreativeBackUpDTO findByStringId(String id);

    CreativeBackUpDTO findByLongId(Long crid);

    void deleteByLongId(Long crid);
}
