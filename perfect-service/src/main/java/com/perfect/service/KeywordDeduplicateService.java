package com.perfect.service;

import com.google.common.collect.Lists;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.perfect.db.mongodb.base.BaseMongoTemplate;
import com.perfect.dto.keyword.KeywordDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.perfect.commons.constants.MongoEntityConstants.*;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Created on 2015-10-23.
 *
 * @author dolphineor
 */
public interface KeywordDeduplicateService {

    /**
     * 重复
     */
    int DUPLICATED = -1;

    /**
     * 新增
     */
    int NEW = 1;

    /**
     * 修改
     */
    int MODIFIED = 2;

    /**
     * 删除
     */
    int DELETED = 3;


    /**
     * <p><code>上传</code>时全账户去重.
     * Map ->
     * Key: 重复的关键词
     * Value: {
     *   Key: NEW MODIFIED
     *   Value: List中的值为: "推广计划名称:推广单元名称"</p>
     * }
     *
     * @param baiduAccountId 百度账户ID
     * @return
     */
    Map<String, Map<Integer, List<String>>> deduplicate(final Long baiduAccountId);

    /**
     * <p><code>上传</code>时同一单元去重.
     * Map ->
     * Key: NEW MODIFIED
     * Value: List中的值为: "推广计划名称:推广单元名称"</p>
     *
     * @param baiduAccountId 百度账户ID
     * @param adgroupId      推广单元ID
     * @return
     */
    Map<Integer, List<String>> deduplicate(final Long baiduAccountId, final Long adgroupId);

    /**
     * <p>本地<code>批量添加</code>关键词时, 全账户去重.
     *
     * @param baiduAccountId 百度账户ID
     * @param newKeywords    批量添加的关键词名称
     * @return 重复关键词的MongoDB ID
     */
    List<String> deduplicate(final Long baiduAccountId, final List<String> newKeywords);

    /**
     * <p>添加关键词时, 对同一单元关键词去重, 对于重复的关键词对其设定相应的状态.
     *
     * @param baiduAccountId 百度账户ID
     * @param adgroupId      推广单元ID
     * @param list           待处理关键词
     * @return List带重复标识的关键词
     */
    List<KeywordDTO> deduplicate(final Long baiduAccountId, final Long adgroupId, final List<KeywordDTO> list);

    default DBCollection getKeywordCollection(String username) {
        return BaseMongoTemplate.getUserMongo(username).getCollection(TBL_KEYWORD);
    }

    /**
     * <p>应用BloomFilter对关键词做全账户去重.
     *
     * @param username       系统用户名
     * @param baiduAccountId 百度账户ID
     * @param newKeywords    待比较的关键词, 可为空
     * @return 重复关键词的MongoDB ID
     */
    default List<String> findOverlaps(final String username, final Long baiduAccountId, final List<String> newKeywords) {
        if (Objects.isNull(baiduAccountId))
            return Collections.<String>emptyList();

        // Build a bloom filter
        BloomFilter<String> keywordBloomFilter = BloomFilter.create(Funnels.stringFunnel(UTF_8), 50_000_000);
        if (Objects.nonNull(newKeywords) && !newKeywords.isEmpty()) {
            // Put new keywords into bloom filter
            newKeywords.forEach(keywordBloomFilter::put);
        }

        List<String> duplicatedKeywordIds = Lists.newArrayList();
        DBObject query = BasicDBObjectBuilder.start(ACCOUNT_ID, baiduAccountId).get();
        DBCursor cursor = getKeywordCollection(username).find(query);
        while (cursor.hasNext()) {
            DBObject doc = cursor.next();

            if (!keywordBloomFilter.put(doc.get(NAME).toString())) {
                duplicatedKeywordIds.add(doc.get(SYSTEM_ID).toString());
            }
        }
        cursor.close();

        return duplicatedKeywordIds;
    }
}
