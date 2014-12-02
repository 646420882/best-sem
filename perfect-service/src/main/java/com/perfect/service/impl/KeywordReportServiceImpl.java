package com.perfect.service.impl;

import com.google.common.primitives.Bytes;
import com.perfect.dao.KeywordReportDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.keyword.KeywordReportDTO;
import com.perfect.entity.report.KeywordReportEntity;
import com.perfect.service.KeywordReportService;
import com.perfect.utils.paging.Pager;
import com.perfect.utils.paging.PagerInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/17.
 * 2014-11-26 refactor
 */
@Service
public class KeywordReportServiceImpl extends AbstractUserBaseDAOImpl<KeywordReportEntity,Long> implements KeywordReportService {
    private static final String DEFAULT_DELIMITER = ",";
    private static final String DEFAULT_END = "\r\n";
    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
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

    @Override
    public void downAccountCSV(OutputStream os,List<KeywordReportDTO> list) {
        try {
            os.write(Bytes.concat(commonCSVHead, ("关键词" +
                    DEFAULT_DELIMITER + "展现" +
                    DEFAULT_DELIMITER + "点击量" +
                    DEFAULT_DELIMITER + "消费" +
                    DEFAULT_DELIMITER + "点击率" +
                    DEFAULT_DELIMITER + "平均点击价格" +
                    DEFAULT_DELIMITER + "平均排名" +
                    DEFAULT_DELIMITER + "质量度" +
                    DEFAULT_DELIMITER + "转化(页面)" +
                    DEFAULT_DELIMITER + "匹配" +
                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            for (KeywordReportDTO entity : list) {
                os.write(Bytes.concat(commonCSVHead, (entity.getKeywordName() +
                                DEFAULT_DELIMITER + entity.getPcImpression() +
                                DEFAULT_DELIMITER + entity.getPcClick() +
                                DEFAULT_DELIMITER + entity.getPcCost() +
                                DEFAULT_DELIMITER + entity.getPcCtr() + "%" +
                                DEFAULT_DELIMITER + entity.getPcCpc() +
                                DEFAULT_DELIMITER + entity.getPcPosition()+
                                DEFAULT_DELIMITER + entity.getQuality()+
                                DEFAULT_DELIMITER + entity.getPcConversion() +
                                DEFAULT_DELIMITER + getMatchTypeName(entity.getMatchType()) +
                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getMatchTypeName(Integer num){
        switch (num){
            case 1:
                return "精确匹配";
            case 2:
                return "短语匹配";
            case 3:
                return "广泛匹配";
            default:
                return "精确匹配";
        }
    }
    @Override
    public List<KeywordReportDTO> getAll(Map<String,Object> params) {
        return keywordReportDAO.getAll(params);
    }
}
