package com.perfect.app.ucenter.controller;

import com.perfect.service.BasisReportUCService;
import com.perfect.web.suport.WebContextSupport;
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
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by john on 2014/7/25.
 */
@RestController
@Scope("prototype")
public class AccountOverviewController extends WebContextSupport {

    @Resource
    private AccountOverviewService accountOverviewService;

    @Resource
    private BasisReportUCService basisReportUCService;

    /**
     * 账户概览(获取汇总数据)
     *
     * @param response
     * @param startDate
     * @param endDate
     */
    @RequestMapping(value = "/account/getAccountOverviewData", method = {RequestMethod.GET, RequestMethod.POST})
    public void getAccountOverviewData(HttpServletResponse response, String startDate, String endDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar YesterdayCal = Calendar.getInstance();
        YesterdayCal.add(Calendar.DATE, -1);
        String Yesterday = dateFormat.format(YesterdayCal.getTime());

        Date newstartDate;
        Date newendDate;
        try {
            if (startDate == null || startDate.equals("")) {

                newstartDate = dateFormat.parse(Yesterday);
            } else {
                newstartDate = dateFormat.parse(startDate);
            }
            if (endDate == null || endDate.equals("")) {
                newendDate = dateFormat.parse(Yesterday);
            } else {
                newendDate = dateFormat.parse(endDate);
            }

            Map<String, List<Object>> returnAccount = basisReportUCService.getAccountDateVS(newstartDate, newendDate, new Date(), new Date(), 0, 0, 0, "-1", 0, 0);
            writeJson(returnAccount, response);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/account/countAssistant", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView countAssistant(HttpServletResponse response) {
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();

        List<CountAssistantVO> maps = accountOverviewService.countAssistant();
        jsonView.setAttributesMap(JSONUtils.getJsonMapData(maps));
        return new ModelAndView(jsonView);
    }
}
