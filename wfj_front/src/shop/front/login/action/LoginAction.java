package shop.front.login.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.Customer;
import shop.customer.pojo.ShopCustomerInfo;
import shop.customer.pojo.Sonaccount;
import shop.customer.service.ICustomerService;
import shop.customer.service.IShopCustomerInfoService;
import shop.customer.service.ISonaccountService;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.action.SecurityCode;
import util.action.SecurityCode.SecurityCodeLevel;
import util.mail.Mail;
import util.other.Escape;
import util.other.Utils;
/**
 * LoginCustomerAction - 用户登录action类
 */
@SuppressWarnings("serial")
public class LoginAction extends BaseAction {
	private Customer customer;//客户实体类
	private ICustomerService customerService;//客户Service
	private IShopCustomerInfoService shopCustomerInfoService;//会员附属表service
	/**店铺service**/
	private IShopInfoService shopInfoService;
	/**会员子账号service**/
	private ISonaccountService sonaccountService;
	private IShoppingCartService shoppingCartService;
	/**是否放入cookie标识**/
	private String falg; 
	/**返回页面信息**/
	private String message;
	/**登录用户状态标识（卖家：1已申请通过，2申请未通过,3未申请店铺,4 审核中）**/
	private String sellerflag;
	private String cmail;
	private String cpassword;
	private String directUrl;
	private String loginName;
	//跳转到登录页面
	public String gotoLoginPage(){
		//过滤重复登录
		if(StringUtils.isNotEmpty(directUrl)){
			String replaceAll = directUrl.replaceAll("_", "%");
			directUrl = Escape.unescape((replaceAll.trim()));
			int indexOf = directUrl.indexOf("directUrl");
			if(indexOf>0){
				directUrl=directUrl.substring(indexOf+10, directUrl.length());
			}
		}
		//根据type判断登陆对象  1:企业用户 2:供应商 3:普通用户
		String type = request.getParameter("type");
		if(StringUtils.isNotEmpty(type)){
			//传递登陆类型(采购商,供应商)
			request.setAttribute("type", type);
			if("1".equals(type)||"3".equals(type)){
				return SUCCESS;//跳转至采购商登陆
			}else{
				return "success2";//跳转至供应商登陆
			}
		}
		return "success3";//直接返回首页
	}
	 private static boolean match( String regex ,String str ){
	        Pattern pattern = Pattern.compile(regex);
	        Matcher  matcher = pattern.matcher( str );
	        return matcher.matches();
	    }
	//验证登录是否成功
	public void loginValidate() throws IOException{
		String passwords = Utils.EncodeMd5(Utils.EncodeMd5(cpassword));
		String isStr = "";
		//过滤登录时使用类似=admin ' or 1=1 ...
		String phone ="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";//手机号码正则验证
		//java正则验证邮箱
		String email ="^[0-9a-zA-Z]+@(([0-9a-zA-Z]+)[.])+[a-z]{2,4}$"; 
		//java正则验证只能有数字，英文字母和下划线組拼的字符串
		String reg ="[^a-zA-Z0-9_]";                     
		Pattern p_reg = Pattern.compile(reg); 
		Matcher m_reg = p_reg.matcher(loginName.trim());
		if(match(phone,loginName.trim())==false&&match(email,loginName.trim())==false){
			loginName =  m_reg.replaceAll("").trim();
		}
		//获取登陆类型(采购商,供应商)
		String type=request.getParameter("type");
		Customer customer=null;
		Sonaccount sonaccount=null;
		request.getSession().removeAttribute("sonaccount");//清除session中sonaccount对象(采收商子账号)
		String flag="";//子帐号标记 如果type为1时标记此变量
		if(Utils.objectIsNotEmpty(type)){
			if("1".equals(type)||"3".equals(type)){
				type="1,3";
				flag="true";
			}
			int total = customerService.getCountByHQL(" select count(*) from Customer o where o.loginName = '"+loginName.trim()+"' and o.password = '"+passwords+"' and o.type in ("+type+")  or o.phone='"+loginName.trim()+"'  and o.password = '"+passwords+"' and o.type in ("+type+")  or o.email='"+loginName.trim()+"'  and o.password = '"+passwords+"' and o.type in ("+type+")");
			if(total>0){//存在
				flag="false";
				customer = (Customer)customerService.getObjectByParams(" where o.loginName = '"+loginName.trim()+"'  and o.password = '"+passwords+"' and o.type in ("+type+")  or o.phone='"+loginName.trim()+"' and o.password = '"+passwords+"' and o.type in ("+type+")  or o.email='"+loginName.trim()+"'  and o.password = '"+passwords+"' and o.type in ("+type+")");
			}else{//不能存在该用户  查询子账号(查询子账号时不需要判断type[只有供应商才可以添加子账号信息])
				if("true".equals(flag)){//判断登陆入口
					sonaccount= (Sonaccount) sonaccountService.getObjectByParams(" where o.loginName = '"+loginName.trim()+"'  and o.password = '"+passwords+"' or o.phone='"+loginName.trim()+"' and o.password = '"+passwords+"' or o.email='"+loginName.trim()+"'  and o.password = '"+passwords+"'");
					if(sonaccount!=null){
						//子账号存在 根据customerId查询其企业账号
						customer = (Customer) customerService.getObjectByParams(" where o.customerId="+sonaccount.getCustomerId());
						//添加子账号信息至session中
						session.setAttribute("sonaccount", sonaccount);
					}
				}
			}
		}
		isStr=customerIsTrue(customer,sonaccount);
		JSONObject jo = new JSONObject();
		//对注册、重置密码地址进行过滤排除
		String buyerLogin_screeningConditions = (String) getFileUrlConfig().get("buyerLogin_screeningConditions");
		if(buyerLogin_screeningConditions.indexOf(";")>0){
			String[] split = buyerLogin_screeningConditions.split(";");
			for(String s:split){
				if(directUrl!=null&&directUrl.indexOf(s)>0){
					directUrl="";
				}
			}
		}
		if("".equals(directUrl)){
			jo.accumulate("gotoHomePage", true);
		}
		session.setAttribute("customer", customer);
		jo.accumulate("isStr", isStr);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 当登陆用户customer信息不为null的时候调用该方法
	 */
	public String customerIsTrue(Customer customer, Sonaccount sonaccount){
		String isStr="";
		if(customer != null){
			if(customer.getLockedState()!=null&&customer.getLockedState()==0){//判断用户是否被冻结
				isStr = "S";//S:成功 ,
				ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
				request.getSession().setAttribute("customer", customer);
				request.getSession().setAttribute("shopInfo", shopInfo);
				//session.setMaxInactiveInterval(-1);
				//重置session失效时间，防止验证码3分钟失效后影响用户登录的session信息使用
				request.getSession().setMaxInactiveInterval(Integer.parseInt(String.valueOf(fileUrlConfig.get("session_user"))));
				try {
					//cookie中登陆名称的组拼
					String cv="";
					if(shopInfo!=null){
						cv=shopInfo.getCompanyName();
					}else{
						isStr= "SQ";
					}
					String nickName="";
					if(sonaccount!=null){
						nickName+=sonaccount.getNickname();
						if(StringUtils.isNotEmpty(nickName.trim())){
							cv+=":"+nickName;
						}
					}
					if(StringUtils.isNotEmpty(cv)){
						cv=URLEncoder.encode(cv,"UTF-8");
					}else{
						cv=customer.getLoginName();
					}
					//在cookie中存储用户名称
					Cookie c;
					c = new Cookie("thshop_customerName",cv);
					c.setMaxAge(60*60*24*7);//cookie存在7天
					c.setPath("/"); 
					response.addCookie(c);
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				//将cookie中的数据更新到购物车中
				//用户没有登录时直接访问购物车
				Cookie[] cookies = request.getCookies();
				boolean flag=false;
				for(Cookie cookie:cookies){
					if(cookie.getName().contentEquals("customerCar")){
						String cookieValues;
						try {
							cookieValues = URLDecoder.decode(cookie.getValue(), "UTF-8");
							flag = shoppingCartService.saveOrUpdateCookieCar(cookieValues, customer);
							if(flag){
								cookie.setMaxAge(0);
								cookie.setPath("/");
								response.addCookie(cookie);
							}
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}//cookie解码
					}
				}
			}else{
				isStr = "DJ";
			}
		}else{
			isStr = "F";
		}
		return isStr;
	}
	//跳转到找回密码页面
	public String forGotPassword(){
		return SUCCESS;
	}
	//根据邮箱获取密码
	public void forGotValidate() throws IOException{
		String email = String.valueOf(request.getParameter("email"));
		customer = (Customer)customerService.getObjectByParams(" where o.email = '"+email+"'");
		String msg = "";//S:成功 , F:用户名或者密码错误 
		if(customer != null){
			String usersId = String.valueOf(customer.getCustomerId());
			Cookie cookie = new Cookie("forGotCookie",usersId);
			cookie.setMaxAge(60*60*24);//cookie有效时间为1天
			cookie.setPath("/");//设置整个项目都可以访问
			response.addCookie(cookie);
			msg = "S";
			//发送临时编码至邮箱
			String resPassword = SecurityCode.getSecurityCode(6,SecurityCodeLevel.Hard, true);
			Utils ut = new Utils();
			@SuppressWarnings("static-access")
			String encodeMd5 = ut.EncodeMd5(resPassword);
			@SuppressWarnings("static-access")
			String encodeMd52 = ut.EncodeMd5(encodeMd5);
			String passwordUrl = (String) fileUrlConfig.get("password_url");
			String con="<a href='"+passwordUrl+"/loginCustomer/reSetPassword.action?code="+encodeMd52+"'>点击链接进行密码重置</a>";
			Mail.sent(customer.getEmail(), "SHOPJSP密码重置验证", con , servletContext);
			//更新对应会员附属表中的“临时编码”字段
			ShopCustomerInfo sci = (ShopCustomerInfo) shopCustomerInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
			sci.setPasswordRandomCode(encodeMd52);
			shopCustomerInfoService.saveOrUpdateObject(sci);
		}else{
			msg = "F";
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("msg", msg);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	//跳转到重设密码页面
	public String reSetPassword(){
		String passwordRandomCode = request.getParameter("code");
		request.setAttribute("passwordRandomCode", passwordRandomCode);
		return SUCCESS;
	}
	//修改新密码
	public void reSetPasswordValidate() throws IOException{
		String password = request.getParameter("password");
		String passwordRandomCode = request.getParameter("passwordRandomCode");
		String passwords = Utils.EncodeMd5(Utils.EncodeMd5(password));
		ShopCustomerInfo sci = (ShopCustomerInfo) shopCustomerInfoService.getObjectByParams(" where o.passwordRandomCode='"+passwordRandomCode+"'");
		JSONObject jo = new JSONObject();
		if(sci!=null){
			customer = (Customer)customerService.getObjectByParams(" where o.customerId ="+sci.getCustomerId());
			if(customer!=null){
				customer.setPassword(passwords);
				customer = (Customer) customerService.saveOrUpdateObject(customer);
				if(customer.getCustomerId()!=null){
					sci.setPasswordRandomCode(null);
					shopCustomerInfoService.saveOrUpdateObject(sci);
					jo.accumulate("isSuccess", "true");
				}
			}
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//跳转到重设密码页面
	public String reSetPasswordOk(){
		return SUCCESS;
	}
	//跳转到重设密码页面
	public String personalPassword(){
		return SUCCESS;
	}
	//验证登录是否成功
	public void personalValidate() throws IOException{
		String password = request.getParameter("password");//密码
		String passwords = Utils.EncodeMd5(Utils.EncodeMd5(password));
		Cookie[] cookies = request.getCookies();
		String usersId = "";
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("forGotCookie".equals(cookie.getName())) {
					String value = cookie.getValue();
					String[] valueArr = value.split("_");
					usersId = valueArr[0];
				}
			}
		}
		Customer customers = (Customer)customerService.getObjectByParams(" where o.customerId = '"+usersId+"'");
		if(customers!=null){
			customers.setPassword(passwords);
			customers = (Customer) customerService.saveOrUpdateObject(customers);
			//如果用户不记录状态，查询cookie当中是否有保存用户的信息，如果有则设置cookie有效时间为-1；
			Cookie [] cs = request.getCookies();
			if(cs != null && cs.length>0){
				for(Cookie c : cs){
					if ("forGotCookie".equals(c.getName())) {
						c.setMaxAge(0);//设置cookie失效方式，0：立即失效；-1：关闭浏览器后失效
						c.setPath("/");//设置整个项目都可以访问
						response.addCookie(c);//设置完毕，必须添加到服务里面
						break;
					}
				}
			}
			if(customers.getCustomerId()!=null){
				JSONObject jo = new JSONObject();
				jo.accumulate("isSuccess", "true");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.println(jo.toString());
				out.flush();
				out.close();
			}
		}
	}
	/**检测session中是否有用户，以便后续操作**/
	public void checkLogin() throws IOException{
		Boolean bool = false;
		Customer cust=(Customer) session.getAttribute("customer");
		if(cust!=null){
			bool = true;
		}else{
			bool = false;
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("bool", bool);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**检测session中是否有用户，以便后续操作**/
	public void checkFirstLogin() throws IOException{
		Boolean bool = false;
		Customer cust=(Customer) session.getAttribute("customer");
		if(cust!=null){
			if("1".equals(String.valueOf(cust.getType()))){
				//甲方用户
				bool = true;
			}else{
				bool = false;
			}
		}else{
			bool = false;
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("bool", bool);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**检测卖家，以便后续操作**/
	public void checkSellerLogin() {
		try {
		String bool = "";
		Customer cust=(Customer) session.getAttribute("customer");
		if(cust!=null){
			ShopInfo si=(ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+cust.getCustomerId());
			if(si!=null){
				if(si.getIsPass()==3){
					bool = "1";//通过审核
				}else if(si.getIsPass()==2){
					bool = "2";//未通过审核
				}else{
					bool = "4";//审核中
				}
			}else{
				bool = "3";//未申请
			}
		}else{
			bool = "0";//未登录
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("bool", bool);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 跳转到申请店铺和已申请等待审核页面
	 * @return
	 */
	public String appleSeller(){
		return SUCCESS;
	}
	/**退出登录**/
	public String exitLogin(){
		if(request.getSession().getAttribute("customer")!=null){
			request.getSession().removeAttribute("customer");//清除session中customer对象
			request.getSession().removeAttribute("shopInfo");//清除session中shopInfo对象
			request.getSession().removeAttribute("sonaccount");//清除session中sonaccount对象(采收商子账号)
			request.getSession().removeAttribute("cartCountMap");//清除session中sonaccount对象(采收商子账号)
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("loginCookie".equals(cookie.getName())) {
					String value = cookie.getValue();
					String[] valueArr = value.split("_");
					request.setAttribute("loginEmail", valueArr[0]);
					request.setAttribute("password", valueArr[1]);
					request.setAttribute("loginName", valueArr[2]);
				}
			}
		}
		/*//清空Cookie中的用户名信息 
		try{
			Cookie c = new Cookie("thshop_customerName",null);
			c.setMaxAge(0);//cookie存在7天
			c.setPath("/"); 
			response.addCookie(c);
		}catch(Exception ex){
			//System.out.println("清空Cookies发生异常！");
		}*/
		return SUCCESS;
	}
	/**
	 * 发送验证码至手机
	 * @author mf
	 */
	public void sentMessage()throws IOException{
		JSONObject jo=new JSONObject();
		try {
			String phoneNumber = request.getParameter("phoneNumber");
			String type = request.getParameter("type");//账号登陆类型
			//发送验证码标记
			boolean bl=true;
			//先判断该手机号的用户是否存在  
			Integer count = customerService.getCount(" where o.phone='"+phoneNumber+"' and o.type="+type);
			//如果用户表中不能直接查处obj信息 则对子账号进行查询
			if(count<=0){
				if("1".equals(type)){//判断登陆入口
					Integer s= sonaccountService.getCount(" where o.phone='"+phoneNumber+"'");
					if(s<=0){
						bl=false;//更改标记为false
					}
				}else{
					bl=false;//更改标记为false
				}
			}
			if(bl){
				//生成验证码
				String securityCode = SecurityCode.getSecurityCode(6, SecurityCodeLevel.Simple, true);
				/****************************************************************/
				//确认系统是否允许使用短信发送功能
				String openSMS = String.valueOf(fileUrlConfig.get("yunSDK_openSMS"));
				if("true".equals(openSMS)){
					//发送短信
					//获取模板ID
					String template = String.valueOf(fileUrlConfig.get("yunSDK_template"));
					util.sdkSMS.SDKSendTemplateSMS.sentSMS(phoneNumber, template, securityCode);
				}else{
				}
				/****************************************************************/
				request.getSession().setAttribute("verificationCodeFront", securityCode);
				 //设置验证码的失效时间计时
		        request.getSession().setAttribute("verificationInvalidationTime", System.currentTimeMillis());
				jo.accumulate("isSuccess","true");
			}else{
				jo.accumulate("isSuccess","false");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 检测用户是否存在  通过phone
	 * @author mf
	 */
	@SuppressWarnings("unused")
	public void loginValidateByPhone()throws IOException{
		JSONObject jo=new JSONObject();
		String verificationCodeFront = (String) session.getAttribute("verificationCodeFront");
		if(Utils.stringIsNotEmpty(verificationCodeFront)){
				String phoneNumber = request.getParameter("phoneNumber");
				String trendsPassword = request.getParameter("trendsPassword");
				String type = request.getParameter("type");
				//定义customer对象
				Customer c=null;
				Sonaccount sonaccount=null;
				if(Utils.stringIsNotEmpty(trendsPassword)){
					//获取验证码的失效时间计时
					long verificationInvalidationTime = Long.parseLong(String.valueOf(request.getSession().getAttribute("verificationInvalidationTime")));
					//获取失效时间，单位毫秒
					long sessionVerificationInvalidationTime =  Long.parseLong( String.valueOf(fileUrlConfig.get("session_verificationInvalidationTime")) );
					if(!trendsPassword.equals(verificationCodeFront)||((System.currentTimeMillis() - verificationInvalidationTime) > sessionVerificationInvalidationTime)){
						jo.accumulate("isStr","F");
					}else{
						c = (Customer) customerService.getObjectByParams(" where o.phone='"+phoneNumber+"' and o.type="+type);
						if(c==null){//如果主表中不存在customer  查询子账号表
							sonaccount = (Sonaccount) sonaccountService.getObjectByParams(" where o.phone='"+phoneNumber+"'");
							if(sonaccount!=null){
								//添加子账号信息至session中
								session.setAttribute("sonaccount", sonaccount);
								//子账号存在 根据customerId查询其企业账号
								c = (Customer) customerService.getObjectByParams(" where o.customerId="+sonaccount.getCustomerId());
							}else{
								jo.accumulate("isStr","F");
							}
						}
						String isStr=customerIsTrue(c,sonaccount);
					}
				}else{
					jo.accumulate("isStr","F");
				}
		}else{
			jo.accumulate("isStr","F");
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public String getFalg() {
		return falg;
	}
	public void setFalg(String falg) {
		this.falg = falg;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public String getSellerflag() {
		return sellerflag;
	}
	public void setSellerflag(String sellerflag) {
		this.sellerflag = sellerflag;
	}
	public String getCmail() {
		return cmail;
	}
	public void setCmail(String cmail) {
		this.cmail = cmail;
	}
	public String getCpassword() {
		return cpassword;
	}
	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}
	public String getDirectUrl() {
		return directUrl;
	}
	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public void setShoppingCartService(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}
	public void setShopCustomerInfoService(
			IShopCustomerInfoService shopCustomerInfoService) {
		this.shopCustomerInfoService = shopCustomerInfoService;
	}
	public void setSonaccountService(ISonaccountService sonaccountService) {
		this.sonaccountService = sonaccountService;
	}
}
