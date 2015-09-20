package com.sprouts.spm_framework.alarmer;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sprouts.spm_framework.utils.ConfigUtils;
import com.sprouts.spm_framework.utils.Logger;


public class MailService {

    private MimeMessage mimeMsg; // MIME邮件对象
    private Session session; // 邮件会话对象
    private Properties props; // 系统属性
    private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
    private Logger logger = new Logger();
    private static MailService instance;

    /**
     * Constructor
     * 
     * @param smtp 邮件发送服务器
     */
    public MailService() {
        setSmtpHost(ConfigUtils.getString("Mail.smtp"));
        createMimeMessage();
    }

    public static MailService getInstance() {
        if (instance == null) {
            instance = new MailService();
        }
        return instance;
    }

    /**
     * 设置邮件发送服务器
     * 
     * @param hostName String
     */
    public void setSmtpHost(String hostName) {
        if (props == null) props = System.getProperties(); // 获得系统属性对象
        props.put("mail.smtp.host", hostName); // 设置SMTP主机
    }


    /**
     * 创建MIME邮件对象
     * 
     * @return
     */
    public boolean createMimeMessage() {
        try {
            session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
            mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
            mp = new MimeMultipart();
            logger.info("MimeMessage created successfully!");
            return true;
        } catch (Exception e) {
            logger.error("MimeMessage created failed\n", e);
            return false;
        }
    }

    /**
     * 设置SMTP是否需要验证
     * 
     * @param need
     */
    public void setNeedAuth(boolean need) {
        if (props == null) props = System.getProperties();
        if (need) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
    }

    /**
     * 设置邮件主题
     * 
     * @param mailSubject
     * @return
     */
    public boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject);
            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    /**
     * 设置邮件正文
     * 
     * @param mailBody String
     */
    public boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("" + mailBody, "text/html;charset=GBK");
            mp.addBodyPart(bp);

            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    /**
     * 设置发信人
     * 
     * @param from String
     */
    public boolean setFrom(String from) {
        // System.out.println("设置发信人！");
        try {
            mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置收信人
     * 
     * @param to String
     */
    public boolean setTo(String to) {
        if (to == null) return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 发送邮件
     */
    public boolean sendOut(String username, String password) {
        try {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();

            Session mailSession = Session.getInstance(props, null);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), username, password);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            transport.close();

            return true;
        } catch (Exception e) {
            logger.error("", e);
            return false;
        }
    }

    /**
     * 调用sendOut方法完成邮件发送
     * 
     * @param smtp
     * @param from
     * @param to
     * @param subject
     * @param content
     * @param username
     * @param password
     * @return boolean
     */
    public boolean send(String smtp, String from, String to, String subject, String content,
            String username, String password) {
        setNeedAuth(true); // 需要验证

        if (!setSubject(subject)) return false;
        if (!setBody(content)) return false;
        if (!setTo(to)) return false;
        if (!setFrom(from)) return false;
        if (!sendOut(username, password)) return false;
        return true;
    }

    public boolean send(ArrayList<String> mailList, String content) {
        boolean all_success = false;
        for (String mail : mailList) {
            all_success =
                    send(ConfigUtils.getString("Mail.smtp"),
                            ConfigUtils.getString("Mail.hostmail"), mail,
                            ConfigUtils.getString("Mail.title"), content,
                            ConfigUtils.getString("Mail.hostmail"),
                            ConfigUtils.getString("Mail.password"));
        }
        return all_success;
    }
}
