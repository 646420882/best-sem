package com.perfect.dao;

import com.perfect.entity.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by baizz on 2014-9-26.
 */
public interface BiddingMaintenanceDAO extends MongoRepository<UrlEntity, Long> {
}
