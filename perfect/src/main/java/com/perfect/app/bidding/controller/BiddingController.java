package com.perfect.app.bidding.controller;

import com.google.common.collect.Lists;
import com.perfect.api.baidu.BaiduApiService;
import com.perfect.api.baidu.BaiduPreviewHelperFactory;
import com.perfect.api.baidu.Keyword10QualityService;
import com.perfect.api.baidu.KeywordBiddingRankService;
import com.perfect.app.bidding.dto.BiddingRuleParam;
import com.perfect.app.bidding.dto.KeywordReportDTO;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.Quality10Type;
import com.perfect.constants.KeywordStatusEnum;
import com.perfect.core.AppContext;
import com.perfect.dto.RegionCodeDTO;
import com.perfect.entity.*;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.KeywordRankEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.mongodb.utils.PaginationParam;
import com.perfect.service.*;
import com.perfect.utils.BiddingRuleUtils;
import com.perfect.utils.JSONUtils;
import com.perfect.utils.NumberUtils;
import com.perfect.utils.RegionalCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by yousheng on 2014/8/22.
 *
 * @author yousheng
 */

@RestController
@RequestMapping("/bidding")
public class BiddingController {

    private static Logger log = LoggerFactory.getLogger(BiddingController.class);

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private BiddingRuleService biddingRuleService;

    @Resource
    private SysCampaignService sysCampaignService;

    @Resource
    private KeywordRankService keywordRankService;

    @Resource
    private SysAdgroupService sysAdgroupService;

    @Resource
    private SysKeywordService sysKeywordService;

    @Resource
    private BasisReportService basisReportService;

    @Resource
    private KeywordBiddingRankService keywordBiddingRankService;

    @Resource
    private Keyword10QualityService keyword10QualityService;

    @Resource
    private BaiduPreviewHelperFactory baiduPreviewHelperFactory;

    @RequestMapping(value = "/save", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView save(@RequestBody BiddingRuleParam param) {
        if (log.isDebugEnabled()) {
            log.debug("保存竞价规则..");
        }

        List<BiddingRuleEntity> newRules = new ArrayList<>();


        //        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//        int[] startEndTimes = getTimes(param.getTimes(), hour);
        List<BiddingRuleEntity> entities = biddingRuleService.findByKeywordIds(Arrays.asList(param.getIds()));

        Map<Long, BiddingRuleEntity> biddingRuleEntityMap = new HashMap<>();

        if (!entities.isEmpty()) {
            for (BiddingRuleEntity biddingRuleEntity : entities) {
                biddingRuleEntityMap.put(biddingRuleEntity.getKeywordId(), biddingRuleEntity);
            }
        }
        for (Long id : param.getIds()) {
            KeywordEntity keywordEntity = sysKeywordService.findById(id);

            BiddingRuleEntity biddingRuleEntity = biddingRuleEntityMap.get(id);

            if (biddingRuleEntity == null) {
                biddingRuleEntity = new BiddingRuleEntity();
                biddingRuleEntity.setAccountId(AppContext.getAccountId());
                biddingRuleEntity.setKeywordId(id);
                biddingRuleEntity.setCurrentPrice(BigDecimal.ZERO);
            }

            biddingRuleEntity.setKeyword(keywordEntity.getKeyword());
            biddingRuleEntity.setMatchType(keywordEntity.getMatchType());
            biddingRuleEntity.setPhraseType(keywordEntity.getPhraseType());
            biddingRuleEntity.setEnabled(param.isRun());

            biddingRuleEntity.setCurrentPrice(BigDecimal.ZERO);

            StrategyEntity strategyEntity = new StrategyEntity();
            biddingRuleEntity.setStrategyEntity(strategyEntity);

            Date date = null;
            int interval = param.getInterval();
            if (interval == -1) {
                boolean runNow = BiddingRuleUtils.runNow(param.getTimes());
                if (runNow) {
                    biddingRuleEntity.setNext(1);
                }
            } else if (interval >= 60) {
                date = BiddingRuleUtils.getDateInvHour(param.getTimes(), interval);
                biddingRuleEntity.setNext(date.getTime());
                //竞价时段
            } else {
                date = BiddingRuleUtils.getDateInvMinute(param.getTimes(), interval);
                biddingRuleEntity.setNext(date.getTime());
                //竞价时段
            }

            //竞价时段
            strategyEntity.setTimes(param.getTimes());
            // 竞价排名位置
            strategyEntity.setExpPosition(param.getExpPosition());
            strategyEntity.setPosition(param.getCustomPos());

            // 出价
            strategyEntity.setMaxPrice(BigDecimal.valueOf(param.getMax()));
            strategyEntity.setMinPrice(BigDecimal.valueOf(param.getMin()));


            // 竞价设备
            strategyEntity.setDevice(param.getDevice());

            // 竞价模式
            strategyEntity.setMode(param.getMode());

            //竞价次数
            strategyEntity.setAuto(param.getAuto());
            strategyEntity.setRunByTimes(param.getRunByTimes());
            biddingRuleEntity.setCurrentTimes(param.getRunByTimes());
            // 目标区域
            if (param.getTarget() != 0) {
                strategyEntity.setRegionTarget(new Integer[]{param.getTarget()});
            }

            // 失败策略
            strategyEntity.setFailedStrategy(param.getFailed());
            strategyEntity.setInterval(param.getInterval());
            newRules.add(biddingRuleEntity);
        }

        for (BiddingRuleEntity entity : newRules) {
            biddingRuleService.createBiddingRule(entity);
        }

        AbstractView view = new MappingJackson2JsonView();
        view.addStaticAttribute("code", HttpStatus.OK);
        return new ModelAndView(view);
    }

    private int[] getTimes(Integer[] times, Integer time) {

        Arrays.sort(times);
        LinkedList<Integer> timeList = new LinkedList<>();
        for (Integer integer : times) {
            timeList.addLast(integer);
        }
        int[] hourOfDays = new int[24];
        boolean set = false;
        int start = timeList.removeFirst();
        int end = timeList.removeFirst();

        for (int i = 0; i < hourOfDays.length; i++) {
            if (i < start) {
                continue;
            } else if (end < i) {

                if (timeList.isEmpty()) {
                    break;
                }

                start = timeList.removeFirst();
                end = timeList.removeFirst();
                set = false;
                if (start <= i && i <= end) {
                    set = true;
                }
            } else {
                set = true;
            }
            if (set) {
                hourOfDays[i] = 1;
            }
        }

//        System.out.println("hourOfDays = " + Arrays.toString(hourOfDays));


        int starts = findStart(hourOfDays, time);
//        System.out.println("start : " + starts);
//        System.out.println("end : " + findEnd(hourOfDays, starts));
        return new int[]{starts, findEnd(hourOfDays, starts)};
    }


    private static int findEnd(int[] hourOfDays, int time) {
        int ret = findExp(hourOfDays, time, 0);
        if (ret == 0 || ret == -1) {
            return 23;
        } else {
            return ret - 1;
        }
    }

    private static int findStart(int[] hourOfDays, int time) {
        if (hourOfDays[time] == 1) {
            return time;
        } else {
            return findExp(hourOfDays, time, 1);
        }
    }

    private static int findExp(int[] hourOfDays, int time, int value) {
        for (int i = time; i < hourOfDays.length; i++) {
            if (hourOfDays[i] == value) {
                return i;
            }
        }
        //从0开始
        for (int i = 0; i < hourOfDays.length; i++) {
            if (hourOfDays[i] == value) {
                return i;
            }
        }
        return -1;
    }

    @RequestMapping(value = "/keyword/{id}", method = RequestMethod.GET)
    public ModelAndView get(HttpServletRequest request, @PathVariable Long id) {
        AbstractView jsonView = new MappingJackson2JsonView();

        List<BiddingRuleEntity> rules = biddingRuleService.findByKeywordIds(Arrays.asList(id));
        if (!rules.isEmpty()) {
            BiddingRuleEntity entity = rules.get(0);
            jsonView.setAttributesMap(JSONUtils.getJsonMapData(entity));
        }
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/keyword/{id}", method = RequestMethod.DELETE)
    public ModelAndView delete(@PathVariable Long id) {
        biddingRuleService.removeByKeywordId(id);

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attribute = new HashMap<>();

        attribute.put("success", true);
        jsonView.setAttributesMap(attribute);

        return new ModelAndView(jsonView);
    }


//    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
//    public void delete(HttpServletRequest request, @RequestBody BiddingRuleEntity biddingRuleEntity) {
//        biddingRuleService.createBiddingRule(biddingRuleEntity);
//    }


    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("bidding/jingjia");
    }

    @RequestMapping(value = "/getKeywordBiddingRank", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getKeywordBiddingRank(@RequestParam(value = "keyword", required = false) String keyword,
                                              @RequestParam(value = "region", required = false) Integer region) {
        Integer rank = keywordBiddingRankService.getKeywordBiddingRank(keyword, 1000);
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> value = new HashMap<>();
        value.put("rank", rank);
        jsonView.setAttributesMap(value);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public ModelAndView list(HttpServletRequest request,
                             @RequestParam(value = "cp", required = false) Long cp,
                             @RequestParam(value = "ag", required = false) Long agid,
                             @RequestParam(value = "s", required = false, defaultValue = "0") int skip,
                             @RequestParam(value = "l", required = false, defaultValue = "20") int limit,
                             @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
                             @RequestParam(value = "o", required = false, defaultValue = "true") boolean asc,
                             @RequestParam(value = "q", required = false) String query,
                             @RequestParam(value = "f", required = false, defaultValue = "false") boolean fullMatch,
                             @RequestParam(value = "filter", required = true, defaultValue = "0") int filter,
                             @RequestParam(value = "matchType", required = false) Integer matchType,
                             @RequestParam(value = "quality", required = false) String quality,
                             @RequestParam(value = "price", required = false) String price) {

        AbstractView jsonView = new MappingJackson2JsonView();

        Map<String, Object> queryParams = new HashMap<>();
        if (matchType != null) {
            queryParams.put("matchType", matchType);
        }
        BigDecimal minPrice = new BigDecimal(0);
        BigDecimal maxPrice = new BigDecimal(0);
        boolean priceStatus = (price != null && price.trim().length() > 0);//为真代表查询已参加竞价的关键词
        if (priceStatus) {
            String p[] = price.split(",");
            if (p.length == 2) {
                minPrice.add(new BigDecimal(Double.valueOf(p[0])));
                maxPrice.add(new BigDecimal(Double.valueOf(p[1])));
            } else {
                priceStatus = false;
            }
        }
        //关键词质量度搜索
        List<Integer> keywordQualityList = new ArrayList<>();
        if (quality != null && quality.trim().length() > 0) {
            for (String s : quality.split(",")) {
                keywordQualityList.add(Integer.valueOf(s));
            }
        }
        int keywordQualityConditionSize = keywordQualityList.size();
        if (keywordQualityConditionSize > 0) {
            fullMatch = true;
        }

        //是否启用了高级搜索
        boolean advancedSearch = (matchType != null) || (priceStatus) || (keywordQualityConditionSize > 0);

        List<KeywordEntity> entities = null;
        Long total = 0l;
        Integer total1 = 0;

        PaginationParam param = new PaginationParam();
        param.setStart(skip);
        param.setLimit(limit);
        param.setOrderBy(sort);
        param.setAsc(asc);

        PaginationParam param1 = new PaginationParam();
        param1.setOrderBy(sort);
        param1.setAsc(asc);


        Map<Long, BiddingRuleEntity> keywordIdRuleMap = new HashMap<>();
        List<Long> ids = new ArrayList<>();

        boolean ruleReady = false;
        if (cp != null) {
            List<AdgroupEntity> adgroupEntityList = sysAdgroupService.findIdByCampaignId(cp);

            List<Long> adGroupIds = new ArrayList<>(adgroupEntityList.size());
            for (AdgroupEntity adgroupEntity : adgroupEntityList) {
                adGroupIds.add(adgroupEntity.getAdgroupId());
            }

            if (query != null) {
                queryParams.put("adgroupIds", adGroupIds);

                if (keywordQualityConditionSize > 0) {
                    entities = sysKeywordService.findByNames(query.split(" "), fullMatch, param1, queryParams);
                    total1 = sysKeywordService.countKeywordfindByNames(query.split(" "), fullMatch, param1, queryParams);
                } else {
                    entities = sysKeywordService.findByNames(query.split(" "), fullMatch, param, queryParams);
                    total1 = sysKeywordService.countKeywordfindByNames(query.split(" "), fullMatch, param1, queryParams);
                }
            } else {
                entities = sysKeywordService.findByAdgroupIds(adGroupIds, param);
                total = sysKeywordService.keywordCount(adGroupIds);
            }
        } else if (agid != null) {
            if (query != null) {
                List<Long> adgroupIds = Lists.newArrayList();
                adgroupIds.add(agid);
                queryParams.put("adgroupIds", adgroupIds);

                if (keywordQualityConditionSize > 0) {
                    entities = sysKeywordService.findByNames(query.split(" "), fullMatch, param1, queryParams);
                    total1 = sysKeywordService.countKeywordfindByNames(query.split(" "), fullMatch, param1, queryParams);
                } else {
                    entities = sysKeywordService.findByNames(query.split(" "), fullMatch, param, queryParams);
                    total1 = sysKeywordService.countKeywordfindByNames(query.split(" "), fullMatch, param1, queryParams);
                }
            } else {
                List<Long> tmpList = new ArrayList<>();
                tmpList.add(agid);
                entities = sysKeywordService.findByAdgroupId(agid, param);
                total = sysKeywordService.keywordCount(tmpList);
            }
        } else if (query != null) {
            if (filter == 0 && !priceStatus) {//全部
                if (keywordQualityConditionSize > 0) {
                    entities = sysKeywordService.findByNames(query.split(" "), fullMatch, param1, queryParams);
                    total1 = sysKeywordService.countKeywordfindByNames(query.split(" "), fullMatch, param1, queryParams);
                } else {
                    entities = sysKeywordService.findByNames(query.split(" "), fullMatch, param, queryParams);
                    total1 = sysKeywordService.countKeywordfindByNames(query.split(" "), fullMatch, param1, queryParams);
                }
            } else if (filter == -1 && !priceStatus) {//未参加竞价的
                if (keywordQualityConditionSize > 0) {
                    entities = sysKeywordService.findByNames(query.split(" "), fullMatch, param1, queryParams);
                    total1 = sysKeywordService.countKeywordfindByNames(query.split(" "), fullMatch, param1, queryParams);
                } else {
                    entities = sysKeywordService.findByNames(query.split(" "), fullMatch, param, queryParams);
                    total1 = sysKeywordService.countKeywordfindByNames(query.split(" "), fullMatch, param1, queryParams);
                }

                Map<Long, KeywordEntity> tmpMap = new HashMap<>();
                for (KeywordEntity tmpEntity : entities) {
                    ids.add(tmpEntity.getKeywordId());
                    tmpMap.put(tmpEntity.getKeywordId(), tmpEntity);
                }

                if (ids.isEmpty()) {
                    jsonView.setAttributesMap(new HashMap<String, Object>());
                    return new ModelAndView(jsonView);
                }
                List<BiddingRuleEntity> byKeywordIds = biddingRuleService.findByKeywordIds(ids);

                for (BiddingRuleEntity ruleEntity : byKeywordIds) {
                    ids.remove(ruleEntity.getKeywordId());
                    KeywordEntity keywordEntity = tmpMap.remove(ruleEntity.getKeywordId());
                    entities.remove(keywordEntity);

                }

            } else if (filter == 1 || priceStatus) {//已参加竞价的
                List<BiddingRuleEntity> ruleEntities;
                if (keywordQualityConditionSize > 0) {
                    ruleEntities = biddingRuleService.findByNames(query.split(" "), fullMatch,
                            param1, queryParams);

                    total1 = biddingRuleService.countBiddingRuleEntityfindByNames(query.split(" "), fullMatch,
                            param1, queryParams);
                } else {
                    ruleEntities = biddingRuleService.findByNames(query.split(" "), fullMatch,
                            param, queryParams);

                    total1 = biddingRuleService.countBiddingRuleEntityfindByNames(query.split(" "), fullMatch,
                            param1, queryParams);
                }

                for (BiddingRuleEntity tmpEntity : ruleEntities) {
                    keywordIdRuleMap.put(tmpEntity.getKeywordId(), tmpEntity);
                    ids.add(tmpEntity.getKeywordId());
                }
                if (ids.isEmpty()) {
                    jsonView.setAttributesMap(new HashMap<String, Object>());
                    return new ModelAndView(jsonView);
                }
                entities = sysKeywordService.findByIds(ids);

                ruleReady = !ruleEntities.isEmpty();
            }
        } else {
            return new ModelAndView(jsonView);
        }


        Map<Long, KeywordReportDTO> keywordReportDTOHashMap = new HashMap<>();
        List<KeywordReportDTO> resultList = new ArrayList<>();

        //获取entities的质量度
        List<Long> tmpKeywordIdList = new ArrayList<>();
        for (KeywordEntity entity : entities) {
            tmpKeywordIdList.add(entity.getKeywordId());
        }
        Map<Long, Quality10Type> quality10TypeMap = keyword10QualityService.getKeyword10Quality(tmpKeywordIdList);
        Integer quality10TypeSize = quality10TypeMap.size();

        Integer index = 0;
        for (KeywordEntity entity : entities) {
            KeywordReportDTO keywordReportDTO = new KeywordReportDTO();
            BeanUtils.copyProperties(entity, keywordReportDTO);

            Long kwid = entity.getKeywordId();
            if (keywordQualityConditionSize > 0) {
                if (!keywordQualityList.contains(quality10TypeMap.get(kwid).getPcQuality())) {
                    continue;
                }
            }

            index++;
            AdgroupEntity adgroupEntity = sysAdgroupService.findByAdgroupId(entity.getAdgroupId());
            CampaignEntity campaignEntity = sysCampaignService.findById(adgroupEntity.getCampaignId());
            keywordReportDTO.setAdgroupId(adgroupEntity.getAdgroupId());
            keywordReportDTO.setCampaignName(campaignEntity.getCampaignName());
            keywordReportDTO.setAdgroupName(adgroupEntity.getAdgroupName());

            if (quality10TypeSize > 0) {
                keywordReportDTO.setPcQuality(quality10TypeMap.get(kwid).getPcQuality());
                keywordReportDTO.setmQuality(quality10TypeMap.get(kwid).getMobileQuality());
            }

            if (entity.getStatus() != null) {
                keywordReportDTO.setStatusStr(KeywordStatusEnum.getName(entity.getStatus()));
            } else {
                keywordReportDTO.setStatusStr(KeywordStatusEnum.STATUS_UNKNOWN.name());
            }

            keywordReportDTOHashMap.put(entity.getKeywordId(), keywordReportDTO);
            resultList.add(keywordReportDTO);
            if (ruleReady)
                ids.add(entity.getKeywordId());

            BiddingRuleEntity ruleEntity = null;
            if (filter != -1) {
                if (ruleReady) {
                    ruleEntity = keywordIdRuleMap.get(entity.getKeywordId());
                } else {
                    ruleEntity = biddingRuleService.findByKeywordId(entity.getKeywordId());
                }
            }

            if (ruleEntity != null) {
                keywordReportDTO.setRule(true);
                keywordReportDTO.setRuleDesc(BiddingRuleUtils.getRule(ruleEntity));
                if (ruleEntity.isEnabled()) {
                    keywordReportDTO.setBiddingStatus(1);
                }
            }
        }
        if (keywordQualityConditionSize > 0) {
            total1 = index; //包含质量度查询时符合条件的最终记录数
        }
        String yesterday = DateUtils.getYesterdayStr();

        Map<String, List<StructureReportEntity>> reports = basisReportService.getKeywordReport(tmpKeywordIdList.toArray(new Long[tmpKeywordIdList.size()]), yesterday, yesterday, 0);
        List<StructureReportEntity> list = reports.get(yesterday);

        for (StructureReportEntity entity : list) {
            long kwid = entity.getKeywordId();
            KeywordReportDTO dto = keywordReportDTOHashMap.get(kwid);
            dto.setClick(NumberUtils.getInteger(entity.getPcClick()));
            dto.setConversion(NumberUtils.getDouble(entity.getPcConversion()));
            dto.setCost(entity.getPcCost());
            dto.setCpc(entity.getPcCpc());
            dto.setCpm(entity.getPcCpm());
            dto.setCtr(NumberUtils.getDouble(entity.getPcCtr()));
            dto.setImpression(NumberUtils.getInteger(entity.getPcImpression()));
        }

        List<KeywordReportDTO> newResultList = new ArrayList<>();
        if (keywordQualityConditionSize > 0) {
            int tmpSize = resultList.size();
            for (int i = skip; i < skip + limit; i++) {
                if (i == tmpSize) {
                    break;
                }
                newResultList.add(resultList.get(i));
            }
        }

        Map<String, Object> attributes;
        if (keywordQualityConditionSize > 0) {
            attributes = JSONUtils.getJsonMapData(newResultList);
        } else {
            attributes = JSONUtils.getJsonMapData(resultList);
        }
        if (total > 0) {
            attributes.put("records", total);
        } else if (total1 > 0) {
            attributes.put("records", total1);
        } else {
            attributes.put("records", 0);
        }

        jsonView.setAttributesMap(attributes);
        // 获取报告信息

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/rank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView checkCurrentRank(HttpServletRequest request,
                                         @RequestParam(value = "ids", required = true) Long[] ids
    ) {

        AbstractView jsonView = new MappingJackson2JsonView();

        List<Integer> result = Collections.EMPTY_LIST;

        if (ids == null || ids.length == 0) {
            return new ModelAndView(jsonView);
        }

        // 只允许指定一个推广地域,如果未指定则按照推广计划->推广账户

        String userName = AppContext.getUser();

        Long accid = AppContext.getAccountId();
        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);
        if (systemUserEntity == null) {
            return new ModelAndView(jsonView);
        }

        List<Integer> accountRegionList = new ArrayList<>();
        CommonService commonService = null;
        String host = null;
        for (BaiduAccountInfoEntity infoEntity : systemUserEntity.getBaiduAccountInfoEntities()) {
            if (infoEntity.getId().longValue() == accid) {
                try {
                    // 如果竞价规则和推广计划都未设置推广地域,则通过账户获取
                    accountRegionList.addAll(infoEntity.getRegionTarget());
                    host = infoEntity.getRegDomain();
                    commonService = ServiceFactory.getInstance(infoEntity.getBaiduUserName(), infoEntity.getBaiduPassword(), infoEntity.getToken(), null);
                    break;
                } catch (ApiException e) {
                    e.printStackTrace();
                }

            }
        }

        if (commonService == null) {
            return new ModelAndView(jsonView);
        }

        Map<KeywordEntity, List<Integer>> searchMap = new HashMap<>();
        Map<String, KeywordEntity> keywordEntityMap = new HashMap<>();

        for (Long kwid : ids) {

            KeywordEntity keywordEntity = sysKeywordService.findById(kwid);
            keywordEntityMap.put(keywordEntity.getKeyword(), keywordEntity);

            BiddingRuleEntity ruleEntity = biddingRuleService.findByKeywordId(kwid);

            if (ruleEntity == null || ruleEntity.getStrategyEntity().getRegionTarget() == null) {
                CampaignEntity campaignEntity = sysCampaignService.findByKeywordId(kwid);
                List<Integer> targetList = campaignEntity.getRegionTarget();
                // 设置计划区域或者账户区域
                if (targetList != null && !targetList.isEmpty()) {
                    searchMap.put(keywordEntity, targetList);
                } else {
                    searchMap.put(keywordEntity, accountRegionList);
                }
            } else {
                searchMap.put(keywordEntity, Arrays.asList(ruleEntity.getStrategyEntity().getRegionTarget()));
            }

        }

        BaiduApiService baiduApiService = new BaiduApiService(commonService);
        baiduApiService.setBaiduPreviewHelperFactory(baiduPreviewHelperFactory);
        Map<String, KeywordRankEntity> rankMap = baiduApiService.getKeywordRank(searchMap, host);

        Long accountId = AppContext.getAccountId();

        for (String key : rankMap.keySet()) {
            String id;
            KeywordEntity keywordEntity = keywordEntityMap.get(key);
            if (keywordEntity.getKeywordId() == null) {
                id = keywordEntity.getId();
            } else {
                id = keywordEntity.getKeywordId().toString();
            }

            KeywordRankEntity currentRank = keywordRankService.findRankByKeywordId(id);

            if (currentRank == null) {
                KeywordRankEntity keywordRankEntity = rankMap.get(key);
                keywordRankEntity.setAccountId(accountId);
                keywordRankEntity.setKwid(id);
            }

        }
        keywordRankService.updateRanks(rankMap.values());

        Map<String, Object> attributes = JSONUtils.getJsonMapData(rankMap);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);

    }

    @RequestMapping(value = "/rank/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getRank(@PathVariable("id") String id) {

        AbstractView jsonView = new MappingJackson2JsonView();

        KeywordRankEntity keywordRankEntity = keywordRankService.findRankByKeywordId(id);
        if (keywordRankEntity == null) {
            Map<String, Object> map = new HashMap<String, Object>() {{
                put("rows", null);
            }};
            jsonView
                    .setAttributesMap(map);
            return new ModelAndView(jsonView);
        }
        Map<Integer, Integer> tmpMap = keywordRankEntity.getTargetRank();
        List<RegionCodeDTO> regionCodeDTOs = new ArrayList<>();
        for (final Map.Entry<Integer, Integer> entry : tmpMap.entrySet()) {
            RegionCodeDTO dto = new RegionCodeDTO();
            Integer regionId = entry.getKey();
            Map<Integer, String> regionMap = RegionalCodeUtils.regionalCode(Arrays.asList(regionId));
            String regionName = regionMap.get(regionId);
            Integer rank = entry.getValue();
//            dto.setRegionId(regionId);
            dto.setRegionName(regionName);
            dto.setRank(rank);
            regionCodeDTOs.add(dto);

        }
//        Map<String, Object> mapObject = JSONUtils.getJsonMapData(keywordRankEntity);
        Map<String, Object> mapObject = JSONUtils.getJsonMapData(regionCodeDTOs);
        jsonView.setAttributesMap(mapObject);

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/enable", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView setEnable(@RequestParam Long[] ids, @RequestParam boolean ebl) {
        boolean result = biddingRuleService.setEnable(ids, ebl);
        AbstractView jsonView = new MappingJackson2JsonView();

        Map<String, Object> map = new HashMap<>();

        if (result) {
            map.put("code", 0);
        } else {
            map.put("msg", "更新失败");
        }
        jsonView.setAttributesMap(map);

        return new ModelAndView(jsonView);
    }


}
