package com.perfect.usercenter.controller;

import com.perfect.service.AccountManageService;
import com.perfect.commons.email.EmailHelper;
import com.perfect.utils.JsonViews;
import com.perfect.utils.redis.JRedisUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
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


    /**
     * <p>修改密码时, 校验当前使用的密码
     *
     * @param oldPassword
     * @param userName
     * @return
     */
    @RequestMapping(value = "/password/validate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView validatePassword(@RequestParam String oldPassword, @RequestParam String userName) {
        int status = accountManageService.JudgePwd(userName, oldPassword);

        if (status == 1) {
            return JsonViews.generateSuccessNoData();
        } else {
            return JsonViews.generateFailedNoData();
        }
    }

    /**
     * <p>修改密码
     *
     * @param userName
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/password/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView updatePassword(@RequestParam String userName, @RequestParam String newPassword) {
        int status = accountManageService.updatePwd(userName, newPassword);

        if (status == 1) {
            return JsonViews.generateSuccessNoData();
        } else {
            return JsonViews.generateFailedNoData();
        }
    }

    /**
     * <p>发送邮箱验证码
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/email/sendCaptcha", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendEmailCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String captcha = createCaptcha();

        Jedis jedis = null;
        try {
            jedis = JRedisUtils.get();
            jedis.setex(captcha, 600, "1");
        } finally {
            if (Objects.nonNull(jedis))
                jedis.close();
        }

        EmailHelper.sendHtmlEmail("", String.format(captchaHtmlTemplate, captcha), email);
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
