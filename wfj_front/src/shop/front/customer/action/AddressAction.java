package shop.front.customer.action;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerAcceptAddress;
import shop.customer.service.ICustomerAcceptAddressService;
import util.action.BaseAction;
import basic.service.IAreaService;
/**
 * 收货地址管理
 * @author ss
 *
 */
public class AddressAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	/**用户指定收货地址id(删除时使用)**/
	private Integer CAAId;
	/**登录用户**/
	private Customer customer;
	/**收货地址**/
	private CustomerAcceptAddress customerAcceptAddress;
	/**查找区域id**/
	private Integer areaId;
	/**用户收货地址集合**/
	private List<Map> listAddress;
	/**默认地址标识**/
	private Integer checkbox;
	/**区域service**/
	private IAreaService areaService;
	/**客户收货地址Service**/
	private ICustomerAcceptAddressService customerAcceptAddressService;
	/**
	 * 根据areaId 查询下级区域
	 * @throws IOException
	 */
	public void findAreaList() throws IOException{
		String firstHql="select a.name as name,a.areaId as aid,a.parentId as parentId from BasicArea a where a.parentId="+areaId+" order by a.areaId";
    	List<Map> areaList=areaService.findListMapByHql(firstHql);
    	JSONArray jo = JSONArray.fromObject(areaList);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		pw = response.getWriter();
		pw.write(String.valueOf(jo));
		pw.flush();
		pw.close();
	}
	/**
	 * 保存或者修改地址
	 * @throws UnsupportedEncodingException 
	 */
	public String saveOrUpdateAddress() throws Exception{
		customer=(Customer) session.getAttribute("customer");
		if(customer!=null){
			customerAcceptAddress.setCustomerId(customer.getCustomerId());
			if(customerAcceptAddress != null && customerAcceptAddress.getIsSendAddress()==1){
				customerAcceptAddressService.updateIsSendAddress(customer);
			}
			String address = customerAcceptAddress.getAddress();
			String consignee = customerAcceptAddress.getConsignee();
			customerAcceptAddress.setAddress(address);
			customerAcceptAddress.setConsignee(consignee);
			customerAcceptAddress.setAddress(customerAcceptAddress.getAddress());
			customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService.saveOrUpdateObject(customerAcceptAddress);
		}
		return SUCCESS;
	}
	/**
	 * 异步删除收货地址
	 * @throws Exception 
	 */
	public void deleteAddress() throws Exception{
		Customer customer = (Customer) session.getAttribute("customer");
		boolean bool = false;
		if(CAAId!=null){
			bool = customerAcceptAddressService.deleteObjectByParams("where o.customerAcceptAddressId="+CAAId+" and o.customerId="+customer.getCustomerId());
		}
		JSONObject jo = JSONObject.fromObject(bool);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		pw = response.getWriter();
		pw.write(String.valueOf(jo));
		pw.flush();
		pw.close();
	}
	/**
	 * 编辑当前地址
	 * @throws Exception 
	 */
	public void getAddress() throws Exception{
		Customer customer = (Customer) session.getAttribute("customer");
		customerAcceptAddress = (CustomerAcceptAddress) customerAcceptAddressService.getObjectByParams("where o.customerAcceptAddressId="+CAAId+" and o.customerId="+customer.getCustomerId());
//		String firstHql="select a.name as name,a.aid as aid,a.parentId as parent from BasicArea a where a.parentId=0 order by a.aid";
//    	firstAreaList=areaService.findListMapByHql(firstHql);
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("customerAcceptAddress", customerAcceptAddress);
//    	map.put("firstAreaList", firstAreaList);
    	JSONObject ja = JSONObject.fromObject(map);
    	response.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		pw = response.getWriter();
		pw.write(String.valueOf(ja));
		pw.flush();
		pw.close();
	}
	/**
	 * 点击编辑地址按钮时获取当前登录人的地址集合
	 * @throws IOException 
	 */
	public void findAddress() throws IOException{
		//用户收货地址
		customer = (Customer) session.getAttribute("customer");
    	String addressHql = "select a.customerAcceptAddressId as CAAId,a.consignee as consignee,a.address as address,a.mobilePhone as mobilePhone ,a.isSendAddress as isSendAddress  from CustomerAcceptAddress a where a.customerId="+customer.getCustomerId();
    	listAddress = customerAcceptAddressService.findListMapByHql(addressHql);
    	JSONArray ja = JSONArray.fromObject(listAddress);
    	response.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		pw = response.getWriter();
		pw.write(String.valueOf(ja));
		pw.flush();
		pw.close();
	}
	/**
	 * 设置默认地址
	 * @throws IOException 
	 */
	public void setDefAddress() throws Exception{
		//用户收货地址
		customer = (Customer) session.getAttribute("customer");
		customerAcceptAddress = customerAcceptAddressService.saveOrUpdateSendAddress(customer, CAAId);
		JSONObject ja = JSONObject.fromObject(customerAcceptAddress);		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw;
		pw = response.getWriter();
		pw.write(String.valueOf(ja));
		pw.flush();
		pw.close();
	}
	public Integer getCAAId() {
		return CAAId;
	}
	public void setCAAId(Integer cAAId) {
		CAAId = cAAId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public CustomerAcceptAddress getCustomerAcceptAddress() {
		return customerAcceptAddress;
	}
	public void setCustomerAcceptAddress(CustomerAcceptAddress customerAcceptAddress) {
		this.customerAcceptAddress = customerAcceptAddress;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public List<Map> getListAddress() {
		return listAddress;
	}
	public void setListAddress(List<Map> listAddress) {
		this.listAddress = listAddress;
	}
	public void setAreaService(IAreaService areaService) {
		this.areaService = areaService;
	}
	public void setCustomerAcceptAddressService(
			ICustomerAcceptAddressService customerAcceptAddressService) {
		this.customerAcceptAddressService = customerAcceptAddressService;
	}
	public Integer getCheckbox() {
		return checkbox;
	}
	public void setCheckbox(Integer checkbox) {
		this.checkbox = checkbox;
	}
}
