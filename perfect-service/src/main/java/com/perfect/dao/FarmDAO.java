package com.perfect.dao;

import com.perfect.entity.UrlEntity;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
public interface FarmDAO extends MongoCrudRepository<UrlEntity, String> {

    public UrlEntity takeOne();

    public void returnOne(UrlEntity urlEntity);


//    public List<UrlEntity> takeBySize(int size);
}
