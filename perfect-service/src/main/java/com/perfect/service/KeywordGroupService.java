package com.perfect.service;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-09.
 */
public interface KeywordGroupService {

    Map<String, Object> getKRResult(String seedWord);

    Map<String, Object> getKRResult(List<String> seedWordList);

    Map<String, Object> getKeywordFromBaidu(List<String> seedWordList, int skip, int limit, String reportId);
}
