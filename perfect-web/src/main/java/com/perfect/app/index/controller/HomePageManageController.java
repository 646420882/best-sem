package com.perfect.app.index.controller;

import com.perfect.commons.constants.AuthConstants;
import com.perfect.commons.message.mail.SendMail;
import com.perfect.core.AppContext;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.AccountRegisterService;
import com.perfect.service.SystemUserService;
import com.perfect.utils.MD5;
import com.perfect.utils.redis.JRedisUtils;
import com.perfect.web.support.WebContextSupport;
import com.perfect.web.support.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

/**
 * Created by baizz on 2014-6-23.
 * 2014-11-28 refactor
 */
@RestController
@Scope("prototype")
public class HomePageManageController extends WebContextSupport implements AuthConstants {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private AccountRegisterService accountRegisterService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request, ModelMap modelMap) {
        modelMap.put("currSystemUserName", WebUtils.getUserName(request));
        modelMap.put("accountList", AppContext.getBaiduAccounts());

        return new ModelAndView("homePage/home");
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public void logout(HttpServletResponse response) {
        Cookie cookies = new Cookie("semToken", null);
        cookies.setMaxAge(0);
        response.addCookie(cookies);
    }

//    /**
//     * 跳转至SEM首页
//     *
//     * @return
//     */
//    @Deprecated
//    @RequestMapping(value = "/home", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView getHomePage(HttpServletRequest request, ModelMap modelMap) {
//        String userName = WebUtils.getUserName(request);
//
//        /**
//         * replace with {@link com.perfect.service.SystemUserInfoService#findSystemUserInfoByUserName(String)}
//         *
//         * @deprecated
//         */
//        SystemUserDTO systemUserDTO = systemUserService.getSystemUser(userName);
//        if (systemUserDTO == null) {
//            return new ModelAndView("redirect:/logout");
//        }
//
//        modelMap.put("currSystemUserName", userName);
//        modelMap.put("accountList", systemUserDTO.getBaiduAccounts());
//        return new ModelAndView("homePage/home");
//    }

    /**
     * 登录成功, 跳转至百思首页
     *
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView getBestIndexPage(HttpServletRequest request, ModelMap modelMap) {
        String userName = WebUtils.getUserName(request);
        modelMap.put("currSystemUserName", userName);
        return new ModelAndView("bestPage/bestIndex");
    }

    /**
     * 智能结构页面
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
    @Deprecated
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
        SystemUserDTO systemUserDTO = systemUserService.getSystemUser(userName);
        if (systemUserDTO == null) {
            writeJson("userName no Exists!", response);
        } else {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";

            Jedis jedis = JRedisUtils.get();
            try {
                String key = UUID.randomUUID().toString();
                jedis.set(key, "");
                jedis.expire(key, 60 * 30);     // 30分钟后失效

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

                if (systemUserDTO.getEmail() != null) {
                    SendMail.startSendHtmlMail(systemUserDTO.getEmail(), subject, content);
                    writeJson("userName Exists!", response);
                } else {
                    writeJson("NO EMAIL", response);
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
        } catch (Exception e) {
            return new ModelAndView("jsp/error/404.jsp", model);
        } finally {
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
    @Deprecated
    @RequestMapping(value = "/forgetPassword/resetPassword", method = {RequestMethod.GET, RequestMethod.POST})
    public void resetPassword(HttpServletResponse response, String key,
                              @RequestParam(value = "baiduAccountName", required = false) String baiduAccountName,
                              @RequestParam(value = "userName", required = false) String userName,
                              @RequestParam(value = "password", required = false) String pwd) {
        Jedis jedis = JRedisUtils.get();
        try {
            if (jedis.exists(key)) {
                SystemUserDTO systemUserDTO = systemUserService.getSystemUser(userName);
                List<BaiduAccountInfoDTO> baiduAccountInfoDTOList = systemUserDTO.getBaiduAccounts();
                String baiduUserName = null;
                for (BaiduAccountInfoDTO dto : baiduAccountInfoDTOList) {
                    if (dto.getBaiduUserName().equals(baiduAccountName)) {
                        baiduUserName = dto.getBaiduUserName();
                        break;
                    }
                }

                if (baiduUserName != null) {
                    // 重置密码
                    MD5.Builder md5Builder = new MD5.Builder();
                    MD5 md5 = md5Builder.password(pwd).salt(userName).build();
                    boolean isSuccess = systemUserService.updatePassword(userName, md5.getMD5());
                    if (isSuccess) {
                        jedis.expire(key, 0);
                        writeJson("updateSuccess", response);
                    } else {
                        writeJson("updateFail", response);
                    }
                } else {
                    // 返回结果, 没有该子账户, 不能重置密码
                    writeJson("NoAccount", response);
                }
            } else {
                // 找回密码的url失效
                writeJson("urlInvali", response);
            }
        } finally {
            if (jedis != null) {
                JRedisUtils.returnJedis(jedis);
            }
        }
    }

    @RequestMapping(value = "/forgetPassword/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginPage(ModelMap model, String mes) {
        model.put("invalidUserName", mes);
        return new ModelAndView("homePage/login", model);
    }
}
