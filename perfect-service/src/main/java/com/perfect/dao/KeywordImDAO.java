package com.perfect.dao;

import com.perfect.entity.KeywordImEntity;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/22.
 */
public interface KeywordImDAO extends MongoCrudRepository<KeywordImEntity,Long>  {
    public KeywordImEntity findByKwdId(Long kwdId);
    public List<KeywordImEntity> findByCgId(String cgId);
    public List<KeywordImEntity> getAll();
    public List<Long> findByAdgroupIds(List<Long> adgroupIds);
    List<Long> findByAdgroupId(Long adgroupId);
    List<Long> findByKeywordName(String str);
    public void deleteByObjId(String cgid);
}
