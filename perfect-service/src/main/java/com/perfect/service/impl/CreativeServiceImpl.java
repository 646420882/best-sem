package com.perfect.service.impl;

import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.service.CreativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/11/26.
 */
@Repository("creativeService")
public class CreativeServiceImpl implements CreativeService {

    @Autowired
    private CreativeDAO creativeDAO;

    @Override
    public List<Long> getCreativeIdByAdgroupId(Long adgroupId) {
        return creativeDAO.getCreativeIdByAdgroupId(adgroupId);
    }

    @Override
    public List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit) {
        return creativeDAO.getCreativeByAdgroupId(adgroupId, params, skip, limit);
    }

    @Override
    public CreativeDTO findOne(Long creativeId) {
        return creativeDAO.findOne(creativeId);
    }

    @Override
    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit) {
        return find(params, skip, limit);
    }

    @Override
    public void insertAll(List<CreativeDTO> list) {
        creativeDAO.insertAll(list);
    }

    @Override
    public void update(CreativeDTO creativeDTO) {
        creativeDAO.update(creativeDTO);
    }

    @Override
    public void delete(Long creativeId) {
        creativeDAO.delete(creativeId);
    }

    @Override
    public void deleteByIds(List<Long> creativeIds) {
        creativeDAO.deleteByIds(creativeIds);
    }
}
