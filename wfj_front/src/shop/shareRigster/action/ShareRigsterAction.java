package shop.shareRigster.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import shop.customer.pojo.Customer;
import shop.customer.service.ICustomerService;
import shop.shareRigster.pojo.ShareRigster;
import shop.shareRigster.service.IShareRigsterService;
import util.action.BaseAction;
/**
 * 分享注册Action
 * @author lmh
 * **/
@SuppressWarnings("serial")
public class ShareRigsterAction extends BaseAction{
	/**
	 * 分享注册Service
	 * **/
	private IShareRigsterService shareRigsterService;
	/**
	 * 会员Service
	 * **/
	private ICustomerService customerService;	
	/**
	 * 分享注册实体类
	 * **/
	private ShareRigster shareRigster;
	/**
	 * 分享注册集合
	 * **/
	private List<Map<String, Object>> shareRigsterList;
	/**
	 * 集合类
	 */
	private List<Map> mapList;
	/**导出excel文件的列名称**/
	private List<String> typeNameList = new ArrayList<String>();
	
	//跳转到分享注册列表页面
	public String gotoShareRigsterPage(){
		return SUCCESS;
	}
	//查询所有后台分享注册信息
	public void listShareRigster() throws Exception{
		String selectFlag=request.getParameter("selectFlag");
		String sb = new String();
		String sb2 = new String();
		String registerDate = request.getParameter("registerDate");
		String registerDate2 = request.getParameter("registerDate2");
		Integer totalRecordCount=0;
		// sql查询语句
		 sb ="SELECT c.loginName as shareCustomerId ,d.loginName as registerCustomerId ,c.giveCoinNumber as giveCoinNumber ,c.shareTime as shareTime,c.shareRegisterId as shareRegisterId " +
				"FROM (SELECT b.loginName as loginName,a.registerCustomerId as registerCustomerId ,a.shareTime as shareTime ,a.giveCoinNumber as giveCoinNumber,a.shareRegisterId as shareRegisterId " +
				"FROM shop_sharerigster a LEFT JOIN basic_customer b on a.shareCustomerId=b.customerId) c LEFT JOIN basic_customer d ON d.customerId=c.registerCustomerId where 1=1";
		 sb2 ="SELECT  count(c.shareRegisterId) as count  " +
					"FROM (SELECT b.loginName as loginName,a.registerCustomerId as registerCustomerId ,a.shareTime as shareTime ,a.giveCoinNumber as giveCoinNumber,a.shareRegisterId as shareRegisterId " +
					"FROM shop_sharerigster a LEFT JOIN basic_customer b on a.shareCustomerId=b.customerId) c LEFT JOIN basic_customer d ON d.customerId=c.registerCustomerId where 1=1";
		String shareCustomerName = request.getParameter("shareCustomerName");
		String rigsterCustomerName = request.getParameter("registerCustomerName");
		if(shareCustomerName!=null&&!"".equals(shareCustomerName.trim())){
			sb+=" and d.loginName like '%"+shareCustomerName+"%'";
			sb2+=" and d.loginName like '%"+shareCustomerName+"%'";
		}
		if(rigsterCustomerName!=null&&!"".equals(rigsterCustomerName)){
			sb+=" and c.loginName like '%"+rigsterCustomerName+"%'";
			sb2+=" and c.loginName like '%"+rigsterCustomerName+"%'";
		}
		if(registerDate!=null&&!"".equals(registerDate.trim())){
			sb += " and UNIX_TIMESTAMP(c.shareTime) >= UNIX_TIMESTAMP('"+registerDate.trim()+" 00:00:00')";
			sb2 += " and UNIX_TIMESTAMP(c.shareTime) >= UNIX_TIMESTAMP('"+registerDate.trim()+" 00:00:00')";
		}
		if(registerDate2!=null&&!"".equals(registerDate2.trim())){
			sb += " and UNIX_TIMESTAMP(c.shareTime) <= UNIX_TIMESTAMP('"+registerDate2.trim()+" 23:59:59')";
			sb2 += " and UNIX_TIMESTAMP(c.shareTime) <= UNIX_TIMESTAMP('"+registerDate2.trim()+" 23:59:59')";
		}
		List<Map<String, Object>> listMap = shareRigsterService.findListMapBySql(sb2);
		if(listMap!=null&&listMap.size()>0){
			Map<String, Object> map = listMap.get(0);
			Object object = map.get("count");
			if(object!=null){
				totalRecordCount = Integer.parseInt(String.valueOf(object));
			}
		}
		pageHelper.setPageInfo(pageSize, totalRecordCount, currentPage);
		shareRigsterList = shareRigsterService.findListMapPageBySql(sb, pageHelper);
		for(Map<String, Object> map:shareRigsterList){
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=format.format(map.get("shareTime"));
			map.put("shareTime",time);
		}
		Map<String, Object> jsonMap = new HashMap<String, Object>();// 定义map
		jsonMap.put("total", totalRecordCount);// total键 存放总记录数，必须的
		jsonMap.put("rows", shareRigsterList);// rows键 存放每页记录 list
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject jo = JSONObject.fromObject(jsonMap, jsonConfig);//格式化result
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println(jo.toString());
		out.flush();
		out.close();
	}
	/**
	 * 分享记录页面
	 */
	public String gotoShareRegisterDetail(){
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		String loginName = request.getParameter("loginName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		request.setAttribute("loginName", loginName);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		String where ="";
		if(loginName!=null&&!"".equals(loginName.trim())){
			where +=" and c.loginName like '%"+loginName.trim()+"%'";
		}
		if(startTime!=null&&!"".equals(startTime.trim())){
			where += " and UNIX_TIMESTAMP(s.shareTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
		}
		if(endTime!=null&&!"".equals(endTime.trim())){
			where += " and UNIX_TIMESTAMP(s.shareTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
		}
		String hql1 = " select count(s.shareRegisterId) from ShareRigster s,Customer c where c.customerId=s.registerCustomerId and s.shareCustomerId = "+customer.getCustomerId();
		int total = shareRigsterService.getCountByHQL(hql1+where);
		pageHelper.setPageInfo(pageSize, total, currentPage);
		String hql2 = " select c.loginName as loginName,s.shareTime as shareTime,s.giveCoinNumber as giveCoinNumber from ShareRigster s,Customer c where c.customerId=s.registerCustomerId and s.shareCustomerId = "+customer.getCustomerId();
		mapList = shareRigsterService.findListMapPage(hql2+where+" order by s.shareTime desc ", pageHelper);
		//格式化日期
		if(mapList!=null&&mapList.size()>0){
			for(Map map:mapList){
				map.put("shareTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)map.get("shareTime"))).toString());
			}
		}
		return SUCCESS;
	}
	
	/**excel列名**/
	private List<String> ordersColumnNamesList(){
		typeNameList.add(0, "分享会员");
		typeNameList.add("赠送金币数");
		typeNameList.add("分享时间");
		return typeNameList;
	}
	/**
	 * 导出分享记录EXCEL
	 */
	public String exportShareRegisterExcel() throws IOException{
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		String loginName = request.getParameter("loginName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		request.setAttribute("loginName", loginName);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		String where ="";
		if(loginName!=null&&!"".equals(loginName.trim())){
			where +=" and c.loginName like '%"+loginName.trim()+"%'";
		}
		if(startTime!=null&&!"".equals(startTime.trim())){
			where += " and UNIX_TIMESTAMP(s.shareTime) >= UNIX_TIMESTAMP('"+startTime.trim()+" 00:00:00')";
		}
		if(endTime!=null&&!"".equals(endTime.trim())){
			where += " and UNIX_TIMESTAMP(s.shareTime) <= UNIX_TIMESTAMP('"+endTime.trim()+" 23:59:59')";
		}
		String hql1 = " select count(s.shareRegisterId) from ShareRigster s,Customer c where c.customerId=s.registerCustomerId and s.shareCustomerId = "+customer.getCustomerId();
		int total = shareRigsterService.getCountByHQL(hql1+where);
		pageHelper.setPageInfo(pageSize, total, currentPage);
		String hql2 = " select c.loginName as loginName,s.shareTime as shareTime,s.giveCoinNumber as giveCoinNumber from ShareRigster s,Customer c where c.customerId=s.registerCustomerId and s.shareCustomerId = "+customer.getCustomerId();
		mapList = shareRigsterService.findListMapByHql(hql2+where+" order by s.shareTime desc ");
		if(mapList!=null&&mapList.size()>0){
			//格式化日期
			for(Map map:mapList){
				map.put("shareTime", (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)map.get("shareTime"))).toString());
			}
			session.setAttribute("columnNames", ordersColumnNamesList());//把所需要传递的columnNames列名集合放在session中。
			session.setAttribute("columnValues", ordersColumnValuesList(mapList));//把所需要传递的columnValues列名对应的值集合放在session中。
			session.setAttribute("moduleName", "ShareRegister");
			return "success";
		}else {
			this.addActionError("没有数据");
			return "error";
		}
	}
	/**excel对应列的值**/
	public List<List<String>> ordersColumnValuesList(List<Map> list) throws IOException{
		//保存二维表样式数据 List <List<String>>
		List<List<String>> columnRowsList = new ArrayList<List<String>>();
		List<String> columnValuesList = null;
		for(Map<String,Object> cc : list){
			columnValuesList = new ArrayList<String>();
			columnValuesList.add(String.valueOf(cc.get("loginName")));
			columnValuesList.add(String.valueOf(cc.get("giveCoinNumber")));
			columnValuesList.add(String.valueOf(cc.get("shareTime")));
			columnRowsList.add(columnValuesList);//把当前的行 添加到 列表中			
		}		
		return columnRowsList;
	}
	public ShareRigster getShareRigster() {
		return shareRigster;
	}
	public void setShareRigster(ShareRigster shareRigster) {
		this.shareRigster = shareRigster;
	}

	public List<Map<String, Object>> getShareRigsterList() {
		return shareRigsterList;
	}
	public void setShareRigsterList(List<Map<String, Object>> shareRigsterList) {
		this.shareRigsterList = shareRigsterList;
	}
	public void setShareRigsterService(IShareRigsterService shareRigsterService) {
		this.shareRigsterService = shareRigsterService;
	}
	public void setCustomerService(ICustomerService customerService) {
		this.customerService = customerService;
	}
	public List<Map> getMapList() {
		return mapList;
	}
	public void setMapList(List<Map> mapList) {
		this.mapList = mapList;
	}
	public List<String> getTypeNameList() {
		return typeNameList;
	}
	public void setTypeNameList(List<String> typeNameList) {
		this.typeNameList = typeNameList;
	}

}
