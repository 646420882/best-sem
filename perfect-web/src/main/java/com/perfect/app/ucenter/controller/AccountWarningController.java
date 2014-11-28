package com.perfect.app.ucenter.controller;

import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountWarningDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.entity.sys.SystemUserEntity;
import com.perfect.entity.WarningRuleEntity;
import com.perfect.commons.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by john on 2014/8/4.
 */

@RestController
@Scope("prototype")
public class AccountWarningController {

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    AccountWarningDAO accountWarningDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource
    private WebContext webContext;

    /**
     * 设置提醒页面
     * @return
     */
    @RequestMapping(value = "assistant/accountWarning" , method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView setWarningPage(ModelMap model){
        SystemUserEntity systemUserEntity = systemUserDAO.findByUserName(AppContext.getUser());
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
        warningRuleEntity.setSystemUserName(AppContext.getUser());
        warningRuleEntity.setAccountId(accountId);
        warningRuleEntity.setBudget(budget);
        warningRuleEntity.setWarningPercent(warningPercent);
        warningRuleEntity.setBudgetType(budgetType);
        warningRuleEntity.setIsWarninged(0);
        warningRuleEntity.setIsEnable(isEnable);
        warningRuleEntity.setMails(mails);
        warningRuleEntity.setTels(tels);
        warningRuleEntity.setStartTime(new Date());

        accountWarningDAO.save(warningRuleEntity);

        return new ModelAndView("redirect:/assistant/showWarningRulePage");
    }


    /**
     * 显示预警规则的页面
     * @return
     */
    @RequestMapping(value = "assistant/showWarningRulePage",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView showWarningRulePage(){
        return new ModelAndView("promotionAssistant/showWarningRule");
    }

    /**
     * 显示已经设置了的预警规则
     * @return
     */
    @RequestMapping(value = "assistant/getAllWarningRule",method = {RequestMethod.GET,RequestMethod.POST})
    public void showAllWarningRule(HttpServletResponse response){
       Iterable<WarningRuleEntity> list = accountWarningDAO.findByUserName(AppContext.getUser());
        webContext.writeJson(list, response);
    }


    /**
     * 根据id修改是否启用
     * @param id
     * @param isEnbled
     * @return
     */
    @RequestMapping(value = "assistant/updateWarningRuleOfIsEnbled",method = {RequestMethod.GET,RequestMethod.POST})
    public void updateWarningRuleOfIsEnbled(@RequestParam(value = "id",required = false)String id,
                                                    @RequestParam(value = "isEnbled",required = false)Integer isEnbled){
        WarningRuleEntity warningRuleEntity = new WarningRuleEntity();
        warningRuleEntity.setId(id);
        warningRuleEntity.setIsEnable(isEnbled);
        accountWarningDAO.update(warningRuleEntity);
    }

}
