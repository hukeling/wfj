package shop.customer.action;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import shop.customer.pojo.Customer;
import shop.customer.pojo.SafetyCertificate;
import shop.customer.service.ICustomerService;
import shop.customer.service.ISafetyCertificateService;
import util.action.BaseAction;
import util.other.CreateWhereSQLForSelect;
import util.other.Utils;
/**
 * 安全认证Action
 * @author ss
 *
 */
@SuppressWarnings("serial")
public class SafetyCertificateAction extends BaseAction{
	private ISafetyCertificateService safetyCertificateService;//安全认证Service
	private ICustomerService customerService;//用户Service
	private List<SafetyCertificate> safetyCertificateList = new ArrayList<SafetyCertificate>();//安全认证List
	private List<Customer> customerList = new ArrayList<Customer>();//用户List
	private SafetyCertificate safetyCertificate;
	private String ids;
	private String safetyCertificateId;
	private String params;//查询条件
	//跳转到安全认证页面
	public String gotoSafetyCertificatePage(){
		//查询用户列表
		customerList = customerService.findObjects(null);
		return SUCCESS;
	}
	//根据用户ID得到用户安全认证信息
	public void listSafetyCertificate() throws Exception {
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			if(!"-1".equals(request.getParameter("customerId"))){
				StringBuffer sb=CreateWhereSQLForSelect.appendEqual("customerId",request.getParameter("customerId"));
				hqlsb=CreateWhereSQLForSelect.createSQL(sb);
			}
		}
		hqlsb.append(CreateWhereSQLForSelect.appendOrderBy(" customerId desc"));
		int totalRecordCount = safetyCertificateService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		safetyCertificateList = safetyCertificateService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", safetyCertificateList);// rows键 存放每页记录 list
		JSONObject jo = JSONObject.fromObject(jsonMap);// 格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//删除用户安全认证
	public void deleteSafetyCertificate() throws Exception {
		Boolean isSuccess = safetyCertificateService.deleteObjectsByIds("safetyCertificateId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//得到一条用户安全认证信息
	public void getSafetyCertificateObject() throws Exception {
		safetyCertificate = (SafetyCertificate) safetyCertificateService.getObjectById(safetyCertificateId);
		JSONObject jo = JSONObject.fromObject(safetyCertificate);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改用户留言
	@SuppressWarnings("static-access")
	public void saveOrUpdateSafetyCertificate() throws Exception {
		if(safetyCertificate!=null){
			if(safetyCertificate.getSecurityQuestion()!=null && !"".equals(safetyCertificate.getSecurityQuestion())){
				String[] split = safetyCertificate.getSecurityQuestion().split(":");
				Utils ut = new Utils();
				String encodeMd5 = ut.EncodeMd5(split[1]);
				String encodeMd52 = ut.EncodeMd5(encodeMd5);
				String securityQuestion = split[0]+":"+encodeMd52;
				safetyCertificate.setSecurityQuestion(securityQuestion);
			}
			safetyCertificate=(SafetyCertificate)safetyCertificateService.saveOrUpdateObject(safetyCertificate);
		}
		if(safetyCertificate.getSafetyCertificateId()!=null){
			JSONObject jo = new JSONObject();
			jo.accumulate("isSuccess", "true");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(jo.toString());
			out.flush();
			out.close();
		}
	}
	public List<SafetyCertificate> getSafetyCertificateList() {
		return safetyCertificateList;
	}
	public void setSafetyCertificateList(
			List<SafetyCertificate> safetyCertificateList) {
		this.safetyCertificateList = safetyCertificateList;
	}
	public List<Customer> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	public SafetyCertificate getSafetyCertificate() {
		return safetyCertificate;
	}
	public void setSafetyCertificate(SafetyCertificate safetyCertificate) {
		this.safetyCertificate = safetyCertificate;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getSafetyCertificateId() {
		return safetyCertificateId;
	}
	public void setSafetyCertificateId(String safetyCertificateId) {
		this.safetyCertificateId = safetyCertificateId;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public void setSafetyCertificateService(
			ISafetyCertificateService safetyCertificateService) {
		this.safetyCertificateService = safetyCertificateService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
}
