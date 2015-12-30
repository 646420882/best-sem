package com.perfect.utils.email;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created on 2015-12-18.
 *
 * @author dolphineor
 */
public class EmailUtils {

    private static final String CHARSET_UTF_8 = "UTF-8";

    private static String hostName;     // 邮件服务器
    private static String from;         // 邮件发送者
    private static String userName;     // 邮箱地址
    private static String password;     // 密码

    static {
        Properties props = new Properties();

        try {
            props.load(new InputStreamReader(EmailUtils.class.getClassLoader().getResourceAsStream("email.properties"), CHARSET_UTF_8));

            hostName = props.getProperty("email.hostName");
            from = props.getProperty("email.from");
            userName = props.getProperty("email.userName");
            password = props.getProperty("email.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>发送HTML邮件
     *
     * @param subject     邮件主题
     * @param msg         邮件内容
     * @param targetEmail 收件人地址
     */
    public static void sendHtmlEmail(String subject, String msg, String targetEmail) {
        HtmlEmail email = new HtmlEmail();

        email.setHostName(hostName);
        email.setAuthentication(userName, password);
        email.setCharset(CHARSET_UTF_8);
        email.setStartTLSEnabled(true);
        try {
            email.setFrom(userName, from);
            email.setSubject(subject);
            email.setHtmlMsg(msg);
            email.addTo(targetEmail);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>发送带附件的邮件
     *
     * @param subject     邮件主题
     * @param msg         邮件内容
     * @param targetEmail 收件人地址
     * @param path        多个附件的路径
     */
    public static void sendMultiPartEmail(String subject, String msg, String targetEmail, String... path) {
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName(hostName);
        email.setAuthentication(userName, password);
        email.setCharset(CHARSET_UTF_8);
        email.setStartTLSEnabled(true);
        try {
            email.setFrom(from);
            email.setSubject(subject);
            email.setMsg(msg);
            email.addTo(targetEmail);
            for (String p : path) { // 循环传进来的附件地址, 可添加多个附件
                EmailAttachment ea = new EmailAttachment(); // 设置一个附件对象
                ea.setPath(p);      // 传入当前附件的路径
                email.attach(ea);   // 添加附件到email中
            }
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
