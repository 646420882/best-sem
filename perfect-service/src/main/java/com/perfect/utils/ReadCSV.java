package com.perfect.utils;


import com.perfect.entity.CSVEntity;
import org.apache.log4j.Logger;
import org.supercsv.io.CsvListReader;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.*;

/**
 * Created by XiaoWei on 2014/8/7.
 */
public class ReadCSV implements Iterator<List<String>> {
    private static final Logger logger = Logger.getLogger(ReadCSV.class);
    private CsvListReader reader = null;
    private List<String> row = null;
    private String encoding;
    private String csvFile;
    public ReadCSV(String csvFile, String encoding) {
        super();
        try {
            this.encoding=encoding;
            this.csvFile=csvFile;
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
            if(reader.getLineNumber() == 0){//
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
    public void close(){
        if(reader != null){
            try {
                reader.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    public int getLineNumber(){
        return reader.getRowNumber();
    }

    /**
     * 获取list集合的对象
     * @return
     */
    public List<CSVEntity> getList(){
        List<CSVEntity> list=new ArrayList<>();
        while (hasNext()){
            List<String> row=next();
            CSVEntity csvEntity=new CSVEntity();
            csvEntity.setLineNumber(getLineNumber());
            csvEntity.setAccount(next().get(0));
            csvEntity.setPlan(next().get(1));
            csvEntity.setUnitName(next().get(2));
            csvEntity.setUnitPrice(Double.parseDouble(next().get(3)));
            csvEntity.setKeyword(next().get(4));
            csvEntity.setOutPrice(Double.parseDouble(next().get(5)));
            csvEntity.setThisMatchMode(next().get(6));
            csvEntity.setQuality(next().get(7));
            csvEntity.setLowestPrice(Double.parseDouble(next().get(8)));
            csvEntity.setAccountId(next().get(9));
            csvEntity.setPlanId(next().get(10));
            csvEntity.setUnitId(next().get(11));
            csvEntity.setKeywordId(next().get(12));
            list.add(csvEntity);
        }
        return list;
    }

    /**
     * 获取map对象的CSV集合
     * @return
     */
    public Map<Integer,CSVEntity> getMap(){
        Map<Integer,CSVEntity> map=new HashMap<>();
        while (hasNext()){
            List<String> row=next();
            CSVEntity csvEntity=new CSVEntity();
            csvEntity.setLineNumber(getLineNumber());
            csvEntity.setAccount(next().get(0));
            csvEntity.setPlan(next().get(1));
            csvEntity.setUnitName(next().get(2));
            csvEntity.setUnitPrice(Double.parseDouble(next().get(3)));
            csvEntity.setKeyword(next().get(4));
            csvEntity.setOutPrice(Double.parseDouble(next().get(5)));
            csvEntity.setThisMatchMode(next().get(6));
            csvEntity.setQuality(next().get(7));
            csvEntity.setLowestPrice(Double.parseDouble(next().get(8)));
            csvEntity.setAccountId(next().get(9));
            csvEntity.setPlanId(next().get(10));
            csvEntity.setUnitId(next().get(11));
            csvEntity.setKeywordId(next().get(12));
            map.put(getLineNumber(),csvEntity);
        }
        return map;
    }

}
