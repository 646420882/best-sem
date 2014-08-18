package com.perfect.service.impl;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dao.AdgroupDAO;
import com.perfect.dao.KeywordDAO;
import com.perfect.entity.AdgroupEntity;
import com.perfect.entity.KeywordEntity;
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

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by baizz on 2014-08-09.
 */
@Service("keywordGroupService")
public class KeywordGroupServiceImpl implements KeywordGroupService {

    @Resource
    private KeywordDAO keywordDAO;

    @Resource
    private AdgroupDAO adgroupDAO;

    private String _krFileId;

    private int _skip;

    private int _limit;

    private int redisMapSize;

    public Map<String, Object> getKeywordFromBaidu(List<String> seedWordList, int skip, int limit, String krFileId) {
        _skip = skip;
        _limit = limit;
        if (krFileId == null) {
            Map<String, Object> map = getKRResult(seedWordList);
            map.put("krFileId", _krFileId);
            map.put("total", redisMapSize);
            return map;
        } else {
            Map<String, String> redisMap = new LinkedHashMap<>();
            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                if (jedis.ttl(krFileId) == -1) {
                    Map<String, Object> map = getKRResult(seedWordList);
                    map.put("krFileId", _krFileId);
                    map.put("total", redisMapSize);
                    return map;
                }
                //sort
                redisMap = sortMapByKey(jedis.hgetAll(krFileId));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
            List<String> list = new ArrayList<>(redisMap.values());
            List<KeywordVO> voList = paging(list, skip, limit);
            Map<String, Object> map = JSONUtils.getJsonMapData(voList);
            map.put("krFileId", krFileId);
            map.put("total", redisMapSize);
            return map;
        }
    }

    public Map<String, Object> getKRResult(String seedWord) {
        KRService krService = getKRService();
        GetKRbySeedWordRequest getKRbySeedWordRequest = new GetKRbySeedWordRequest();
        getKRbySeedWordRequest.setSeedWord(seedWord);
        GetKRbySeedWordResponse getKRbySeedWordResponse = krService.getKRbySeedWord(getKRbySeedWordRequest);
        List<KRResult> krResult = getKRbySeedWordResponse.getKrResult();
        return null;
    }

    public Map<String, Object> getKRResult(List<String> seedWordList) {
        KRService krService = getKRService();
        GetKRFileIdbySeedWordRequest getKRFileIdbySeedWordRequest = new GetKRFileIdbySeedWordRequest();
        getKRFileIdbySeedWordRequest.setSeedWords(seedWordList);
        GetKRFileIdbySeedWordResponse getKRFileIdbySeedWordResponse = krService.getKRFileIdbySeedWord(getKRFileIdbySeedWordRequest);
        String krFileId = getKRFileIdbySeedWordResponse.getKrFileId();
        Map<String, Object> attributes = null;
        String krFilePath;

        //检查关键词推荐结果文件是否生成
        for (int i = 0; i < 3; i++) {
            GetKRFileStateRequest getKRFileStateRequest = new GetKRFileStateRequest();
            getKRFileStateRequest.setKrFileId(krFileId);
            GetKRFileStateResponse getKRFileStateResponse = krService.getKRFileState(getKRFileStateRequest);
            if (getKRFileStateResponse.getIsGenerated().compareTo(3) == 0) {
                _krFileId = krFileId;
                GetKRFilePathRequest getKRFilePathRequest = new GetKRFilePathRequest();
                getKRFilePathRequest.setKrFileId(krFileId);
                GetKRFilePathResponse getKRFilePathResponse = krService.getKRFilePath(getKRFilePathRequest);
                krFilePath = getKRFilePathResponse.getFilePath();
                //拓展词文件解析
                Map<String, String> redisMap = httpFileHandler(krFilePath);
                this.redisMapSize = redisMap.size();
                List<String> list = new ArrayList<>(redisMap.values());
                List<KeywordVO> voList = paging(list, _skip, _limit);
                attributes = JSONUtils.getJsonMapData(voList);
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return attributes;
    }

    //基于百度的关键词自动分组
    public Map<String, Object> autoGroupByBaidu(List<String> words) {
        KRService krService = getKRService();
        GetAdGroupBySeedWordsRequest request = new GetAdGroupBySeedWordsRequest();
        request.setSeedWords(words);
        request.setAdGroupIds(null);
        GetAdGroupBySeedWordsResponse response = krService.getAdGroupBySeedWords(request);
        List<AutoAdGroupResult> list = response.getAutoAdGroupResults();
        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        return values;
    }

    public void addKeywords(List<AdgroupEntity> list1, List<KeywordEntity> list2) {
        if (list1 != null)
            adgroupDAO.insertAll(list1);
        else if (list2 != null)
            keywordDAO.insertAll(list2);
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


    /**
     * HttpClient文件处理
     *
     * @param url
     * @return
     */
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
            long index = 2_147_483_647;
            while ((str = br.readLine()) != null) {
                if (index == 2_147_483_647) {
                    index++;
                    continue;
                }
                String[] arr = str.split("\\t");
                redisMap.put(index + "", arr[8] + "," + arr[0] + "," + arr[1] + "," +
                        arr[4] + "," + arr[5] + "," + arr[9] + "," + arr[10]);
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

    private void saveToRedis(String krFileId, Map<String, String> redisMap) {
        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            jedis.hmset(krFileId, redisMap);
            jedis.expire(krFileId, 300);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }
    }

    /**
     * 对Map按key进行排序
     *
     * @param map
     * @return
     */
    private Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    //Map,key比较器
    private class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }

    //分页
    private List<KeywordVO> paging(List<String> list, int skip, int limit) {
        List<KeywordVO> voList = new ArrayList<>(limit);
        for (int i = skip * limit; i < skip * limit + limit; i++) {
            String[] arr = list.get(i).split(",");
            voList.add(new KeywordVO(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6]));
        }

        return voList;
    }

    class KeywordVO {

        private String groupName;

        private String seedWord;

        private String keywordName;

        private String dsQuantity;      //日均展现量(精确)

        private String competition;     //竞争激烈程度

        private String recommendReason1;    //一级推荐理由

        private String recommendReason2;    //二级推荐理由

        KeywordVO(String groupName, String seedWord, String keywordName, String dsQuantity,
                  String competition, String recommendReason1, String recommendReason2) {
            this.groupName = groupName;
            this.seedWord = seedWord;
            this.keywordName = keywordName;
            this.dsQuantity = dsQuantity;
            this.competition = competition;
            this.recommendReason1 = recommendReason1;
            this.recommendReason2 = recommendReason2;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
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

        public String getCompetition() {
            return competition;
        }

        public void setCompetition(String competition) {
            this.competition = competition;
        }

        public String getRecommendReason1() {
            return recommendReason1;
        }

        public void setRecommendReason1(String recommendReason1) {
            this.recommendReason1 = recommendReason1;
        }

        public String getRecommendReason2() {
            return recommendReason2;
        }

        public void setRecommendReason2(String recommendReason2) {
            this.recommendReason2 = recommendReason2;
        }
    }
}
