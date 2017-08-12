package shop.customer.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import shop.customer.pojo.CusService;
import shop.customer.pojo.Customer;
import shop.customer.pojo.ShopCustomerService;
import shop.customer.service.ICustomerServiceService;
import util.action.BaseAction;
import util.other.Utils;

/**
 * 客服信息Action
 * @author wy
 */
@SuppressWarnings("all")
public class CustomerServiceAction extends BaseAction{
	private ICustomerServiceService customerServiceService;
	private List<CusService> customerServiceList;
	private CusService customerService;
	private String ids;
	//设置QQ使用的数量
	private int qqUseCount=0;
	//设置QQ使用的数量上限
	private int qqUseCountMax=0;
	//是否大于QQ使用数量上限
	private String isExceedQQ;
	/**跳转至客服列表页面**/
	public String gotoCustomerServicePage(){
		Customer customer = (Customer)session.getAttribute("customer");
		String str = (String) getFileUrlConfig().get("QQuseCount");
		qqUseCountMax = Integer.valueOf(str);
		List<Map>userCountList = customerServiceService.findListMapByHql("select sc.customerServiceId as customerServiceId from CusService sc,ShopCustomerService ss where sc.customerServiceId=ss.customerServiceId and sc.useState=1 and ss.customerId="+customer.getCustomerId());
		if(Utils.objectIsNotEmpty(userCountList))qqUseCount = userCountList.size();
		if(qqUseCount>=qqUseCountMax){
			isExceedQQ = "true";
		}else{
			isExceedQQ = "false";
		}
		return SUCCESS;
	}
	/**查找客服列表**/
	public void listCustomerService() throws IOException{
		String selectFlag=request.getParameter("selectFlag");
		StringBuffer hqlsb = new StringBuffer();
		hqlsb.append(" where 1=1");
		if("true".equals(selectFlag)){//判断是否点击查询按钮
			String trueName = request.getParameter("trueName");
			String nikeName = request.getParameter("nikeName");
			String qq = request.getParameter("qq");
			String useState = request.getParameter("useState");
			if(StringUtils.isNotEmpty(trueName)){
				trueName = trueName.trim();
				hqlsb.append(" and o.trueName like '%"+trueName+"%'");
			}
			if(StringUtils.isNotEmpty(nikeName)){
				nikeName = nikeName.trim();
				hqlsb.append(" and o.nikeName like '%"+nikeName+"%'");
			}
			if(StringUtils.isNotEmpty(qq)){
				nikeName = nikeName.trim();
				hqlsb.append(" and o.qq like '%"+qq+"%'");
			}
			if(useState!=null&&!"-1".equals(useState.trim())){
				useState = useState.trim();
				hqlsb.append(" and o.useState="+useState);
			}
		}
		hqlsb.append(" order by o.customerServiceId desc");
		int totalRecordCount = customerServiceService.getCount(hqlsb.toString());
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		customerServiceList = customerServiceService.findListByPageHelper(null,pageHelper, hqlsb.toString());
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("total", totalRecordCount);
		jsonMap.put("rows", customerServiceList);
		JSONObject jo = JSONObject.fromObject(jsonMap);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	//保存或者修改
	public String saveOrUpdateCustomerService() throws IOException{
		if(customerService!=null){
			//根据id是否存在去判断是添加还是修改记录，为null去添加
			if(customerService.getCustomerServiceId()==null){//添加
				List<CusService> cusList = customerServiceService.findObjects("order by o.workNumber desc");
				if(cusList==null&&cusList.size()==0){
					customerService.setWorkNumber("01000");
				}else{
					customerService.setWorkNumber("0"+(Integer.valueOf(cusList.get(0).getWorkNumber())+1));
				}
				Customer customer = (Customer)session.getAttribute("customer");
				ShopCustomerService shopCustomerService = new ShopCustomerService();
				shopCustomerService.setCustomerId(customer.getCustomerId());
				customerServiceService.saveOrUpdateCustomerService(customerService,shopCustomerService);
			}else{//修改
				customerServiceService.saveOrUpdateObject(customerService);
			}
		}
		return SUCCESS;
	}
	//获取一条记录
	public String getCustomerServiceInfo() throws IOException{
		Customer customer = (Customer)session.getAttribute("customer");
		String str = (String) getFileUrlConfig().get("QQuseCount");
		qqUseCountMax = Integer.valueOf(str);
		List<Map>userCountList = customerServiceService.findListMapByHql("select sc.customerServiceId as customerServiceId from CusService sc,ShopCustomerService ss where sc.customerServiceId=ss.customerServiceId and sc.useState=1 and ss.customerId="+customer.getCustomerId());
		if(Utils.objectIsNotEmpty(userCountList))qqUseCount = userCountList.size();
		if(qqUseCount>=qqUseCountMax){
			isExceedQQ = "true";
		}else{
			isExceedQQ = "false";
		}
		customerService = (CusService) customerServiceService.getObjectByParams(" where o.customerServiceId='"+ids+"'");
		return SUCCESS;
	}
	//删除记录
	public void deleteCustomerServices() throws IOException{
		Boolean isSuccess = customerServiceService.deleteObjectsByIds("customerServiceId",ids);
		JSONObject jo = new JSONObject();
		jo.accumulate("isSuccess", isSuccess + "");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	
	public void setCustomerServiceService(
			ICustomerServiceService customerServiceService) {
		this.customerServiceService = customerServiceService;
	}
	public List<CusService> getCustomerServiceList() {
		return customerServiceList;
	}
	public void setCustomerServiceList(List<CusService> customerServiceList) {
		this.customerServiceList = customerServiceList;
	}
	public CusService getCustomerService() {
		return customerService;
	}
	public void setCustomerService(CusService customerService) {
		this.customerService = customerService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public int getQqUseCount() {
		return qqUseCount;
	}
	public void setQqUseCount(int qqUseCount) {
		this.qqUseCount = qqUseCount;
	}
	public String getIsExceedQQ() {
		return isExceedQQ;
	}
	public void setIsExceedQQ(String isExceedQQ) {
		this.isExceedQQ = isExceedQQ;
	}
	public int getQqUseCountMax() {
		return qqUseCountMax;
	}
	public void setQqUseCountMax(int qqUseCountMax) {
		this.qqUseCountMax = qqUseCountMax;
	}
}
