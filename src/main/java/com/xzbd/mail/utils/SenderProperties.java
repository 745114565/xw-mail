package com.xzbd.mail.utils;

import javax.validation.constraints.NotNull;
import java.util.Properties;

public class SenderProperties {
    @NotNull
    private String senderAddr;
    @NotNull
    private String senderPwd;

    private String senderName;

    // 使用的协议（JavaMail规范要求）
    private String mailTransportProtocol;
    // 发件人的邮箱的 SMTP
    private String mailSmtpHost;
    // 是否开启认证
    private String mailSmtpAuth = "true";
    // 连接超时时间
    private String mailSmtpConnectionTimeout = "800000";
    // 超时时间
    private String mailSmtpTimeout = "800000";


    public Properties getProperties(){
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", this.mailTransportProtocol);
        props.setProperty("mail.smtp.host", this.mailSmtpHost);
        props.setProperty("mail.smtp.auth", this.mailSmtpAuth); // 需要请求认证
        props.setProperty("mail.smtp.connectiontimeout", this.mailSmtpConnectionTimeout);
        props.setProperty("mail.smtp.timeout", this.mailSmtpTimeout);
        return props;
    }

    public SenderProperties(@NotNull String senderAddr, @NotNull String senderPwd, String senderName) {
        this.senderAddr = senderAddr;
        this.senderPwd = senderPwd;
        this.senderName = senderName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAddr() {
        return senderAddr;
    }

    public void setSenderAddr(String senderAddr) {
        this.senderAddr = senderAddr;
    }

    public String getSenderPwd() {
        return senderPwd;
    }

    public void setSenderPwd(String senderPwd) {
        this.senderPwd = senderPwd;
    }

    public String getMailTransportProtocol() {
        return mailTransportProtocol;
    }

    public void setMailTransportProtocol(String mailTransportProtocol) {
        this.mailTransportProtocol = mailTransportProtocol;
    }

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public void setMailSmtpHost(String mailSmtpHost) {
        this.mailSmtpHost = mailSmtpHost;
    }

    public String getMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public void setMailSmtpAuth(String mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }

    public String getMailSmtpConnectionTimeout() {
        return mailSmtpConnectionTimeout;
    }

    public void setMailSmtpConnectionTimeout(String mailSmtpConnectionTimeout) {
        this.mailSmtpConnectionTimeout = mailSmtpConnectionTimeout;
    }

    public String getMailSmtpTimeout() {
        return mailSmtpTimeout;
    }

    public void setMailSmtpTimeout(String mailSmtpTimeout) {
        this.mailSmtpTimeout = mailSmtpTimeout;
    }
}
