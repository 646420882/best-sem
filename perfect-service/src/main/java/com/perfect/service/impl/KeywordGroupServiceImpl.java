package com.perfect.service.impl;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.mongodb.utils.BaseBaiduService;
import com.perfect.redis.JRedisUtils;
import com.perfect.service.KeywordGroupService;
import com.perfect.utils.JSONUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-09.
 */
@Service("keywordGroupService")
public class KeywordGroupServiceImpl implements KeywordGroupService {

    private String _krFileId;

    public Map<String, Object> getKeywordFromBaidu(List<String> seedWordList, int skip, int limit, String krFileId) {
        if (krFileId == null) {
            Map<String, Object> map = getKRResult(seedWordList);
            map.put("krFileId", _krFileId);
            return map;
        } else {
            Map<String, String> redisMap = new LinkedHashMap<>();
            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                redisMap = jedis.hgetAll(krFileId);
                if (redisMap.size() == 0) {
                    Map<String, Object> map = getKRResult(seedWordList);
                    map.put("krFileId", _krFileId);
                    return map;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
            List<String> list = new ArrayList<>(redisMap.values());
            List<KeywordVO> voList = new ArrayList<>(limit);
            for (int i = skip; i < skip + limit; i++) {
                String[] arr = list.get(i).split(",");
                voList.add(new KeywordVO(arr[0], arr[1], arr[2]));
            }
            Map<String, Object> map = JSONUtils.getJsonMapData(voList);
            map.put("krFileId", krFileId);
            return map;
        }
    }

    public Map<String, Object> getKRResult(String seedWord) {
        KRService krService = getKRService();
        GetKRbySeedWordRequest getKRbySeedWordRequest = new GetKRbySeedWordRequest();
        getKRbySeedWordRequest.setSeedWord(seedWord);
        GetKRbySeedWordResponse getKRbySeedWordResponse = krService.getKRbySeedWord(getKRbySeedWordRequest);
        List<KRResult> krResult = getKRbySeedWordResponse.getKrResult();
        for (KRResult kr : krResult) {

        }
        return null;
    }

    public Map<String, Object> getKRResult(List<String> seedWordList) {
        KRService krService = getKRService();
        GetKRFileIdbySeedWordRequest getKRFileIdbySeedWordRequest = new GetKRFileIdbySeedWordRequest();
        getKRFileIdbySeedWordRequest.setSeedWords(seedWordList);
        GetKRFileIdbySeedWordResponse getKRFileIdbySeedWordResponse = krService.getKRFileIdbySeedWord(getKRFileIdbySeedWordRequest);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String krFileId = getKRFileIdbySeedWordResponse.getKrFileId();

        //检查关键词推荐结果文件是否生成
        GetKRFileStateRequest getKRFileStateRequest = new GetKRFileStateRequest();
        getKRFileStateRequest.setKrFileId(krFileId);
        GetKRFileStateResponse getKRFileStateResponse = krService.getKRFileState(getKRFileStateRequest);

        Map<String, Object> attributes = null;
        String krFilePath;

        if (getKRFileStateResponse.getIsGenerated().compareTo(3) == 0) {
            _krFileId = krFileId;
            GetKRFilePathRequest getKRFilePathRequest = new GetKRFilePathRequest();
            getKRFilePathRequest.setKrFileId(krFileId);
            GetKRFilePathResponse getKRFilePathResponse = krService.getKRFilePath(getKRFilePathRequest);
            krFilePath = getKRFilePathResponse.getFilePath();
            //拓展词文件解析
            Map<String, String> redisMap = httpFileHandler(krFilePath);
            List<String> list = new ArrayList<>(redisMap.values());
            list = list.subList(0, 10);
            List<KeywordVO> voList = new ArrayList<>();
            for (String str : list) {
                String[] arr = str.split(",");
                voList.add(new KeywordVO(arr[0], arr[1], arr[2]));
            }
            attributes = JSONUtils.getJsonMapData(voList);
        }
        return attributes;
    }

    private KRService getKRService() {
        CommonService service = BaseBaiduService.getCommonService();
        KRService krService = null;
        try {
            krService = service.getService(KRService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return krService;
    }


    private Map<String, String> httpFileHandler(String url) {
        Map<String, String> redisMap = new LinkedHashMap<>();
        //create HttpClient
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        //create HTTP GET request
        HttpGet httpGet = new HttpGet(url);

        try {
            //execute GET request
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            //get response body entity
            HttpEntity entity = response.getEntity();
            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "GBK"));
            String str;
            int index = -1;
            while ((str = br.readLine()) != null) {
                if (index == -1) {
                    index++;
                    continue;
                }
                String[] arr = str.split("\\t");
                redisMap.put(arr[1].hashCode() + "", arr[0] + "," + arr[1] + "," + arr[4]);
                index++;
            }
            saveToRedis(_krFileId, redisMap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return redisMap;
    }

    private void saveToRedis(String reportId, Map<String, String> redisMap) {
        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            jedis.hmset(reportId, redisMap);
            jedis.expire(reportId, 300);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }
    }

    class KeywordVO {

        private String seedWord;

        private String keywordName;

        private String dsQuantity;

        KeywordVO(String seedWord, String keywordName, String dsQuantity) {
            this.seedWord = seedWord;
            this.keywordName = keywordName;
            this.dsQuantity = dsQuantity;
        }

        public String getSeedWord() {
            return seedWord;
        }

        public void setSeedWord(String seedWord) {
            this.seedWord = seedWord;
        }

        public String getKeywordName() {
            return keywordName;
        }

        public void setKeywordName(String keywordName) {
            this.keywordName = keywordName;
        }

        public String getDsQuantity() {
            return dsQuantity;
        }

        public void setDsQuantity(String dsQuantity) {
            this.dsQuantity = dsQuantity;
        }
    }
}
