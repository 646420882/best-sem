package com.perfect.service;

import com.perfect.autosdk.sms.v3.Quality10Type;

import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-11-24.
 * 2014-11-24 refactor
 */
public interface Keyword10QualityService {

    Map<Long, Quality10Type> getKeyword10Quality(List<Long> keywordIds);
}
