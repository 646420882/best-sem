package com.perfect.service.impl;

import com.google.common.collect.Maps;
import com.google.common.primitives.Bytes;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.dto.BaiduKeywordDTO;
import com.perfect.dto.KRResultDTO;
import com.perfect.entity.*;
import com.perfect.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.mongodb.base.BaseMongoTemplate;
import com.perfect.mongodb.dao.impl.KeywordGroupDAOImpl;
import com.perfect.mongodb.utils.Pager;
import com.perfect.mongodb.utils.PagerInfo;
import com.perfect.redis.JRedisUtils;
import com.perfect.service.KeywordGroupService;
import com.perfect.utils.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.perfect.mongodb.utils.EntityConstants.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

/**
 * Created by baizz on 2014-08-09.
 */
@Service("keywordGroupService")
public class KeywordGroupServiceImpl extends AbstractUserBaseDAOImpl implements KeywordGroupService {

    // CSV's default delimiter is ','
    private static final String DEFAULT_DELIMITER = ",";
    // Mark a new line
    private static final String DEFAULT_END = "\r\n";

    private static final byte commonCSVHead[] = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    private String _krFileId;

    private int resultSize;

    @Resource
    private KeywordGroupDAOImpl keywordGroupDAO;

    @Resource
    private AccountManageDAO<BaiduAccountInfoEntity> accountManageDAO;

    public Map<String, Object> getKeywordFromBaidu(List<String> seedWordList, int skip, int limit, String krFileId, int sort, String fieldName) {
        if (krFileId == null || "".equals(krFileId)) {
            Map<String, Object> map = getKRResult(seedWordList, skip, limit, sort, fieldName);
            map.put("krFileId", _krFileId);
            map.put("total", resultSize);
            return map;
        } else {
            List<BaiduKeywordDTO> list = new ArrayList<>();
            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                if (jedis.ttl(SerializeUtils.serialize(krFileId)) == -1) {
                    Map<String, Object> map = getKRResult(seedWordList, skip, limit, sort, fieldName);
                    map.put("krFileId", _krFileId);
                    map.put("total", resultSize);
                    return map;
                }
                list = SerializeUtils.deSerializeList(jedis.get(SerializeUtils.serialize(krFileId)), BaiduKeywordDTO.class);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
            //sort
            int s = list.size();
            BaiduKeywordDTO[] keywordVOs = TopN.getTopN(list.toArray(new BaiduKeywordDTO[s]), s, fieldName, sort);
            List<BaiduKeywordDTO> voList = paging(keywordVOs, skip, limit);
            Map<String, Object> map = JSONUtils.getJsonMapData(voList);
            map.put("krFileId", krFileId);
            map.put("total", resultSize);
            return map;
        }
    }

    public Map<String, Object> getKeywordFromSystem(String trade, String category, int skip, int limit, int status) {
        //查询参数
        Map<String, Object> params = new HashMap<>();
        if (trade != null) {
            params.put("tr", trade);
        }
        if (category != null) {
            params.put("cg", category);
        }

        List<LexiconEntity> list = keywordGroupDAO.find(params, skip, limit);
        Map<String, Object> values = Maps.newHashMap(JSONUtils.getJsonMapData(list));
        Integer total = keywordGroupDAO.getCurrentRowsSize(params);
        values.put("total", total);

//        //获取记录总数
//        if (status == 1) {//在进行分页操作
//            Jedis jedis = null;
//            try {
//                jedis = JRedisUtils.get();
//                if (jedis.ttl("keyword_total") == -1) {
//                    Integer total = keywordGroupDAO.getCurrentRowsSize(params);
//                    Map<String, String> tempMap = new HashMap<>();
//                    tempMap.put("total", total.toString());
//                    saveToRedis("keyword_total", tempMap);
//                    values.put("total", total);
//                }
//                values.put("total", jedis.hgetAll("keyword_total").get("total"));
//            } catch (final Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (jedis != null) {
//                    JRedisUtils.returnJedis(jedis);
//                }
//            }
//        } else {//在进行查询操作
//            Jedis jedis = null;
//            try {
//                jedis = JRedisUtils.get();
//                Integer total = keywordGroupDAO.getCurrentRowsSize(params);
//                Map<String, String> tempMap = new HashMap<>();
//                tempMap.put("total", total.toString());
//                saveToRedis("keyword_total", tempMap);
//                values.put("total", total);
//            } catch (final Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (jedis != null) {
//                    JRedisUtils.returnJedis(jedis);
//                }
//            }
//        }

        return values;
    }

    public void saveKeywordFromBaidu(List<String> seedWordList, String krFileId, String newCampaignName) {
        if (krFileId == null) {
            List<BaiduKeywordDTO> list = getKRResult(seedWordList);
            genericSave(list, newCampaignName);
        } else {
            List<BaiduKeywordDTO> list1 = new ArrayList<>();
            Jedis jedis = null;
            try {
                jedis = JRedisUtils.get();
                if (jedis.ttl(SerializeUtils.serialize(krFileId)) == -1) {
                    List<BaiduKeywordDTO> list = getKRResult(seedWordList);
                    genericSave(list, newCampaignName);
                    return;
                }
                list1 = SerializeUtils.deSerializeList(jedis.get(SerializeUtils.serialize(krFileId)), BaiduKeywordDTO.class);
            } catch (final Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
            genericSave(list1, newCampaignName);
        }
    }

    private void genericSave(List<BaiduKeywordDTO> list, String newCampaignName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long accountId = AppContext.getAccountId();

        if (mongoTemplate.findOne(Query.query(Criteria.where(ACCOUNT_ID).is(accountId).and(NAME).is(newCampaignName)), CampaignEntity.class) != null) {
            throw new DuplicateKeyException("\"" + newCampaignName + "\"" + " already exists");
        }

        //save a new campaign
        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setAccountId(accountId);
        campaignEntity.setCampaignName(newCampaignName);
        campaignEntity.setNegativeWords(new ArrayList<String>());
        campaignEntity.setExactNegativeWords(new ArrayList<String>());
        campaignEntity.setPause(false);
        campaignEntity.setShowProb(1);
        campaignEntity.setDevice(0);
        campaignEntity.setIsDynamicCreative(true);
        CampaignEntity campaign = (CampaignEntity) save(campaignEntity);
        String campaignObjectId = campaign.getId();

        for (BaiduKeywordDTO dto : list) {
            String adgroupName = dto.getGroupName();
            String keyword = dto.getKeywordName();
            String adgroupObjectId;

            Aggregation aggregation = newAggregation(
                    match(Criteria.where(ACCOUNT_ID).is(accountId).and(OBJ_CAMPAIGN_ID).is(campaignObjectId).and(NAME).is(adgroupName)),
                    project(ACCOUNT_ID, OBJ_CAMPAIGN_ID, NAME)
            );

            AdgroupEntity _adgroup = mongoTemplate.aggregate(aggregation, TBL_ADGROUP, AdgroupEntity.class).getUniqueMappedResult();
            if (_adgroup != null) {
                //当前分组在数据库中已经存在, 直接新增关键词即可
                KeywordEntity keywordEntity = new KeywordEntity();
                keywordEntity.setAccountId(accountId);
                keywordEntity.setAdgroupObjId(_adgroup.getId());
                keywordEntity.setKeyword(keyword);
                keywordEntity.setPrice(BigDecimal.ONE);
                keywordEntity.setMatchType(1);
                keywordEntity.setPause(false);
                keywordEntity.setStatus(-1);
                keywordEntity.setPhraseType(1);

                save(keywordEntity);
            } else {
                //新增一个分组
                AdgroupEntity adgroupEntity = new AdgroupEntity();
                adgroupEntity.setAccountId(accountId);
                adgroupEntity.setCampaignObjId(campaignObjectId);
                adgroupEntity.setAdgroupName(adgroupName);
                adgroupEntity.setMaxPrice(1.0);
                adgroupEntity.setNegativeWords(new ArrayList<String>());
                adgroupEntity.setExactNegativeWords(new ArrayList<String>());
                adgroupEntity.setPause(false);
                adgroupEntity.setStatus(-1);
                adgroupEntity.setMib(0.0);
                adgroupObjectId = ((AdgroupEntity) save(adgroupEntity)).getId();

                KeywordEntity keywordEntity = new KeywordEntity();
                keywordEntity.setAccountId(accountId);
                keywordEntity.setAdgroupObjId(adgroupObjectId);
                keywordEntity.setKeyword(keyword);
                keywordEntity.setPrice(BigDecimal.ONE);
                keywordEntity.setMatchType(1);
                keywordEntity.setPause(false);
                keywordEntity.setStatus(-1);
                keywordEntity.setPhraseType(1);

                save(keywordEntity);
            }
        }

    }

    public void saveKeywordFromSystem(String trade, String category, String newCampaignName) {
        MongoTemplate mongoTemplate = BaseMongoTemplate.getUserMongo();
        Long accountId = AppContext.getAccountId();

        if (mongoTemplate.findOne(Query.query(Criteria.where(ACCOUNT_ID).is(accountId).and(NAME).is(newCampaignName)), CampaignEntity.class) != null) {
            throw new DuplicateKeyException("\"" + newCampaignName + "\"" + " already exists");
        }

        CampaignEntity campaignEntity = new CampaignEntity();
        campaignEntity.setAccountId(accountId);
        campaignEntity.setCampaignName(newCampaignName);
        campaignEntity.setNegativeWords(new ArrayList<String>());
        campaignEntity.setExactNegativeWords(new ArrayList<String>());
        campaignEntity.setPause(false);
        campaignEntity.setShowProb(1);
        campaignEntity.setDevice(0);
        campaignEntity.setIsDynamicCreative(true);
        CampaignEntity campaign = (CampaignEntity) save(campaignEntity);
        String campaignObjectId = campaign.getId();

        Aggregation aggregation = newAggregation(
                match(Criteria.where("tr").is(trade).and("cg").is(category)),
                project("gr", "kw").andExclude(SYSTEM_ID),
//                project("tr", "cg", "gr", "kw").andExclude(SYSTEM_ID),
//                group("gr", "kw"),
                sort(Sort.Direction.ASC, "gr")
        );

        AggregationResults<LexiconEntity> results = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName())
                .aggregate(aggregation, SYS_KEYWORD, LexiconEntity.class);

        Iterator<LexiconEntity> iterator = results.iterator();
        List<KeywordEntity> keywordEntities = new ArrayList<>();
        String group = null;
        String adgroupObjectId = null;
        while (iterator.hasNext()) {
            LexiconEntity entity = iterator.next();

            if (group == null) {
                group = entity.getGroup();

                AdgroupEntity adgroup = new AdgroupEntity();
                adgroup.setAccountId(accountId);
                adgroup.setCampaignObjId(campaignObjectId);
                adgroup.setAdgroupName(group);
                adgroup.setMaxPrice(1.0);
                adgroup.setNegativeWords(new ArrayList<String>());
                adgroup.setExactNegativeWords(new ArrayList<String>());
                adgroup.setPause(false);
                adgroup.setStatus(-1);
                adgroup.setMib(0.0);
                adgroupObjectId = ((AdgroupEntity) save(adgroup)).getId();

                KeywordEntity keyword = new KeywordEntity();
                keyword.setAdgroupObjId(adgroupObjectId);
                keyword.setKeyword(entity.getKeyword());
                keyword.setAccountId(accountId);
                keyword.setPrice(BigDecimal.ONE);
                keyword.setMatchType(1);
                keyword.setPause(false);
                keyword.setStatus(-1);
                keyword.setPhraseType(1);
                keywordEntities.add(keyword);
                continue;
            }
            if (group.equals(entity.getGroup())) {
                KeywordEntity keyword = new KeywordEntity();
                keyword.setAdgroupObjId(adgroupObjectId);
                keyword.setKeyword(entity.getKeyword());
                keyword.setAccountId(accountId);
                keyword.setPrice(BigDecimal.ONE);
                keyword.setMatchType(1);
                keyword.setPause(false);
                keyword.setStatus(-1);
                keyword.setPhraseType(1);
                keywordEntities.add(keyword);
            } else {
                save(keywordEntities);
                keywordEntities.clear();

                group = entity.getGroup();
                AdgroupEntity adgroup = new AdgroupEntity();
                adgroup.setAccountId(accountId);
                adgroup.setCampaignObjId(campaignObjectId);
                adgroup.setAdgroupName(group);
                adgroup.setMaxPrice(1.0);
                adgroup.setNegativeWords(new ArrayList<String>());
                adgroup.setExactNegativeWords(new ArrayList<String>());
                adgroup.setPause(false);
                adgroup.setStatus(-1);
                adgroup.setMib(0.0);
                adgroupObjectId = ((AdgroupEntity) save(adgroup)).getId();

                KeywordEntity keyword = new KeywordEntity();
                keyword.setAdgroupObjId(adgroupObjectId);
                keyword.setKeyword(entity.getKeyword());
                keyword.setAccountId(accountId);
                keyword.setPrice(BigDecimal.ONE);
                keyword.setMatchType(1);
                keyword.setPause(false);
                keyword.setStatus(-1);
                keyword.setPhraseType(1);
                keywordEntities.add(keyword);
            }
        }

        if (!keywordEntities.isEmpty()) {
            save(keywordEntities);
            keywordEntities.clear();
        }
    }

    public void downloadCSV(String trade, String category, OutputStream os) {
        //查询参数
        Map<String, Object> params = new HashMap<>();
        if (trade != null) {
            params.put("tr", trade);
        }
        if (category != null && category.trim().length() > 0) {
            params.put("cg", category);
        }

        List<LexiconEntity> list = keywordGroupDAO.find(params, -1, -1);

        //CSV文件写入
        try {
            os.write(Bytes.concat(commonCSVHead, ("行业" + DEFAULT_DELIMITER + "计划" + DEFAULT_DELIMITER + "单元" + DEFAULT_DELIMITER + "关键词" + DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            for (LexiconEntity entity : list) {
                String bytes = (entity.getTrade() + DEFAULT_DELIMITER + entity.getCategory() + DEFAULT_DELIMITER + entity.getGroup() + DEFAULT_DELIMITER + entity.getKeyword() + DEFAULT_END);
                os.write(Bytes.concat(commonCSVHead, bytes.getBytes(StandardCharsets.UTF_8)));
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
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
        }

        //CSV文件写入
        try {
            os.write(Bytes.concat(commonCSVHead, ("分组" + DEFAULT_DELIMITER + "种子词" + DEFAULT_DELIMITER + "关键词" + DEFAULT_DELIMITER + "日均搜索量" + DEFAULT_DELIMITER + "竞争激烈程度" + DEFAULT_END).getBytes(StandardCharsets.UTF_8)));
            for (BaiduKeywordDTO entity : dtoList) {
                String bytes = (entity.getGroupName() + DEFAULT_DELIMITER + entity.getSeedWord() + DEFAULT_DELIMITER + entity.getKeywordName() + DEFAULT_DELIMITER + entity.getDsQuantity() + DEFAULT_DELIMITER + entity.getCompetition() + "%" + DEFAULT_END);
                os.write(Bytes.concat(commonCSVHead, bytes.getBytes(StandardCharsets.UTF_8)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> findCategories(String trade) {
        return JSONUtils.getJsonMapData(keywordGroupDAO.findCategories(trade));
    }

    @Override
    public Map<String, Object> getKRbySeedWord(String seedWord) {
        KRService krService = getKRService();
        GetKRbySeedWordRequest getKRbySeedWordRequest = new GetKRbySeedWordRequest();
        getKRbySeedWordRequest.setSeedWord(seedWord);
        getKRbySeedWordRequest.setDevice(1);
        GetKRbySeedWordResponse getKRbySeedWordResponse = krService.getKRbySeedWord(getKRbySeedWordRequest);
        List<KRResult> list = getKRbySeedWordResponse.getKrResult();
        List<KRResultDTO> results = new ArrayList<>();
        for (KRResult entity : list) {
            KRResultDTO krResultDTO = new KRResultDTO();
            krResultDTO.setWord(entity.getWord());
            krResultDTO.setExactPV(entity.getExactPV());
            krResultDTO.setCompetition(entity.getCompetition());
            krResultDTO.setFlag1(entity.getFlag1());
            results.add(krResultDTO);
        }
        Map<String, Object> map = JSONUtils.getJsonMapData(results);
        return map;
    }

    @Override
    public Map<String, Object> findTr() {

        return JSONUtils.getJsonMapData(keywordGroupDAO.findTr());
    }

    @Override
    public int saveTrade(String tr, String cg, String gr, String kw, String url) {
        LexiconEntity lexiconEntity = new LexiconEntity();
        lexiconEntity.setTrade(tr);
        lexiconEntity.setCategory(cg);
        lexiconEntity.setGroup(gr);
        lexiconEntity.setKeyword(kw);
        lexiconEntity.setUrl(url);
        return keywordGroupDAO.saveTrade(lexiconEntity);
    }

    @Override
    public PagerInfo findByPager(Map<String, Object> params, int page, int limit) {

        return keywordGroupDAO.findByPager(params, page, limit);
    }

    public Map<String, Object> getKRResult(List<String> seedWordList, int skip, int limit, int sort, String fieldName) {
        KRService krService = getKRService();
        GetKRFileIdbySeedWordRequest getKRFileIdbySeedWordRequest = new GetKRFileIdbySeedWordRequest();
        getKRFileIdbySeedWordRequest.setSeedWords(seedWordList);
        GetKRFileIdbySeedWordResponse getKRFileIdbySeedWordResponse = krService.getKRFileIdbySeedWord(getKRFileIdbySeedWordRequest);
        String krFileId = getKRFileIdbySeedWordResponse.getKrFileId();
        Map<String, Object> attributes = null;
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
                List<BaiduKeywordDTO> keywordList = httpFileHandler(krFilePath);
                int s = keywordList.size();
                BaiduKeywordDTO[] sortedKeywords = TopN.getTopN(keywordList.toArray(new BaiduKeywordDTO[s]), s, fieldName, sort);
                List<BaiduKeywordDTO> list = paging(sortedKeywords, skip, limit);
                attributes = JSONUtils.getJsonMapData(list);
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

    private KRService getKRService() {
        BaiduAccountInfoEntity accountInfo = accountManageDAO.findByBaiduUserId(AppContext.getAccountId());
        CommonService service = BaiduServiceSupport.getCommonService(accountInfo);
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
    private List<BaiduKeywordDTO> httpFileHandler(String url) {
        List<BaiduKeywordDTO> keywordList = new ArrayList<>();
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
                String competitionStr = arr[5].substring(0, arr[5].indexOf("%"));
                keywordList.add(new BaiduKeywordDTO(arr[8], arr[0], arr[1], Integer.valueOf(arr[4]), Double.valueOf(competitionStr), arr[9], arr[10]));
                index++;
            }
            this.resultSize = keywordList.size();
            saveToRedis(SerializeUtils.serialize(_krFileId), SerializeUtils.serialize(keywordList));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public Class getEntityClass() {
        return null;
    }

    public Pager findByPager(int start, int pageSize, Map q, int orderBy) {
        return null;
    }

    //Map,key比较器
    private class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
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
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

}