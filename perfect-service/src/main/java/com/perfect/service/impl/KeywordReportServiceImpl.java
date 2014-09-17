package com.perfect.service.impl;

import com.perfect.autosdk.sms.v3.KeywordService;
import com.perfect.dao.KeywordReportDAO;
import com.perfect.entity.KeywordReportEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.service.KeywordReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/17.
 */
@Service
public class KeywordReportServiceImpl extends AbstractUserBaseDAOImpl<KeywordReportEntity,Long> implements KeywordReportService {

    @Resource
    private KeywordReportDAO keywordReportDAO;

    @Override
    public Class<KeywordReportEntity> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public PagerInfo findByPagerInfo(Map<String, Object> params) {
        return keywordReportDAO.findByPagerInfo(params);
    }
}
