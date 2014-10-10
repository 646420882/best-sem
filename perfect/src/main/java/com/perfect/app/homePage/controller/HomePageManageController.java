package com.perfect.app.homePage.controller;

import com.perfect.app.homePage.service.CustomUserDetailsService;
import com.perfect.app.web.WebUtils;
import com.perfect.entity.MD5;
import com.perfect.entity.SystemUserEntity;
import com.perfect.redis.JRedisUtils;
import com.perfect.service.AccountRegisterService;
import com.perfect.service.SystemUserService;
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

/**
 * Created by baizz on 2014-6-23.
 */
@RestController
@Scope("prototype")
public class HomePageManageController {

    private static final String usernameNotFound = "org.springframework.security.core.userdetails.UsernameNotFoundException";
    private static final String badCredentials = "org.springframework.security.authentication.BadCredentialsException";

    @Resource
    private SystemUserService systemUserService;
    @Resource
    private AccountRegisterService accountRegisterService;

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
        Object _exception = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (error) {
            if (_exception != null) {
                String exceptionName = _exception.toString();
                exceptionName = exceptionName.substring(0, exceptionName.indexOf(":"));
                if (badCredentials.equals(exceptionName)) {
                    String userName = CustomUserDetailsService.getUserName();
                    String key = new MD5(userName).getMD5();

                    Jedis jedis = null;
                    try {
                        jedis = JRedisUtils.get();
                        if (jedis.ttl(key) == -1) {
                            jedis.set(key, 1 + "");
                            model.put("invalidPassword", "密码不对");
                        } else {
                            Integer oldValue = Integer.valueOf(jedis.get(key));
                            if (oldValue == 3) {
                                model.put("invalidPassword", "您已输错密码3次, 账户已被锁定, 请于1小时后重试!");
                            } else {
                                Integer newValue = oldValue + 1;
                                if (newValue == 3) {
                                    jedis.set(key, newValue.toString());
                                    model.put("invalidPassword", "您已输错密码3次, 账户已被锁定, 请于1小时后重试!");
                                } else {
                                    jedis.set(key, newValue.toString());
                                    model.put("invalidPassword", "密码不对");
                                }
                            }

                        }
                        jedis.expire(key, 600);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (jedis != null) {
                            JRedisUtils.returnJedis(jedis);
                        }
                    }

                } else if (usernameNotFound.equals(exceptionName)) {
                    model.put("invalidUserName", "用户名不对");
                }
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
                                     @RequestParam(value = "companyname", required = false) String company) {
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

        int flag = accountRegisterService.addAccount(account, pwd, company);
        model.addAttribute("state", flag);
        return new ModelAndView("homePage/pageBlock/register", model);
    }
}
