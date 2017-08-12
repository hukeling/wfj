package shop.front.register.action;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;

import net.sf.json.JSONObject;
import shop.common.pojo.CoinRules;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import shop.customer.service.IMallCoinService;
import shop.customer.service.IShopCustomerInfoService;
import shop.customer.service.ISonaccountService;
import shop.customer.service.IVirtualCoinService;
import shop.shareRigster.pojo.ShareRigster;
import shop.shareRigster.service.IShareRigsterService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.action.SecurityCode;
import util.action.SecurityCode.SecurityCodeLevel;
import util.other.jcaptcha.CaptchaServiceSingleton;
import cms.article.pojo.CmsArticle;
import cms.article.service.IArticleService;
/**
 * RegisterAction - 注册Action类
 */
@SuppressWarnings("serial")
public class RegisterAction extends BaseAction {
	private ICustomerService customerService;// 会员Service
	private IShopInfoService shopInfoService;// 店铺Service
	private IShopCustomerInfoService shopCustomerInfoService;
	private IMallCoinService mallCoinService;
	private Customer customer;// 会员
	private IArticleService articleService;// 文章Service
	private IShareRigsterService shareRigsterService;
	private CmsArticle cmsArticle;// 文章
	private String articleId;
	/** 控制协议选定 **/
	private Integer str;
	/** 虚拟金币明细service */
	private IVirtualCoinService virtualCoinService;
	/** id */
	private String id;
	/**注册方式1.用户名，2邮箱，3.手机**/
	private String registerType;
	private Integer type;
	/**图片流**/
    private ByteArrayInputStream imageStream;
    /**子帐号service**/
    private ISonaccountService sonaccountService;
    private BufferedImage bufferedImage;
	/******************************end****************************************/
    private static boolean match( String regex ,String str ){
        Pattern pattern = Pattern.compile(regex);
        Matcher  matcher = pattern.matcher( str );
        return matcher.matches();
    }
    /**
     * 跳转到重复提交提醒页面
     * @return
     */
    public String repeatSubmit(){
    	return SUCCESS;
    }
	/**获取默认 宽度和长度的验证码
	public String userRegister(){
		  //如果开启Hard模式，可以不区分大小写
 //        String securityCode = SecurityCode.getSecurityCode(4,SecurityCodeLevel.Hard, false).toLowerCase();
        String securityCode = SecurityCode.getSecurityCode();
        imageStream = SecurityImage.getImageAsInputStream(securityCode);
        request.getSession().setAttribute("verificationCode", securityCode);
        request.getSession().setMaxInactiveInterval(180);//设置验证码的失效时间为3分钟
		return SUCCESS;
	}**/
	
	/**获取默认 宽度和长度的验证码**/
	public String userRegister() throws IOException{
		//生成验证码图片bufferedImage
        bufferedImage = util.other.jcaptcha.ImageCaptcha.genernateCaptchaImage(request,response);
        //将图片转换成字节流进行传输
        imageStream = convertImageToStream(bufferedImage);
        //设置验证码的失效时间计时
        request.getSession().setAttribute("verificationInvalidationTime", System.currentTimeMillis());
		return SUCCESS;
	}
	/**
	 * 将BufferedImage转换成ByteArrayInputStream
	 * @param image  图片
	 * @return ByteArrayInputStream 流
	 */
	private static ByteArrayInputStream convertImageToStream(BufferedImage image){
		ByteArrayInputStream inputStream = null;
		   ByteArrayOutputStream bos = new ByteArrayOutputStream();
		   byte [] bimage=null;
		   try {
		ImageIO.write(image, "jpg", bos);
		bimage=bos.toByteArray();
		inputStream = new ByteArrayInputStream(bimage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
    }
	
	/**
	 * 异步验证验证码是否失效
	 */
	public void checkCode(){
		try {
			String verificationCode = request.getParameter("verificationCode");//前台传递的验证码
			String verificationCodeFront = (String) session.getAttribute("verificationCodeFront");//session中存储的验证码
			//获取验证码的失效时间计时
			long verificationInvalidationTime = Long.parseLong(String.valueOf(request.getSession().getAttribute("verificationInvalidationTime")));
			String isExsit="0";//0默认，1:正确，2验证码错误,3:验证码失效
			//获取失效时间，单位毫秒
			long sessionVerificationInvalidationTime =  Long.parseLong( String.valueOf(fileUrlConfig.get("session_verificationInvalidationTime")) );
			//判断验证码状态，判断验证码时间是否超时
			if(null==verificationCodeFront||((System.currentTimeMillis() - verificationInvalidationTime) > sessionVerificationInvalidationTime)){
				isExsit="3";
			}else if(verificationCode!=null&&verificationCode.equals(verificationCodeFront)){
				isExsit="1";
			}else if(verificationCode!=null&&!verificationCode.equals(verificationCodeFront)){
				isExsit="2";
			}
			JSONObject jo = new JSONObject();
			jo.accumulate("isExsit", isExsit);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter pw;
			pw = response.getWriter();
			pw.print(jo.toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 发送验证码至手机
	 * @author mf
	 */
	public void sentMessage()throws IOException{
		JSONObject jo=new JSONObject();
		try {
			String phoneNumber = request.getParameter("phoneNumber");
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	// 跳转商城首页
	public String home() {
		return SUCCESS;
	}
	// 跳转到选择注册页面
	public String gotoRegister() {
		if (str == null) {
			return SUCCESS;
		} else if (str == 1) {
			str = 2;
		}
		return SUCCESS;
	}
	// 跳转到注册页面
	public String gotoCustomerRegistrationPage() {
		//分享注册id
		String persionId = (String) request.getParameter("persionId");
		request.setAttribute("persionId", persionId);
		if (str == null) {
			return SUCCESS;
		} else if (str == 1) {
			str = 2;
		}
		return SUCCESS;
	}
	// 跳转到注册成功页面
	public String success() {
		return SUCCESS;
	}
	// 验证用户名是否存在
	public void checkingCustomer() throws IOException {
		String loginName = request.getParameter("loginName");
		Integer count = customerService.getCount(" where o.loginName='"
				+ loginName.trim() + "' or o.phone='"+loginName.trim()+"' or o.email='"+loginName.trim()+"'");
		Boolean isExsit = false;
		if (count != 0) {
			isExsit = true;//已注册
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isExsit", isExsit);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	// 验证邮箱是否存在
	public void checkEmail() throws IOException {
		String email = request.getParameter("email");
		Integer count = customerService.getCount(" where o.email='" + email
				+ "'");
		Integer count2 = sonaccountService.getCount(" where o.email='" + email
				+ "'");
		Boolean isExsit = false;
		if (count>0 || count2>0) {
			isExsit = true;
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isExsit", isExsit);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	
	/**
	 * 检验验证码是否正确
	 */
	public void checkYzm()throws IOException{
		String yzmV = "";//验证码是否正确 0:失效 1.验证码错误 2.验证码正确
		//获取验证码的失效时间计时
		long verificationInvalidationTime = Long.parseLong(String.valueOf(request.getSession().getAttribute("verificationInvalidationTime")));
		//获取失效时间，单位毫秒
		long sessionVerificationInvalidationTime =  Long.parseLong( String.valueOf(((Map<Object,Object>) servletContext.getAttribute("fileUrlConfig")).get("session_verificationInvalidationTime")) );
		String verificationCode = request.getParameter("yzm");
		String captchaID = request.getSession().getId();//获取当前的sessionID
		Boolean validateResponseForID = CaptchaServiceSingleton.getInstance().validateResponseForID(captchaID, verificationCode);//对验证码进行校验
		//判断验证码状态，判断验证码时间是否超时
		if(((System.currentTimeMillis() - verificationInvalidationTime) > sessionVerificationInvalidationTime)){
			yzmV="0";//失效、超时
		}else if(validateResponseForID){
			yzmV="2";//正确
			//生成验证码图片bufferedImage
	        bufferedImage = util.other.jcaptcha.ImageCaptcha.genernateCaptchaImage(request,response);
	        //设置验证码的失效时间计时
	        request.getSession().setAttribute("verificationInvalidationTime", System.currentTimeMillis());
		}else{
			yzmV="1";//正确
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("yzmV", yzmV);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw=response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	
	/**
	 * 用户注册 向邮箱发送邮件
	 * 
	 * @author 。。。 2014.01.03 刘钦楠修改
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String register() throws Exception {
		String trueName=String.valueOf(request.getParameter("trueName"));
		String cardNo=String.valueOf(request.getParameter("cardNo"));
		String shareRegisterId=String.valueOf(request.getParameter("persionId")).trim();
		String ip=request.getRemoteHost();
		
		customer = customerService.saveRegister(customer, trueName, cardNo, ip,registerType);
		if (customer!= null) {
			//获取数据字典
			Map<String,Object> keyBookMap = (Map<String, Object>) servletContext.getAttribute("coinRules");
			//注册用户赠送商城币
			mallCoinService.saveRegisterVirtualCoin(customer.getCustomerId(), keyBookMap,1);
			//如果当前用户是被推荐的 则给推荐人赠送商城币
			if(shareRegisterId!=null&&!"".equals(shareRegisterId)){
				mallCoinService.saveRegisterVirtualCoin(Integer.parseInt(shareRegisterId), keyBookMap,2);
				//向分享注册表中插入一条数据
				ShareRigster sr = new ShareRigster();
				sr.setShareCustomerId(Integer.parseInt(shareRegisterId));//分享人ID
				sr.setRegisterCustomerId(customer.getCustomerId());//注册人ID
				sr.setShareTime(new Date());
				List<CoinRules> crList = (List<CoinRules>) keyBookMap.get("coinRulesRecommend");
				sr.setGiveCoinNumber(new BigDecimal(crList.get(0).getValue()));//邀请注册赠送金币数量
				shareRigsterService.saveOrUpdateObject(sr);
			}
			//注册成功后直接登录跳转是商城首页 
			session.setAttribute("customer", customer);
			session.setMaxInactiveInterval(30*24*3600);
			ShopInfo shopInfo = (ShopInfo) shopInfoService.getObjectByParams(" where o.customerId="+customer.getCustomerId());
			session.setAttribute("shopInfo" ,shopInfo);
			//在cookie中存储用户名称
			Cookie c=new Cookie("thshop_customerName", customer.getLoginName());
			c.setMaxAge(60*60*24*7);//cookie存在7天
			c.setPath("/"); 
			response.addCookie(c);
			return SUCCESS;
		} else {
			return "";
		}
	}
	// 查看用户协议
	public String getAgreement() throws Exception {
		String categoryId = request.getParameter("categoryId");
		String type = request.getParameter("type");
		if(categoryId!=null&&!"".equals(categoryId.trim())){
			String articleId="673";//用户协议
			if(type!=null&&!"".equals(type.trim())&&"2".equals(type.trim())){
				articleId="668";//供应商协议
			}
			cmsArticle = (CmsArticle) articleService.getObjectByParams(" where o.isShow=1 and o.categoryId ="+categoryId+" and o.articleId="+articleId);
		}
		return SUCCESS;
	}
	/**
	 * 检测真实姓名是否存在
	 * @param trueName 真实姓名
	 * @author mf
	 */
	public void checkTrueName() throws Exception{
		//获取真实姓名
		String trueName = request.getParameter("trueName");
		Integer count =  shopCustomerInfoService.getCount(" where o.trueName='"+trueName.trim()+"'");
		Integer count2 = sonaccountService.getCount(" where o.nickname='"+trueName.trim()+"'");
		JSONObject jo=new JSONObject();
		if(count>0||count2>0){
			jo.accumulate("isSuccess",true);
		}else{
			jo.accumulate("isSuccess",false);
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 检测身份证是否被注册
	 * @param IDcard 身份证
	 * @author mf
	 */
	public void checkIDcard() throws Exception{
		//获取身份证
		String IDcard = request.getParameter("IDcard");
		//过滤登录时使用类似=admin ' or 1=1 ...
		  String phone ="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";//手机号码正则验证
		  //java正则验证邮箱
		  String email ="^[0-9a-zA-Z]+@(([0-9a-zA-Z]+)[.])+[a-z]{2,4}$"; 
		  //java正则验证只能有数字，英文字母和下划线組拼的字符串
		  String reg ="[^a-zA-Z0-9_]"; 
		  Pattern p_reg = Pattern.compile(reg); 
		  Matcher m_reg = p_reg.matcher(IDcard.trim());
		  if(match(phone,IDcard.trim())==false&&match(email,IDcard.trim())==false){
			  IDcard = m_reg.replaceAll("").trim();
		  }
		Integer count =  shopCustomerInfoService.getCount(" where o.idCard='"+IDcard+"'");
		JSONObject jo=new JSONObject();
		if(count>0){
			jo.accumulate("isSuccess",true);
		}else{
			jo.accumulate("isSuccess",false);
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	/**
	 * 检测手机号是否存在
	 * @param phone 手机号
	 * @author mf
	 */
	public void checkPhone() throws Exception{
		//获取手机号
		String phone1 = request.getParameter("phone");
		//过滤登录时使用类似=admin ' or 1=1 ...
		  String phone ="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";//手机号码正则验证
		  //java正则验证邮箱
		  String email ="^[0-9a-zA-Z]+@(([0-9a-zA-Z]+)[.])+[a-z]{2,4}$";
		  //java正则验证只能有数字，英文字母和下划线組拼的字符串
		  String reg ="[^a-zA-Z0-9_]";
		  Pattern p_reg = Pattern.compile(reg);
		  Matcher m_reg = p_reg.matcher(phone1.trim());
		  if(match(phone,phone1.trim())==false&&match(email,phone1.trim())==false){
			  phone1 = m_reg.replaceAll("").trim();
		  }
		Integer count =  customerService.getCount(" where o.phone='"+phone1+"'");
		Integer count2 = sonaccountService.getCount(" where o.phone='"+phone1+"'");
		JSONObject jo=new JSONObject();
		if(count>0||count2>0){
			jo.accumulate("isSuccess",true);
		}else{
			jo.accumulate("isSuccess",false);
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void setArticleService(IArticleService articleService) {
		this.articleService = articleService;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	public CmsArticle getCmsArticle() {
		return cmsArticle;
	}
	public void setCmsArticle(CmsArticle cmsArticle) {
		this.cmsArticle = cmsArticle;
	}
	public void setShopCustomerInfoService(
			IShopCustomerInfoService shopCustomerInfoService) {
		this.shopCustomerInfoService = shopCustomerInfoService;
	}
	public Integer getStr() {
		return str;
	}
	public void setStr(Integer str) {
		this.str = str;
	}
	public void setVirtualCoinService(IVirtualCoinService virtualCoinService) {
		this.virtualCoinService = virtualCoinService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRegisterType() {
		return registerType;
	}
	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}
	public ByteArrayInputStream getImageStream() {
		return imageStream;
	}
	public void setImageStream(ByteArrayInputStream imageStream) {
		this.imageStream = imageStream;
	}
	public void setSonaccountService(ISonaccountService sonaccountService) {
		this.sonaccountService = sonaccountService;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}
	public void setMallCoinService(IMallCoinService mallCoinService) {
		this.mallCoinService = mallCoinService;
	}
	public void setShareRigsterService(IShareRigsterService shareRigsterService) {
		this.shareRigsterService = shareRigsterService;
	}
}
