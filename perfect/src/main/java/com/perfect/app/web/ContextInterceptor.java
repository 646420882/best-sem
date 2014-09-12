package com.perfect.app.web;

import com.perfect.core.AppContext;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.SystemUserEntity;
import com.perfect.service.AccountManageService;
import com.perfect.service.SystemUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by vbzer_000 on 2014/8/27.
 */
public class ContextInterceptor implements HandlerInterceptor {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private AccountManageService accountManageService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return true;
        }

        String userName = WebUtils.getUserName(request);
        if (userName == null) {
            return false;
        }

        Long accoundId = WebUtils.getAccountId(request);
        if (accoundId != null && accoundId > 0) {
            AppContext.setUser(userName, accoundId);
            return true;
        } else {
            SystemUserEntity entity = systemUserService.getSystemUser(userName);
            if (entity == null) {
                return false;
            }
            if (entity.getBaiduAccountInfoEntities().size() == 0) {
                return false;
            }

            if (entity.getBaiduAccountInfoEntities().size() == 1) {
                BaiduAccountInfoEntity infoEntity = entity.getBaiduAccountInfoEntities().get(0);
                WebUtils.setAccountId(request, infoEntity.getId());
                AppContext.setUser(userName, infoEntity.getId());
                return true;
            }

            for (BaiduAccountInfoEntity infoEntity : entity.getBaiduAccountInfoEntities()) {
                if (infoEntity.isDfault()) {
                    WebUtils.setAccountId(request, infoEntity.getId());
                    AppContext.setUser(userName, infoEntity.getId());
                    break;
                }
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || (modelAndView.getView() != null && modelAndView.getView() instanceof MappingJackson2JsonView)) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }

        if (modelAndView != null) {

            Long accountId = WebUtils.getAccountId(request);
            Double datas[] = getBalanceAndBudget(accountId);

            ModelMap modelMap = modelAndView.getModelMap();
            modelMap.put("currSystemUserName", WebUtils.getUserName(request));

            if (datas[0] == null) {
                if (datas[1] == null) {
                    modelMap.put("accountBalance", 0);
                    modelMap.put("accountBudget", 0);
                    modelMap.put("remainderDays", 0);
                } else {
                    modelMap.put("accountBalance", 0);
                    modelMap.put("accountBudget", datas[1]);
                    modelMap.put("remainderDays", 0);
                }
            }
            if (datas[0] != null) {
                if (datas[1] != null) {
                    if (datas[0] == 0 || datas[1] == 0) {
                        modelMap.put("remainderDays", 0);
                    } else {
                        String vStr = Double.valueOf(datas[0] / datas[1]).toString();
                        modelMap.put("remainderDays", vStr.substring(0, vStr.indexOf(".")));
                    }
                    modelMap.put("accountBalance", datas[0]);
                    modelMap.put("accountBudget", datas[1]);
                } else {
                    modelMap.put("accountBalance", datas[0]);
                    modelMap.put("accountBudget", 0);
                    modelMap.put("remainderDays", 0);
                }

            }


            /*BaiduAccountInfoEntity baiduAccountInfoEntity = null;
            CommonService commonService = BaiduServiceSupport.getCommonService(accountManageService.getBaiduAccountInfoById(accountId));
            BaiduApiService apiService = new BaiduApiService(commonService);
            //获取账户信息
            AccountInfoType accountInfoType = apiService.getAccountInfo();
            BeanUtils.copyProperties(accountInfoType, baiduAccountInfoEntity);

            MongoTemplate mongoTemplate = BaseMongoTemplate.getMongoTemplate(DBNameUtils.getSysDBName());
            Update update = new Update();
            update.set("bdAccounts.$", baiduAccountInfoEntity);
            mongoTemplate.updateFirst(
                    Query.query(
                            Criteria.where("userName").is(WebUtils.getUserName(request)).and("bdAccounts._id").is(accountId)),
                    update, SystemUserEntity.class);*/
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    //获取账户余额和账户余额
    private Double[] getBalanceAndBudget(Long accountId) {
        Double balance = accountManageService.getBaiduAccountInfoById(accountId).getBalance();
        Double yesterdayCost = accountManageService.getBaiduAccountInfoById(accountId).getBudget();
        return new Double[]{balance, yesterdayCost};
    }
}
