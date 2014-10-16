package com.perfect.app.homePage.controller;

import com.perfect.commons.CustomUserDetailsService;
import com.perfect.app.web.WebUtils;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.entity.MD5;
import com.perfect.entity.SystemUserEntity;
import com.perfect.redis.JRedisUtils;
import com.perfect.service.AccountRegisterService;
import com.perfect.service.SystemUserService;
import com.perfect.transmitter.sendMail.SendMail;
import com.perfect.utils.web.WebContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
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
            if (CustomUserDetailsService.isUsernameNotFound())
                model.put("invalidUserName", "用户名不存在");
            else if (badCredentialsNum > 0) {
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


    /**
     * 忘记密码
     */
    @RequestMapping(value = "/validate/validateUserNameIsExists", method = {RequestMethod.GET, RequestMethod.POST})
    public void validateUserNameIsExists(HttpServletResponse response,HttpServletRequest request,String userName){
        SystemUserEntity entity = systemUserService.getSystemUser(userName);
        if(entity==null){
            webContext.writeJson("userName don't Exists!",response);
        }else{

            String path = request.getContextPath();
            String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

            Jedis jedis = JRedisUtils.get();
            jedis.set(userName,"");
            jedis.expire(userName, 60 * 30);
            basePath+="forgetPassword/findPasswordPage?userName="+userName+"&key="+userName;

            String content = "亲爱的用户 '"+userName+"' , 您好！\n" +
                    "\n" +
                    "请点击这里，重置您的密码： \n" +
                    ""+basePath+"\n" +
                    "该邮件链接地址点击后会失效，或者30分钟后失效\n"+
                    "(如果链接无法点击，请将它拷贝到浏览器的地址栏中)\n" +
                    "\n" +
                    "好的密码，不但应该容易记住，还要尽量符合以下强度标准： \n" +
                    "·包含大小写字母、数字和符号 \n" +
                    "·不少于 10 位 \n" +
                    "·不包含生日、手机号码等易被猜出的信息 \n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "普菲特安全中心 敬启\n" +
                    "\n" +
                    "\n" +
                    "此为自动发送邮件，请勿直接回复\n";

            sendMail.startSendMail(entity.getEmail(),"找回密码-普菲特安全中心",content);
            webContext.writeJson("userName Exists!",response);
        }
    }

    /**
     * 找回密码页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/forgetPassword/findPasswordPage",method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView findPasswordPage(ModelMap model,String userName,String key) {
        Jedis jedis = JRedisUtils.get();
        if (jedis.exists(key)) {
            model.put("userName",userName);
            model.put("key",key);
            return new ModelAndView("homePage/pageBlock/findPassword", model);
        }else{
            return new ModelAndView("jsp/error/404.jsp",model);
        }
    }

    /**
     * 找回密码-重置密码
     * @return
     */
    @RequestMapping(value = "/forgetPassword/resetPassword",method = {RequestMethod.GET, RequestMethod.POST})
    public void resetPassword(HttpServletResponse response,String key,
                                      @RequestParam(value = "baiduAccountName", required = false) String baiduAccountName,
                                      @RequestParam(value = "userName", required = false) String userName,
                                      @RequestParam(value = "password", required = false) String pwd
                                     ) {
        Jedis jedis = JRedisUtils.get();
        if (jedis.exists(key)) {
            SystemUserEntity sysUserEntity = systemUserService.getSystemUser(userName);
            List<BaiduAccountInfoEntity> baiduAccountList = sysUserEntity.getBaiduAccountInfoEntities();
            String baiduUserName = null;
            for(BaiduAccountInfoEntity entity:baiduAccountList){
                if(entity.getBaiduUserName().equals(baiduAccountName)){
                    baiduUserName = entity.getBaiduUserName();
                    break;
                }
            }

            if(baiduUserName!=null){
                //重置密码
                MD5.Builder md5Builder = new MD5.Builder();
                MD5 md5 = md5Builder.password(pwd).salt(userName).build();
                boolean  isSuccess = systemUserService.updatePassword(userName,md5.getMD5());
                if(isSuccess){
                    jedis.expire(key, 0);
                    webContext.writeJson("updateSuccess",response);
                }else{
                    webContext.writeJson("updateFail", response);
                }
            }else{
                webContext.writeJson("NoAccount", response);
                //返回结果，没有该子账户，不能重置密码
            }
        }else{
            //找回密码的url失效
           webContext.writeJson("urlInvali",response);
        }

    }


}
