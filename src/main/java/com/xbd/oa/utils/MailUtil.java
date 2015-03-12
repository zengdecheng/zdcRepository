package com.xbd.oa.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class MailUtil {
	private String username;

	private String password;

	private Session session;

	private Transport transport;

	private MimeMessage mimeMessage;

	private Properties properties = new Properties();

	private Multipart multipart = new MimeMultipart();;

	public void setHost(String protocol, String host) {
		// 设置邮件服务起地址
		properties.setProperty(protocol, host);
	}

	public void setAuth(String protocol, String auth) {
		// 需要验证
		properties.setProperty(protocol, auth);
	}

	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	public void createSession() {
		// Authenticator authenticator = new MyAuthenticator();
		session = Session.getDefaultInstance(properties, null);
	}

	public void createMimeMessage() {
		mimeMessage = new MimeMessage(session);
	}

	public void createTransport(String protocol) {
		// "smtp"
		try {
			transport = session.getTransport(protocol);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}

	public void setSubject(String subject) {
		try {
			mimeMessage.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setBody(String mailbody) {
		BodyPart bodyPart = new MimeBodyPart();
		try {
			bodyPart.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312>" + mailbody, "text/html;charset=GB2312");
			multipart.addBodyPart(bodyPart);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void addFileAffix(String file) {
		BodyPart bodyPart = new MimeBodyPart();
		FileDataSource fileDataSource = new FileDataSource(file);
		try {
			bodyPart.setDataHandler(new DataHandler(fileDataSource));
			bodyPart.setFileName(fileDataSource.getName());
			multipart.addBodyPart(bodyPart);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setFrom(String from, String name) {
		try {
			try {
				mimeMessage.setFrom(new InternetAddress(from, name));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setTo(String to) {
		try {
			// 指定收件人
			mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setCc(String cc) {
		try {
			// 指定抄送
			mimeMessage.addRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setBc(String bc) {
		try {
			// 指定密送
			mimeMessage.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(bc));
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendout() {
		try {
			mimeMessage.setContent(multipart);
			mimeMessage.saveChanges();
			transport.connect(username, password);
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			transport.close();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	// private class MyAuthenticator extends Authenticator {
	// public PasswordAuthentication getPasswordAuthentication() {
	// return new PasswordAuthentication(username, password);
	// }
	// }

	private static void sendBySmtp(String to, String cc, String bc, String name, String subject, String body) {
		MailUtil mail = new MailUtil();
		mail.setHost("mail.smtp.host", "smtp.163.com");
		mail.setAuth("mail.smtp.auth", "true");
		mail.setNamePass("15810868082@163.com", "3571581");
		mail.createSession();
		mail.createMimeMessage();
		mail.createTransport("smtp");
		mail.setFrom("15810868082@163.com", name);
		mail.setSubject(subject);
		mail.setBody(body);
		mail.setTo(to);
		mail.setBc(bc);
		mail.setCc(cc);
		// 不带附件，注释掉
//		mail.addFileAffix(filePath);
		mail.sendout();
	}
	public static void sendBySmtp(String to, String cc, String bc, String name, String subject, String body,String mail_sender,String mail_host,String mail_password) {
		MailUtil mail = new MailUtil();
		mail.setHost("mail.smtp.host", mail_host);
		mail.setAuth("mail.smtp.auth", "true");
		mail.setNamePass(mail_sender, mail_password);
		mail.createSession();
		mail.createMimeMessage();
		mail.createTransport("smtp");
		mail.setFrom(mail_sender, name);
		mail.setSubject(subject);
		mail.setBody(body);
		mail.setTo(to);
		mail.setBc(bc);
		mail.setCc(cc);
		// 不带附件，注释掉
//		 mail.addFileAffix("E:/tmp/1.txt");
		mail.sendout();
	}

	public static void main(String args[]) {
		String title = "iframe22222";
		String content = "这是份测试邮件";
//		sendBySmtp("380189291@qq.com", "", "", "aa", title, content,"");

	}
}
