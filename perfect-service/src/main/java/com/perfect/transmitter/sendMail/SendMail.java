package com.perfect.transmitter.sendMail;

import org.springframework.stereotype.Component;

/**
 * Created by john on 2014/10/14.
 */
@Component("sendMail")
public class SendMail {

    /**
     * 开始发送文本格式邮件
     *
     * @param address
     * @param subject
     * @param content
     */
    public void startSendTextMail(String address, String subject, String content) {
        SimpleMailSender sms = new SimpleMailSender();
        //这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        //接收地址
        mailInfo.setToAddress(address);
        //邮件主题
        mailInfo.setSubject(subject);
        //邮件内容太
        mailInfo.setContent(content);
        //这个类主要来发送邮件
        sms.sendTextMail(mailInfo);//发送文体格式
    }


    /**
     * 开始发送HTML格式邮件
     *
     * @param address
     * @param subject
     * @param content
     */
    public void startSendHtmlMail(String address, String subject, String content) {
        SimpleMailSender sms = new SimpleMailSender();
        //这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        //接收地址
        mailInfo.setToAddress(address);
        //邮件主题
        mailInfo.setSubject(subject);
        //邮件内容太
        mailInfo.setContent(content);
        //这个类主要来发送邮件
        sms.sendHtmlMail(mailInfo);
    }
}
