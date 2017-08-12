package shop.customer.action;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.customer.pojo.Customer;
import shop.customer.pojo.CustomerMsg;
import shop.customer.service.ICustomerMsgService;
import shop.customer.service.ICustomerService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.JSONFormatDate;
/**
 * 用户留言Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class CustomerMsgAction extends BaseAction {
	private ICustomerMsgService customerMsgService;//用户留言Service
	private ICustomerService customerService;//用户Service
	private CustomerMsg customerMsg;
	private List<CustomerMsg> customerMsgList = new ArrayList<CustomerMsg>();//用户留言List
	private List<Customer> customerList = new ArrayList<Customer>();
	private String ids;
	private String customerMsgId;//用户留言ID
	private String customerId;//用户ID
	private String params;//查询条件
	//根据用户ID查询客户留言页面
	public String gotoCustomerMsgPage(){
		//查询用户
		customerList = customerService.findObjects(null);
		request.setAttribute("customerId",customerId);
		return SUCCESS;
	}
	//保存或者修改用户留言
	public void saveOrUpdateCustomerMsg() throws Exception {
		if(customerMsg!=null){
			if("".equals(customerMsg.getBackMsg()) || customerMsg.getBackMsg()==null){
				customerMsg.setState(0);
			}else{
				customerMsg.setState(1);
			}
			if(customerMsg.getCustomerMsgId()==null || "".equals(customerMsg.getCustomerMsgId())){
				SimpleDateFormat sdf = new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String formatTime = sdf.format(new Date());
				Date date = sdf.parse(formatTime);
				customerMsg.setCreateTime(date);
			}
			customerMsg=(CustomerMsg)customerMsgService.saveOrUpdateObject(customerMsg);
		}
		if(customerMsg.getCustomerMsgId()!=null){
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	//得到一条用户留言信息
	public void getCustomerMsgObject() throws Exception {
		customerMsg = (CustomerMsg) customerMsgService.getObjectById(customerMsgId);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(customerMsg,jsonConfig);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除用户留言
	public void deleteCustomerMsg() throws Exception {
		Boolean isSuccess = customerMsgService.deleteObjectsByIds("customerMsgId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//根据用户ID得到用户留言List
	public void listCustomerMsg() throws Exception {
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			if(!"-1".equals(request.getParameter("customerId"))){
				StringBuffer sb=CreateWhereSQLForSelect.appendEqual("customerId",request.getParameter("customerId"));
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" customerId desc"));
		int totalRecordCount = customerMsgService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		customerMsgList = customerMsgService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", customerMsgList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JSONFormatDate("yyyy-MM-dd HH:mm:ss"));
		JSONObject jo = JSONObject.fromObject(jsonMap,jsonConfig);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	public CustomerMsg getCustomerMsg() {
		return customerMsg;
	}
	public void setCustomerMsg(CustomerMsg customerMsg) {
		this.customerMsg = customerMsg;
	}
	public List<CustomerMsg> getCustomerMsgList() {
		return customerMsgList;
	}
	public void setCustomerMsgList(List<CustomerMsg> customerMsgList) {
		this.customerMsgList = customerMsgList;
	}
	public List<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getCustomerMsgId() {
		return customerMsgId;
	}
	public void setCustomerMsgId(String customerMsgId) {
		this.customerMsgId = customerMsgId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public void setCustomerMsgService(ICustomerMsgService customerMsgService) {
		this.customerMsgService = customerMsgService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
