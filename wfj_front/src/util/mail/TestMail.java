package util.mail;
/**
 * TestMail：测试邮件的发送
 * @author 张攀攀
 *
 */
public class TestMail {
	@SuppressWarnings("static-access")
	public static void main(String[] args){   
    //这个类主要是设置邮件   
     MailSenderInfo mailInfo = new MailSenderInfo();    
     mailInfo.setMailServerHost("smtp.163.com");//定义邮箱为那个邮箱规则 
     mailInfo.setMailServerPort("25");    
     mailInfo.setValidate(true);    
     mailInfo.setUserName("13520186024@163.com");//商家邮箱地址  
     mailInfo.setPassword("7ruiWE##Q!");//商家邮箱密码    
     mailInfo.setFromAddress("13520186024@163.com");//商家邮箱地址     
     mailInfo.setToAddress("200994870@qq.com");//用户邮箱  
     mailInfo.setSubject("华宇盈通邮箱验证问题");//邮件标题
     mailInfo.setContent("<b style='color: green;font-size: 30px;text-align: center;'>华宇盈通邮箱验证问题链接路径</b><br>" +
     		"<b style='color: red;'>请点击链接路径进行验证: </b><font><a href='http://www.hyytbj.com'>http://www.hyytbj.com</a></font>");    
    //这个类主要来发送邮件   
     SimpleMailSender sms = new SimpleMailSender(); 
     sms.sendHtmlMail(mailInfo);//发送html格式   
	// sms.sendTextMail(mailInfo);//发送文体格式    
   }  
}
