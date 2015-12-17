package com.perfect.app.conf.controller;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ResHeader;
import com.perfect.autosdk.core.ResHeaderUtil;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.AccountInfoType;
import com.perfect.autosdk.sms.v3.AccountService;
import com.perfect.autosdk.sms.v3.GetAccountInfoRequest;
import com.perfect.autosdk.sms.v3.GetAccountInfoResponse;
import com.perfect.web.suport.WebUtils;
import com.perfect.core.AppContext;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.SystemUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vbzer_000 on 2014/9/2.
 */
@Controller
@RequestMapping("/configuration")
public class ConfigurationController {

    @Resource
    private SystemUserService systemUserService;

    @RequestMapping(value = "/")
    public ModelAndView index(ModelMap modelMap) {
        String userName = AppContext.getUser();
        if (userName == null)
            return new ModelAndView("configuration/configure", modelMap);

        SystemUserDTO systemUserEntity = systemUserService.getSystemUser(userName);

        List<BaiduAccountInfoDTO> baiduAccountInfoEntityList = systemUserEntity.getBaiduAccounts();

        modelMap.addAttribute("accountList", baiduAccountInfoEntityList);
        return new ModelAndView("configuration/configure", modelMap);
    }


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        return new ModelAndView("configuration/add");
    }


    @RequestMapping(value = "/save", method = RequestMethod.GET)
    public ModelAndView save(HttpServletRequest request, String username, String password, String token, ModelMap modelMap) {
        int flag = 0;
        try {
            CommonService commonService = ServiceFactory.getInstance(username, password, token, null);

            GetAccountInfoRequest getAccountInfoRequest = new GetAccountInfoRequest();

            AccountService accountService = commonService.getService(AccountService.class);
            GetAccountInfoResponse response = accountService.getAccountInfo(getAccountInfoRequest);
            if (response != null) {
                AccountInfoType accountInfoType = response.getAccountInfoType();

                BaiduAccountInfoDTO baiduAccountInfoEntity = new BaiduAccountInfoDTO();

                BeanUtils.copyProperties(accountInfoType, baiduAccountInfoEntity);
                baiduAccountInfoEntity.setId(accountInfoType.getUserid());
                baiduAccountInfoEntity.setBaiduUserName(username);
                baiduAccountInfoEntity.setBaiduPassword(password);
                baiduAccountInfoEntity.setToken(token);
                baiduAccountInfoEntity.setState(1l);

                systemUserService.addAccount(WebUtils.getUserName(request), baiduAccountInfoEntity);
                flag = 1;
            } else {
                ResHeader resHeader = ResHeaderUtil.getJsonResHeader(false);
                modelMap.put("error", resHeader.getDesc());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            flag = -1;
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("status", flag);
        jsonView.setAttributesMap(statusMap);
        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/acc/deletebdUser", method = RequestMethod.GET)
    public ModelAndView del(Long id, String account) {
        boolean success = systemUserService.removeAccount(id, account);

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("status", success);

        jsonView.setAttributesMap(statusMap);

        return new ModelAndView(jsonView);
    }

    @RequestMapping(value = "/acc/upBaiduName", method = RequestMethod.GET)
    public ModelAndView upBaiduName(Long id, String name) {
        boolean success = systemUserService.updateBaiDuName(name, id);

        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> statusMap = new HashMap<>();
        statusMap.put("status", success);

        jsonView.setAttributesMap(statusMap);

        return new ModelAndView(jsonView);
    }
}
