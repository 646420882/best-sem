package com.perfect.dao;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.UrlDTO;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/24.
 */
public interface FarmDAO extends HeyCrudRepository<UrlDTO, String> {

    public UrlDTO takeOne();

    public void returnOne(UrlDTO urlEntity);

    public List<UrlDTO> allUnused();
}
