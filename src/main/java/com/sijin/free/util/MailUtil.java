package com.sijin.free.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * Created by sijinzhang on 16/5/6.
 */
public class MailUtil {

    public static void main(String[] args) throws MessagingException, GeneralSecurityException {


    }

    public static boolean sendMessage(String subject,String contnet) {
        try {
            Properties props = new Properties();// 开启debug调试
            props.setProperty("mail.debug", "false");// 发送服务器需要身份验证
            props.setProperty("mail.smtp.auth", "true");  // 设置邮件服务器主机名
            props.setProperty("mail.host", "smtp.163.com");  // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            Session session = Session.getInstance(props);

            //邮件内容部分
            Message msg = new MimeMessage(session);
            msg.setSubject(subject);
            msg.setText(contnet);
            msg.setFrom(new InternetAddress("zhanghan_198@163.com"));//邮件发送者

            //发送邮件
            Transport transport = session.getTransport();
            transport.connect("smtp.163.com", "zhanghan_198@163.com", "xxx");
            transport.sendMessage(msg, new Address[]{new InternetAddress("272885400@qq.com")});
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
