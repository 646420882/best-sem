package com.perfect.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dao.MongoCrudRepository;
import com.perfect.entity.CustomGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/19.
 */
public interface CustomGroupService extends MongoCrudRepository<CustomGroupEntity,Long> {
    List<CustomGroupEntity> findAll(Long acId);
    Map<String,Object> getCustomGroupTree();
    CustomGroupEntity findByCustomName(String customName);
}
