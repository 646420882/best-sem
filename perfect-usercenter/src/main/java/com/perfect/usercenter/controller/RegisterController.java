package com.perfect.usercenter.controller;

import com.perfect.service.AccountRegisterService;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by subdong on 15-12-16.
 */
@RestController
@Scope("prototype")
@RequestMapping(value = "/userCenter")
public class RegisterController {

    private AccountRegisterService accountRegisterService;

    /**
     * 注册帐号
     *
     * @return
     */
    @RequestMapping(value = "/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView registerData(ModelMap model, HttpServletResponse response, HttpServletRequest request,
                                     @RequestParam(value = "companyname", required = false) String companyname,
                                     @RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "email", required = false) String email,
                                     @RequestParam(value = "password", required = false) String password,
                                     @RequestParam(value = "mailinAddress", required = false) String mailinAddress,
                                     @RequestParam(value = "contacts", required = false) String contacts,
                                     @RequestParam(value = "contactsPhone", required = false) String contactsPhone,
                                     @RequestParam(value = "openPlatform", required = false) String openPlatform,
                                     @RequestParam(value = "phoenixNestUser", required = false) String phoenixNestUser,
                                     @RequestParam(value = "phoenixNestPassword", required = false) String phoenixNestPassword,
                                     @RequestParam(value = "urlAddress", required = false) String urlAddress,
                                     @RequestParam(value = "accountType", required = false) String accountType) {
        if (companyname == null || "".equals(companyname)) getModel(model);
        if (username == null || "".equals(username)) getModel(model);
        if (email == null || "".equals(email)) getModel(model);
        if (password == null || "".equals(password)) getModel(model);
        if (mailinAddress == null || "".equals(mailinAddress)) getModel(model);
        if (contacts == null || "".equals(contacts)) getModel(model);
        if (contactsPhone == null || "".equals(contactsPhone)) getModel(model);
        if (openPlatform == null || "".equals(openPlatform)) getModel(model);
        if (phoenixNestUser == null || "".equals(phoenixNestUser)) getModel(model);
        if (phoenixNestPassword == null || "".equals(phoenixNestPassword)) getModel(model);
        if (urlAddress == null || "".equals(urlAddress)) getModel(model);
        if (accountType == null || "".equals(accountType)) getModel(model);


        //int flag = accountRegisterService.addAccount(account, pwd, company, email);
        model.addAttribute("state", 1);
        return new ModelAndView("loginOrReg/register", model);
    }

    private ModelAndView getModel(ModelMap model){
        model.addAttribute("state", -1);
        return new ModelAndView("loginOrReg/register", model);
    }
}
