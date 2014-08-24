package com.perfect.app.bidding.controller;

import com.perfect.entity.bidding.BiddingRuleEntity;
import com.perfect.service.BasisReportService;
import com.perfect.service.BiddingRuleService;
import com.perfect.utils.JSONUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yousheng on 2014/8/22.
 *
 * @author yousheng
 */
@Controller("/bidding")
public class BiddingController {


    @Resource
    private BiddingRuleService biddingRuleService;

    @Resource
    private BasisReportService basisReportService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(HttpServletRequest request, @ModelAttribute("biddingModel") BiddingRuleEntity entity) {
        biddingRuleService.createBiddingRule(entity);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    public ModelAndView home(HttpServletRequest request,
                             @RequestParam("ag") long agid,
                             @RequestParam("q") String query,
                             @RequestParam("s") int skip,
                             @RequestParam("l") int limit, @RequestParam("sort") String sort,
                             @RequestParam("o") boolean asc) {

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> q = new HashMap<>();
        if (agid > 0) {
            q.put("agid", agid);
        }

        List<BiddingRuleEntity> entities = null;

        if (query == null) {
            entities = biddingRuleService.findRules(q, skip, limit, sort, (asc) ? Sort.Direction.ASC : Sort.Direction.DESC);
        } else {
            entities = biddingRuleService.findRules(q, "kw", query, skip, limit, sort, (asc) ? Sort.Direction.ASC : Sort.Direction.DESC);
        }
        List<Long> ids = new ArrayList<>();

        for (BiddingRuleEntity entity : entities) {
            ids.add(entity.getKeywordId());
        }


        Map<String, Object> attributes = JSONUtils.getJsonMapData(entities);
        jsonView.setAttributesMap(attributes);

        String yesterday = "";

        // 获取报告信息

        return new ModelAndView(jsonView);
    }
}
