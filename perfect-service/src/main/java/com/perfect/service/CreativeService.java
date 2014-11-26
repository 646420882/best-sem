package com.perfect.service;

import com.perfect.dto.creative.CreativeDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by SubDong on 2014/11/26.
 */
public interface CreativeService {

    public List<Long> getCreativeIdByAdgroupId(Long adgroupId);

    public List<CreativeDTO> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

    public CreativeDTO findOne(Long creativeId);

    public List<CreativeDTO> find(Map<String, Object> params, int skip, int limit);

    void insertAll(List<CreativeDTO> list);

    void update(CreativeDTO creativeDTO);

    void delete(Long creativeId);

    void deleteByIds(List<Long> creativeIds);
}
