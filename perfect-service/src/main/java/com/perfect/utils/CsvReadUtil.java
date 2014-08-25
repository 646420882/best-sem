package com.perfect.utils;


import com.perfect.entity.KeywordEntity;
import com.perfect.utils.vo.CSVTotalEntity;
import com.perfect.utils.vo.CSVUrlEntity;
import org.apache.log4j.Logger;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.*;

/**
 * Created by XiaoWei on 2014/8/7.
 */
public class CsvReadUtil implements Iterator<List<String>> {
    private static final Logger logger = Logger.getLogger(CsvReadUtil.class);
    public static final String ENCODING_GBK = "GBK";
    private CsvListReader reader = null;
    private List<String> row = null;
    private String encoding;
    private String csvFile;

    public CsvReadUtil(String csvFile, String encoding) {
        super();
        try {
            this.encoding = encoding;
            this.csvFile = csvFile;
            reader = new CsvListReader(new InputStreamReader(new FileInputStream(csvFile), encoding), CsvPreference.EXCEL_PREFERENCE);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean hasNext() {
        try {
            if (reader.getLineNumber() == 0) {
                row = reader.read();
            }
            row = reader.read();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return row != null;
    }

    @Override
    public List<String> next() {
        return row;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("本CSV解析器是只读的.");
    }

    public void close() {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public int getLineNumber() {
        return reader.getRowNumber();
    }

    /**
     * 获取关键词CSV文件的list集合的对象
     *
     * @return
     */
    public List<KeywordEntity> getList() {
        List<KeywordEntity> list = new LinkedList<>();
        while (hasNext()) {
            KeywordEntity keywordEntity = new KeywordEntity();
            keywordEntity.setKeywordId(Long.valueOf(next().get(12)));
            keywordEntity.setAdgroupId(Long.valueOf(next().get(11)));
            keywordEntity.setKeyword(next().get(4));
            keywordEntity.setPrice(Double.valueOf(next().get(5)));
            keywordEntity.setPcDestinationUrl(null);
            keywordEntity.setMobileDestinationUrl(null);
            keywordEntity.setMatchType(getMatchType(next().get(6)));
            keywordEntity.setPause(false);
            keywordEntity.setStatus(42);
            keywordEntity.setAccountId(Long.valueOf(next().get(9)));
            list.add(keywordEntity);
        }
        close();
        return list;
    }

    private Integer getMatchType(String matchType) {
        Integer matchTypeInteger = 1;
        switch (matchType) {
            case "高级短语匹配":
                matchTypeInteger = 2;
                break;
            case "广泛匹配":
                matchTypeInteger = 3;
                break;
            default:
                return 1;
        }
        return matchTypeInteger;
    }

    /**
     * 获取关键词CSV文件的map对象的CSV集合
     *
     * @return
     */
    public Map<Integer, KeywordEntity> getMap() {
        Map<Integer, KeywordEntity> map = new HashMap<>();
        while (hasNext()) {
            KeywordEntity keywordEntity = new KeywordEntity();
            keywordEntity.setKeywordId(Long.valueOf(next().get(12)));
            keywordEntity.setAdgroupId(Long.valueOf(next().get(11)));
            keywordEntity.setKeyword(next().get(4));
            keywordEntity.setPrice(Double.valueOf(next().get(5)));
            keywordEntity.setPcDestinationUrl(null);
            keywordEntity.setMobileDestinationUrl(null);
            keywordEntity.setMatchType(getMatchType(next().get(6)));
            keywordEntity.setPause(false);
            keywordEntity.setStatus(42);
            keywordEntity.setAccountId(Long.valueOf(next().get(9)));
            map.put(getLineNumber(), keywordEntity);
        }
        close();
        return map;
    }



}
