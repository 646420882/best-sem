package com.perfect.service.impl;

import com.google.common.primitives.Bytes;
import com.perfect.autosdk.sms.v3.KeywordService;
import com.perfect.dao.KeywordReportDAO;
import com.perfect.entity.AccountReportEntity;
import com.perfect.entity.KeywordReportEntity;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.utils.Pager;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.service.KeywordReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/17.
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
    public void downAccountCSV(OutputStream os,List<KeywordReportEntity> list) {
        try {
            os.write(Bytes.concat(commonCSVHead, ("关键词" +
                    DEFAULT_DELIMITER + "展现" +
                    DEFAULT_DELIMITER + "点击量" +
                    DEFAULT_DELIMITER + "消费" +
                    DEFAULT_DELIMITER + "点击率" +
                    DEFAULT_DELIMITER + "平均点击价格" +
                    DEFAULT_DELIMITER + "转化(页面)" +
                    DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            for (KeywordReportEntity entity : list) {
                os.write(Bytes.concat(commonCSVHead, (entity.getKeywordName() +
                                DEFAULT_DELIMITER + entity.getPcImpression() +
                                DEFAULT_DELIMITER + entity.getPcClick() +
                                DEFAULT_DELIMITER + entity.getPcCost() +
                                DEFAULT_DELIMITER + entity.getPcCtr() + "%" +
                                DEFAULT_DELIMITER + entity.getPcCpc() +
                                DEFAULT_DELIMITER + entity.getPcConversion() +
                                DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<KeywordReportEntity> getAll(Map<String,Object> params) {
        return keywordReportDAO.getAll(params);
    }
}
