package com.perfect.app.bidding.controller;

import com.perfect.app.bidding.dto.BiddingRuleParam;
import com.perfect.app.bidding.dto.KeywordReportDTO;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.core.AppContext;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.*;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.entity.bidding.StrategyEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.mongodb.utils.PaginationParam;
import com.perfect.service.*;
import com.perfect.utils.JSONUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yousheng on 2014/8/22.
 *
 * @author yousheng
 */

@RestController
@Controller
@RequestMapping("/bidding")
public class BiddingController {

    private static Logger log = LoggerFactory.getLogger(BiddingController.class);

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private BiddingRuleService biddingRuleService;

    @Resource
    private CampaignDAO campaignDAO;

    @Resource
    private SysAdgroupService sysAdgroupService;

    @Resource
    private SysKeywordService sysKeywordService;

    @Resource
    private BasisReportService basisReportService;

    @RequestMapping(value = "/save", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody BiddingRuleParam param) {
        if (log.isDebugEnabled()) {
            log.debug("保存竞价规则..");
        }

        List<BiddingRuleEntity> newRules = new ArrayList<>();

        for (Long id : param.getIds()) {
            BiddingRuleEntity biddingRuleEntity = new BiddingRuleEntity();

            biddingRuleEntity.setAccountId(AppContext.getAccountId());
            biddingRuleEntity.setKeywordId(id);
            biddingRuleEntity.setEnabled(param.isRun());

            StrategyEntity strategyEntity = new StrategyEntity();
            biddingRuleEntity.setStrategyEntity(strategyEntity);

            //竞价时段
            strategyEntity.setTimes(param.getTimes());

            // 竞价排名位置
            strategyEntity.setPositionStrategy(param.getExpPosition());
            strategyEntity.setPosition(param.getCustomPos());

            // 出价
            strategyEntity.setMaxPrice(param.getMax());
            strategyEntity.setMinPrice(param.getMin());

            // 竞价设备
            strategyEntity.setDevice(param.getDevice());

            // 竞价模式
            strategyEntity.setMode(param.getMode());

            // 目标区域
            strategyEntity.setRegionTarget(param.getTarget());

            // 失败策略
            strategyEntity.setFailedStrategy(param.getFailed());
            strategyEntity.setInterval(param.getInterval());
            newRules.add(biddingRuleEntity);
        }

        biddingRuleService.removeByKeywordIds(Arrays.asList(param.getIds()));

        for (BiddingRuleEntity entity : newRules) {
            biddingRuleService.createBiddingRule(entity);
        }
    }


    @RequestMapping(value = "/keyword/{id}", method = RequestMethod.GET)
    public ModelAndView get(HttpServletRequest request, @PathVariable Long id) {
        AbstractView jsonView = new MappingJackson2JsonView();

        List<BiddingRuleEntity> rules = biddingRuleService.findRules(Arrays.asList(id));
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

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView home(HttpServletRequest request,
                             @RequestParam(value = "cp", required = false) Long cp,
                             @RequestParam(value = "ag", required = false) Long agid,
                             @RequestParam(value = "s", required = false, defaultValue = "0") int skip,
                             @RequestParam(value = "l", required = false, defaultValue = "20") int limit,
                             @RequestParam(value = "sort", required = false, defaultValue = "name") String sort,
                             @RequestParam(value = "o", required = false, defaultValue = "true") boolean asc) {

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> q = new HashMap<>();
        List<KeywordEntity> entities = null;

        PaginationParam param = new PaginationParam();
        param.setStart(skip);
        param.setLimit(limit);
        param.setOrderBy(sort);
        param.setAsc(asc);


        if (cp != null) {
            List<AdgroupEntity> adgroupEntityList = sysAdgroupService.findIdByCampaignId(cp);

            List<Long> ids = new ArrayList<>(adgroupEntityList.size());
            for (AdgroupEntity adgroupEntity : adgroupEntityList) {
                ids.add(adgroupEntity.getAdgroupId());
            }
            entities = sysKeywordService.findByAdgroupIds(ids, param);
        } else if (agid != null) {
            entities = sysKeywordService.findByAdgroupId(agid, param);
        } else {
            return new ModelAndView(jsonView);
        }


        List<Long> ids = new ArrayList<>();

        Map<Long, KeywordReportDTO> keywordReportDTOHashMap = new HashMap<>();
        List<KeywordReportDTO> resultList = new ArrayList<>();
        for (KeywordEntity entity : entities) {
            KeywordReportDTO keywordReportDTO = new KeywordReportDTO();
            BeanUtils.copyProperties(entity, keywordReportDTO);

            keywordReportDTOHashMap.put(entity.getKeywordId(), keywordReportDTO);
            resultList.add(keywordReportDTO);
            ids.add(entity.getKeywordId());
//            boolean hasRule = biddingRuleService.exists(entity.getKeywordId());
//            keywordReportDTO.setRule(true);
        }
        String yesterday = DateUtils.getYesterdayStr();

        Map<String, List<StructureReportEntity>> reports = basisReportService.getKeywordReport(ids.toArray(new Long[]{}), yesterday, yesterday, 0);
        List<StructureReportEntity> list = reports.get(yesterday);

        for (StructureReportEntity entity : list) {
            long kwid = entity.getKeywordId();
            KeywordReportDTO dto = keywordReportDTOHashMap.get(kwid);
            dto.setClick(entity.getPcClick());
            dto.setConversion(entity.getPcConversion());
            dto.setCost(entity.getPcCost());
            dto.setCpc(entity.getPcCpc());
            dto.setCpm(entity.getPcCpm());
            dto.setCtr(entity.getPcCtr());
            dto.setImpression(entity.getPcImpression());
        }

        Map<String, Object> attributes = JSONUtils.getJsonMapData(resultList);
        jsonView.setAttributesMap(attributes);

        // 获取报告信息

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/rank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView checkCurrentRank(HttpServletRequest request,
                                         @RequestParam(value = "acid", required = true) Long accid,
                                         @RequestParam(value = "ids", required = true) Long[] ids,
                                         @RequestParam(value = "cid", required = true) Long cid
    ) {

        AbstractView jsonView = new MappingJackson2JsonView();

        List<Integer> result = Collections.EMPTY_LIST;
        if (accid == null) {
            return new ModelAndView(jsonView);
        }

        CampaignEntity campaignEntity = campaignDAO.findOne(cid);

        if (campaignEntity == null) {
            return new ModelAndView(jsonView);
        }
        String userName = AppContext.getUser();

        SystemUserEntity systemUserEntity = systemUserService.getSystemUser(userName);
        if (systemUserEntity == null) {
            return new ModelAndView(jsonView);
        }

        CommonService commonService = null;
        String host = null;
        for (BaiduAccountInfoEntity infoEntity : systemUserEntity.getBaiduAccountInfoEntities()) {
            if (infoEntity.getId().longValue() == accid) {
                try {
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

        List<BiddingRuleEntity> list = biddingRuleService.findRules(Arrays.asList(ids));

        BaiduApiService baiduApiService = new BaiduApiService(commonService);

        Map<String, Integer> rankMap = baiduApiService.checkKeywordRank(list, host);

        Map<String, Object> attributes = JSONUtils.getJsonMapData(rankMap);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }
}
