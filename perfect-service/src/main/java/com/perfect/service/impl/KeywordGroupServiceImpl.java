package com.perfect.service.impl;

import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import com.perfect.api.baidu.BaiduServiceSupport;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.keyword.KeywordGroupDAO;
import com.perfect.dto.baidu.BaiduKeywordDTO;
import com.perfect.dto.baidu.KRResultDTO;
import com.perfect.dto.keyword.LexiconDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.service.KeywordGroupService;
import com.perfect.utils.TopN;
import com.perfect.utils.json.JSONUtils;
import com.perfect.utils.json.SerializeUtils;
import com.perfect.utils.paging.PagerInfo;
import com.perfect.utils.redis.JRedisUtils;
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
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.isNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created on 2014-08-09.
 *
 * @author dolphineor
 * @update 2015-09-28
 */
@Service("keywordGroupService")
public class KeywordGroupServiceImpl implements KeywordGroupService {

    // CSV's default delimiter is ','
    private static final String DEFAULT_DELIMITER = ",";
    // Mark a new line
    private static final String DEFAULT_END = "\r\n";

    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    private String _krFileId;

    private int resultSize;


    @Resource
    private KeywordGroupDAO keywordGroupDAO;

    @Resource
    private AccountManageDAO accountManageDAO;


    public Map<String, Object> getKeywordFromBaidu(List<String> seedWordList, int skip, int limit, String krFileId, int sort, String fieldName) {
        if (isNull(krFileId) || Objects.equals("", krFileId)) {
            Map<String, Object> map = getKRResult(seedWordList, skip, limit, sort, fieldName);
            map.put(KR_FIELD_ID, _krFileId);
            map.put(RESULT_TOTAL, resultSize);
            return map;
        } else {
            List<BaiduKeywordDTO> list = new ArrayList<>();
            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                if (jedis.ttl(SerializeUtils.serialize(krFileId)) == -1) {
                    Map<String, Object> map = getKRResult(seedWordList, skip, limit, sort, fieldName);
                    map.put(KR_FIELD_ID, _krFileId);
                    map.put(RESULT_TOTAL, resultSize);
                    return map;
                }
                list = SerializeUtils.deSerializeList(jedis.get(SerializeUtils.serialize(krFileId)), BaiduKeywordDTO.class);
            } catch (final Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }

            if (list == null)
                return Collections.emptyMap();

            //sort
            int s = list.size();
            BaiduKeywordDTO[] keywordVOs = TopN.getTopN(list.toArray(new BaiduKeywordDTO[s]), s, fieldName, sort);
            List<BaiduKeywordDTO> voList = paging(keywordVOs, skip, limit);
            Map<String, Object> map = JSONUtils.getJsonMapData(voList);
            map.put(KR_FIELD_ID, krFileId);
            map.put(RESULT_TOTAL, resultSize);
            return map;
        }
    }

    public Map<String, Object> getKeywordFromSystem(String trade, List<String> categories, List<String> groups, int skip, int limit, int status) {
        //查询参数
        Map<String, Object> params = Maps.newHashMap();
        params.put(LEXICON_TRADE, trade);

        if (categories != null && !categories.isEmpty()) {
            params.put(LEXICON_CATEGORY, categories);
        }

        if (groups != null && !groups.isEmpty()) {
            params.put(LEXICON_GROUP, groups);
        }

        List<LexiconDTO> list = keywordGroupDAO.find(params, skip, limit);
        Map<String, Object> values = Maps.newHashMap(JSONUtils.getJsonMapData(list));
        long total = keywordGroupDAO.getCurrentRowsSize(params);
        values.put(RESULT_TOTAL, total);

        return values;
    }

    public void downloadCSV(String trade, List<String> categories, List<String> groups, OutputStream os) {
        //查询参数
        Map<String, Object> params = Maps.newHashMap();
        params.put(LEXICON_TRADE, trade);

        if (categories != null && !categories.isEmpty()) {
            params.put(LEXICON_CATEGORY, categories);
        }
        if (groups != null && !groups.isEmpty()) {
            params.put(LEXICON_GROUP, groups);
        }

        List<LexiconDTO> list = keywordGroupDAO.find(params, -1, -1);

        //CSV文件写入
        try {
            os.write(Bytes.concat(commonCSVHead, ("行业" + DEFAULT_DELIMITER +
                    "计划" + DEFAULT_DELIMITER +
                    "单元" + DEFAULT_DELIMITER +
                    "关键词" + DEFAULT_END).getBytes(UTF_8)));
            for (LexiconDTO entity : list) {
                String bytes = (entity.getTrade() + DEFAULT_DELIMITER +
                        entity.getCategory() + DEFAULT_DELIMITER +
                        entity.getGroup() + DEFAULT_DELIMITER +
                        entity.getKeyword() + DEFAULT_END);
                os.write(Bytes.concat(commonCSVHead, bytes.getBytes(UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadBaiduCSV(List<String> seedWordList, String krFileId, OutputStream os) {
        List<BaiduKeywordDTO> dtoList = new ArrayList<>();
        if (krFileId == null) {
            dtoList = getKRResult(seedWordList);
        } else {
            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                if (jedis.ttl(SerializeUtils.serialize(krFileId)) == -1) {
                    dtoList = getKRResult(seedWordList);
                }
                dtoList = SerializeUtils.deSerializeList(jedis.get(SerializeUtils.serialize(krFileId)), BaiduKeywordDTO.class);
            } catch (final Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }

        if (dtoList == null || dtoList.isEmpty())
            return;

        //CSV文件写入
        try {
            os.write(Bytes.concat(commonCSVHead, ("分组" + DEFAULT_DELIMITER +
                    "种子词" + DEFAULT_DELIMITER +
                    "关键词" + DEFAULT_DELIMITER +
                    "日均搜索量" + DEFAULT_DELIMITER +
                    "竞争激烈程度" + DEFAULT_END).getBytes(UTF_8)));
            for (BaiduKeywordDTO entity : dtoList) {
                String bytes = (entity.getGroupName() + DEFAULT_DELIMITER +
                        entity.getSeedWord() + DEFAULT_DELIMITER +
                        entity.getKeywordName() + DEFAULT_DELIMITER +
                        entity.getDsQuantity() + DEFAULT_DELIMITER +
                        entity.getCompetition() + "%" + DEFAULT_END);
                os.write(Bytes.concat(commonCSVHead, bytes.getBytes(UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> findCategories(String trade) {
        return JSONUtils.getJsonMapData(keywordGroupDAO.findCategories(trade));
    }

    @Override
    public Map<String, Object> findKeywordByCategories(List<String> categories) {
        return JSONUtils.getJsonMapData(keywordGroupDAO.findSecondDirectoryByCategories(categories));
    }

    @Override
    public Map<String, Object> getKRbySeedWord(String seedWord) {
        KRService krService = getKRService();
        if (krService == null)
            return Collections.emptyMap();

        GetKRbySeedWordRequest getKRbySeedWordRequest = new GetKRbySeedWordRequest();
        getKRbySeedWordRequest.setSeedWord(seedWord);
        getKRbySeedWordRequest.setDevice(1);
        GetKRbySeedWordResponse getKRbySeedWordResponse = krService.getKRbySeedWord(getKRbySeedWordRequest);
        List<KRResult> list = getKRbySeedWordResponse.getKrResult();
        List<KRResultDTO> results = list.stream().map(e -> {
            KRResultDTO krResultDTO = new KRResultDTO();
            krResultDTO.setWord(e.getWord());
            krResultDTO.setExactPV(e.getExactPV());
            krResultDTO.setCompetition(e.getCompetition());
            krResultDTO.setFlag1(e.getFlag1());
            return krResultDTO;
        }).collect(Collectors.toList());

        return JSONUtils.getJsonMapData(results);
    }

    @Override
    public Map<String, Object> findTr() {
        return JSONUtils.getJsonMapData(keywordGroupDAO.findTr());
    }

    @Override
    public int saveTrade(String tr, String cg, String gr, String kw, String url) {
        LexiconDTO lexiconDTO = new LexiconDTO();
        lexiconDTO.setTrade(tr);
        lexiconDTO.setCategory(cg);
        lexiconDTO.setGroup(gr);
        lexiconDTO.setKeyword(kw);
        lexiconDTO.setUrl(url);
        return keywordGroupDAO.saveTrade(lexiconDTO);
    }

    @Override
    public PagerInfo findByPager(Map<String, Object> params, int page, int limit) {
        return keywordGroupDAO.findByPager(params, page, limit);
    }

    @Override
    public void deleteByParams(String trade, String keyword) {
        keywordGroupDAO.deleteByParams(trade, keyword);
    }

    @Override
    public void updateByParams(Map<String, Object> mapParams) {
        keywordGroupDAO.updateByParams(mapParams);
    }

    public Map<String, Object> getKRResult(List<String> seedWordList, int skip, int limit, int sort, String fieldName) {
        KRService krService = getKRService();
        if (krService == null)
            return Collections.emptyMap();

        GetKRFileIdbySeedWordRequest getKRFileIdbySeedWordRequest = new GetKRFileIdbySeedWordRequest();
        getKRFileIdbySeedWordRequest.setSeedWords(seedWordList);
        GetKRFileIdbySeedWordResponse getKRFileIdbySeedWordResponse = krService.getKRFileIdbySeedWord(getKRFileIdbySeedWordRequest);
        String krFileId = getKRFileIdbySeedWordResponse.getKrFileId();
        Map<String, Object> attributes = null;
        String krFilePath;

        //检查关键词推荐结果文件是否生成
        for (int i = 0; i < 10; i++) {
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
                List<BaiduKeywordDTO> keywordList = httpFileHandler(krFilePath);
                int s = keywordList.size();
                BaiduKeywordDTO[] sortedKeywords = TopN.getTopN(keywordList.toArray(new BaiduKeywordDTO[s]), s, fieldName, sort);
                List<BaiduKeywordDTO> list = paging(sortedKeywords, skip, limit);
                attributes = JSONUtils.getJsonMapData(list);
                break;
            }
            try {
                MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return attributes;
    }

    private KRService getKRService() {
        ModuleAccountInfoDTO baiduAccount = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService service = BaiduServiceSupport.getCommonService(baiduAccount.getBaiduUserName(), baiduAccount.getBaiduPassword(), baiduAccount.getToken());
        try {
            if (service != null)
                return service.getService(KRService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * HttpClient文件处理
     *
     * @param url
     * @return
     */
    private List<BaiduKeywordDTO> httpFileHandler(String url) {
        List<BaiduKeywordDTO> keywordList = new ArrayList<>();
        //create HTTP GET request
        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpClient closeableHttpClient = HttpClients.createDefault(); CloseableHttpResponse response = closeableHttpClient.execute(httpGet)) {
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
                String competitionStr = arr[5].substring(0, arr[5].indexOf("%"));
                keywordList.add(new BaiduKeywordDTO(arr[8], arr[0], arr[1], Integer.valueOf(arr[4]), Double.valueOf(competitionStr), arr[9], arr[10]));
                index++;
            }
            this.resultSize = keywordList.size();
            saveToRedis(SerializeUtils.serialize(_krFileId), SerializeUtils.serialize(keywordList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keywordList;
    }

    private void saveToRedis(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            jedis.set(key, value);
            jedis.expire(key, 600);
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
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
            return Collections.emptyMap();
        }

        Map<String, String> sortMap = new TreeMap<>(String::compareTo);
        sortMap.putAll(map);
        return sortMap;
    }

    //分页
    private List<BaiduKeywordDTO> paging(BaiduKeywordDTO[] list, int skip, int limit) {
        List<BaiduKeywordDTO> voList = new ArrayList<>(limit);
        int size = list.length, start = skip * limit, end = (skip + 1) * limit;
        for (int i = start; i < end; i++) {
            if (i == size)
                break;

            voList.add(list[i]);
        }

        return voList;
    }

    private List<BaiduKeywordDTO> getKRResult(List<String> seedWordList) {
        KRService krService = getKRService();
        if (krService == null)
            return Collections.emptyList();

        GetKRFileIdbySeedWordRequest getKRFileIdbySeedWordRequest = new GetKRFileIdbySeedWordRequest();
        getKRFileIdbySeedWordRequest.setSeedWords(seedWordList);
        GetKRFileIdbySeedWordResponse getKRFileIdbySeedWordResponse = krService.getKRFileIdbySeedWord(getKRFileIdbySeedWordRequest);
        String krFileId = getKRFileIdbySeedWordResponse.getKrFileId();
        List<BaiduKeywordDTO> results = null;
        String krFilePath;

        //检查关键词推荐结果文件是否生成
        for (int i = 0; i < 5; i++) {
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
                results = httpFileHandler(krFilePath);
                break;
            }

            try {
                MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

}