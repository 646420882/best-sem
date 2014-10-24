package com.perfect.app.homePage.controller;

import com.perfect.app.web.WebUtils;
import com.perfect.commons.CustomUserDetailsService;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.MD5;
import com.perfect.entity.SystemUserEntity;
import com.perfect.redis.JRedisUtils;
import com.perfect.service.AccountRegisterService;
import com.perfect.service.SystemUserService;
import com.perfect.transmitter.sendMail.SendMail;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Created by baizz on 2014-6-23.
 */
@RestController
@Scope("prototype")
public class HomePageManageController {

    @Resource
    private SystemUserService systemUserService;
    @Resource
    private AccountRegisterService accountRegisterService;
    @Resource
    private WebContext webContext;
    @Resource
    private SendMail sendMail;

    /**
     * 登陆页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getLoginPage(HttpServletRequest request,
                                     ModelMap model,
                                     @RequestParam(value = "error", required = false) boolean error) {
        if (error) {
            int badCredentialsNum = CustomUserDetailsService.getPasswdBadCredentialsNum();
            if (CustomUserDetailsService.isUsernameNotFound()) {
                if (CustomUserDetailsService.isVerifyNotPass())
                    model.put("invalidUserName", "正在审核中");
                else
                    model.put("invalidUserName", "用户名不存在");
            } else if (badCredentialsNum > 0) {
                if (badCredentialsNum == 3)
                    model.put("invalidPassword", "账户已被锁定");
                else
                    model.put("invalidPassword", "密码错误, 剩余" + (3 - badCredentialsNum) + "次");
            }
        } else {
            model.put("error", "");
        }
        return new ModelAndView("homePage/login", model);
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView logout() {
        return null;
    }

    /**
     * 登录成功, 跳转至首页
     *
     * @return
     */
    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getHomePage(HttpServletRequest request, ModelMap modelMap) {
        String userName = WebUtils.getUserName(request);
        SystemUserEntity entity = systemUserService.getSystemUser(userName);
        if (entity == null) {
            return new ModelAndView("redirect:/logout");
        }

        modelMap.put("currSystemUserName", userName);
        modelMap.put("accountList", entity.getBaiduAccountInfoEntities());
        return new ModelAndView("homePage/home");
    }

    /**
     * 智能分组页面
     *
     * @return
     */
    @RequestMapping(value = "/keyword_group", method = RequestMethod.GET)
    public ModelAndView getKeywordGroupPage() {
        return new ModelAndView("keywordGroup/keyword");
    }

    /**
     * 注册页面
     *
     * @return
     */
    @RequestMapping(value = "/register/page", method = RequestMethod.GET)
    public ModelAndView register(ModelMap model) {
        return new ModelAndView("homePage/pageBlock/register", model);
    }

    /**
     * 注册帐号
     *
     * @return
     */
    @RequestMapping(value = "/register/add", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView registerData(ModelMap model, HttpServletResponse response, HttpServletRequest request,
                                     @RequestParam(value = "username", required = false) String account,
                                     @RequestParam(value = "password", required = false) String pwd,
                                     @RequestParam(value = "companyname", required = false) String company,
                                     @RequestParam(value = "email", required = false) String email) {
        if (account == null || "".equals(account)) {
            model.addAttribute("state", -1);
            return new ModelAndView("homePage/pageBlock/register", model);
        }
        if (pwd == null || "".equals(pwd)) {
            model.addAttribute("state", -1);
            return new ModelAndView("homePage/pageBlock/register", model);
        }
        if (company == null || "".equals(company)) {
            model.addAttribute("state", -1);
            return new ModelAndView("homePage/pageBlock/register", model);
        }
        if (email == null || "".equals(email)) {
            model.addAttribute("state", -1);
            return new ModelAndView("homePage/pageBlock/register", model);
        }

        int flag = accountRegisterService.addAccount(account, pwd, company, email);
        model.addAttribute("state", flag);
        return new ModelAndView("homePage/pageBlock/register", model);
    }


    /**
     * 忘记密码
     */
    @RequestMapping(value = "/validate/validateUserNameIsExists", method = {RequestMethod.GET, RequestMethod.POST})
    public void validateUserNameIsExists(HttpServletResponse response, HttpServletRequest request, String userName) {
        SystemUserEntity entity = systemUserService.getSystemUser(userName);
        if (entity == null) {
            webContext.writeJson("userName no Exists!", response);
        } else {

            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

            Jedis jedis = JRedisUtils.get();
            try {
                String key = UUID.randomUUID().toString();
                jedis.set(key, "");
                jedis.expire(key, 60 * 30);//30分钟后失效

                basePath += "forgetPassword/findPasswordPage?userName=" + userName + "&key=" + key;

                String html = "<a href='" + basePath + "'>" + basePath + "</a>";

                String subject = "找回密码-普菲特安全中心";

                String content = "亲爱的用户 '" + userName + "' , 您好！<br/>" +
                        "<br/>" +
                        "请点击这里，重置您的密码： <br/>" +
                        "" + html + "<br/>" +
                        "该邮件链接地址在成功重置密码后会失效，或者30分钟后失效<br/>" +
                        "(如果链接无法点击，请将它拷贝到浏览器的地址栏中)<br/>" +
                        "<br/>" +
                        "好的密码，不但应该容易记住，还要尽量符合以下强度标准： <br/>" +
                        "·包含大小写字母、数字和符号 <br/>" +
                        "·不少于 10 位 <br/>" +
                        "·不包含生日、手机号码等易被猜出的信息 <br/>" +
                        "<br/>" +
                        "<br/>" +
                        "<br/>" +
                        "<br/>" +
                        "普菲特安全中心 敬启<br/>" +
                        "<br/>" +
                        "<br/>" +
                        "此为自动发送邮件，请勿直接回复<br/>";

                if(entity.getEmail()!=null){
                    sendMail.startSendHtmlMail(entity.getEmail(), subject, content);
                    webContext.writeJson("userName Exists!", response);
                }else{
                    webContext.writeJson("NO EMAIL", response);
                }
            } finally {
                if (jedis != null) {
                    JRedisUtils.returnJedis(jedis);
                }
            }
        }
    }

    /**
     * 找回密码页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/forgetPassword/findPasswordPage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView findPasswordPage(ModelMap model, String userName, String key) {
        Jedis jedis = JRedisUtils.get();
        try {
            if (jedis.exists(key)) {
                model.put("userName", userName);
                model.put("key", key);
                return new ModelAndView("homePage/pageBlock/findPassword", model);
            } else {
                return new ModelAndView("jsp/error/404.jsp", model);
            }
        }catch (Exception ex){
            return new ModelAndView("jsp/error/404.jsp", model);
        }finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }
    }

    /**
     * 找回密码-重置密码
     *
     * @return
     */
    @RequestMapping(value = "/forgetPassword/resetPassword", method = {RequestMethod.GET, RequestMethod.POST})
    public void resetPassword(HttpServletResponse response, String key,
                              @RequestParam(value = "baiduAccountName", required = false) String baiduAccountName,
                              @RequestParam(value = "userName", required = false) String userName,
                              @RequestParam(value = "password", required = false) String pwd
    ) {
        Jedis jedis = JRedisUtils.get();
        try {
            if (jedis.exists(key)) {
                SystemUserEntity sysUserEntity = systemUserService.getSystemUser(userName);
                List<BaiduAccountInfoEntity> baiduAccountList = sysUserEntity.getBaiduAccountInfoEntities();
                String baiduUserName = null;
                for (BaiduAccountInfoEntity entity : baiduAccountList) {
                    if (entity.getBaiduUserName().equals(baiduAccountName)) {
                        baiduUserName = entity.getBaiduUserName();
                        break;
                    }
                }

                if (baiduUserName != null) {
                    //重置密码
                    MD5.Builder md5Builder = new MD5.Builder();
                    MD5 md5 = md5Builder.password(pwd).salt(userName).build();
                    boolean isSuccess = systemUserService.updatePassword(userName, md5.getMD5());
                    if (isSuccess) {
                        jedis.expire(key, 0);
                        webContext.writeJson("updateSuccess", response);
                    } else {
                        webContext.writeJson("updateFail", response);
                    }
                } else {
                    //返回结果，没有该子账户，不能重置密码
                    webContext.writeJson("NoAccount", response);
                }
            } else {
                //找回密码的url失效
                webContext.writeJson("urlInvali", response);
            }
        } finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }
    }


    @RequestMapping(value = "/forgetPassword/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginPage(ModelMap model,String mes) {
        model.put("invalidUserName", mes);
        return new ModelAndView("homePage/login", model);
    }


}
