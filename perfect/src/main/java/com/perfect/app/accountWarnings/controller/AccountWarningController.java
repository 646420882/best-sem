package com.perfect.app.accountWarnings.controller;

import com.perfect.app.accountWarnings.service.AccountWarningService;
import com.perfect.core.AppContext;
import com.perfect.dao.SystemUserDAO;
import com.perfect.entity.SystemUserEntity;
import com.perfect.entity.WarningRuleEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by john on 2014/8/4.
 */

@RestController
@Scope("prototype")
public class AccountWarningController {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    AccountWarningService accountWarningService;

    @Resource
    private SystemUserDAO systemUserDAO;

    //当前登录用户名
    private static String currLoginUserName;

    static {
        currLoginUserName = (currLoginUserName == null) ? AppContext.getUser().toString() : currLoginUserName;
    }

    /**
     * 设置提醒页面
     * @return
     */
    @RequestMapping(value = "assistant/accountWarning" , method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView setWarningPage(ModelMap model){
        SystemUserEntity systemUserEntity = systemUserDAO.findByUserName(currLoginUserName);
        model.addAttribute("list",systemUserEntity.getBaiduAccountInfoEntities());
        return new ModelAndView("promotionAssistant/setWarning");
    }


    /**
     * 保存账户提醒信息
     * @param budgetType 预算类型
     * @param budget 预算金额
     * @param mails 以分号隔开的的多个邮箱地址
     * @param tels  以分号隔开的多个手机号码
     * @param isEnable  是否启用 @RequestParam(value = "startDate", required = false) String startDate,
     * @return
     */
    @RequestMapping(value = "assistant/saveWarningRule" , method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView saveWarningInfo(@RequestParam(value = "budgetType",required = false) Integer budgetType,
                                        @RequestParam(value = "accountId",required = false) Long accountId,
                                        @RequestParam(value = "budget",required = false) Double budget,
                                        @RequestParam(value = "warningPercent",required = false) Double warningPercent,
                                        @RequestParam(value = "mails",required = false) String mails,
                                        @RequestParam(value = "tels",required = false) String tels,
                                        @RequestParam(value = "isEnable",required = false)Integer isEnable){

        WarningRuleEntity warningRuleEntity = new WarningRuleEntity();
        warningRuleEntity.setAccountId(accountId);
        warningRuleEntity.setBudget(budget);
        warningRuleEntity.setWarningPercent(warningPercent);
        warningRuleEntity.setBudgetType(budgetType);
        warningRuleEntity.setIsWarninged(0);
        warningRuleEntity.setIsEnable(isEnable);
        warningRuleEntity.setMails(mails);
        warningRuleEntity.setTels(tels);
        warningRuleEntity.setStartTime(new Date());
        warningRuleEntity.setDayCountDate(new Date());

        accountWarningService.saveWarningRule(warningRuleEntity);

        return new ModelAndView("redirect:/assistant/showAllWarningRule");
    }


    /**
     * 显示已经设置了的预警规则
     * @param model
     * @return
     */
    @RequestMapping(value = "assistant/showAllWarningRule",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView showAllWarningRule(ModelMap model){
        Iterable<WarningRuleEntity> list = accountWarningService.findAllWarningRule();
        model.addAttribute("list",list);
        return new ModelAndView("promotionAssistant/showWarningRule");
    }


    /**
     * 根据id修改是否启用
     * @param id
     * @param isEnbled
     * @return
     */
    @RequestMapping(value = "assistant/updateWarningRuleOfIsEnbled",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView updateWarningRuleOfIsEnbled(@RequestParam(value = "id",required = false)String id,
                                                    @RequestParam(value = "isEnbled",required = false)Integer isEnbled){
        accountWarningService.updateWarningRuleOfIsEnbled(id,isEnbled);
        return new ModelAndView("redirect:/assistant/showAllWarningRule");
    }

}
