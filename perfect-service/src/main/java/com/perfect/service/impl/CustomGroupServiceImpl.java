package com.perfect.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dao.adgroup.CustomGroupDAO;
import com.perfect.dto.CustomGroupDTO;
import com.perfect.service.CustomGroupService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 * 2014-11-26 refactor
 */
@Service
public class CustomGroupServiceImpl implements CustomGroupService {
    private static final String ZTREE="trees";

    @Resource
    private CustomGroupDAO customGroupDAO;

    @Override
    public List<CustomGroupDTO> findAll(Long acId) {
        return customGroupDAO.findAll(acId);
    }

    @Override
    public Map<String,Object> getCustomGroupTree() {
        Map<String,Object> map=new HashMap<>();
        ArrayNode arrayNode=customGroupDAO.getCustomGroupTree();
        map.put(ZTREE,arrayNode);
       return map;
    }

    @Override
    public CustomGroupDTO findByCustomName(String customName) {
        return customGroupDAO.findByCustomName(customName);
    }

    @Override
    public void myInsert(CustomGroupDTO customGroupDTO) {
        customGroupDAO.myInsert(customGroupDTO);
    }
}
