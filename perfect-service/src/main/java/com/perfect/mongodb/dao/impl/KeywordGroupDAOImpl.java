package com.perfect.mongodb.dao.impl;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dao.KeywordGroupDAO;
import com.perfect.mongodb.utils.BaseBaiduService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by baizz on 2014-08-06.
 */
public class KeywordGroupDAOImpl implements KeywordGroupDAO {

    private CommonService service = BaseBaiduService.getCommonService();

    //输入一个种子词, 获取推荐词
    public List<KRResult> getKRResult(String aSeedWord) {
        KRService krService = null;
        List<KRResult> krResult;
        try {
            krService = service.getService(KRService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }

        GetKRbySeedWordRequest getKRbySeedWordRequest = new GetKRbySeedWordRequest();
        getKRbySeedWordRequest.setSeedWord(aSeedWord);
        SeedFilter filter = new SeedFilter();
        //广泛匹配模式
        filter.setMatchType(3);
        getKRbySeedWordRequest.setSeedFilter(filter);
        GetKRbySeedWordResponse getKRbySeedWordResponse = krService.getKRbySeedWord(getKRbySeedWordRequest);
        krResult = getKRbySeedWordResponse.getKrResult();
        return krResult;

    }

    //输入多个种子词, 获取推荐词
    public String getKRFilePath(List<String> seedWordList) throws InterruptedException {
        KRService krService = null;
        String krFilePath = null;
        try {
            krService = service.getService(KRService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        GetKRFileIdbySeedWordRequest getKRFileIdbySeedWordRequest = new GetKRFileIdbySeedWordRequest();
        getKRFileIdbySeedWordRequest.setSeedWords(seedWordList);
        SeedFilter filter = new SeedFilter();
        //广泛匹配模式
        filter.setMatchType(3);
        getKRFileIdbySeedWordRequest.setSeedFilter(filter);
        GetKRFileIdbySeedWordResponse getKRFileIdbySeedWordResponse = krService.getKRFileIdbySeedWord(getKRFileIdbySeedWordRequest);
        String krFileId = getKRFileIdbySeedWordResponse.getKrFileId();

        //检查关键词推荐结果文件是否生成
        GetKRFileStateRequest getKRFileStateRequest = new GetKRFileStateRequest();
        getKRFileStateRequest.setKrFileId(krFileId);
        Thread.sleep(1000);
        GetKRFileStateResponse getKRFileStateResponse = krService.getKRFileState(getKRFileStateRequest);
        if (getKRFileStateResponse.getIsGenerated() == 3) {
            GetKRFilePathRequest getKRFilePathRequest = new GetKRFilePathRequest();
            getKRFilePathRequest.setKrFileId(krFileId);
            GetKRFilePathResponse getKRFilePathResponse = krService.getKRFilePath(getKRFilePathRequest);
            krFilePath = getKRFilePathResponse.getFilePath();
            //网络文件下载, 解析
            httpFileHandler(krFilePath);
            return krFilePath;
        }

        return krFilePath;
    }

    private void httpFileHandler(String url) {
        //create HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        //create HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpGet httpGet = new HttpGet(url);

        try {
            //execute GET request
            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            //get response body entity
            HttpEntity entity = httpResponse.getEntity();

            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
            int index = 0;
            String str;
            while ((str = br.readLine()) != null) {
                String[] arr = str.split("\\t");
                System.out.println(arr[0] + "," + arr[1] + "," + arr[2]);
                index++;
                if (index == 100) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
