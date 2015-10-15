package com.perfect.dao.keyword;

import com.perfect.dao.base.HeyCrudRepository;
import com.perfect.dto.adgroup.AdgroupDTO;
import com.perfect.dto.backup.KeywordBackUpDTO;
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

    List<KeywordDTO> findHasLocalStatus();

    List<KeywordDTO> findHasLocalStatusStr(List<String> strs);

    List<KeywordDTO> findHasLocalStatusLong(List<Long> longs);

    Long keywordCount(List<Long> adgroupIds);

    /**
     * 安全添加
     *
     * @param keywordDTOList
     */
    void insertAndQuery(List<KeywordDTO> keywordDTOList);

    KeywordDTO findByName(String name, Long accountId);

    /**
     * <p>获取指定百度账号下在本地新增 修改 删除的关键词
     * type: 1 -> 新增, 2 -> 修改, 3 -> 删除</p>
     *
     * @param baiduAccountId
     * @return
     */
    List<KeywordDTO> findLocalChangedKeywords(Long baiduAccountId, int type);

    List<KeywordDTO> findByAdgroupId(Long adgroupId, PaginationParam param, Map<String, Object> queryParams);

    List<KeywordDTO> findByAdgroupId(String adgroupId, PaginationParam param);

    List<KeywordDTO> findByAdgroupIds(List<Long> adgroupIds, PaginationParam param, Map<String, Object> queryParams);

    KeywordDTO findByObjectId(String oid);

    KeywordDTO findByLongId(Long oid);

    PagerInfo findByPageInfoForAcctounId(int pageSize, int pageNo);

    PagerInfo findByPageInfoForLongId(Long aid, int pageSize, int pageNo);

    PagerInfo findByPageInfoForStringId(String aid, int pageSize, int pageNo);

    PagerInfo findByPageInfoForLongIds(List<Long> longIds, int pageSize, int pageNo);

    PagerInfo findByPageInfoForStringIds(List<String> stringIds, int pageSize, int pageNo);

    void updateAdgroupIdByOid(String id, Long adgroupId);

    List<KeywordDTO> find(Map<String, Object> params, int skip, int limit, String order);

    void deleteById(String id);

    void updateByMongoId(KeywordDTO keywordDTO);

    List<KeywordDTO> getKeywordByIds(List<Long> ids);

    List<KeywordDTO> findByNames(String[] query, boolean fullMatch, PaginationParam param, Map<String, Object> queryParams);

    List<KeywordDTO> findByIds(List<Long> ids, PaginationParam... param);

    void update(KeywordDTO keywordDTO, KeywordBackUpDTO keywordBackUpDTO);

    void update(KeywordDTO keywordDTO, KeywordDTO keywordBackUpDTO);

    void softDelete(Long id);

    void updateLocalstatu(long cid);

    void deleteByObjectAdgroupIds(List<String> agids);

    void softDeleteByLongAdgroupIds(List<Long> longSet);

    List<KeywordDTO> findByObjectIds(List<String> strIds);

    List<KeywordDTO> findKeywordByIds(List<Long> ids);

    void insertAll(List<KeywordDTO> dtos);

    List<KeywordDTO> findByParams(Map<String, Object> mapParams);

    KeywordDTO findByParamsObject(Map<String, Object> mapParams);

    void updateByObjId(KeywordDTO dto);

    void update(String oid, KeywordDTO dto);

    /**
     * 只用于修改上传修改成功的ls状态,以及获取到的Stauts
     *
     * @param dto 只包含Status和kid
     */
    void updateLs(KeywordDTO dto);

    Map<String, Map<String, List<String>>> getNoKeywords(Long aid);

    Map<String, Map<String, List<String>>> getNoKeywords(String aid);


    /**
     * 做文字查找或替换时如果用户选取的该计划下所有的数据时，用于查询所有关键词，紧接着的下一个方法也是做改功能的方法，只是这个是id类型不一样
     *
     * @param adgroupIds
     * @return
     */
    List<KeywordDTO> findKeywordByAdgroupIdsStr(List<String> adgroupIds);

    List<KeywordDTO> findKeywordByAdgroupIdsLong(List<Long> adgroupIds);

    void batchDelete(List<String> strings);

}
