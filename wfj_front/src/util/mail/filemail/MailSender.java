package util.mail.filemail;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
/**
 * 利用java.mail的邮件发送的实体bean，真正的实现邮件的发送
 * @author 张攀攀
 */
public class MailSender {
	/**
	 * 有附件
	 * 
	 * @param stmpservice:简单电子邮件协议
	 * @param name：发送者邮箱的用户名
	 * @param password：发送者邮箱的密码
	 * @param subject：要发送邮件的标题
	 * @param from：发送者邮件的地址（与name相同）
	 * @param String[] to：目的邮箱的集合（要发送到邮箱地址集合）
	 * @param text：要发送邮件的内容
	 * @param String[] filenames：邮件附加的集合
	 * @param mimeType：发送邮件的格式
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static void sendmailfile(String stmpservice,String name,String password,String subject, String from, String[] to,
			String text, String[] filenames, String mimeType) throws Exception {
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
//			System.out.println("发送到:" + to[i]);
			sendTo[i] = new InternetAddress(to[i]);
		}
		mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO,sendTo);
		mimeMsg.setSubject(subject, "gb2312");
		MimeBodyPart messageBodyPart1 = new MimeBodyPart();
		// messageBodyPart.setText(UnicodeToChinese(text));
		messageBodyPart1.setContent(text, mimeType);
		Multipart multipart = new MimeMultipart();// 附件传输格式
		multipart.addBodyPart(messageBodyPart1);
		for (int i = 0; i < filenames.length; i++) {
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();
			// 选择出每一个附件名
			String filename = filenames[i].split(",")[0];
//			System.out.println("附件名：" + filename);
			String displayname = filenames[i].split(",")[0];
			// 得到数据源
			FileDataSource fds = new FileDataSource(filename);
			// 得到附件本身并至入BodyPart
			messageBodyPart2.setDataHandler(new DataHandler(fds));
			// 得到文件名同样至入BodyPart
			// messageBodyPart2.setFileName(displayname);
			// messageBodyPart2.setFileName(fds.getName());
			messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
			multipart.addBodyPart(messageBodyPart2);
		}
		mimeMsg.setContent(multipart);
		// 设置信件头的发送日期
		mimeMsg.setSentDate(new Date());
		mimeMsg.saveChanges();
		// 发送邮件
		transport.send(mimeMsg);
		transport.close();
	}
	/**
	 * 没有附件
	 * 
	 * @param stmpservice:简单电子邮件协议
	 * @param name：发送者邮箱的用户名
	 * @param password：发送者邮箱的密码
	 * @param subject：要发送邮件的标题
	 * @param from：发送者邮件的地址（与name相同）
	 * @param String[] to：目的邮箱的集合（要发送到邮箱地址集合）
	 * @param text：要发送邮件的内容
	 * @param mimeType：发送邮件的格式
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static void sendmail(String stmpservice,String name,String password,String subject, String from, String[] to,
			String text, String mimeType) throws Exception {
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
//			System.out.println("发送到:" + to[i]);
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
