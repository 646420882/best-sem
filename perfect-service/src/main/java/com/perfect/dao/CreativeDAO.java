package com.perfect.dao;

import com.perfect.entity.CreativeEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-10.
 */
public interface CreativeDAO extends MongoCrudRepository<CreativeEntity, Long> {

    List<Long> getCreativeIdByAdgroupId(Long adgroupId);

    List<CreativeEntity> getCreativeByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);
}
