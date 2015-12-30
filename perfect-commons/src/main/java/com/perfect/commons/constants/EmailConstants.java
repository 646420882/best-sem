package com.perfect.commons.constants;

/**
 * Created on 2015-12-30.
 *
 * @author dolphineor
 */
public interface EmailConstants {

    /**
     * TODO 修改管理员邮箱地址
     * 测试时的管理员邮箱: best_a1d2@sina.com
     * 密码: perfect2016
     */
    String adminEmail = "best_a1d2@sina.com";

    // 密码重置模板(用户)
    String resetPasswordTemplateForUser = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=10\">" +
            "</head>" +
            "<body>" +
            "<div>" +
            "<span>%s, 您好:</span>" +
            "</br>" +
            "<span>您的密码已经成功重置为: %s, 请注意保存。</span>" +
            "</div>" +
            "</body>" +
            "</html>";

    // 密码重置模板(管理员)
    String resetPasswordTemplateForAdmin = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=10\">" +
            "</head>" +
            "<body>" +
            "<div>" +
            "<span>用户%s的密码已经成功重置为: %s</span>" +
            "</div>" +
            "</body>" +
            "</html>";

    // 修改邮箱验证码模板
    String captchaHtmlTemplate = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=10\">" +
            "</head>" +
            "<body>" +
            "<div>" +
            "<span>此验证码10分钟内有效: </span>" +
            "</br>" +
            "<span>%s</span>" +
            "</div>" +
            "</body>" +
            "</html>";

    // 新用户注册通知模板
    String registerUserForAdmin = "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset=\"UTF-8\">" +
            "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=10\">" +
            "</head>" +
            "<body>" +
            "<div>" +
            "<span>用户%s已注册成功，其所在公司为：%s。</span>" +
            "</div>" +
            "</body>" +
            "</html>";
}
