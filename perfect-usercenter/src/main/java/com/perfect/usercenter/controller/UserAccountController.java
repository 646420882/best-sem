package com.perfect.usercenter.controller;

import com.google.common.collect.Maps;
import com.perfect.commons.email.EmailHelper;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.service.AccountManageService;
import com.perfect.service.UserAccountService;
import com.perfect.utils.redis.JRedisUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created on 2015-12-20.
 *
 * @author dolphineor
 */
@RestController
@Scope("prototype")
@RequestMapping("/account")
public class UserAccountController {

    private static final String EMAIL_CAPTCHA_OF_REDIS_KEY = "BEST-USER-%s-EMAIL-CAPTCHA";

    private static String captchaHtmlTemplate = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=10\">" +
            "</head>" +
            "<body>" +
            "<div>" +
            "<span>此验证码10分钟内有效</span>" +
            "</br>" +
            "<span>%s</span>" +
            "</div>" +
            "</body>" +
            "</html>";


    @Resource
    private AccountManageService accountManageService;

    @Resource
    private UserAccountService userAccountService;


    /**
     * <p>修改密码时, 校验当前使用的密码
     *
     * @param pwd
     * @param userName
     * @return
     */
    @RequestMapping(value = "/password/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView validatePassword(@RequestParam String pwd, @RequestParam String userName) {
        int status = accountManageService.JudgePwd(userName, pwd);

        Map<String, Boolean> map = new HashMap<>();
        if (status == 1) {
            map.put("valid", true);
        } else {
            map.put("valid", false);
        }
        AbstractView jsonView = new MappingJackson2JsonView();
        jsonView.setAttributesMap(map);
        return new ModelAndView(jsonView);
    }

    /**
     * <p>修改密码
     *
     * @param userName
     * @param NewPassword
     * @return
     */
    @RequestMapping(value = "/password/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updatePassword(@RequestParam String userName, @RequestParam String NewPassword, ModelMap modelMap) {
        int status = accountManageService.updatePwd(userName, NewPassword);
        if (status == 1) {
            modelMap.put("valid", true);
        } else {
            modelMap.put("valid", false);
        }
        return new ModelAndView("/password/password", modelMap);
    }

    /**
     * <p>发送邮箱验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/email/sendCaptcha", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmailCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String captcha = createCaptcha();

        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            jedis.lpush(String.format(EMAIL_CAPTCHA_OF_REDIS_KEY, username), captcha);
            jedis.expire(String.format(EMAIL_CAPTCHA_OF_REDIS_KEY, username), 600);
        } finally {
            if (Objects.nonNull(jedis))
                jedis.close();
        }

        EmailHelper.sendHtmlEmail("邮箱绑定", String.format(captchaHtmlTemplate, captcha), email);
    }

    @RequestMapping(value = "/email/confirmCaptcha", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView confirmEmailCaptcha(@RequestParam String username, @RequestParam String email, @RequestParam String captcha) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = Maps.newHashMap();

        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            String orgCaptcha = jedis.get(captcha);
            if (Objects.nonNull(orgCaptcha)) {
                if (Objects.equals(orgCaptcha, captcha)) {
                    // 校验成功
                    attrMap.put("status", 1);
                    userAccountService.updateEmail(username, email);
                } else {
                    // 验证码错误
                    attrMap.put("status", 0);
                }
            } else {
                // 验证码失效
                attrMap.put("status", -1);
            }
        } finally {
            if (Objects.nonNull(jedis))
                jedis.close();
        }

        jsonView.setAttributesMap(attrMap);
        return new ModelAndView(jsonView);
    }

    /**
     * <p>解除邮箱绑定
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/email/unbind", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView unbindEmail(@RequestParam String username) {
        userAccountService.updateEmail(username, "");

        return jsonView(true);
    }

    /**
     * <p>绑定搜客帐号
     *
     * @param username
     * @param moduleAccount
     * @return
     */
    @RequestMapping(value = "/souke/add", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView bindSoukeAccount(@RequestParam String username, @RequestBody ModuleAccountInfoDTO moduleAccount) {
        userAccountService.bindAccountForSem(username, moduleAccount);

        return jsonView(true);
    }

    /**
     * <p>解除绑定
     *
     * @param username
     * @param moduleAccountName
     * @return
     */
    @RequestMapping(value = "/souke/unbind", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView unbindSoukeAccount(@RequestParam String username, @RequestParam String moduleAccountName) {
        userAccountService.unbindAccountForSem(username, moduleAccountName);

        return jsonView(true);
    }

    /**
     * <p>激活搜客帐号
     *
     * @param username
     * @param moduleAccountName
     * @return
     */
    @RequestMapping(value = "/souke/active", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView activeSoukeAccount(@RequestParam String username, @RequestParam String moduleAccountName) {
        userAccountService.activeAccountForSem(username, moduleAccountName);

        return jsonView(true);
    }

    /**
     * <p>更新搜客账号
     *
     * @param username
     * @param moduleAccount
     * @return
     */
    @RequestMapping(value = "/souke/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updateSoukeAccount(@RequestParam String username, @RequestBody ModuleAccountInfoDTO moduleAccount) {
        userAccountService.updateAccountForSem(username, moduleAccount);

        return jsonView(true);
    }

    /**
     * <p>更新搜客帐号
     *
     * @param username
     * @param moduleAccountName
     * @return
     */
    @RequestMapping(value = "/souke/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView deleteSoukeAccount(@RequestParam String username, @RequestParam String moduleAccountName) {
        userAccountService.deleteAccountForSem(username, moduleAccountName);

        return jsonView(true);
    }

    private ModelAndView jsonView(boolean result) {
        AbstractView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attrMap = Maps.newHashMap();
        attrMap.put("status", result);
        jsonView.setAttributesMap(attrMap);

        return new ModelAndView(jsonView);
    }


    /**
     * <p>生成邮箱验证码
     *
     * @return
     */
    private String createCaptcha() {
        // 验证码在Redis中是否已经存在
        boolean continueFlag = true;
        String num = getRandom(6);
        while (continueFlag) {
            // 不存在num, 返回validateCode, 结束循环
            if (!captchaIsExists(num)) {
                return num;
            } else {
                num = getRandom(6);
            }
        }

        return getRandom(6);
    }

    private static String getRandom(int codeCount) {
        StringBuffer randomCodeRes = new StringBuffer();

        char[] codeSequenceNumber = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] codeSequenceChar = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'
        };

        List<String> randomCode = new ArrayList<String>();

        // 创建一个随机数生成器类
        Random random = new Random();

        // 随机产生, 验证码由几个数字、几个字母组成
        int shuziNum = random.nextInt(5) + 1;
        int charNum = codeCount - shuziNum;

        // 随机产生codeCount数字的验证码
        for (int i = 0; i < shuziNum; i++) {
            // 得到随机产生的验证码数字
            String numRand = String.valueOf(codeSequenceNumber[random.nextInt(codeSequenceNumber.length)]);
            // 将产生的六个随机数组合在一起
            randomCode.add(numRand);
        }
        for (int i = 0; i < charNum; i++) {
            // 得到随机产生的验证码字母
            String strRand = String.valueOf(codeSequenceChar[random.nextInt(codeSequenceChar.length)]);
            // 将产生的六个随机数组合在一起
            randomCode.add(strRand);
        }

        Collections.shuffle(randomCode);

        for (int i = 0, s = randomCode.size(); i < s; i++) {
            randomCodeRes.append(randomCode.get(i));
        }

        return randomCodeRes.toString();
    }

    private boolean captchaIsExists(String captcha) {
        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            return jedis.exists(captcha);
        } finally {
            if (Objects.nonNull(jedis))
                jedis.close();
        }
    }
}
