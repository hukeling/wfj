package shop.customer.action;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.service.ICustomerAcceptAddressService;
import util.action.BaseAction;
import util.other.Utils;
import basic.pojo.BasicArea;
import basic.service.IAreaService;
@SuppressWarnings("serial")
public class CustomerAcceptAddressAction extends BaseAction {
	private ICustomerAcceptAddressService customerAcceptAddressService;
	private IAreaService areaService;
	private CustomerAcceptAddress customerAcceptAddress;
	private List<CustomerAcceptAddress> customerAcceptAddressList = new ArrayList<CustomerAcceptAddress>();
	private String ids;
	private String customerAcceptAddressId;
	private String customerId;
	//根据用户ID跳转到此用户的地址列表页面
	public String findAcceptAddressListByCustomerId(){
		request.setAttribute("customerId", customerId);
		session.setAttribute("customerId", customerId);
		session.setMaxInactiveInterval(7*24*3600);
		return SUCCESS;
	}
	//保存或者修改用户地址
	public void saveOrUpdateCustomerAcceptAddress() throws Exception {
		if(customerAcceptAddress!=null){
			customerAcceptAddress=(CustomerAcceptAddress)customerAcceptAddressService.saveOrUpdateObject(customerAcceptAddress);
		}
		if(customerAcceptAddress.getCustomerAcceptAddressId()!=null){
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//根据用户ID得到用户地址一条信息
	public void getCustomerAcceptAddressObject() throws Exception {
		customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService.getObjectById(customerAcceptAddressId);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("customerAcceptAddress", customerAcceptAddress);
		BasicArea areaCounty = (BasicArea) areaService.getObjectByParams(" where o.areaId="+customerAcceptAddress.getAreaCounty());
		BasicArea city = (BasicArea) areaService.getObjectByParams(" where o.areaId="+customerAcceptAddress.getCity());
		BasicArea regionLocation = (BasicArea) areaService.getObjectByParams(" where o.areaId="+customerAcceptAddress.getRegionLocation());
		String areaCounty2="-";
		String city2="-";
		String regionLocation2="-";
		if(areaCounty!=null){
			areaCounty2=areaCounty.getName();
		}
		if(city!=null){
			city2=city.getName();
		}
		if(regionLocation!=null){
			regionLocation2=regionLocation.getName();
		}
		map.put("areaCounty", areaCounty2);
		map.put("city", city2);
		map.put("regionLocation", regionLocation2);
		JSONObject jo = JSONObject.fromObject(map);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除用户地址
	public void deleteCustomerAcceptAddress() throws Exception {
		Boolean isSuccess = customerAcceptAddressService.deleteObjectsByIds("customerAcceptAddressId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//根据用户ID得到用户地址List
	public void listCustomerAcceptAddress() throws Exception {
		int totalRecordCount = customerAcceptAddressService.getCount(" where o.customerId='"+customerId+"'");
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		customerAcceptAddressList = customerAcceptAddressService.findListByPageHelper(null,pageHelper, " where o.customerId='"+customerId+"' order by o.customerAcceptAddressId desc");
		if(customerAcceptAddressList!=null&&customerAcceptAddressList.size()>0){
			for(CustomerAcceptAddress ca:customerAcceptAddressList){
				if(Utils.stringIsNotEmpty(ca.getAreaCounty())){
					BasicArea areaCounty = (BasicArea) areaService.getObjectByParams(" where o.areaId="+ca.getAreaCounty());
					ca.setAreaCounty(areaCounty.getName());
				}else{
					ca.setAreaCounty("-");
				}
				if(Utils.stringIsNotEmpty(ca.getCity())){
					BasicArea city = (BasicArea) areaService.getObjectByParams(" where o.areaId="+ca.getCity());
					ca.setCity(city.getName());
				}else{
					ca.setCity("-");
				}
				if(Utils.stringIsNotEmpty(ca.getRegionLocation())){
					BasicArea regionLocation = (BasicArea) areaService.getObjectByParams(" where o.areaId="+ca.getRegionLocation());
					ca.setRegionLocation(regionLocation.getName());
				}else{
					ca.setRegionLocation("-");
				}
			}
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", customerAcceptAddressList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public CustomerAcceptAddress getCustomerAcceptAddress() {
		return customerAcceptAddress;
	}
	public void setCustomerAcceptAddress(CustomerAcceptAddress customerAcceptAddress) {
		this.customerAcceptAddress = customerAcceptAddress;
	}
	public List<CustomerAcceptAddress> getCustomerAcceptAddressList() {
		return customerAcceptAddressList;
	}
	public void setCustomerAcceptAddressList(
			List<CustomerAcceptAddress> customerAcceptAddressList) {
		this.customerAcceptAddressList = customerAcceptAddressList;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getCustomerAcceptAddressId() {
		return customerAcceptAddressId;
	}
	public void setCustomerAcceptAddressId(String customerAcceptAddressId) {
		this.customerAcceptAddressId = customerAcceptAddressId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
}
