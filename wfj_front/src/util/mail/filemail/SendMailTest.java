package util.mail.filemail;
/**
 * 利用java.mail的邮件发送程序(可添加附件、可同时发送多个邮箱地址)
 * @author 张攀攀
 */
public class SendMailTest {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		//smtp: 设置发送邮件所用到的smtp(邮件协议)
		String smtp = "smtp.163.com"; 
		//name: 发送者邮箱地址（用户名）
		String name = "zhangpanpan0803@163.com";
		//password: 发送者邮箱密码
		String password = "13426066764";
		//title: 要发邮件的标题 
		String title = "邮件测试";
		//from: 从那里发送
		String from = "zhangpanpan0803@163.com";
		//sendTo: 发送到那里（可以发送多个）
		String sendTo[] = { "839094785@qq.com"};
		//content: 邮件的文本内容，可以包含html标记则显示为html页面
		String content = "mail test!!!!!!<br><a href=#>aaa</a><br>";
		try {
			//邮件发送
			MailSender mailsender = new MailSender();
			mailsender.sendmail(smtp,name,password,title, from, sendTo, content,"text/html;charset=gb2312");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
