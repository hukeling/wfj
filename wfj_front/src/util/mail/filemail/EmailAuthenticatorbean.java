package util.mail.filemail;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * EmailAuthenticatorbean:电子邮件身份认证类（发送者的省份验证）
 * @author 张攀攀
 *
 */
public class EmailAuthenticatorbean extends Authenticator{ 
	private String m_username = null; 
	private String m_userpass = null; 
	public void setUsername(String username){ 
		m_username = username; 
	} 
	public void setUserpass(String userpass){ 
		m_userpass = userpass; 
	} 
	public EmailAuthenticatorbean(String username, String userpass){ 
		super(); 
		setUsername(username); 
		setUserpass(userpass); 
	} 
	public PasswordAuthentication getPasswordAuthentication(){ 
		return new PasswordAuthentication(m_username,m_userpass); 
	} 
} 
