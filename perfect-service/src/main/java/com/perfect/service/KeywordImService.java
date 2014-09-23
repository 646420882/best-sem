package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.entity.KeywordImEntity;

import java.util.List;

/**
 * Created by XiaoWei on 2014/9/22.
 */
public interface KeywordImService extends MongoCrudRepository<KeywordImEntity,Long> {
    KeywordImEntity findByKwdId(Long kwdId);
    List<KeywordImEntity> findByCgId(String cgId);
}
