package com.perfect.app.ucenter.controller;

import com.perfect.commons.web.WebContextSupport;
import com.perfect.core.AppContext;
import com.perfect.service.AccountOverviewService;
import com.perfect.utils.json.JSONUtils;
import com.perfect.vo.CountAssistantVO;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/7/25.
 */
@RestController
@Scope("prototype")
public class AccountOverviewController extends WebContextSupport{

    @Resource
    private AccountOverviewService accountOverviewService;


    /**
     * 账户概览(获取汇总数据)
     *
     * @param response
     * @param startDate
     * @param endDate
     */
    @RequestMapping(value = "/account/getAccountOverviewData", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAccountOverviewData(HttpServletResponse response, String startDate, String endDate) {
        Map<String, Object> map = accountOverviewService.getKeyWordSum(startDate, endDate);
        writeJson(map, response);
    }


    @RequestMapping(value = "/account/countAssistant", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView countAssistant(HttpServletResponse response){
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

        List<CountAssistantVO> maps = accountOverviewService.countAssistant();
        jsonView.setAttributesMap(JSONUtils.getJsonMapData(maps));
        return new ModelAndView(jsonView);
    }
}
