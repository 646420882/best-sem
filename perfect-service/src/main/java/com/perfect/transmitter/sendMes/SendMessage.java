package com.perfect.transmitter.sendMes;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by john on 2014/9/29.
 * 发送模板短信
 */
public class SendMessage {
    //短信服务器地址("sandboxapp.cloopen.com"是开发调试环境下使用的，若正式上线则修改为"app.cloopen.com")
    private static final String SERVER_ADDRESS = "sandboxapp.cloopen.com";

    //服务器端口
    private static final String SERVER_PORT = "8883";

    //主账号
    private static final String ACOUNT_SID = "8a48b55148bbb4140148c01e557d0270";

    //主账号令牌
    private static final String AUTH_TOKEN = "203c3d962fef45079a1011bbd53bda4f";

    //应用ID(系统正式上线需要使用自己创建的应用的App ID )
    private static final String APP_ID = "8a48b55148bbb4140148c01e78410272";

    //短信模板ID，1是系统默认的模板id,若要换成其他模板，需登录云通讯官网创建短信模板,再在程序中将TEMPLETEID修改成你刚创建的模板id
    private static final String TEMPLETEID = "1";


    /**
     * 发送短信
     *
     * @param phoneNums 多个以逗号分隔
     * @param data
     */
    public static void startSendMes(String phoneNums, String[] data) {
        HashMap<String, Object> result = null;

        //初始化SDK
        CCPRestSmsSDK restAPI = new CCPRestSmsSDK();

        //初始化短信服务器地址和端口
        restAPI.init(SERVER_ADDRESS, SERVER_PORT);

        //设置主账号和主账号令牌
        restAPI.setAccount(ACOUNT_SID, AUTH_TOKEN);

        //设置应用ID
        restAPI.setAppId(APP_ID);


        //*调用发送模板短信的接口发送短信
        //*参数顺序说明：
        //*第一个参数:是要发送的手机号码，可以用逗号分隔，一次最多支持100个手机号
        //*第二个参数:是模板ID，在平台上创建的短信模板的ID值；测试的时候可以使用系统的默认模板，id为1
        //*系统默认模板的内容为“【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入”
        //*第三个参数是要替换的内容数组。																														       *
        result = restAPI.sendTemplateSMS(phoneNums, TEMPLETEID,/*data*/new String[]{"6532", "5"});
    }


}


//若result的状态码是000000，则是发送成功,否则是发送失败
           /* if("000000".equals(result.get("statusCode"))){
                //正常返回输出data包体信息（map）
                HashMap<String,Object> returnData = (HashMap<String, Object>) result.get("data");
                Set<String> keySet = returnData.keySet();
                for(String key:keySet){
                    Object object = returnData.get(key);
                    System.out.println(key +" = "+object);
                }
            }else{
                //异常返回输出错误码和错误信息
                System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
            }*/