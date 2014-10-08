import com.perfect.transmitter.sendMail.MailSenderInfo;
import com.perfect.transmitter.sendMail.SimpleMailSender;
import com.perfect.transmitter.sendMes.SendMessage;
import org.junit.Test;

public class SendTest {

    @Test
    public void mail(){
        //这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setToAddress("1582980368@qq.com");
        mailInfo.setSubject("我的测试邮件");
        mailInfo.setContent("邮件可以了!");

        //这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendTextMail(mailInfo);//发送文体格式
        sms.sendHtmlMail(mailInfo);//发送html格式
    }

    @Test
    public void message(){
        SendMessage mes = new SendMessage();
        mes.SendMes("15923844052",new String[]{});
    }
}
