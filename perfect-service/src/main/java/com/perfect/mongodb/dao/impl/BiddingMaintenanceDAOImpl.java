package com.perfect.mongodb.dao.impl;

import com.perfect.dao.BiddingMaintenanceDAO;
import com.perfect.entity.UrlEntity;
import com.perfect.mongodb.base.BaseMongoTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by baizz on 2014-9-26.
 */
@Repository("biddingMaintenanceDAO")
public class BiddingMaintenanceDAOImpl implements BiddingMaintenanceDAO {
    @Override
    public <S extends UrlEntity> List<S> save(Iterable<S> entites) {
        return null;
    }

    @Override
    public <S extends UrlEntity> S save(S entity) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        mongoTemplate.save(entity, "sys_urlpool");
        return entity;
    }

    @Override
    public UrlEntity findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public List<UrlEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<UrlEntity> findAll(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(UrlEntity entity) {

    }

    @Override
    public void delete(Iterable<? extends UrlEntity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<UrlEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<UrlEntity> findAll(Pageable pageable) {
        return null;
    }
}
