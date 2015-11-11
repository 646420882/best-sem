package com.perfect.service.impl;

import com.perfect.commons.deduplication.KeywordDeduplication;
import com.perfect.dao.creative.CreativeDAO;
import com.perfect.dto.creative.CreativeDTO;
import com.perfect.service.CreativeDeduplicateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created on 2015-10-23.
 *
 * @author dolphineor
 */
@Service
public class CreativeDeduplicateServiceImpl implements CreativeDeduplicateService {

    @Resource
    private CreativeDAO creativeDAO;


    @Override
    public Map<String, Map<Integer, List<String>>> deduplicate(final Long baiduUserId) {
        return Collections.emptyMap();
    }

    @Override
    public Map<Integer, List<String>> deduplicate(final Long baiduUserId, final Long adgroupId) {
        return Collections.emptyMap();
    }

    @Override
    public List<CreativeDTO> deduplicate(final Long baiduUserId, final Long adgroupId, final List<CreativeDTO> list) {
        return ((Function<List<CreativeDTO>, List<CreativeDTO>>) creativeList -> {
            final Map<String, List<CreativeDTO>> sameAdgroupCreativeMap = ((BiFunction<Long, Long, List<CreativeDTO>>) creativeDAO::findByAdgroupId).apply(baiduUserId, adgroupId)
                    .stream()
                    .collect(Collectors.groupingBy(creative -> {
                        return KeywordDeduplication.MD5.getMD5(creative.getTitle() + creative.getDescription1() + creative.getDescription2());
                    }));

            return creativeList.stream()
                    .map(creative -> {
                        if (sameAdgroupCreativeMap.containsKey(creative.getTitle() + creative.getDescription1() + creative.getDescription2()))
                            creative.setLocalStatus(DUPLICATED);

                        return creative;
                    })
                    .collect(Collectors.toList());
        }).apply(list);
    }

    @Override
    public List<CreativeDTO> deduplicate(Long baiduUserId, String adgroupId, List<CreativeDTO> list) {
        return ((Function<List<CreativeDTO>, List<CreativeDTO>>) creativeList -> {
            final Map<String, List<CreativeDTO>> sameAdgroupCreativeMap = ((BiFunction<Long, String, List<CreativeDTO>>) creativeDAO::findByAdgroupId).apply(baiduUserId, adgroupId)
                    .stream()
                    .collect(Collectors.groupingBy(creative -> {
                        return KeywordDeduplication.MD5.getMD5(creative.getTitle() + creative.getDescription1() + creative.getDescription2());
                    }));

            return creativeList.stream()
                    .map(creative -> {
                        if (sameAdgroupCreativeMap.containsKey(creative.getTitle() + creative.getDescription1() + creative.getDescription2()))
                            creative.setLocalStatus(DUPLICATED);

                        return creative;
                    })
                    .collect(Collectors.toList());
        }).apply(list);
    }
}
