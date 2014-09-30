package com.perfect.service;

import com.perfect.entity.UrlEntity;

import java.util.List;

/**
 * Created by baizz on 2014-9-26.
 */
public interface BiddingMaintenanceService {

    List<UrlEntity> findAll();

    void saveUrlEntity(UrlEntity urlEntity);
}
