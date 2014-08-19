package com.perfect.service;

import com.perfect.entity.KeywordRealTimeDataVOEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by john on 2014/8/19.
 */
public interface ImportKeywordService {
    List<KeywordRealTimeDataVOEntity> getMap(HttpServletRequest request);
}
