package com.perfect.commons.message.sender;

import com.perfect.commons.message.mail.SendMail;
import com.perfect.commons.message.sms.SendMessage;

import java.util.Map;

/**
 * 发送邮件和发送短信的线程
 */

// TODO 这个类是干什么的
public class Sender implements Runnable {

    private Map<String, String> map;


    public Sender(Map<String, String> map) {
        this.map = map;
    }

    public void run() {
        String[] mails =  map.get("mails").split(",");
        String[] tels = map.get("tels").split(",");
        if (mails.length > 0) {
            sendMail(mails);
        }

        if (tels.length > 0) {
            sendMessage(map.get("tels"));
        }
    }


    /**
     * 发送邮件
     */
    public void sendMail(String[] mails) {
        for (String mail : mails) {
            SendMail.startSendTextMail(mail, "账户预警", "超出了预算!");
        }
    }


    /**
     * 发送手机短信
     */
    public void sendMessage(String tels) {
        SendMessage.startSendMes(tels, new String[]{});
    }

}
