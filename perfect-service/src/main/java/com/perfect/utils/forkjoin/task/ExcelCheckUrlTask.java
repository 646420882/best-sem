package com.perfect.utils.forkjoin.task;

import com.perfect.utils.vo.CSVUrlEntity;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

/**
 * Created by XiaoWei on 2014/8/13.
 */
public class ExcelCheckUrlTask extends RecursiveTask<List<CSVUrlEntity>> {
    private static final int thread = 25;

    private int start;
    private int last;
    private List<CSVUrlEntity> urlList;
    private List<CSVUrlEntity> finalList;

    public ExcelCheckUrlTask(int start, int last, List<CSVUrlEntity> urlList) {
        this.start = start;
        this.last = last;
        this.urlList = urlList;
    }

    @Override
    protected List<CSVUrlEntity> compute() {
        List<CSVUrlEntity> fatcList = new LinkedList<>();
        if ((last - last) < thread) {
            for (int i = start; i < last; i++) {
                MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
                HttpClient client = new HttpClient(connectionManager);
                GetMethod get = new GetMethod(urlList.get(i).getKeywordURL());
                try {
                    client.executeMethod(get);
                    String factURL = get.getURI().toString();
                    if (factURL.indexOf("&tourl=") > -1) {
                        factURL = factURL.split("&tourl=")[1];
                    }
                    CSVUrlEntity csvUrlEntity=urlList.get(i);
                    csvUrlEntity.setFactURL(factURL);
                    fatcList.add(csvUrlEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    get.releaseConnection();
                }
            }
            return fatcList;
        }else{
            int middel=(last+start)/2;
            ExcelCheckUrlTask left=new ExcelCheckUrlTask(start,middel,urlList);
            ExcelCheckUrlTask right=new ExcelCheckUrlTask(middel,last,urlList);
            left.fork();
            right.fork();
            fatcList.addAll(left.join());
            fatcList.addAll(right.join());
            return fatcList;
        }

    }
}
