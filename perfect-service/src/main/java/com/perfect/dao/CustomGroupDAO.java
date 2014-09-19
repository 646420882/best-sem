package com.perfect.dao;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.entity.CustomGroupEntity;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/19.
 */
public interface CustomGroupDAO extends  MongoCrudRepository<CustomGroupEntity,Long> {
    public List<CustomGroupEntity> findAll(Long acId);
    ArrayNode getCustomGroupTree();
}
