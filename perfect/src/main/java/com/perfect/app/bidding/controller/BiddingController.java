package com.perfect.app.bidding.controller;

import com.perfect.app.bidding.dto.BiddingRuleDTO;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.core.AppContext;
import com.perfect.dao.CampaignDAO;
import com.perfect.entity.*;
import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.mongodb.utils.DateUtils;
import com.perfect.service.*;
import com.perfect.utils.JSONUtils;
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

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, @RequestBody BiddingRuleEntity entity) {
        biddingRuleService.createBiddingRule(entity);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void get(HttpServletRequest request) {

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


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView delete(@PathVariable String id) {
        biddingRuleService.remove(id);

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attribute = new HashMap<>();

        attribute.put("success", true);
        jsonView.setAttributesMap(attribute);

        return new ModelAndView(jsonView);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public void delete(HttpServletRequest request, @RequestBody BiddingRuleEntity biddingRuleEntity) {
        biddingRuleService.createBiddingRule(biddingRuleEntity);
    }


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
                             @RequestParam(value = "sort", required = false, defaultValue = "kw") String sort,
                             @RequestParam(value = "o", required = false, defaultValue = "true") boolean asc) {

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> q = new HashMap<>();
        List<KeywordEntity> entities = null;
        if (cp != null) {
            List<AdgroupEntity> adgroupEntityList = sysAdgroupService.findIdByCampaignId(cp);

            List<Long> ids = new ArrayList<>(adgroupEntityList.size());
            for (AdgroupEntity adgroupEntity : adgroupEntityList) {
                ids.add(adgroupEntity.getAdgroupId());
            }
            entities = sysKeywordService.findByAdgroupIds(ids);
        } else if (agid != null) {
            entities = sysKeywordService.findByAdgroupId(agid);
        } else {
            return new ModelAndView(jsonView);
        }


        List<Long> ids = new ArrayList<>();

        Map<Long, BiddingRuleDTO> biddingRuleDTOs = new HashMap<>();

        for (KeywordEntity entity : entities) {
            ids.add(entity.getKeywordId());
        }
        String yesterday = DateUtils.getYesterdayStr();

        Map<String, List<StructureReportEntity>> reports = basisReportService.getKeywordReport(ids.toArray(new Long[]{}), yesterday, yesterday, 0);
        List<StructureReportEntity> list = reports.get(yesterday);

        for (StructureReportEntity entity : list) {
            long kwid = entity.getKeywordId();
            BiddingRuleDTO dto = biddingRuleDTOs.get(kwid);
            dto.setClick(entity.getPcClick());
            dto.setConversion(entity.getPcConversion());
            dto.setCost(entity.getPcCost());
            dto.setCpc(entity.getPcCpc());
            dto.setCpm(entity.getPcCpm());
            dto.setCtr(entity.getPcCtr());
            dto.setImpression(entity.getPcImpression());
        }

        Map<String, Object> attributes = JSONUtils.getJsonMapData(entities);
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
