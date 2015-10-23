package com.perfect.service.impl;

import com.google.common.collect.Maps;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.KeywordUploadService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created on 2015-10-23.
 *
 * @author dolphineor
 */
@Service
public class KeywordUploadServiceImpl implements KeywordUploadService {

    @Resource
    private KeywordDAO keywordDAO;

    private final Function<Long, Map<String, Map<Integer, List<String>>>> accountDeduplicateFunction = baiduUserId -> {
        Map<String, Map<Integer, List<String>>> result = Maps.newHashMap();

        List<KeywordDTO> newKeywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, NEW);

        List<KeywordDTO> modifiedKeywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, MODIFIED);


        return result;
    };

    private final BiFunction<Long, Long, Map<Integer, List<String>>> adgroupDeduplicateFunction = (baiduUserId, adgroupId) -> {
        // 同一推广单元中已经存在的百度关键词
        final Map<String, KeywordDTO> sameAdgroupKeywordMap = keywordDAO
                .findAllKeywordFromBaiduByAdgroupId(baiduUserId, adgroupId)
                .stream()
                .collect(Collectors.toMap(k -> k.getKeyword().trim().toUpperCase(), v -> v));

        return IntStream.rangeClosed(1, 2)
                .boxed()
                .map(i -> {
                    if (i == 1) {
                        // NEW
                        return Maps.immutableEntry(i, keywordDAO
                                .findLocalChangedKeywords(baiduUserId, i)
                                .stream()
                                .collect(Collectors.groupingBy(k -> k.getKeyword().trim().toUpperCase()))
                                .values()
                                .stream()
                                        // 1. 找出新增关键词中本身重复的
                                .map(sourceList -> sourceList
                                        .stream()
                                        .sorted((o1, o2) -> {
                                            ObjectId oId1 = new ObjectId(o1.getId());
                                            ObjectId oId2 = new ObjectId(o2.getId());
                                            return ~Integer.compare(oId1.getTimestamp(), oId2.getTimestamp());
                                        })
                                        .findFirst()
                                        .orElse(null))
                                .filter(Objects::nonNull)
                                        // 2. 找出和本单元已经存在的百度关键词重复的
                                .filter(keywordDTO -> sameAdgroupKeywordMap.containsKey(keywordDTO.getKeyword().trim().toUpperCase()))
                                .map(KeywordDTO::getKeyword)
                                .collect(Collectors.toList()));
                    } else {
                        // MODIFIED
                        return Maps.immutableEntry(i, keywordDAO
                                .findLocalChangedKeywords(baiduUserId, i)
                                .stream()
                                .filter(keywordDTO -> sameAdgroupKeywordMap.containsKey(keywordDTO.getKeyword().trim().toUpperCase()))
                                .map(KeywordDTO::getKeyword)
                                .collect(Collectors.toList()));
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    };


    @Override
    public Map<String, Map<Integer, List<String>>> deduplicate(Long baiduUserId) {
        return accountDeduplicateFunction.apply(baiduUserId);
    }

    @Override
    public Map<Integer, List<String>> deduplicate(Long baiduUserId, Long adgroupId) {
        return adgroupDeduplicateFunction.apply(baiduUserId, adgroupId);
    }
}
