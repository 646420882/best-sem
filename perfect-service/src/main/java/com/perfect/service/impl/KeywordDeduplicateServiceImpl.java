package com.perfect.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.perfect.core.AppContext;
import com.perfect.dao.adgroup.AdgroupDAO;
import com.perfect.dao.campaign.CampaignDAO;
import com.perfect.dao.keyword.KeywordDAO;
import com.perfect.dto.keyword.KeywordAggsDTO;
import com.perfect.dto.keyword.KeywordDTO;
import com.perfect.service.KeywordDeduplicateService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
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
public class KeywordDeduplicateServiceImpl implements KeywordDeduplicateService {

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    @Resource
    private KeywordDAO keywordDAO;


    private final Function<Long, Map<String, Map<Integer, List<String>>>> accountDeduplicateFunction = baiduUserId -> {
        // 1. 查询出当前账户所有存在于凤巢的关键词.
        final Map<Long, List<KeywordAggsDTO>> baiduKeywordMap = keywordDAO.findAllKeywordFromBaiduByAccountId(baiduUserId)
                .stream()
                .collect(Collectors.groupingBy(KeywordAggsDTO::getAdgroupId));

        // 数据结果集
        final Map<String, Map<Integer, List<String>>> resultMap = new HashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CompletionService<Boolean> compService = new ExecutorCompletionService<>(executor);
        IntStream.rangeClosed(1, 2).boxed().forEach(i -> {
            if (i == NEW)
                // 2. 检测所有新增的关键词是否和1中的关键词有重复.
                compService.submit(new NewKeywordDeduplicateTask(AppContext.getUser(), baiduUserId, baiduKeywordMap, resultMap));
            else if (i == MODIFIED)
                // 3. 检测所有修改后的关键词是否和1中的关键词有重复.
                compService.submit(new ModifiedKeywordDeduplicateTask(AppContext.getUser(), baiduUserId, baiduKeywordMap, resultMap));
        });
        executor.shutdown();

        // 异步任务完成标识
        final Boolean[] taskStatusArr = new Boolean[]{false, false};
        IntStream.range(0, 2).boxed().forEach(i -> {
            try {
                // 获取已经完成的异步任务
                Future<Boolean> future = compService.take();
                taskStatusArr[i] = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });


        if (taskStatusArr[0] && taskStatusArr[1]) {
            // 获取当前百度账户的所有推广计划名称和ID
            final Map<Long, String> campaignMap = campaignDAO.findAllDownloadCampaignByBaiduAccountId(baiduUserId);

            /**
             * resultMap的后续处理:
             * 之前的处理中, 对于重复的关键词, 只是存储了其对应的推广单元ID.
             * 需要更新到其对应的推广计划和推广单元.
             */
            for (Map.Entry<String, Map<Integer, List<String>>> entry : resultMap.entrySet()) {
                final Map<Integer, List<String>> valueMap = entry.getValue();

                IntStream.rangeClosed(1, 2).boxed().forEach(i -> {
                    valueMap.computeIfPresent(i, (k, adgroupIds) -> {
                        return adgroupDAO.findDownloadAdgroup(baiduUserId, adgroupIds.stream().map(Long::parseLong).collect(Collectors.toList()))
                                .stream()
                                .map(o -> campaignMap.get(o.getCampaignId()) + ":" + o.getAdgroupName())
                                .collect(Collectors.toList());
                    });
                });

                resultMap.put(entry.getKey(), valueMap);
            }

            return resultMap;
        }

        return Collections.emptyMap();
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
                        return Maps.immutableEntry(i, keywordDAO.findLocalChangedKeywords(baiduUserId, i)
                                .stream()
                                .collect(Collectors.groupingBy(k -> k.getKeyword().trim().toUpperCase()))
                                .values()
                                .stream()
                                        // 1. 去除新增关键词中本身重复的
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
                        return Maps.immutableEntry(i, keywordDAO.findLocalChangedKeywords(baiduUserId, i)
                                .stream()
                                .filter(keywordDTO -> sameAdgroupKeywordMap.containsKey(keywordDTO.getKeyword().trim().toUpperCase()))
                                .map(KeywordDTO::getKeyword)
                                .collect(Collectors.toList()));
                    }
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    };


    @Override
    public Map<String, Map<Integer, List<String>>> deduplicate(final Long baiduUserId) {
        return accountDeduplicateFunction.apply(baiduUserId);
    }

    @Override
    public Map<Integer, List<String>> deduplicate(final Long baiduUserId, final Long adgroupId) {
        return adgroupDeduplicateFunction.apply(baiduUserId, adgroupId);
    }

    @Override
    public List<String> deduplicate(final Long baiduUserId, final List<String> newKeywords) {
        return ((BiFunction<Long, List<String>, List<String>>) (bId, stringList) -> {
            final List<KeywordDTO> list = keywordDAO.findAllByBaiduAccountId(bId);

            final Map<String, List<KeywordDTO>> allKeywordGroupedMap = list.stream()
                    .collect(Collectors.groupingBy(k -> k.getKeyword().trim().toUpperCase()));

            final List<String> deduplicateKeywordIds = Lists.newArrayList();

            stringList.stream()
                    .map(kn -> kn.trim().toUpperCase())
                    .filter(allKeywordGroupedMap::containsKey)
                    .forEach(k -> {
                        allKeywordGroupedMap.get(k).forEach(keywordDTO -> deduplicateKeywordIds.add(keywordDTO.getId()));
                    });

            return deduplicateKeywordIds;
        }).apply(baiduUserId, newKeywords);
    }

    @Override
    public List<KeywordDTO> deduplicate(final Long baiduUserId, final Long adgroupId, final List<KeywordDTO> list) {
        return ((Function<List<KeywordDTO>, List<KeywordDTO>>) keywordList -> {
            final Map<String, List<KeywordDTO>> sameAdgroupKeywordMap = ((BiFunction<Long, Long, List<KeywordDTO>>) keywordDAO::findByAdgroupId).apply(baiduUserId, adgroupId)
                    .stream()
                    .collect(Collectors.groupingBy(k -> k.getKeyword().trim().toUpperCase()));

            return keywordList.stream()
                    .map(k -> {
                        if (sameAdgroupKeywordMap.containsKey(k.getKeyword().trim().toUpperCase()))
                            k.setLocalStatus(DUPLICATED);

                        return k;
                    })
                    .collect(Collectors.toList());
        }).apply(list);
    }


    /**
     * <p>新增关键词的去重任务类.
     */
    class NewKeywordDeduplicateTask implements Callable<Boolean> {

        private final String username;

        private final Long baiduUserId;

        private final Map<Long, List<KeywordAggsDTO>> baiduKeywordMap;

        private final Map<String, Map<Integer, List<String>>> resultMap;


        NewKeywordDeduplicateTask(String username, Long baiduUserId, Map<Long, List<KeywordAggsDTO>> baiduKeywordMap, Map<String, Map<Integer, List<String>>> resultMap) {
            this.username = username;
            this.baiduUserId = baiduUserId;
            this.baiduKeywordMap = baiduKeywordMap;
            this.resultMap = resultMap;
        }


        @Override
        public Boolean call() throws Exception {
            AppContext.setUser(username);
            final List<KeywordDTO> newKeywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, NEW)
                    .stream()
                    .collect(Collectors.groupingBy(k -> k.getKeyword().trim().toUpperCase()))
                    .values()
                    .stream()
                            // 1). 去除新增关键词中本身重复的
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
                    .collect(Collectors.toList());

            baiduKeywordMap.entrySet().stream().forEach(entry -> {
                // 同一推广单元的百度关键词Map对象
                final Map<String, KeywordAggsDTO> sameAdgroupKeywordMap = entry.getValue().stream()
                        .collect(Collectors.toMap(o -> o.getKeywordName().toUpperCase(), o -> o));

                // 2). 找出和本单元已经存在的百度关键词重复的
                newKeywordDTOList.stream()
                        .filter(keyword -> sameAdgroupKeywordMap.containsKey(keyword.getKeyword().trim().toUpperCase()))
                        .forEach(duplicatedKeyword -> {
                            if (resultMap.containsKey(duplicatedKeyword.getKeyword())) {
                                Map<Integer, List<String>> valueMap = resultMap.get(duplicatedKeyword.getKeyword());
                                if (valueMap.containsKey(NEW)) {
                                    valueMap.get(NEW).add(duplicatedKeyword.getAdgroupId().toString());
                                } else {
                                    valueMap.put(NEW, Lists.newArrayList(duplicatedKeyword.getAdgroupId().toString()));
                                }

                                resultMap.put(duplicatedKeyword.getKeyword(), valueMap);
                            } else {
                                Map<Integer, List<String>> valueMap = new HashMap<>();
                                valueMap.put(NEW, Lists.newArrayList(duplicatedKeyword.getAdgroupId().toString()));
                                resultMap.put(duplicatedKeyword.getKeyword(), valueMap);
                            }
                        });
            });

            return true;
        }
    }

    /**
     * <p>修改关键词的去重任务类.
     */
    class ModifiedKeywordDeduplicateTask implements Callable<Boolean> {

        private final String username;

        private final Long baiduUserId;

        private final Map<Long, List<KeywordAggsDTO>> baiduKeywordMap;

        private final Map<String, Map<Integer, List<String>>> resultMap;


        ModifiedKeywordDeduplicateTask(String username, Long baiduUserId, Map<Long, List<KeywordAggsDTO>> baiduKeywordMap, Map<String, Map<Integer, List<String>>> resultMap) {
            this.username = username;
            this.baiduUserId = baiduUserId;
            this.baiduKeywordMap = baiduKeywordMap;
            this.resultMap = resultMap;
        }


        @Override
        public Boolean call() throws Exception {
            AppContext.setUser(username);
            final List<KeywordDTO> modifiedKeywordDTOList = keywordDAO.findLocalChangedKeywords(baiduUserId, MODIFIED);

            baiduKeywordMap.entrySet().stream().forEach(entry -> {
                // 同一推广单元的百度关键词Map对象
                final Map<String, KeywordAggsDTO> sameAdgroupKeywordMap = entry.getValue().stream()
                        .collect(Collectors.toMap(o -> o.getKeywordName().toUpperCase(), o -> o));

                modifiedKeywordDTOList
                        .stream()
                        .filter(keywordDTO -> sameAdgroupKeywordMap.containsKey(keywordDTO.getKeyword().trim().toUpperCase()))
                        .forEach(duplicatedKeyword -> {
                            if (resultMap.containsKey(duplicatedKeyword.getKeyword())) {
                                Map<Integer, List<String>> valueMap = resultMap.get(duplicatedKeyword.getKeyword());
                                if (valueMap.containsKey(MODIFIED)) {
                                    valueMap.get(MODIFIED).add(duplicatedKeyword.getAdgroupId().toString());
                                } else {
                                    valueMap.put(MODIFIED, Lists.newArrayList(duplicatedKeyword.getAdgroupId().toString()));
                                }

                                resultMap.put(duplicatedKeyword.getKeyword(), valueMap);
                            } else {
                                Map<Integer, List<String>> valueMap = new HashMap<>();
                                valueMap.put(MODIFIED, Lists.newArrayList(duplicatedKeyword.getAdgroupId().toString()));
                                resultMap.put(duplicatedKeyword.getKeyword(), valueMap);
                            }
                        });
            });

            return true;
        }
    }
}
