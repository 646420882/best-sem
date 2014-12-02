package com.perfect.service.impl;

import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.service.CreativeService;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/11/26.
 */
@Service("creativeService")
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

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> map, int nowPage, int pageSize) {
        return creativeDAO.findByPagerInfo(map,nowPage,pageSize);
    }

    @Override
    public PagerInfo findByPagerInfo(Long l, Integer nowPage, Integer pageSize) {
        return creativeDAO.findByPagerInfo(l,nowPage,pageSize);
    }


    @Override
    public PagerInfo findByPagerInfoForLong(List<Long> longs, int nowpage, int pageSize) {
        return creativeDAO.findByPagerInfoForLong(longs,nowpage,pageSize);
    }

    @Override
    public String insertOutId(CreativeDTO creativeEntity) {
        return creativeDAO.insertOutId(creativeEntity);
    }

    @Override
    public void deleteByCacheId(Long cacheCreativeId) {
        creativeDAO.deleteByCacheId(cacheCreativeId);
}

    @Override
    public void deleteByCacheId(String cacheCreativeId) {
        creativeDAO.deleteByCacheId(cacheCreativeId);
    }

    @Override
    public CreativeDTO findByObjId(String obj) {
        return creativeDAO.findByObjId(obj);
    }

    @Override
    public void updateByObjId(CreativeDTO creativeEntity) {
        creativeDAO.updateByObjId(creativeEntity);
    }

    @Override
    public void update(CreativeDTO newCreativeEntity, CreativeDTO creativeBackUpEntity) {
        creativeDAO.update(newCreativeEntity,creativeBackUpEntity);
    }

    @Override
    public void delBack(Long oid) {
        creativeDAO.delBack(oid);
    }

    @Override
    public CreativeDTO getAllsBySomeParams(Map<String, Object> params) {
        return creativeDAO.getAllsBySomeParams(params);
    }
}
