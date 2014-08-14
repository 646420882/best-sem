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
