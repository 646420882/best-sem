package com.perfect.usercenter.controller;

import com.google.common.base.Strings;
import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.commons.web.JsonResultMaps;
import com.perfect.dto.sys.SystemModuleDTO;
import com.perfect.param.RegisterParam;
import com.perfect.service.AccountRegisterService;
import com.perfect.service.SystemModuleService;
import com.perfect.utils.JsonViews;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by subdong on 15-12-16.
 */
@RestController
@Scope("prototype")
public class RegisterController {

    @Resource
    private AccountRegisterService accountRegisterService;

    @Resource
    private SystemModuleService systemModuleService;

    /**
     * 平台查询
     */
    @RequestMapping(value = "/getPlatform", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getPlatform(HttpServletResponse response, HttpServletRequest request) {
        List<SystemModuleDTO> systemModuleDTOs = systemModuleService.list();

        if (systemModuleDTOs == null || systemModuleDTOs.isEmpty()) {
            return JsonViews.generateSuccessNoData();
        }
        return JsonViews.generate(JsonResultMaps.successMap(systemModuleDTOs));
    }

    /**
     * 注册帐号
     * <p>
     *
     * @return
     */
    @RequestMapping(value = "/userAdd", method = RequestMethod.POST)
    public ModelAndView registerData(ModelMap model, HttpServletResponse response, HttpServletRequest request,
                                     @ModelAttribute RegisterParam registerParam) {

        String msg = validParams(registerParam);

        if (!Strings.isNullOrEmpty(msg)) {
            model.addAttribute("msg", msg);
            return new ModelAndView("loginOrReg/register", model);
        }
        int flag = accountRegisterService.addAccount(registerParam);
        model.addAttribute("state", 1);
        return new ModelAndView("loginOrReg/register", model);
    }

    private ModelAndView getModel(ModelMap model) {
        model.addAttribute("state", -1);
        return new ModelAndView("loginOrReg/register", model);
    }


    public String validParams(RegisterParam registerParam) {

        String openplatform = registerParam.getOpenPlatform();

        StringBuilder msg = new StringBuilder();
        Stream.of(openplatform.split(",")).filter(name -> !Strings.isNullOrEmpty(name)).forEach((name -> {
            if (msg.toString().length() > 0) {
                return;
            }
            if (name.equals(SystemNameConstant.SOUKE_SYSTEM_NAME)) {
                if (Strings.isNullOrEmpty(registerParam.getBaiduUserName())) {
                    msg.append("开通搜客系统需要凤巢账号.");
                    return;
                } else if (Strings.isNullOrEmpty(registerParam.getBaiduUserPassword())) {
                    msg.append("开通搜客系统需要凤巢密码.");
                    return;
                }
            } else if (name.equals(SystemNameConstant.HUIYAN_SYSTEM_NAME)) {
                if (Strings.isNullOrEmpty(registerParam.getUrlAddress())) {
                    msg.append("开通慧眼系统需要网站url地址.");
                    return;
                }
            }
        }));

        return msg.toString();
    }
}
