package cc.blisscorp.event.game.message.delivery;

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

import com.bliss.framework.common.Config;

public class DeliveryMessage {
	
	private static String FROM_ADDRESS = "taikhoan@xct.vn";
	private static String FROM_ADDRESS_PASS = "hotrotaikhoan";
	private static String TO_ADDRESS = "anhlnt@blisscorp.cc";
	
	public static void sendNotification(String subject, String msgBody) throws Exception {
		FROM_ADDRESS = Config.getParam("support", "from_address");
		FROM_ADDRESS_PASS = Config.getParam("support", "pass_address");
		TO_ADDRESS = Config.getParam("support", "to_address");
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(FROM_ADDRESS, FROM_ADDRESS_PASS);
			}
		});	
		// create a message
		MimeMessage message = new MimeMessage(session);
		message.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
		message.setFrom(new InternetAddress(FROM_ADDRESS));
		message.setRecipients(Message.RecipientType.TO, TO_ADDRESS);
		message.setSubject(subject, "UTF-8");
		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();
		// Now set the actual message
		messageBodyPart.setText(msgBody);
		// Create a multipar message
		Multipart multipart = new MimeMultipart();
		// Set text message part
		multipart.addBodyPart(messageBodyPart);
		// Send the complete message parts
		message.setContent(multipart);
		// Send message
		Transport.send(message);
	}

}
