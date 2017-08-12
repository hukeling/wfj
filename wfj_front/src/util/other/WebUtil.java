package util.other;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.customer.pojo.Customer;
import shop.store.pojo.ShopInfo;
import basic.pojo.Users;
/**
 * 操作web相关工具类
 * @author CYS
 *
 */
public class WebUtil {
	/**
	 * 获取后台登录员工
	 */
	public static Users getUsers(HttpServletRequest request){
		return (Users) request.getSession().getAttribute("users");
	}
	/**
	 * 获取前台登录用户
	 */
	public static Customer getCustomer(HttpServletRequest request){
		return (Customer) request.getSession().getAttribute("customer");
	}
	/**
	 * 获取前台登录店铺
	 */
	public static ShopInfo getShopInfo(HttpServletRequest request){
		return (ShopInfo) request.getSession().getAttribute("shopInfo");
	}
	/**
	 * 获取购物车
	 * @param request
	 * @return
	public static BuyCart getBuyCart(HttpServletRequest request){
		return (BuyCart)request.getSession().getAttribute("buyCart");
	}
	 */	
	/**
	 * 删除购物车
	public static void deleteBuyCart(HttpServletRequest request){
		request.getSession().removeAttribute("buyCart");
	}
	*/
    /***
     * 获取URI的路径,如路径为http://www.xxx.com/action/post.htm?method=add, 得到的值为"/action/post.htm"
     * @param request
     * @return
     */
    public static String getRequestURI(HttpServletRequest request){     
        return request.getRequestURI();
    }
    /**
     * 获取完整请求路径(含内容路径及请求参数)
     * @param request
     * @return
     */
    public static String getRequestURIWithParam(HttpServletRequest request){     
        return getRequestURI(request) + (request.getQueryString() == null ? "" : "?"+ request.getQueryString());
    }
    /**
     * 添加cookie
     * @param response
     * @param name cookie的名称
     * @param value cookie的值
     * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {        
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge>0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    /**
     * 获取cookie的值
     * @param request
     * @param name cookie的名称
     * @return
     */
    public static String getCookieByName(HttpServletRequest request, String name) {
    	Map<String, Cookie> cookieMap = WebUtil.readCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie.getValue();
        }else{
            return null;
        }
    }
    protected static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (int i = 0; i < cookies.length; i++) {
                cookieMap.put(cookies[i].getName(), cookies[i]);
            }
        }
        return cookieMap;
    }
	/**
	 * 获取当前项目URL全路径
	 * @param request
	 * @return URL全路径，例如：http://www.yglpin.com/gsjshopback/
	 */
	public static String getDomainUrl(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		return basePath;
	}
	/**
	 * 获取访问者IP地址
	 * @param request
	 * @return String类型的IP地址
	 */
	public static String getVisitorIP(HttpServletRequest request){
		String spbill_create_ip = request.getHeader("x-forwarded-for");
		if(spbill_create_ip == null || spbill_create_ip.length() == 0 || "unknown".equalsIgnoreCase(spbill_create_ip)) {
			spbill_create_ip = request.getHeader("Proxy-Client-IP");
		}
		if(spbill_create_ip == null || spbill_create_ip.length() == 0 || "unknown".equalsIgnoreCase(spbill_create_ip)) {
			spbill_create_ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(spbill_create_ip == null || spbill_create_ip.length() == 0 || "unknown".equalsIgnoreCase(spbill_create_ip)) {
			spbill_create_ip = request.getRemoteAddr();
		}
		return spbill_create_ip;
	}
}
