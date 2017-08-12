package phone.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;

import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.pojo.ShopCustomerInfo;
import shop.customer.service.ICustomerAcceptAddressService;
import shop.customer.service.ICustomerCollectProductService;
import shop.customer.service.ICustomerCollectShopService;
import shop.customer.service.ICustomerService;
import shop.customer.service.IShopCustomerInfoService;
import shop.order.pojo.Orders;
import shop.order.service.IOrdersService;
import shop.store.pojo.ShopInfo;
import shop.store.service.IShopInfoService;
import util.action.BaseAction;
import util.action.SecurityCode;
import util.action.SecurityCode.SecurityCodeLevel;
import util.mail.Mail;
import util.other.Utils;
import util.upload.ImageFileUploadUtil;
import basic.service.IAreaService;

@SuppressWarnings("all")
@Controller
public class PhoneUserAction extends BaseAction {
	public ICustomerService customerService;
	private ICustomerCollectProductService customerCollectProductService;
	private ICustomerCollectShopService customerCollectShopService;
	private ICustomerAcceptAddressService customerAcceptAddressService;
	private IShopCustomerInfoService shopCustomerInfoService;
	private IShopInfoService shopInfoService;

	@Resource
	private IOrdersService ordersService;
	private IAreaService areaService;
	public String customerId;
	public String nickname;
	public String phone;
	private File txImage;
	private String txImageFileName;
	private ShopCustomerInfo customerInfo;
	private Integer customerAcceptAddressId;
	private CustomerAcceptAddress customerAcceptAddress;
	private String password;// 用户旧密码
	private String cpassword;// 用户新密码
	private String email;// 用户邮箱

	// 根据邮箱获取密码
	public void forGotValidate() throws IOException {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = response.getWriter();
		Customer customer = (Customer) customerService
				.getObjectByParams(" where o.email = '" + email + "'");
		if (customer != null) {
			String usersId = String.valueOf(customer.getCustomerId());
			Cookie cookie = new Cookie("forGotCookie", usersId);
			cookie.setMaxAge(60 * 60 * 24);// cookie有效时间为1天
			cookie.setPath("/");// 设置整个项目都可以访问
			response.addCookie(cookie);
			int customerId = customer.getCustomerId();
			String passwordUrl = (String) fileUrlConfig.get("password_url");
			String con = "<a href='" + passwordUrl
					+ "/phone/phoneReSetPassword.action?customerId="
					+ customerId + "'>点击链接进行密码重置</a>";
			Mail.sent(customer.getEmail(), "SHOPJSP密码重置验证", con, servletContext);
			jo.accumulate("Status", true);
			jo.accumulate("Data", "邮件发送成功！");
		} else {
			jo.accumulate("Status", false);
			jo.accumulate("Data", "邮件发送失败！");
		}
		pw.print(jo.toString());
		pw.flush();
		pw.close();
	}

	// 跳转到重设密码页面
	public String reSetPassword() {
		String customerId = request.getParameter("customerId");
		request.setAttribute("customerId", customerId);
		session.setAttribute("customerId", customerId);
		session.setMaxInactiveInterval(7 * 24 * 3600);
		return SUCCESS;
	}

	// 修改新密码
	public void reSetPasswordValidate() throws IOException {
		String customerId = request.getParameter("customerId");
		String password = request.getParameter("password");
		String passwords = Utils.EncodeMd5(Utils.EncodeMd5(password));
		// ShopCustomerInfo sci = (ShopCustomerInfo)
		// shopCustomerInfoService.getObjectByParams(" where o.passwordRandomCode='"+passwordRandomCode+"'");
		JSONObject jo = new JSONObject();
		Customer customer = (Customer) customerService
				.getObjectByParams(" where o.customerId =" + customerId);
		if (customer != null) {
			customer.setPassword(passwords);
			customer = (Customer) customerService.saveOrUpdateObject(customer);
			if (customer.getCustomerId() != null) {
				jo.accumulate("isSuccess", "true");
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
	 * https://192.168.10.210:8443/wfj_front/phone/register.action?email=2389@12.
	 * com&phone=12345678912&password=123456 用户注册
	 * 
	 * @throws IOException
	 */
	public void register() throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		JSONObject jo = new JSONObject();
		Customer customer = new Customer();
		password = Utils.EncodeMd5(Utils.EncodeMd5(password));
		if (checkEmail(email) == true) {
			jo.accumulate("Status", false);
			jo.accumulate("Data", "邮箱已被注册，注册失败！");
		} else if (checkPhone(phone) == true) {
			jo.accumulate("Status", false);
			jo.accumulate("Data", "电话号码已被注册，注册失败！");
		} else {
			customer.setPassword(password);
			customer.setEmail(email);
			customer.setPhone(phone);
			customer.setLockedState(0);// 可用的用户
			customer.setRegisterDate(new Date());// 注册时间
			customer.setType(3);// 会员类型默认为用户3
			customer.setShareType(1);// 默认可以分享
			// 产生4位随机字母
			String str = SecurityCode.getSecurityCode(4,
					SecurityCodeLevel.onlyChar, true);
			// 拼装用户loginName 规则：邮箱@前字符+下斜线+随机字母（4位随机数），比如：94876_oppo）胡
			String loginName = email.split("@")[0] + "_" + str;
			customer.setLoginName(loginName);
			customer = (Customer) customerService.saveOrUpdateObject(customer);
			// 增加一条shopinfo记录
			ShopInfo si = new ShopInfo();
			si.setCustomerId(customer.getCustomerId());
			si.setCustomerName(customer.getLoginName());
			si = (ShopInfo) shopInfoService.saveOrUpdateObject(si);
			if (customer != null) {
				jo.accumulate("Status", true);
				jo.accumulate("Data", "注册成功！");
			} else {
				jo.accumulate("Status", false);
				jo.accumulate("Data", "注册失败！");
			}
		}
		out.write(jo.toString());
		out.flush();
		out.close();
	}

	public void editCustomer() throws IOException {
		if (nickname != null)
			nickname = new String(nickname.getBytes("iso8859-1"), "utf-8");
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		if (nickname != null) {
			customer.setNickName(nickname);
		} else {
			customer.setPhone(phone);
		}
		customerService.saveOrUpdateObject(customer);
		// 取关注的商品和店铺
		int proCount = customerCollectProductService
				.getCount(" where o.customerId=" + customer.getCustomerId());
		int shopCount = customerCollectShopService
				.getCount(" where o.customerId=" + customer.getCustomerId());
		jo.accumulate("proCount", proCount);
		jo.accumulate("shopCount", shopCount);
		jo.accumulate("Status", true);
		jo.accumulate("Data", customer);
		pw.write(jo.toString());
		pw.flush();
		pw.close();
	}

	public void flushUser() {
		JSONObject jo = new JSONObject();
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=UTF-8");
		if (customerId == null) {
			jo.accumulate("Status", false);
		} else {
			Customer customer = (Customer) customerService
					.getObjectById(customerId);
			jo.accumulate("Status", true);
			jo.accumulate("Data", customer);
			int proCount = customerCollectProductService
					.getCount(" where o.customerId=" + customer.getCustomerId());
			int shopCount = customerCollectShopService
					.getCount(" where o.customerId=" + customer.getCustomerId());
			jo.accumulate("proCount", proCount);
			jo.accumulate("shopCount", shopCount);
			List<Orders> list = ordersService
					.findObjects(" where ordersState =0 or ordersState =1 or ordersState =2 or ordersState =3 or ordersState =4 or ordersState =5 or ordersState =7");
			int count1 = 0;
			int count2 = 0;
			int count4 = 0;
			int count5 = 0;
			int count7 = 0;
			for (Orders orders : list) {
				if (orders.getOrdersState() == 1) {
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
		}
		pw.print(jo);
		pw.flush();
		pw.close();
	}

	public void uploadPic() throws IOException {
		Enumeration<String> headerNames = request.getHeaderNames();
		String method = request.getMethod();
		JSONObject jo = new JSONObject();
		PrintWriter pw = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		File file = new File(txImageFileName);
		String returnImagePathFileName = ImageFileUploadUtil.uploadImageFile(
				txImage, txImageFileName, fileUrlConfig, "image_customer");
		if (txImageFileName.equals(returnImagePathFileName)
				|| txImageFileName.equals("图片上传失败!")) {
			jo.accumulate("Status", false);
		} else {
			customerId = txImageFileName.substring(0,
					txImageFileName.lastIndexOf('.'));
			Customer customer = (Customer) customerService
					.getObjectById(customerId);
			customer.setPhotoUrl(returnImagePathFileName);
			customerService.saveOrUpdateObject(customer);
			jo.accumulate("Status", true);
			jo.accumulate("Data", customer);
			int proCount = customerCollectProductService
					.getCount(" where o.customerId=" + customerId);
			int shopCount = customerCollectShopService
					.getCount(" where o.customerId=" + customerId);
			jo.accumulate("proCount", proCount);
			jo.accumulate("shopCount", shopCount);
		}
		pw.println(jo.toString());
		pw.flush();
		pw.close();
	}

	public String getReceiveAddress() {
		if (customerId == null) {
			customerId = request.getAttribute("customerId").toString();
		}
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		// 查询收货地址
		CustomerAcceptAddress customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectByParams("where o.isSendAddress=1 and o.customerId="
						+ customer.getCustomerId());
		session.setAttribute("defaultAddress", customerAcceptAddress);
		if (customer != null) {
			List<CustomerAcceptAddress> customerAcceptAddressList = customerAcceptAddressService
					.findObjects(" where o.isSendAddress=0 and o.customerId="
							+ customer.getCustomerId()
							+ " order by o.customerAcceptAddressId");
			session.setAttribute("otherAddress", customerAcceptAddressList);
			session.setAttribute("customer", customer);
		}
		// 一级地区parent=0
		String firstHql = "select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		List<Map> firstAreaList = areaService.findListMapByHql(firstHql);
		List obj = shopCustomerInfoService.findObjects(" where customerId ="
				+ customer.getCustomerId());
		if (obj.size() > 0) {
			this.customerInfo = shopCustomerInfoService.findObjects(
					" where customerId =" + customer.getCustomerId()).get(0);
		}
		return SUCCESS;
	}

	public void setDefault() throws IOException {
		CustomerAcceptAddress customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectByParams(" where customerAcceptAddressId="
						+ customerAcceptAddressId);
		CustomerAcceptAddress defatltAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectByParams(" where customerId=" + customerId
						+ " and isSendAddress=1");
		defatltAddress.setIsSendAddress(0);
		customerAcceptAddress.setIsSendAddress(1);
		customerAcceptAddressService.saveOrUpdateObject(defatltAddress);
		customerAcceptAddressService.saveOrUpdateObject(customerAcceptAddress);
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		// 查询收货地址
		customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectByParams("where isSendAddress=1 and customerId="
						+ customer.getCustomerId());
		session.setAttribute("defaultAddress", customerAcceptAddress);
		if (customer != null) {
			List<CustomerAcceptAddress> customerAcceptAddressList = customerAcceptAddressService
					.findObjects(" where isSendAddress=0 and customerId="
							+ customer.getCustomerId()
							+ " order by customerAcceptAddressId");
			session.setAttribute("otherAddress", customerAcceptAddressList);
			session.setAttribute("customer", customer);
		}
		// 一级地区parent=0
		String firstHql = "select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		List<Map> firstAreaList = areaService.findListMapByHql(firstHql);
		List obj = shopCustomerInfoService.findObjects(" where customerId ="
				+ customer.getCustomerId());
		if (obj.size() > 0) {
			this.customerInfo = shopCustomerInfoService.findObjects(
					" where customerId =" + customer.getCustomerId()).get(0);
		}
		JSONObject jo=new JSONObject();
		jo.accumulate("Status", true);
		PrintWriter pw=response.getWriter();
		pw.print(jo);
		pw.flush();
		pw.close();
	}

	public String delAddress() {
		CustomerAcceptAddress customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectById(customerAcceptAddressId.toString());
		Customer customer = (Customer) customerService
				.getObjectById(customerAcceptAddress.getCustomerId().toString());
		List<CustomerAcceptAddress> list = customerAcceptAddressService
				.findObjects(" where customerId=" + customer.getCustomerId());
		if (list.size() > 1) {
			customerAcceptAddressService
					.deleteObjectById(customerAcceptAddressId.toString());
			list = customerAcceptAddressService
					.findObjects(" where customerId="
							+ customer.getCustomerId());
			CustomerAcceptAddress address = list.get(0);
			address.setIsSendAddress(1);
			customerAcceptAddressService.saveOrUpdateObject(address);
		}else{
			customerAcceptAddressService
			.deleteObjectById(customerAcceptAddressId.toString());
		}
		if (customer != null) {
			List<CustomerAcceptAddress> customerAcceptAddressList = customerAcceptAddressService
					.findObjects(" where o.isSendAddress=0 and o.customerId="
							+ customer.getCustomerId()
							+ " order by o.customerAcceptAddressId");
			request.setAttribute("otherAddress", customerAcceptAddressList);
		}
		// 一级地区parent=0
		String firstHql = "select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		List<Map> firstAreaList = areaService.findListMapByHql(firstHql);
		List obj = shopCustomerInfoService.findObjects(" where customerId ="
				+ customer.getCustomerId());
		if (obj.size() > 0) {
			this.customerInfo = shopCustomerInfoService.findObjects(
					" where customerId =" + customer.getCustomerId()).get(0);
		}
		request.setAttribute("customer", customer);
		// 查询收货地址
		customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectByParams("where o.isSendAddress=1 and o.customerId="
						+ customer.getCustomerId());
		session.setAttribute("defaultAddress", customerAcceptAddress);
		if (customer != null) {
			List<CustomerAcceptAddress> customerAcceptAddressList = customerAcceptAddressService
					.findObjects(" where o.isSendAddress=0 and o.customerId="
							+ customer.getCustomerId()
							+ " order by o.customerAcceptAddressId");
			session.setAttribute("otherAddress", customerAcceptAddressList);
			session.setAttribute("customer", customer);
		}
		// 一级地区parent=0
		firstHql = "select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		firstAreaList = areaService.findListMapByHql(firstHql);
		obj = shopCustomerInfoService.findObjects(" where customerId ="
				+ customer.getCustomerId());
		if (obj.size() > 0) {
			this.customerInfo = shopCustomerInfoService.findObjects(
					" where customerId =" + customer.getCustomerId()).get(0);
		}
		return SUCCESS;
	}

	public String newAddress() {
		Customer customer = (Customer) customerService
				.getObjectById(customerId);
		request.setAttribute("customer", customer);
		// customerAcceptAddressService.saveOrUpdateObject(obj);
		return SUCCESS;
	}

	// 保存或者修改用户地址
	public String saveOrUpdateCustomerAcceptAddress() throws Exception {
		Customer customer = null;
		if (customerAcceptAddress != null) {
			CustomerAcceptAddress c = (CustomerAcceptAddress) customerAcceptAddressService
					.getObjectByParams(" where customerId="
							+ customerAcceptAddress.getCustomerId()
							+ " and isSendAddress =1");
			if (c != null) {
				customerAcceptAddress.setIsSendAddress(0);
			} else {
				customerAcceptAddress.setIsSendAddress(1);
			}
			customerId = customerAcceptAddress.getCustomerId().toString();
			customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
					.saveOrUpdateObject(customerAcceptAddress);
		}
		customer = (Customer) customerService.getObjectById(customerId);
		// 查询收货地址
		CustomerAcceptAddress customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectByParams("where o.isSendAddress=1 and o.customerId="
						+ customer.getCustomerId());
		session.setAttribute("defaultAddress", customerAcceptAddress);
		if (customer != null) {
			List<CustomerAcceptAddress> customerAcceptAddressList = customerAcceptAddressService
					.findObjects(" where o.isSendAddress=0 and o.customerId="
							+ customer.getCustomerId()
							+ " order by o.customerAcceptAddressId");
			session.setAttribute("otherAddress", customerAcceptAddressList);
			session.setAttribute("customer", customer);
		}
		// 一级地区parent=0
		String firstHql = "select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		List<Map> firstAreaList = areaService.findListMapByHql(firstHql);
		List obj = shopCustomerInfoService.findObjects(" where customerId ="
				+ customer.getCustomerId());
		if (obj.size() > 0) {
			this.customerInfo = shopCustomerInfoService.findObjects(
					" where customerId =" + customer.getCustomerId()).get(0);
		}
		return SUCCESS;
	}

	// 验证邮箱是否存在
	public Boolean checkEmail(String email) {
		Integer count = customerService.getCount(" where o.email='" + email
				+ "'");
		if (count > 0) {
			return true;
		}
		return false;
	}

	// 检测手机号是否存在
	public boolean checkPhone(String phone) {
		Integer count = customerService.getCount(" where o.phone='" + phone
				+ "'");
		JSONObject jo = new JSONObject();
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void changepass() throws IOException {
		Customer customer = null;// 用户
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		String md5 = Utils.EncodeMd5(Utils.EncodeMd5("1"));
		PrintWriter out = response.getWriter();
		// 判断用户是否存在
		customer = (Customer) customerService
				.getObjectByParams(" where o.customerId = '" + customerId + "'");
		if (customer != null) {// 存在
			String oldPwd = customer.getPassword();

			if (oldPwd.equals(Utils.EncodeMd5(Utils.EncodeMd5(password)))) {
				customer.setPassword(Utils.EncodeMd5(Utils.EncodeMd5(cpassword)));
				customerService.saveOrUpdateObject(customer);
				jo.accumulate("Status", true);
			} else {
				jo.accumulate("Status", false);
				jo.accumulate("Data", "原密码不正确");
			}
		} else {
			jo.accumulate("Status", false);
			jo.accumulate("Data", "用户不存在");
		}
		out.print(jo);
		System.out.println(jo);
		out.flush();
		out.close();
	}

	public String editAddress() {
		CustomerAcceptAddress customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectByParams(" where customerId=" + customerId
						+ " and customerAcceptAddressId="
						+ customerAcceptAddressId);
		session.setAttribute("customerAcceptAddress", customerAcceptAddress);
		return SUCCESS;
	}

	public String edit() {
		CustomerAcceptAddress address = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectById(customerAcceptAddress
						.getCustomerAcceptAddressId().toString());
		address.setConsignee(customerAcceptAddress.getConsignee());
		address.setMobilePhone(customerAcceptAddress.getMobilePhone());
		address.setAddress(customerAcceptAddress.getAddress());
		customerAcceptAddressService.saveOrUpdateObject(address);
		Customer customer = (Customer) customerService.getObjectById(address
				.getCustomerId().toString());
		// 查询收货地址
		CustomerAcceptAddress customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
				.getObjectByParams("where o.isSendAddress=1 and o.customerId="
						+ address.getCustomerId());
		session.setAttribute("defaultAddress", customerAcceptAddress);
		if (customer != null) {
			List<CustomerAcceptAddress> customerAcceptAddressList = customerAcceptAddressService
					.findObjects(" where o.isSendAddress=0 and o.customerId="
							+ address.getCustomerId()
							+ " order by o.customerAcceptAddressId");
			session.setAttribute("otherAddress", customerAcceptAddressList);
			session.setAttribute("customer", customer);
		}
		// 一级地区parent=0
		String firstHql = "select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		List<Map> firstAreaList = areaService.findListMapByHql(firstHql);
		List obj = shopCustomerInfoService.findObjects(" where customerId ="
				+ address.getCustomerId());
		if (obj.size() > 0) {
			this.customerInfo = shopCustomerInfoService.findObjects(
					" where customerId =" + customer.getCustomerId()).get(0);
		}
		return SUCCESS;
	}

	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setCustomerCollectProductService(
			ICustomerCollectProductService customerCollectProductService) {
		this.customerCollectProductService = customerCollectProductService;
	}

	public void setCustomerCollectShopService(
			ICustomerCollectShopService customerCollectShopService) {
		this.customerCollectShopService = customerCollectShopService;
	}

	public void setTxImage(File txImage) {
		this.txImage = txImage;
	}

	public void setTxImageFileName(String txImageFileName) {
		this.txImageFileName = txImageFileName;
	}

	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}

	public void setShopCustomerInfoService(
			IShopCustomerInfoService shopCustomerInfoService) {
		this.shopCustomerInfoService = shopCustomerInfoService;
	}

	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}

	public void setCustomerInfo(ShopCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}

	public void setCustomerAcceptAddressId(Integer customerAcceptAddressId) {
		this.customerAcceptAddressId = customerAcceptAddressId;
	}

	public CustomerAcceptAddress getCustomerAcceptAddress() {
		return customerAcceptAddress;
	}

	public void setCustomerAcceptAddress(
			CustomerAcceptAddress customerAcceptAddress) {
		this.customerAcceptAddress = customerAcceptAddress;
	}

	public File getTxImage() {
		return txImage;
	}

	public String getTxImageFileName() {
		return txImageFileName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOrdersService(IOrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public void setShopInfoService(IShopInfoService shopInfoService) {
		this.shopInfoService = shopInfoService;
	}

}
