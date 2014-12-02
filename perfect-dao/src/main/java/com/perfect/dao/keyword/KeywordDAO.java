package com.perfect.dao.keyword;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeyWordBackUpDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.utils.paging.PagerInfo;
import com.perfect.utils.paging.PaginationParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-7-7.
 * 2014-12-2 refactor
 */
public interface KeywordDAO extends HeyCrudRepository<KeywordDTO, Long> {
    /**
     * 按条件批量更新
     * <br>------------------------------<br>
     *
     * @param field
     * @param seedWord
     * @param value
     */
    void updateMulti(String field, String seedWord, Object value);

    void updateMultiKeyword(Long[] ids, BigDecimal price, String pcUrl);

    AdgroupDTO findByKeywordId(Long keywordId);

    List<Long> getKeywordIdByAdgroupId(Long adgroupId);

    List<KeywordDTO> getKeywordByAdgroupId(Long adgroupId, Map<String, Object> params, int skip, int limit);

    List<KeywordDTO> getKeywordByAdgroupId(String adgroupId, Map<String, Object> params, int skip, int limit);

    List<KeywordDTO> findByAgroupId(Long oid);

    List<KeywordDTO> getKeywordInfo();

    Long keywordCount(List<Long> adgroupIds);

    /**
     * 安全添加
     *
     * @param keywordDTOList
     */
    void insertAndQuery(List<KeywordDTO> keywordDTOList);

    KeywordDTO findByName(String name, Long accountId);

//    void remove(Query query);
//
//    List<KeywordDTO> findByQuery(Query query);

    List<KeywordDTO> findByAdgroupId(Long adgroupId, PaginationParam param, Map<String, Object> queryParams);

    List<KeywordDTO> findByAdgroupId(String adgroupId, PaginationParam param);

    List<KeywordDTO> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param, Map<String, Object> queryParams);

    KeywordDTO findByObjectId(String oid);

    PagerInfo findByPageInfoForAcctounId( int pageSize, int pageNo);

    PagerInfo findByPageInfoForLongId(Long  aid, int pageSize, int pageNo);

    PagerInfo findByPageInfoForStringId(String  aid, int pageSize, int pageNo);

    PagerInfo findByPageInfoForLongIds(List<Long>  longIds, int pageSize, int pageNo);

    PagerInfo findByPageInfoForStringIds(List<String>  stringIds, int pageSize, int pageNo);

    void updateAdgroupIdByOid(String id, Long adgroupId);

    List<KeywordDTO> find(Map<String, Object> params, int skip, int limit, String order);

    void deleteById(String id);

    void updateByMongoId(KeywordDTO keywordDTO);

    List<KeywordDTO> getKeywordByIds(List<Long> ids);

    List<KeywordDTO> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    List<KeywordDTO> findByIds(List<Long> ids, PaginationParam... param);

    void update(KeywordDTO keywordDTO, KeyWordBackUpDTO keyWordBackUpDTO);

    void softDelete(Long id);

    void updateLocalstatu(long cid);

    void deleteByObjectAdgroupIds(List<String> agids);

    void softDeleteByLongAdgroupIds(List<Long> longSet);

    List<KeywordDTO> findByObjectIds(List<String> strIds);

    List<KeywordDTO> findKeywordByIds(List<Long> ids);

    void insertAll(List<KeywordDTO> dtos);


}
