package phone.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import phone.pojo.CollectProductInfo;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerCollectProduct;
import shop.customer.pojo.CustomerCollectShop;
import shop.customer.pojo.ShopCustomerInfo;
import shop.customer.service.ICustomerCollectProductService;
import shop.customer.service.ICustomerCollectShopService;
import shop.customer.service.ICustomerService;
import shop.customer.service.IShopCustomerInfoService;
import shop.front.shoppingOnLine.service.IShoppingCartService;
import shop.order.pojo.Orders;
import shop.order.service.IOrdersService;
import shop.product.pojo.ProductInfo;
import shop.product.service.IProductInfoService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.other.Utils;

/**
 * 手机登录,普通用户
 * 
 * @author Norberta
 * 
 */
@SuppressWarnings("all")
@Controller
public class PhoneLoginAction extends BaseAction {
	private Customer customer;// 客户实体类
	private ICustomerService customerService;// 客户Service
	private IProductInfoService productInfoService;
	private IShopCustomerInfoService shopCustomerInfoService;// 会员附属表service
	private ICustomerCollectProductService customerCollectProductService;// 用户收藏Service
	private ICustomerCollectShopService customerCollectShopService;// 店铺收藏Service
	@Resource
	private IOrdersService ordersService;
	/** 店铺service **/
	@Resource
	private IShopInfoService shopInfoService;
	/** 会员子账号service **/
	private IShoppingCartService shoppingCartService;
	/** 是否放入cookie标识 **/
	private String falg;
	/** 返回页面信息 **/
	private String message;
	/** 登录用户状态标识（卖家：1已申请通过，2申请未通过,3未申请店铺,4 审核中） **/
	private String sellerflag;
	private String cmail;
	private String cpassword;
	private String directUrl;
	private String loginName;
	private String shopInfoId;
	private String productId;
	private String customerId;

	public void setShopInfoId(String shopInfoId) {
		this.shopInfoId = shopInfoId;
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public void customerIdSession() {
		if (customerId != null) {
			session.setAttribute("customerId", customerId);
			session.setMaxInactiveInterval(30 * 24 * 3600);
		}
	}

	//登录功能
	public void login() throws IOException {
		session.removeAttribute("customerId");
		String passwords = Utils.EncodeMd5(Utils.EncodeMd5(cpassword));
		Boolean status = false;
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		// 过滤登录时使用类似=admin ' or 1=1 ...
		String phone = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";// 手机号码正则验证
		// java正则验证邮箱
		String email = "^[0-9a-zA-Z]+@(([0-9a-zA-Z]+)[.])+[a-z]{2,4}$";
		// java正则验证只能有数字，英文字母和下划线組拼的字符串
		String reg = "[^a-zA-Z0-9_]";
		Pattern p_reg = Pattern.compile(reg);
		Matcher m_reg = p_reg.matcher(loginName.trim());
		if (match(phone, loginName.trim()) == false
				&& match(email, loginName.trim()) == false) {
			loginName = m_reg.replaceAll("").trim();
			jo.accumulate("statu", false);
			jo.accumulate("Data", "用户名格式错误");
			pw.print(jo);
			pw.flush();
			pw.close();
			return;
		}
		// 手机端只允许消费者登录
		String type = "3";
		request.getSession().removeAttribute("sonaccount");// 清除session中sonaccount对象(采收商子账号)
		if (Utils.objectIsNotEmpty(type)) {
			int total = customerService
					.getCountByHQL(" select count(*) from Customer o where o.loginName = '"
							+ loginName.trim()
							+ "' and o.password = '"
							+ passwords
							+ "' and o.type in ("
							+ type
							+ ")  or o.phone='"
							+ loginName.trim()
							+ "'  and o.password = '"
							+ passwords
							+ "' and o.type in ("
							+ type
							+ ")  or o.email='"
							+ loginName.trim()
							+ "'  and o.password = '"
							+ passwords + "' and o.type in (" + type + ")");
			if (total > 0) {// 存在
				customer = (Customer) customerService
						.getObjectByParams(" where o.loginName = '"
								+ loginName.trim() + "'  and o.password = '"
								+ passwords + "' and o.type in (" + type
								+ ")  or o.phone='" + loginName.trim()
								+ "' and o.password = '" + passwords
								+ "' and o.type in (" + type
								+ ")  or o.email='" + loginName.trim()
								+ "'  and o.password = '" + passwords
								+ "' and o.type in (" + type + ")");
				if (customer.getLockedState() == 0) {
					status = true;
					jo.accumulate("status", status);
					jo.accumulate("Data", customer);
					// 取关注的商品和店铺
					int proCount = customerCollectProductService
							.getCount(" where o.customerId="
									+ customer.getCustomerId());
					int shopCount = customerCollectShopService
							.getCount(" where o.customerId="
									+ customer.getCustomerId());
					jo.accumulate("proCount", proCount);
					jo.accumulate("shopCount", shopCount);
					List<Orders> list = ordersService.findObjects(" where ordersState =0 or ordersState =1 or ordersState =2 or ordersState =3 or ordersState =4 or ordersState =5 or ordersState =7");
					int count1 = 0;
					int count2 = 0;
					int count4 = 0;
					int count5 = 0;
					int count7 = 0;
					for (Orders orders : list) {
						if (orders.getOrdersState() == 1||orders.getOrdersState() == 0) {
							count1++;
						} else if (orders.getOrdersState() == 2
								|| orders.getOrdersState() == 3) {
							count2++;
						} else if (orders.getOrdersState() == 4) {
							count4++;
						} else if (orders.getOrdersState() == 5) {
							count5++;
						} else if (orders.getOrdersState() == 7) {
							count7++;
						}
					}
					jo.accumulate("obligation", count1);
					jo.accumulate("unreceived", count4+count2);
					jo.accumulate("unevaluated", count5);
					jo.accumulate("returned", count7);
					session.setAttribute("customerId", customer.getCustomerId());
					session.setMaxInactiveInterval(30 * 24 * 3600);
				} else {
					jo.accumulate("status", false);
					jo.accumulate("Data", "帐号封禁中....");
				}
			} else {
				jo.accumulate("status", status);
				jo.accumulate("Data", "用户名或密码错误");
			}
		}
		pw.print(jo);
		pw.flush();
		pw.close();
	}

	public void setProductInfoService(IProductInfoService productInfoService) {
		this.productInfoService = productInfoService;
	}

	// 获取观注商品
	public void getCustomerCollectPro() throws IOException {
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		if (customer == null) {
			return;
		}
		List<CollectProductInfo> infos = new ArrayList<CollectProductInfo>();
		List<CustomerCollectProduct> list = customerCollectProductService
				.findObjects(" where customerId=" + customer.getCustomerId());
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		if (list != null) {
			for (CustomerCollectProduct customerCollectProduct : list) {
				ProductInfo productInfo = (ProductInfo) productInfoService
						.getObjectById(customerCollectProduct.getProductId()
								.toString());
				CollectProductInfo inf = new CollectProductInfo();
				inf.setLogoUrl(productInfo.getLogoImg());
				inf.setProductId(productInfo.getProductId());
				inf.setProductName(productInfo.getProductName());
				inf.setSalesPrice(productInfo.getSalesPrice());
				infos.add(inf);
			}
			jo.accumulate("Status", true);
			jo.accumulate("Data", infos);
		} else {
			jo.accumulate("Status", false);
		}
		pw.print(jo);
		pw.flush();
		pw.close();
	}

	// 获取观注店铺
	public void getCustomerCollectShop() throws IOException {
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		if (customer == null) {
			return;
		}
		List<ShopInfo> infos = new ArrayList<ShopInfo>();
		List<CustomerCollectShop> list = customerCollectShopService
				.findObjects(" where customerId=" + customer.getCustomerId());
		if (list != null) {
			for (CustomerCollectShop customerCollectShop : list) {
				ShopInfo shopInfo = (ShopInfo) shopInfoService
						.getObjectById(customerCollectShop.getShopInfoId()
								.toString());
				infos.add(shopInfo);
			}
			jo.accumulate("Status", true);
			jo.accumulate("Data", infos);
		} else {
			jo.accumulate("Status", false);
		}
		pw.print(jo);
		pw.flush();
		pw.close();
	}

	// 关注的商品
	public void addProToCollect() throws Exception {
		session.removeAttribute("collect");
		if(customerId==null){
			customerId=(String) session.getAttribute("customerId");
		}
		ProductInfo productInfo = (ProductInfo) productInfoService
				.getObjectById(productId);
		List<CustomerCollectProduct> findObjects = customerCollectProductService
				.findObjects(" where productId=" + productId
						+ " and customerId=" + customerId);
		if (findObjects.size() < 1) {
			CustomerCollectProduct customerCollectProduct = new CustomerCollectProduct();
			customerCollectProduct.setCustomerId(Integer.parseInt(customerId));
			customerCollectProduct.setProductId(Integer.parseInt(productId));
			customerCollectProduct.setProductName(productInfo.getProductName());
			customerCollectProductService
					.saveOrUpdateObject(customerCollectProduct);
		} else {
			customerCollectProductService
					.deleteObjectByParams(" where productId=" + productId
							+ " and customerId=" + customerId);
		}
	}

	// 添加关注店铺
	@SuppressWarnings("unchecked")
	public void addShopToCollect() throws Exception {
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		ShopInfo shopInfo = (ShopInfo) shopInfoService
				.getObjectById(shopInfoId);
		List<CustomerCollectShop> list = (List<CustomerCollectShop>) customerCollectShopService
				.getObjectById(shopInfoId);
		if (list != null && list.size() > 0) {
			for (CustomerCollectShop customerCollectShop : list) {
				if (customerCollectShop.getShopInfoId().toString()
						.equals(shopInfoId)) {
					jo.accumulate("status", false);
					jo.accumulate("Data", "此店铺已在关注中");
					break;
				}
			}
		} else {
			jo.accumulate("status", true);
			customerCollectShopService.saveOrUpdateObject(shopInfo);
			jo.accumulate("Data", "添加关注成功");
		}
		pw.print(jo);
		pw.flush();
		pw.close();
	}

	public void setCustomerCollectProductService(
			ICustomerCollectProductService customerCollectProductService) {
		this.customerCollectProductService = customerCollectProductService;
	}

	public void setCustomerCollectShopService(
			ICustomerCollectShopService customerCollectShopService) {
		this.customerCollectShopService = customerCollectShopService;
	}

	// 跳转到找回密码页面
	public String forGotPassword() {
		return SUCCESS;
	}

	// 跳转到重设密码页面
	public String reSetPassword() {
		String passwordRandomCode = request.getParameter("code");
		request.setAttribute("passwordRandomCode", passwordRandomCode);
		return SUCCESS;
	}

	// 修改新密码
	public void reSetPasswordValidate() throws IOException {
		String password = request.getParameter("password");
		String passwordRandomCode = request.getParameter("passwordRandomCode");
		String passwords = Utils.EncodeMd5(Utils.EncodeMd5(password));
		ShopCustomerInfo sci = (ShopCustomerInfo) shopCustomerInfoService
				.getObjectByParams(" where o.passwordRandomCode='"
						+ passwordRandomCode + "'");
		JSONObject jo = new JSONObject();
		if (sci != null) {
			customer = (Customer) customerService
					.getObjectByParams(" where o.customerId ="
							+ sci.getCustomerId());
			if (customer != null) {
				customer.setPassword(passwords);
				customer = (Customer) customerService
						.saveOrUpdateObject(customer);
				if (customer.getCustomerId() != null) {
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

	// 跳转到重设密码页面
	public String reSetPasswordOk() {
		return SUCCESS;
	}

	// 跳转到重设密码页面
	public String personalPassword() {
		return SUCCESS;
	}

	/**
	 * 跳转到申请店铺和已申请等待审核页面
	 * 
	 * @return
	 */
	public String appleSeller() {
		return SUCCESS;
	}

	/** 退出登录 **/
	public String exitLogin() {
		if (request.getSession().getAttribute("customer") != null) {
			request.getSession().removeAttribute("customer");// 清除session中customer对象
			request.getSession().removeAttribute("shopInfo");// 清除session中shopInfo对象
			request.getSession().removeAttribute("sonaccount");// 清除session中sonaccount对象(采收商子账号)
			request.getSession().removeAttribute("cartCountMap");// 清除session中sonaccount对象(采收商子账号)
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
		/*
		 * //清空Cookie中的用户名信息  try{ Cookie c = new
		 * Cookie("thshop_customerName",null); c.setMaxAge(0);//cookie存在7天
		 * c.setPath("/"); response.addCookie(c); }catch(Exception ex){
		 * //System.out.println("清空Cookies发生异常！"); }
		 */
		return SUCCESS;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setShopCustomerInfoService(
			IShopCustomerInfoService shopCustomerInfoService) {
		this.shopCustomerInfoService = shopCustomerInfoService;
	}

	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}

	public void setShoppingCartService(IShoppingCartService shoppingCartService) {
		this.shoppingCartService = shoppingCartService;
	}

	public void setFalg(String falg) {
		this.falg = falg;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSellerflag(String sellerflag) {
		this.sellerflag = sellerflag;
	}

	public void setCmail(String cmail) {
		this.cmail = cmail;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public void setDirectUrl(String directUrl) {
		this.directUrl = directUrl;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

}
