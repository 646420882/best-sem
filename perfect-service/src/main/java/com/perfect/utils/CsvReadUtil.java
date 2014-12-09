package com.perfect.utils;


import com.perfect.dto.keyword.KeywordDTO;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by XiaoWei on 2014/8/7.
 */
public class CsvReadUtil implements Iterator<List<String>> {
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
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
                e.printStackTrace();
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
    public List<KeywordDTO> getList() {
        List<KeywordDTO> list = new LinkedList<>();
        while (hasNext()) {
            KeywordDTO keywordEntity = new KeywordDTO();
            keywordEntity.setKeywordId(Long.valueOf(next().get(12)));
            keywordEntity.setAdgroupId(Long.valueOf(next().get(11)));
            keywordEntity.setKeyword(next().get(4));
            keywordEntity.setPrice(BigDecimal.valueOf(Double.valueOf(next().get(5))));
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
    public Map<Integer, KeywordDTO> getMap() {
        Map<Integer, KeywordDTO> map = new HashMap<>();
        while (hasNext()) {
            KeywordDTO keywordEntity = new KeywordDTO();
            keywordEntity.setKeywordId(Long.valueOf(next().get(12)));
            keywordEntity.setAdgroupId(Long.valueOf(next().get(11)));
            keywordEntity.setKeyword(next().get(4));
            keywordEntity.setPrice(BigDecimal.valueOf(Double.valueOf(next().get(5))));
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
