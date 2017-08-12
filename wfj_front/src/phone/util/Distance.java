package phone.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.compass.core.json.JsonObject;

//根据两点的经纬度计算两点之间的距离
public class Distance {

	/**
	 *  计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double getDistance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
	
	
	/**
	 * http://restapi.amap.com/v3/geocode/regeo?location=116.39548,39.932906&key=bc1d3f3de97b285dc5334c98fb9ef007&extensions=base
	 * 根据经纬度获取城市信息
	 * @throws Exception 
	 */
	public static JSONObject getCityByXy(double longtitude,double latitude) throws Exception{
		String url="http://restapi.amap.com/v3/geocode/regeo?extensions=base";
		String key=PhoneStaticConstants.key;
		String location=String.valueOf(longtitude)+","+String.valueOf(latitude);
		url+="&location="+location;
		url+="&key="+key;
		URL u=new URL(url);
		InputStream in=u.openStream();
		String ret=intoString(in);
		JSONObject json = JSONObject.fromObject(ret);
		return json;
	}

	public static String intoString(InputStream in) throws IOException{ 
        StringBuffer out=new StringBuffer(); 
        byte[] b=new byte[4096]; 
        for(int n;(n=in.read(b))!=-1;){ 
            out.append(new String(b,0,n)); 
        } 
        return out.toString(); 
	}

	public static void main(String []args) throws Exception{
		JSONObject json=getCityByXy(116.5209,39.912093);//beijing
//		JSONObject json=getCityByXy(108.990209,34.253838);//xian
		System.out.println(json);
		JSONObject regeocode=json.getJSONObject("regeocode");
		JSONObject addressComponent=regeocode.getJSONObject("addressComponent");
		System.out.println(addressComponent);
		Object city=addressComponent.get("city");
		Object province=addressComponent.get("province");
		System.out.println(province);
		System.out.println(addressComponent.get("city"));
		
	}

}