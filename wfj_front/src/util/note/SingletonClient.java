package util.note;
import util.action.BaseAction;
import cn.emay.sdk.client.api.Client;
public class SingletonClient extends BaseAction{
	private static final long serialVersionUID = -8474494773818412969L;
	private static Client client=null;
	//该类继承baseAction类 从fileUrlConfig获取相关信息
	//获取序列号
	private static String softwareSerialNo = (String) fileUrlConfig.get("YimeiSDK_softwareSerialNo");
	//获取秘钥
	private static String key = (String) fileUrlConfig.get("YimeiSDK_key");
	//获取密码
	@SuppressWarnings("unused")
	private static String password = (String) fileUrlConfig.get("YimeiSDK_password");
	private SingletonClient(){
	}
	public synchronized static Client getClient(String softwareSerialNo,String key){
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public synchronized static Client getClient(){
	//	ResourceBundle bundle=PropertyResourceBundle.getBundle("yimeiconfig");//读取亿美短信的配置文件获取参数信息
//		System.out.println(softwareSerialNo);
		if(client==null){
			try {
				client=new Client(softwareSerialNo,key);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return client;
	}
	public static String getPassword(){
		return password;
	}
	public static void main(String str[]){
		SingletonClient.getClient();
	}
}
