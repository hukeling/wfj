package util.sdkSMS;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import util.action.BaseAction;

import com.cloopen.rest.sdk.CCPRestSDK;
/**
 * 云通讯 短息发送类
 * 此功能需要传递短信模板ID参数 在系统的配置文件中进行配置
 * @author mqr
 */
public class SDKSendTemplateSMS {
	private static String serverAddress; //服务器地址
	private static String serverPort; //服务器端口
	private static String primaryAccountNumberName;//主账号名称 
	private static String primaryAccountNumberToken; //主账号令牌
	private static String appId; //应用ID
	
	static{
		String filePath = "systemConfig.properties";
		Properties ps = new Properties();
		try {
			InputStream	in = BaseAction.class.getClassLoader().getResourceAsStream(filePath);
			ps.load(in);
			serverAddress=String.valueOf(ps.get("yunSDK_serverAddress"));
			serverPort=String.valueOf(ps.get("yunSDK_serverPort"));
			primaryAccountNumberName=String.valueOf(ps.get("yunSDK_primaryAccountNumberName"));
			primaryAccountNumberToken=String.valueOf(ps.get("yunSDK_primaryAccountNumberToken"));
			appId=String.valueOf(ps.get("yunSDK_appId"));
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 公共的初始化信息方法
	 */
	public static CCPRestSDK initSMSInfo(){
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(serverAddress, serverPort);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(primaryAccountNumberName, primaryAccountNumberToken);// 初始化主帐号名称和主帐号令牌
		restAPI.setAppId(appId);// 初始化应用ID
		return restAPI;
	}
	
	/**
	 * 发送短信验证码
	 * @param phone 发送的号码
	 * @param contId 模板ID
	 * @param number 发送随机码
	 */
	@SuppressWarnings("unchecked")
	public static void sentSMS(String phone,String contId,String conter){
		HashMap<String, Object> result = null;
		//调用公共的初始化方法
		CCPRestSDK restAPI=initSMSInfo();
		result = restAPI.sendTemplateSMS(phone,contId ,new String[]{conter});
		//System.out.println("SDKTestGetSubAccounts result=" + result);
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				//System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
	}
	
	
	/**
	 * 内部main方法测试
	 */
	public static void main(String[] args) {
		sentSMS("13293067820","3150","123456");
	}

}
