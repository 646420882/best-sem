package com.perfect.transmitter.sender;

import com.perfect.transmitter.sendMail.MailSenderInfo;
import com.perfect.transmitter.sendMail.SendMail;
import com.perfect.transmitter.sendMail.SimpleMailSender;
import com.perfect.transmitter.sendMes.SendMessage;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 发送邮件和发送短信的线程
 */

public class Sender implements Runnable{


    @Resource
    private SendMail sendMail;

    @Resource
    private SendMessage sendMessage;

    private Map<String,Object> map;


    public Sender(Map<String, Object> map) {
        this.map = map;
    }

    public void run() {
        String[] mails = (String[])map.get("mails");
        String[] tels = ((String)map.get("tels")).split(",");
        if(mails.length>0){
            sendMail(mails);
        }

        if(tels.length>0){
            sendMessage((String)map.get("tels"));
        }
    }


    /**
     * 发送邮件
     */
    public void sendMail(String[] mails){
        for(String mail:mails){
            sendMail.startSendMail(mail,"账户预警","超出了预算!");
        }
    }


    /**
     * 发送手机短信
     */
    public void sendMessage(String tels){
        sendMessage.startSendMes(tels,new String[]{});
    }

}
