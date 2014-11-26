package com.perfect.service.impl;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dao.CustomGroupDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.CustomGroupDTO;
import com.perfect.entity.CustomGroupEntity;
import com.perfect.service.CustomGroupService;
import com.perfect.utils.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 */
@Service
public class CustomGroupServiceImpl extends AbstractUserBaseDAOImpl<CustomGroupDTO,Long> implements CustomGroupService {
    private static final String ZTREE="trees";

    @Resource
    private CustomGroupDAO customGroupDAO;
    @Override
    public Class<CustomGroupDTO> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

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
