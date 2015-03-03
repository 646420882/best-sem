package com.perfect.dao.creative;

import com.perfect.dto.creative.MobileSublinkDTO;

/**
 * Created by XiaoWei on 2015/2/27.
 */
public interface MobileSublinkDAO {
    void customSave(MobileSublinkDTO mobileSublinkDTO);
    MobileSublinkDTO findByAdgroupLongId(Long adgroupId);
    MobileSublinkDTO findByAdgroupObjId(String objectId);
}
