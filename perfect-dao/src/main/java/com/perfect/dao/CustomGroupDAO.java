package com.perfect.dao;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.dto.CustomGroupDTO;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/19.
 */
/*
 */
public interface CustomGroupDAO  {
    public List<CustomGroupDTO> findAll(Long acId);
    ArrayNode getCustomGroupTree();
    public CustomGroupDTO findByCustomName(String customName);
    void myInsert(CustomGroupDTO customGroupDTO);
}
