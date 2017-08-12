package util.mail.filemail;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
/**
 * 利用java.mail的邮件发送的实体bean，真正的实现邮件的发送
 * @author 张攀攀
 */
public class MailSenderBean {
	/**
	 * 
	 * @param subject：要发送邮件的标题
	 * @param String[] to：目的邮箱的集合（要发送到邮箱地址集合）
	 * @param text：要发送邮件的内容
	 * @param String[] filenames：邮件附加的集合
	 */
	@SuppressWarnings("static-access")
	public static void sendmail(String subject, String[] to,String text, String[] filenames) throws Exception {
		//smtp: 设置发送邮件所用到的smtp(邮件协议)
		String stmpservice = "smtp.Bargainout.com"; 
		//name: 发送者邮箱地址（用户名）
		String name = "CustomerService@Bargainout.com";
		//password: 发送者邮箱密码
		String password = "";
		//from: 从那里发送
		String from = "CustomerService@Bargainout.com";
		//发送邮件的格式
		String mimeType = "text/html;charset=gb2312";
		// ResourceBundle mailProps = ResourceBundle.getBundle("mail");
		// 可以从配置文件读取相应的参数
		Properties props = new Properties();
		javax.mail.Session mailSession; // 邮件会话对象
		javax.mail.internet.MimeMessage mimeMsg; // MIME邮件对象
		props = java.lang.System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", stmpservice); // 设置SMTP主机
		props.put("mail.smtp.auth", "true"); // 是否到服务器用户名和密码验证
		// 到服务器验证发送的用户名和密码是否正确
		EmailAuthenticatorbean myEmailAuther = new EmailAuthenticatorbean(name, password);
		// 设置邮件会话
		mailSession = javax.mail.Session.getInstance(props,(Authenticator) myEmailAuther);
		// 设置传输协议
		javax.mail.Transport transport = mailSession.getTransport("smtp");
		// 设置from、to等信息
		mimeMsg = new javax.mail.internet.MimeMessage(mailSession);
		if (!StringUtils.isEmpty(from)) {
			InternetAddress sentFrom = new InternetAddress(from);
			mimeMsg.setFrom(sentFrom); // 设置发送人地址
		}
		InternetAddress[] sendTo = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			sendTo[i] = new InternetAddress(to[i]);
		}
		mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO,sendTo);
		mimeMsg.setSubject(subject, "gb2312");
		MimeBodyPart messageBodyPart1 = new MimeBodyPart();
		// messageBodyPart.setText(UnicodeToChinese(text));
		messageBodyPart1.setContent(text, mimeType);
		Multipart multipart = new MimeMultipart();// 附件传输格式
		multipart.addBodyPart(messageBodyPart1);
		mimeMsg.setContent(multipart);
		// 设置信件头的发送日期
		mimeMsg.setSentDate(new Date());
		mimeMsg.saveChanges();
		// 发送邮件
		transport.send(mimeMsg);
		transport.close();
	}
}
