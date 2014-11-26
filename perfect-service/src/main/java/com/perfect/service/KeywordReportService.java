package com.perfect.service;

import com.perfect.dao.MongoCrudRepository;
import com.perfect.dto.keyword.KeywordReportDTO;
import com.perfect.entity.KeywordReportEntity;
import com.perfect.utils.PagerInfo;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014-9-17.
 */
public interface KeywordReportService  {
    PagerInfo findByPagerInfo(Map<String, Object> params);
    void downAccountCSV(OutputStream os,List<KeywordReportDTO> list);
    List<KeywordReportDTO> getAll(Map<String,Object> params);
}
