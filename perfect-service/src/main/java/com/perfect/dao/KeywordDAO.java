package com.perfect.dao;

import com.perfect.autosdk.sms.v3.KeywordInfo;
import com.perfect.entity.KeywordEntity;
import com.perfect.mongodb.utils.Pager;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.mongodb.utils.PaginationParam;
import org.springframework.data.mongodb.core.query.Query;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-7.
 */
public interface KeywordDAO extends MongoCrudRepository<KeywordEntity, Long> {
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

    List<KeywordEntity> getKeywordByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit);

    List<KeywordEntity> findByAgroupId(Long oid);

    Pager getKeywordByPager(HttpServletRequest request, Map<String, Object> params, int orderBy);

    List<KeywordInfo> getKeywordInfo();

    /**
     * 安全添加
     *
     * @param keywordEntity
     */
    public void insertAndQuery(List<KeywordEntity> keywordEntity);

    KeywordEntity findByName(String name, Long accountId);

    void remove(Query query);

    List<KeywordEntity> findByQuery(Query query);

    List<KeywordEntity> findByAdgroupId(Long adgroupId, PaginationParam param);

    List<KeywordEntity> findByAdgroupId(String adgroupId, PaginationParam param);

    List<KeywordEntity> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param);

    KeywordEntity findByObjectId(String oid);

    PagerInfo findByPageInfo(Query q,int pageSize, int pageNo);

    void updateAdgroupIdByOid(String id, Long adgroupId);

    void deleteById(String id);

    void updateByMongoId(KeywordEntity keywordEntity);

    List<KeywordEntity> getKeywordByIds(List<Long> ids);
}
