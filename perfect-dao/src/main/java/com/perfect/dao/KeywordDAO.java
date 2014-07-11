package com.perfect.dao;

import com.perfect.entity.KeywordEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-7.
 */
public interface KeywordDAO extends CrudRepository<KeywordEntity, Long> {
    /**
     * 按条件批量更新
     * <br>------------------------------<br>
     *
     * @param field
     * @param seedWord
     * @param value
     */
    void updateMulti(String field, String seedWord, Object value);

    List<Long> getKeywordIdByAdgroupId(Long adgroupId);

    List<KeywordEntity> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

}
