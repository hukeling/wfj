package util.mail;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**    
 * MyAuthenticator：邮箱身份验证的类
 * @author 张攀攀
 */ 
public class MyAuthenticator extends Authenticator{   
    String userName=null;   
    String password=null;   
    public MyAuthenticator(){   
    }   
    /**    
     * MyAuthenticator：邮箱验证构造方法
     * @param username:邮箱用户名
     * @param password:邮箱密码
     */ 
    public MyAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}   
