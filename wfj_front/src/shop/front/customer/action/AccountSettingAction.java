package shop.front.customer.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.common.pojo.DistrictInfo;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.pojo.SafetyCertificate;
import shop.customer.pojo.ShopCustomerInfo;
import shop.customer.service.ICustomerAcceptAddressService;
import shop.customer.service.ICustomerService;
import shop.customer.service.ISafetyCertificateService;
import shop.customer.service.IShopCustomerInfoService;
import util.action.BaseAction;
import util.other.Utils;
import util.other.WebUtil;
import util.upload.ImageFileUploadUtil;
import basic.service.IAreaService;
/**
 * 账户信息管理（基本信息，密码，收货地址，安全认证）
 * 
 */
public class AccountSettingAction extends BaseAction {
	private ICustomerService customerService;
	private IShopCustomerInfoService shopCustomerInfoService;
	private ICustomerAcceptAddressService customerAcceptAddressService;// 收货地址service
	private List<DistrictInfo> districtInfoList;// 国家信息列表
	private List<DistrictInfo> regionLocationList;// 各州信息列表
	private List<CustomerAcceptAddress> customerAcceptAddressList = new ArrayList<CustomerAcceptAddress>();// 收货地址List
	private CustomerAcceptAddress customerAcceptAddress = new CustomerAcceptAddress();// 收货地址对象
	private String radioName;
	private String id;
	private Customer customer;
	/**
	 * flag 标记 flag=1 :商品购买时地址调用编辑方法 flag=2 :用户管理地址调用编辑方法
	 */
	private String flag;
	private ShopCustomerInfo customerInfo;
	// 基本信息修改用到
	private String birthday;
	// 修改头像
	private File txImage;
	private String txImageFileName;
	private String txImageContentType;
	private String tableIndex;
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unchecked")
	private List<Map> countrys; // 国家
	private String easyName;// 国家简称
	/** 区域service **/
	private IAreaService areaService;
	/** 一级地址 **/
	@SuppressWarnings("unchecked")
	private List<Map> firstAreaList;
	/** 安全认证pojo **/
	private SafetyCertificate safetyCertificate;
	/** 安全认证Service **/
	private ISafetyCertificateService safetyCertificateService;
	private Integer index=0;
	/********************************* end **************************************/
	/**
	 * 修改基本信息     
	 * @author 刘钦楠     修改于2014.01.07
	 * @throws ParseException
	 * @throws IOException
	 */
	public void modifyBaseInfo() throws Exception {
		int userId = ((Customer) session.getAttribute("customer")).getCustomerId();
		Customer c = (Customer) customerService.getObjectByParams(" where customerId =" + userId);
		ShopCustomerInfo cinfo = (ShopCustomerInfo) shopCustomerInfoService.getObjectByParams(" where customerId =" + userId);
		c.setPhone(customer.getPhone());// 修改手机
		c.setEmail(customer.getEmail());// 修改邮箱
		if(customer.getLoginName()!=null&&!"".equals(customer.getLoginName())){
			c.setLoginName(customer.getLoginName());//修改登录名
		}
		if (cinfo == null) {
			cinfo = new ShopCustomerInfo();
			cinfo.setCustomerId(userId);
		}
		if(customerInfo.getTrueName()!=null&&!"".equals(customerInfo.getTrueName())){
			cinfo.setTrueName(customerInfo.getTrueName());
		}
		if(Utils.stringIsNotEmpty(birthday)){
			birthday=birthday.replaceAll("/", "-");
			Date parse = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").parse(birthday+" 00:00:00");
			cinfo.setBirthday(parse);// 修改生日
		}
		if(Utils.stringIsNotEmpty(customer.getPhotoUrl())){
			c.setPhotoUrl(customer.getPhotoUrl());// 修改头像
		}
		cinfo.setSex(customerInfo.getSex());// 修改性别
		cinfo.setPhone(customerInfo.getPhone());// 修改手机
		cinfo.setNotes(customerInfo.getNotes());// 修改备注
		try {
			shopCustomerInfoService.saveOrUpdateObject(cinfo);
			customerService.saveOrUpdateObject(c);
			session.setAttribute("customer", c);
			session.setMaxInactiveInterval(30*24*3600);
			response.getWriter().print("{\"success\":true}");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("{\"success\":false}");
		}
	}
	/**
	 * 旧密码校验
	 * @author mf
	 */
	public void checkPassword() throws IOException{
		//从session中取得用户customer
		this.customer = (Customer) session.getAttribute("customer");
		//从request中取得前台传递的密码信息password
		String password = request.getParameter("password");
		JSONObject jo=new JSONObject();
		if(Utils.stringIsNotEmpty(password)){
			//对password进行两次md5编译
			password=Utils.EncodeMd5(Utils.EncodeMd5(password));
			//判断password是否与当前登录用户的密码一致
			if(customer.getPassword().equals(password)){
				jo.accumulate("success", true);
			}else{
				jo.accumulate("success", false);
			}
		}
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 修改密码
	 * @author mf
	 */
	public void changePassword() throws IOException {
		     //从session中取得用户customer
			this.customer = (Customer) session.getAttribute("customer");
			//从request中取得密码password0
		    String password0 = request.getParameter("password0");
		    //对新密码进行两次md5编译
			String newpass = Utils.EncodeMd5(Utils.EncodeMd5(password0));
			customer.setPassword(newpass);
			Object object = customerService.saveOrUpdateObject(customer);
			// 更新到session
			session.setAttribute("customer", customer);
			session.setMaxInactiveInterval(30*24*3600);
			JSONObject jo=new JSONObject();
			if(object!=null){
				jo.accumulate("success", true);
			}else{
				jo.accumulate("success",false);
			}
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
	}
	/**
	 * 修改头像
	 * 
	 * @throws IOException
	 * @throws Exception
	 */
	public void editImage() throws IOException {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		out = response.getWriter();
		String returnImagePathFileName = ImageFileUploadUtil.uploadImageFile(
				txImage, txImageFileName, fileUrlConfig, "image_customer");
		if (txImageFileName.equals(returnImagePathFileName)
				|| txImageFileName.equals("图片上传失败!")) {
			jo.accumulate("photoUrl", "false");
		} else {
			jo.accumulate("photoUrl", returnImagePathFileName);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig
					.get("visitFileUploadRoot"));
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	// 设为默认地址操作
	public void setDefAddress() throws Exception {
		Customer customer = (Customer) session.getAttribute("customer");
		if (radioName != null) {
			int customerAcceptAddressId = Integer.parseInt(radioName);
			// 得到要修改的对象
			CustomerAcceptAddress caas = (CustomerAcceptAddress) customerAcceptAddressService
					.getObjectByParams(" where o.customerAcceptAddressId="
							+ customerAcceptAddressId+" and o.customerId ="+customer.getCustomerId());
			// 将其IsSendAddress值修改为1
			caas.setIsSendAddress(1);
			// 得到原来默认地址对象
			CustomerAcceptAddress caas2 = (CustomerAcceptAddress) customerAcceptAddressService
					.getObjectByParams(" where o.customerId="
							+ caas.getCustomerId() + "and o.isSendAddress=1");
			if (null != caas2) {
				// 将其IsSendAddress值修改为0
				caas2.setIsSendAddress(0);
				// 保存两个对象
				customerAcceptAddressService.saveOrUpdateObject(caas2);
			}
			CustomerAcceptAddress customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
					.saveOrUpdateObject(caas);
			JSONObject jo = JSONObject.fromObject(customerAcceptAddress);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	// 删除地址
	public void deleteAddress() throws IOException {
		Customer customer = (Customer) session.getAttribute("customer");
		Boolean bool =customerAcceptAddressService.deleteObjectByParams(" where o.customerAcceptAddressId="+id+" and o.customerId="+customer.getCustomerId());
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", bool);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	// 通过ID获得obj对象
	public void findObjById() throws IOException {
		if (id != "-1" && !id.equals("-1")) {
			customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
					.getObjectById(id);
			JSONObject jo = JSONObject.fromObject(customerAcceptAddress);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	// 保存或更新
	public String saveOrUpdate() throws IOException {
		if (customerAcceptAddress != null) {
			String address = new String(customerAcceptAddress.getAddress());
			String consignee = new String(customerAcceptAddress.getConsignee());
			customerAcceptAddress.setAddress(address);
			customerAcceptAddress.setConsignee(consignee);
			customer = (Customer) session.getAttribute("customer");
			if (customer != null) {
				customerAcceptAddress.setCustomerId(customer.getCustomerId());
				if (customerAcceptAddress.getIsSendAddress() != null
						&& customerAcceptAddress.getIsSendAddress() == 1) {
					customerAcceptAddressService.saveOrUpdateSendAddress(
							customer, null);
				} else {
					customerAcceptAddress.setIsSendAddress(0);
				}
				customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService
						.saveOrUpdateObject(customerAcceptAddress);
			}
		}
		return "success2";
	}
	/**
	 * 基本信息页面
	 *@author mf 
	 */
	public String baseInfo(){
		customer = (Customer) session.getAttribute("customer");
		List obj = shopCustomerInfoService.findObjects(" where customerId ="+ customer.getCustomerId());
		if (obj!=null&&obj.size() > 0) {
			this.customerInfo = shopCustomerInfoService.findObjects(" where customerId =" + customer.getCustomerId()).get(0);
		}
		return SUCCESS;
	}
	/**
	 * 修改密码页面
	 * @author mf 
	 */
	public String changePass(){
		customer = (Customer) session.getAttribute("customer");
		return SUCCESS;
	}
	/**
	 * 收货地址页面
	 * @author mf 
	 */
	public String address(){
		customer = (Customer) session.getAttribute("customer");
		// 查询收货地址
		customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams("where o.isSendAddress=1 and o.customerId="+ customer.getCustomerId());
		if (customer != null) {
			customerAcceptAddressList = customerAcceptAddressService.findObjects(" where o.isSendAddress=0 and o.customerId="+ customer.getCustomerId()+ " order by o.customerAcceptAddressId");
		}
		// 一级地区parent=0
		String firstHql = "select a.name as name,a.areaId as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.areaId";
		firstAreaList = areaService.findListMapByHql(firstHql);
		List obj = shopCustomerInfoService.findObjects(" where customerId ="+ customer.getCustomerId());
		if (obj.size() > 0) {
			this.customerInfo = shopCustomerInfoService.findObjects(" where customerId =" + customer.getCustomerId()).get(0);
		}
		return SUCCESS;
	}
	/**
	 * 安全认证信息页
	 * @return
	 */
	public String gotoSafetyCertificate() {
		customer = (Customer) session.getAttribute("customer");
		safetyCertificate = (SafetyCertificate) safetyCertificateService
				.getObjectByParams(" where o.customerId="
						+ customer.getCustomerId());
		return SUCCESS;
	}
	/**
	 * 安全认证信息页
	 * 
	 * @return
	 */
	public String saveSafetyCertificate() {
		customer = (Customer) session.getAttribute("customer");
		if (safetyCertificate != null) {
			if (safetyCertificate.getCustomerId() == null) {
				safetyCertificate.setCustomerId(customer.getCustomerId());
			}
			safetyCertificate = (SafetyCertificate) safetyCertificateService
					.saveOrUpdateObject(safetyCertificate);
		}
		return SUCCESS;
	}
	
	/**
	 *跳转到分享注册页面
	 */
	public String gotoShareRegister(){
		return SUCCESS;
	}
	
	/**
	 *生成分享注册链接
	 */
	public void  makeUrl() throws IOException{
		//获取当前用户数据
		Customer customer = (Customer) session.getAttribute("customer");
		//获取信息标记
		String p = request.getParameter("p");
		String phoneUrl = (String) fileUrlConfig.get("phoneBasePath");//手机端路径
		phoneUrl += "/phone/gotoRegister.action?persionId="+customer.getCustomerId();
		//取得本网站地址[pc端分享注册]
		String basePath = WebUtil.getDomainUrl(request);
		basePath+="register/gotoCustomerRegistrationPage.action?type=3&persionId="+customer.getCustomerId();
		JSONObject jo = new JSONObject();
		if(p!=null&&"phoneUrl".equals(p)){
			jo.accumulate("sru", phoneUrl);
		}else{
			jo.accumulate("sru", basePath);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	/********************************************** set get *******************************************/
	public List<DistrictInfo> getDistrictInfoList() {
		return districtInfoList;
	}
	public void setDistrictInfoList(List<DistrictInfo> districtInfoList) {
		this.districtInfoList = districtInfoList;
	}
	public List<DistrictInfo> getRegionLocationList() {
		return regionLocationList;
	}
	public void setRegionLocationList(List<DistrictInfo> regionLocationList) {
		this.regionLocationList = regionLocationList;
	}
	public List<CustomerAcceptAddress> getCustomerAcceptAddressList() {
		return customerAcceptAddressList;
	}
	public void setCustomerAcceptAddressList(
			List<CustomerAcceptAddress> customerAcceptAddressList) {
		this.customerAcceptAddressList = customerAcceptAddressList;
	}
	public CustomerAcceptAddress getCustomerAcceptAddress() {
		return customerAcceptAddress;
	}
	public void setCustomerAcceptAddress(
			CustomerAcceptAddress customerAcceptAddress) {
		this.customerAcceptAddress = customerAcceptAddress;
	}
	public String getRadioName() {
		return radioName;
	}
	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public ShopCustomerInfo getCustomerInfo() {
		return customerInfo;
	}
	public void setCustomerInfo(ShopCustomerInfo customerInfo) {
		this.customerInfo = customerInfo;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public File getTxImage() {
		return txImage;
	}
	public void setTxImage(File txImage) {
		this.txImage = txImage;
	}
	public String getTxImageFileName() {
		return txImageFileName;
	}
	public void setTxImageFileName(String txImageFileName) {
		this.txImageFileName = txImageFileName;
	}
	public String getTxImageContentType() {
		return txImageContentType;
	}
	public void setTxImageContentType(String txImageContentType) {
		this.txImageContentType = txImageContentType;
	}
	public String getTableIndex() {
		return tableIndex;
	}
	public void setTableIndex(String tableIndex) {
		this.tableIndex = tableIndex;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getCountrys() {
		return countrys;
	}
	@SuppressWarnings("unchecked")
	public void setCountrys(List<Map> countrys) {
		this.countrys = countrys;
	}
	public String getEasyName() {
		return easyName;
	}
	public void setEasyName(String easyName) {
		this.easyName = easyName;
	}
	@SuppressWarnings("unchecked")
	public List<Map> getFirstAreaList() {
		return firstAreaList;
	}
	@SuppressWarnings("unchecked")
	public void setFirstAreaList(List<Map> firstAreaList) {
		this.firstAreaList = firstAreaList;
	}
	public SafetyCertificate getSafetyCertificate() {
		return safetyCertificate;
	}
	public void setSafetyCertificate(SafetyCertificate safetyCertificate) {
		this.safetyCertificate = safetyCertificate;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public void setShopCustomerInfoService(
			IShopCustomerInfoService shopCustomerInfoService) {
		this.shopCustomerInfoService = shopCustomerInfoService;
	}
	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public void setSafetyCertificateService(
			ISafetyCertificateService safetyCertificateService) {
		this.safetyCertificateService = safetyCertificateService;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
}
