package util.aftership;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * aftership api
 * 
 * @author 李小龙
 *
 */
public class TrackingUtil {
	private static TrackingUtil trackingUtil = new TrackingUtil();
	private Properties config;
	private Log log = LogFactory.getLog(this.getClass());
	private TrackingUtil(){
		try {
			loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void loadConfig() throws IOException{
		config = new Properties();
		config.load(this.getClass().getResourceAsStream("aftership_config.properties"));
	}
	/**
	 * 获取所有快递商
	 * @return https://github.com/AfterShip/api/wiki/Couriers
	 * @throws IOException 
	 */
	public String getCouriers() throws IOException{
		return postAPI(config.getProperty(ConfigKey.GET_COURIERS), null);
	}
	/**
	 * 创建一个订单跟踪
	 * @param tracking_number 快递号
	 * @param courier  快递商
	 * @return https://github.com/AfterShip/api/wiki/Create-Single-Tracking-Shipment
	 * @throws IOException 
	 */
	public String createSingleTracking(String tracking_number, String courier) throws IOException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("tracking_number", tracking_number);
		params.put("courier", courier);
		return postAPI(config.getProperty(ConfigKey.CREATE_SINGLE_TRACKING), params);
	}
	/**
	 * 查看一个订单跟踪
	 * @param tracking_number 快递号
	 * @param courier  快递商
	 * @return https://github.com/AfterShip/api/wiki/Get-Single-Tracking-Shipment-Result
	 * @throws IOException 
	 */
	public String getSingleTracking(String tracking_number, String courier) throws IOException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("tracking_number", tracking_number);
		params.put("courier", courier);
		return postAPI(config.getProperty(ConfigKey.GET_SINGLE_TRACKING), params);
	}
	/**
	 * 请求api
	 * @param apiUrl
	 * @param params
	 * @return
	 * @throws IOException
	 */
	private String postAPI(String apiUrl ,Map<String,String> params) throws IOException{
		String apiKey = config.getProperty(ConfigKey.API_KEY);
		String apiPoint = config.getProperty(ConfigKey.API_POINT);
		String requestUrl  = new StringBuffer(apiPoint).append(apiUrl).append("?api_key=").append(apiKey).toString() ;
		log.info("-- api_key -- :" + apiKey);
		log.info("-- request url -- :" + requestUrl);
		StringBuffer data = new StringBuffer();
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				data.append("&").append(entry.getKey()).append("=").append(entry.getValue());
			}
		}
		log.info("-- data -- :" + data.toString());
		URL url = new URL(requestUrl + data.toString());
		HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.disconnect();
		String encoding = conn.getContentEncoding();  
		String response = IOUtils.toString(conn.getInputStream(),encoding);
		return response;
	}
	public static TrackingUtil getInstance() {
		return trackingUtil;
	}
}
