package shop.customer.action;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.customer.pojo.Customer;
import shop.customer.pojo.ShopCustomerInfo;
import shop.customer.service.ICustomerService;
import shop.customer.service.IShopCustomerInfoService;
import util.action.BaseAction;
import util.other.JSONFormatDate;
import util.other.Utils;
import util.upload.ImageFileUploadUtil;
/**
 * 客户信息Action
 * @author ss
 *@author 孟琦瑞 --修改布局（增加单条数据的 "修改" "删除"操作）
 */
@SuppressWarnings("all")
public class CustomerAction extends BaseAction{
	private ICustomerService customerService;//客户Service
	private IShopCustomerInfoService shopCustomerInfoService;//个性信息Service
	private List<Customer> customerList = new ArrayList<Customer>();//客户List
	private Customer customer;
	private ShopCustomerInfo shopCustomerInfo;
	private String ids;
	private String customerId;
	// 上传文件路径
	private File imagePath;
	// 上传文件名称
	private String imagePathFileName;
	//验证用户名是否重复
	public void checkLoginName() throws IOException{
		String loginName = request.getParameter("loginName");
		if(loginName!=null && !"".equals(loginName)){
			Integer count = customerService.getCount(" where o.loginName='"+loginName+"'");
			if(count.intValue()==0){
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.print("ok");
				out.flush();
				out.close();
			}
		}
	}
	//验证邮箱是否重复
	public void checkEmail() throws IOException{
		String email = request.getParameter("email");
		if(email!=null && !"".equals(email)){
			Integer count = customerService.getCount(" where o.email='"+email+"'");
			if(count.intValue()==0){
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.print("ok");
				out.flush();
				out.close();
			}
		}
	}
	//跳转用户页面
	public String gotoCustomerPage(){
		return SUCCESS;
	}
	// 异步ajax 图片上传
	public void uploadImage() throws Exception {
		JSONObject jo = new JSONObject();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 1图片上传
		if (imagePath != null) {
			String otherImg = ImageFileUploadUtil.uploadImageFile(imagePath, imagePathFileName, fileUrlConfig, "image_customer");
			jo.accumulate("photoUrl", otherImg);
			jo.accumulate("visitFileUploadRoot", (String) fileUrlConfig.get("visitFileUploadRoot"));
		} else {
			jo.accumulate("photoUrl", "false1");
		}
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存会员基本信息
	@SuppressWarnings("static-access")
	public void saveOrUpdateCustomer() throws ParseException{
		if(customer != null){
			SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String formatTime = sdf.format(new Date());
			Date date = sdf.parse(formatTime);
			if(customer.getCustomerId()==null){
				customer.setRegisterDate(date);
				//获得IP地址
				String ip = request.getHeader("x-forwarded-for");
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("Proxy-Client-IP");
				}
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getHeader("WL-Proxy-Client-IP");
				}
				if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
					ip = request.getRemoteAddr();
				}
				customer.setRegisterIp(ip);
			}else{
				if(customer.getLockedState().intValue()==1){
					customer.setLockedDate(date);
				}
			}
			if(customer.getCustomerId()==null){
				Utils ut = new Utils();
				String password = customer.getPassword();
				String encodeMd5 = ut.EncodeMd5(password);
				String encodeMd52 = ut.EncodeMd5(encodeMd5);
				customer.setPassword(encodeMd52);
			}
			customerService.saveOrUpdateObject(customer);
		}
	}
	//会员基本信息列表
	public void listCustomer() throws IOException{
		//hql查询语句
		String hql="SELECT a.loginName as loginName,a.customerId as customerId,b.trueName as trueName , b.sex as sex ,a.type as type ,a.email as email ,a.registerDate as registerDate,a.registerIp as registerIp,a.lockedState as lockedState FROM Customer a,ShopCustomerInfo b where a.customerId=b.customerId"; 
		String countHql="SELECT count(a.loginName) FROM Customer a,ShopCustomerInfo b where a.customerId=b.customerId"; 
		//获取前台查询参数
		String loginName = request.getParameter("loginName");
		String registerDate = request.getParameter("registerDate");
		String email = request.getParameter("email");
		String lockedState = request.getParameter("lockedState");
		//追加where条件
		if(loginName!=null&&!"".equals(loginName)){
			hql+=" and a.loginName like '%"+loginName+"%'";
			countHql+=" and a.loginName like '%"+loginName+"%'";
		}
		if(registerDate!=null&&!"".equals(registerDate)){
			hql+=" and a.registerDate like '%"+registerDate+"%'";
			countHql+=" and a.registerDate like '%"+registerDate+"%'";
		}
		if(email!=null&&!"".equals(email)){
			hql+=" and a.email like '%"+email+"%'";
			countHql+=" and a.email like '%"+email+"%'";
		}
		if(lockedState!=null&&!"-1".equals(lockedState)){
			hql+=" and a.lockedState="+lockedState;
			countHql+=" and a.lockedState="+lockedState;
		}
		int totalRecordCount = customerService.getMultilistCount(countHql);
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		List<Map> customerList = customerService.findListMapPage(hql+" order by a.registerDate desc", pageHelper);
		if(customerList!=null&&customerList.size()>0){
			for(Map m:customerList){
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time = fm.format(m.get("registerDate"));
				m.put("registerDate", time);
			}
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", customerList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//得到Customer对象
	public void getCustomerObject() throws IOException{
		if(customerId != null){
			customer = (Customer)customerService.getObjectByParams(" where o.customerId='"+customerId+"'");
			shopCustomerInfo = (ShopCustomerInfo)shopCustomerInfoService.getObjectByParams(" where o.customerId='"+customerId+"'");
			Map<String,Object> m=new HashMap<String ,Object>();
			m.put("customer", customer);
			if(customer.getCustomerId() != null&&Utils.objectIsNotEmpty(shopCustomerInfo)){
				if(shopCustomerInfo.getSex()==null){
					shopCustomerInfo.setSex(3);
				}
				m.put("shopCustomerInfo", shopCustomerInfo);
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
			JSONObject jo = JSONObject.fromObject(m,jsonConfig);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(jo.toString());
			out.flush();
			out.close();
		}
	}
	//删除个性信息
	public void deleteSpCustomer(){
		if(customerId != null && !"".equals(customerId)){
			ShopCustomerInfo shopCustomerInfo = (ShopCustomerInfo) shopCustomerInfoService.getObjectByParams(" where o.customerId="+customerId);
			shopCustomerInfoService.deleteObjectByParams(" where o.shopCustomerInfoId="+shopCustomerInfo.getShopCustomerInfoId());
		}
	}
	//查询用户个性信息
	public void getSpCustomerObject() throws IOException{
		if(customerId != null && !"".equals(customerId)){
			shopCustomerInfo = (ShopCustomerInfo)shopCustomerInfoService.getObjectByParams(" where o.customerId='"+customerId+"'");
			if(shopCustomerInfo != null && !"".equals(customerId)){
				JSONObject jo = JSONObject.fromObject(shopCustomerInfo);
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				out.print(jo.toString());
				out.flush();
				out.close();
			}
		}
	}
	//保存用户个性信息
	public void saveOrUpdateShopCustomer() throws IOException{
		if(shopCustomerInfo != null){
			if("".equals(shopCustomerInfo.getShopCustomerInfoId())){
				//shopCustomerInfo.setPreDeposit(0.0);
			}
			shopCustomerInfoService.saveOrUpdateObject(shopCustomerInfo);
		}
	}
	//删除会员信息并把个性信息删除
	public void deleteCustomerAll() throws IOException {
		Boolean isSuccess=null;
		if(ids != null && !"".equals(ids)){
			 isSuccess = customerService.deleteObjectsByIds("customerId", ids);
			shopCustomerInfoService.deleteObjectsByIds("customerId", ids);
		}
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//修改用户冻结状态
	public void changeLockedState()throws IOException{
		String value = request.getParameter("params");
		String customerIds = request.getParameter("customerIds");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String format = df.format(new Date());
		String sql="update basic_customer set lockedState="+value+",lockedDate='"+format+"' where customerId in ("+customerIds+")";
		boolean updateObject = customerService.updateObject(sql);
		JSONObject jo = new JSONObject();
		if(updateObject){
			jo.accumulate("isSuccess", true);
		}else{
			jo.accumulate("isSuccess", false);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public List<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public File getImagePath() {
		return imagePath;
	}
	public void setImagePath(File imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePathFileName() {
		return imagePathFileName;
	}
	public void setImagePathFileName(String imagePathFileName) {
		this.imagePathFileName = imagePathFileName;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public ShopCustomerInfo getShopCustomerInfo() {
		return shopCustomerInfo;
	}
	public void setShopCustomerInfo(ShopCustomerInfo shopCustomerInfo) {
		this.shopCustomerInfo = shopCustomerInfo;
	}
	public void setShopCustomerInfoService(
			IShopCustomerInfoService shopCustomerInfoService) {
		this.shopCustomerInfoService = shopCustomerInfoService;
	}
}
