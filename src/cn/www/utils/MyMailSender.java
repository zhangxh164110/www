package cn.www.utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MyMailSender {
	private static Properties props;
	static {
		props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "mail.chainco.cn");
	}

	public static void sendMail(final String fromUsername, final String fromPassword, String toUsername, String content)
			throws Exception {
		sendMail(fromUsername, fromPassword, toUsername, "", content);
	}

	public static void sendMail(final String fromUsername, final String fromPassword, String toUsername,
			String subject, String content) throws Exception {
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromUsername, fromPassword);
			}
		});
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(fromUsername, "物流邦"));
		msg.setDescription("desc");
		msg.setText("text");
		msg.setSubject(subject);
		msg.setRecipients(RecipientType.TO, InternetAddress.parse(toUsername));
		msg.setContent(content, "text/html;charset=utf-8");
		Transport.send(msg);
	}

	public static void sendMail(final String fromUsername, final String fromPassword, String toUsername,
			String subject, String subjectTemp, String content) throws Exception {
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromUsername, fromPassword);
			}
		});
		session.setDebug(true);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(fromUsername, subjectTemp));
		msg.setDescription("desc");
		msg.setText("text");
		msg.setSubject(subject);
		msg.setRecipients(RecipientType.TO, InternetAddress.parse(toUsername));
		msg.setContent(content, "text/html;charset=utf-8");
		Transport.send(msg);
	}
}
