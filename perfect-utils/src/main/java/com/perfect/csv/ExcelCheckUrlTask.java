package com.perfect.csv;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by XiaoWei on 2014/8/13.
 */
public class ExcelCheckUrlTask extends RecursiveTask<List<ExcelCheckUrlTask.CSVUrlEntity>> {
    private static final int thread = 25;

    private int start;
    private int last;
    private List<CSVUrlEntity> urlList;
    private List<CSVUrlEntity> finalList;

    private CloseableHttpClient httpClient;

    public ExcelCheckUrlTask(int start, int last, List<CSVUrlEntity> urlList) {
        this.start = start;
        this.last = last;
        this.urlList = urlList;

        httpClient = HttpClients.createDefault();
    }

    @Override
    protected List<CSVUrlEntity> compute() {
        List<CSVUrlEntity> fatcList = new LinkedList<>();
        if ((last - last) < thread) {

            for (int i = start; i < last; i++) {


                HttpGet get = new HttpGet(urlList.get(i).getKeywordURL());
                try {
                    httpClient.execute(get);

                    String factURL = get.getURI().toString();
                    if (factURL.indexOf("&tourl=") > -1) {
                        factURL = factURL.split("&tourl=")[1];
                    }
                    CSVUrlEntity csvUrlEntity = urlList.get(i);
                    csvUrlEntity.setFactURL(factURL);
                    fatcList.add(csvUrlEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    get.releaseConnection();
                }
            }
            return fatcList;
        } else {
            int middel = (last + start) / 2;
            ExcelCheckUrlTask left = new ExcelCheckUrlTask(start, middel, urlList);
            ExcelCheckUrlTask right = new ExcelCheckUrlTask(middel, last, urlList);
            left.fork();
            right.fork();
            fatcList.addAll(left.join());
            fatcList.addAll(right.join());
            return fatcList;
        }

    }
    protected static class CSVUrlEntity {
        private Integer lineNumber;
        private String planName;
        private String unitName;
        private String keyword;
        private String keywordURL;
        private String factURL;

        public Integer getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(Integer lineNumber) {
            this.lineNumber = lineNumber;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeywordURL() {
            return keywordURL;
        }

        public void setKeywordURL(String keywordURL) {
            this.keywordURL = keywordURL;
        }

        public String getFactURL() {
            return factURL;
        }

        public void setFactURL(String factURL) {
            this.factURL = factURL;
        }

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public String getUnitName() {
            return unitName;
        }

        public void setUnitName(String unitName) {
            this.unitName = unitName;
        }
    }

}
