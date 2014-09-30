package com.perfect.transmitter.sender;

import com.perfect.transmitter.sendMail.MailSenderInfo;
import com.perfect.transmitter.sendMail.SimpleMailSender;
import com.perfect.transmitter.sendMes.SendMessage;

import java.util.Map;

/**
 * 发送邮件和发送短信的线程
 */

public class Sender implements Runnable{
    private Map<String,Object> map;


    public Sender(Map<String, Object> map) {
        this.map = map;
    }

    public void run() {
        String[] mails = (String[])map.get("mails");
        String[] tels = ((String)map.get("tels")).split(",");
        if(mails.length>0){
            sendMail(mails);
        }else if(tels.length>0){
            sendMessage((String)map.get("tels"));
        }
    }


    /**
     * 发送邮件
     */
    public void sendMail(String[] mails){
        SimpleMailSender sms = new SimpleMailSender();
        for(String mail:mails){
            //这个类主要是设置邮件
            MailSenderInfo mailInfo = new MailSenderInfo();
            //接收地址
            mailInfo.setToAddress(mail);
            //邮件主题
            mailInfo.setSubject("账户预警");
            //邮件内容太
            mailInfo.setContent("超出了当天的预算");
            //这个类主要来发送邮件
            sms.sendTextMail(mailInfo);//发送文体格式
        }
    }


    /**
     * 发送手机短信
     */
    public void sendMessage(String tels){
        SendMessage sms = new SendMessage();
        sms.SendMes(tels,new String[]{});
    }

}
