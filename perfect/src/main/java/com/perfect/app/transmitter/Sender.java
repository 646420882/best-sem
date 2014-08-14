package com.perfect.app.transmitter;
import java.util.Map;

public class Sender implements Runnable{
    private Map<String,String[]> map;


    public Sender(Map<String,String[]> map) {
        this.map = map;
    }

    public void run() {
        if(map.get("mail")!=null){
            System.out.println("发送邮件到"+map.get("mail"));
        }else if(map.get("tel")!=null){
            System.out.println("发送短信到"+map.get("tel"));
        }
    }

}


/*

//这个类主要是设置邮件
MailSenderInfo mailInfo = new MailSenderInfo();
mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("15923844052@163.com");
        mailInfo.setPassword("xj691864758xk.");//您的邮箱密码
        mailInfo.setFromAddress("15923844052@163.com");
        mailInfo.setToAddress("691864758@qq.com");
        mailInfo.setSubject("我的测试邮件");
        mailInfo.setContent("邮件可以了!");
        //这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendTextMail(mailInfo);//发送文体格式
        sms.sendHtmlMail(mailInfo);//发送html格式*/
