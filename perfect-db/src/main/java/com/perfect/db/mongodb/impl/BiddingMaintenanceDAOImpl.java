package com.perfect.db.mongodb.impl;

import com.perfect.dao.BiddingMaintenanceDAO;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.UrlDTO;
import com.perfect.entity.UrlEntity;
import com.perfect.utils.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by baizz on 2014-9-26.
 * 2014-11-26 refactor
 */
@Repository("biddingMaintenanceDAO")
public class BiddingMaintenanceDAOImpl implements BiddingMaintenanceDAO {

    @Override
    public <S extends UrlDTO> S save(S dto) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        UrlEntity urlEntity = new UrlEntity();
        BeanUtils.copyProperties(dto, urlEntity);
        mongoTemplate.save(urlEntity, "sys_urlpool");
        BeanUtils.copyProperties(urlEntity, dto);
        return dto;
    }

    @Override
    public <S extends UrlDTO> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public UrlDTO findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public List<UrlDTO> findAll() {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getSysMongo();
        return ObjectUtils.convert(mongoTemplate.findAll(UrlEntity.class, "sys_urlpool"), UrlDTO.class);
    }

    @Override
    public Iterable<UrlDTO> findAll(Iterable<Long> longs) {
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
    public void delete(UrlDTO t) {

    }

    @Override
    public void delete(Iterable<? extends UrlDTO> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
