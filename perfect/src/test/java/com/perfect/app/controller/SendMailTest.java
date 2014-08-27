package com.perfect.app.controller;

import com.perfect.app.transmitter.MailSenderInfo;
import com.perfect.app.transmitter.SimpleMailSender;
import org.junit.Test;

public class SendMailTest {

    @Test
    public void mail(){
        //这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("15923844052@163.com");
        mailInfo.setPassword("*******");//您的邮箱密码
        mailInfo.setFromAddress("15923844052@163.com");
        mailInfo.setToAddress("691864758@qq.com");
        mailInfo.setSubject("我的测试邮件");
        mailInfo.setContent("邮件可以了!");
        //这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendTextMail(mailInfo);//发送文体格式
        sms.sendHtmlMail(mailInfo);//发送html格式
    }
}
