package shop.front.shoppingOnLine.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.service.ICustomerAcceptAddressService;
import util.action.BaseAction;
/**
 * CustomerAddress - 前台填写地址Action类
 * @author 孟琦瑞
 */
@SuppressWarnings("serial")
public class CustomerAddressAction extends BaseAction{
	private ICustomerAcceptAddressService customerAcceptAddressService;//收货地址service
	private List<CustomerAcceptAddress> customerAcceptAddressList = new ArrayList<CustomerAcceptAddress>();//用户地址List
	private CustomerAcceptAddress customerAcceptAddress=new CustomerAcceptAddress();
	private String radioName;//单选钮
	private String id;
	/**客户收货地址**/
	private String addressId;
	/**------------------------------------------------------------------------**/
	public String gotoCustomerAddressPage(){
		//获得登录会员的收获地址
		//测试取会员ID：1
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		customerAcceptAddressList=customerAcceptAddressService.findObjects(" where o.customerId="+customer.getCustomerId()+" order by isSendAddress desc,customerAcceptAddressId");
		return SUCCESS;
	}
	//保存新增地址
	public void saveNewAdd() throws Exception{
		Customer customer = (Customer) session.getAttribute("customer");
		if(null!=customer){
			Integer customerId = customer.getCustomerId();
			if(customerAcceptAddress!=null){
				String checkbox = request.getParameter("checkbox");
				if(checkbox!=null){
					int checkValue = Integer.parseInt(checkbox);
					customerAcceptAddress.setIsSendAddress(checkValue);
					customerAcceptAddress.setCustomerId(customerId);
					//得到默认选中的对象并修改为普通地址
					CustomerAcceptAddress ca=(CustomerAcceptAddress)customerAcceptAddressService.getObjectByParams(" where o.customerId="+customerId+"and o.isSendAddress="+checkValue);
					if(null!=ca){
						ca.setIsSendAddress(0);
						customerAcceptAddressService.saveOrUpdateObject(ca);
					}
				}else{
					customerAcceptAddress.setIsSendAddress(0);
					customerAcceptAddress.setCustomerId(customerId);
				}
				customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService.saveOrUpdateObject(customerAcceptAddress);
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
		}
	}
	//设为默认地址操作
	public void setDefAddress() throws Exception{
		if(radioName!=null){
			int customerAcceptAddressId = Integer.parseInt(radioName);
			//得到要修改的对象
			CustomerAcceptAddress caas =(CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams(" where o.customerAcceptAddressId="+customerAcceptAddressId);
			//将其IsSendAddress值修改为1
			caas.setIsSendAddress(1);
			//得到原来默认地址对象
			CustomerAcceptAddress caas2 =(CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams(" where o.customerId="+caas.getCustomerId()+"and o.isSendAddress=1");
			if(null!=caas2){
				//将其IsSendAddress值修改为0
				caas2.setIsSendAddress(0);
				//保存两个对象
				customerAcceptAddressService.saveOrUpdateObject(caas2);
			}
			CustomerAcceptAddress customerAcceptAddress =(CustomerAcceptAddress) customerAcceptAddressService.saveOrUpdateObject(caas);
			JSONObject jo = JSONObject.fromObject(customerAcceptAddress);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	public void deleteAddress() throws IOException{
		Boolean bool=customerAcceptAddressService.deleteObjectsByIds("customerAcceptAddressId",addressId);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", bool);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//通过ID获得obj对象
	public void findObjById() throws IOException{
		if(id!="-1"&&!id.equals("-1")){
			customerAcceptAddress =(CustomerAcceptAddress) customerAcceptAddressService.getObjectById(id);
			JSONObject jo = JSONObject.fromObject(customerAcceptAddress);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	/**
	 * setter getter 
	 * @param customerAcceptAddressService
	 */
	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
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
	public void setCustomerAcceptAddress(CustomerAcceptAddress customerAcceptAddress) {
		this.customerAcceptAddress = customerAcceptAddress;
	}
	public String getRadioName() {
		return radioName;
	}
	public void setRadioName(String radioName) {
		this.radioName = radioName;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
