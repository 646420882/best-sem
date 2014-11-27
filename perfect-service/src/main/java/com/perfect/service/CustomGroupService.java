package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.CustomGroupDTO;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 */
public interface CustomGroupService {
    List<CustomGroupDTO> findAll(Long acId);
    Map<String,Object> getCustomGroupTree();
    CustomGroupDTO findByCustomName(String customName);
    void myInsert(CustomGroupDTO customGroupDTO);
}
