package com.perfect.dao.sys;


import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/2.
 */
public interface LogDAO extends MongoCrudRepository<LogEntity, String> {
    Iterable<LogEntity> findAll(Long accountId);

    void deleteByBids(List<Long> ids);

    boolean existsByOid(String oid);

    boolean existsByBid(Long bid);

    void insertLog(String oid, String entity);

    void insertLog(Long bid, String entity, int opt);
}
