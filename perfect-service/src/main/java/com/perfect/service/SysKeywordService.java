package com.perfect.service;

import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.PaginationParam;

import java.util.List;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public interface SysKeywordService {

    public List<KeywordEntity> findByAdgroupId(Long adgroupId, PaginationParam param);

    public List<KeywordEntity> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param);

}
